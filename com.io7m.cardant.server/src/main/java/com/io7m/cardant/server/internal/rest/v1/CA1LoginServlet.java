/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.cardant.server.internal.rest.v1;

import com.io7m.anethum.common.ParseException;
import com.io7m.anethum.common.SerializeException;
import com.io7m.cardant.database.api.CADatabaseException;
import com.io7m.cardant.database.api.CADatabaseType;
import com.io7m.cardant.model.CAModelDatabaseQueriesType;
import com.io7m.cardant.model.CAUsers;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandLoginUsernamePassword;
import com.io7m.cardant.protocol.inventory.api.CAMessageParserFactoryType;
import com.io7m.cardant.protocol.inventory.api.CAMessageSerializerFactoryType;
import com.io7m.cardant.protocol.inventory.api.CAMessageType;
import com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseError;
import com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseLoginUsernamePassword;
import com.io7m.cardant.server.internal.CAServerMessages;
import com.io7m.cardant.server.internal.rest.CAServerEventType;
import com.io7m.cardant.server.internal.rest.CAServerLoginFailed;
import com.io7m.cardant.server.internal.rest.CAServerLoginSucceeded;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Collectors;

import static com.io7m.cardant.server.internal.rest.CAMediaTypes.applicationCardantXML;

/**
 * A servlet that performs authentication.
 */

public final class CA1LoginServlet extends HttpServlet
{
  private static final Logger LOG =
    LoggerFactory.getLogger(CA1LoginServlet.class);

  private final CAMessageSerializerFactoryType serializers;
  private final CADatabaseType database;
  private final SubmissionPublisher<CAServerEventType> events;
  private final CAMessageParserFactoryType parsers;
  private final CAServerMessages messages;

  /**
   * Construct a servlet.
   *
   * @param inEvents      An event sink
   * @param inParsers     A provider of inventory message parsers
   * @param inSerializers A provider of inventory message serializers
   * @param inDatabase    A database
   * @param inMessages    A provider of server messages
   */

  public CA1LoginServlet(
    final SubmissionPublisher<CAServerEventType> inEvents,
    final CAMessageParserFactoryType inParsers,
    final CAMessageSerializerFactoryType inSerializers,
    final CADatabaseType inDatabase,
    final CAServerMessages inMessages)
  {
    this.events =
      Objects.requireNonNull(inEvents, "inEvents");
    this.parsers =
      Objects.requireNonNull(inParsers, "parsers");
    this.serializers =
      Objects.requireNonNull(inSerializers, "serializers");
    this.database =
      Objects.requireNonNull(inDatabase, "database");
    this.messages =
      Objects.requireNonNull(inMessages, "messages");
  }

  private static URI uriOf(
    final HttpServletRequest request)
  {
    return URI.create(
      String.format(
        "tcp://%s:%d/",
        request.getRemoteAddr(),
        Integer.valueOf(request.getRemotePort()))
    );
  }

  private static String clientOf(
    final HttpServletRequest request)
  {
    return new StringBuilder(64)
      .append('[')
      .append(request.getRemoteAddr())
      .append(':')
      .append(request.getRemotePort())
      .append(']')
      .toString();
  }

  private void sendMessage(
    final HttpServletResponse response,
    final CAMessageType message)
    throws IOException, SerializeException
  {
    response.setContentType(applicationCardantXML());
    try (var output = response.getOutputStream()) {
      this.serializers.serialize(URI.create("urn:output"), output, message);
      output.flush();
    }
  }

  @Override
  protected void service(
    final HttpServletRequest request,
    final HttpServletResponse response)
    throws IOException
  {
    MDC.put("client", clientOf(request));

    try {
      final CAMessageType loginRequest;

      try {
        loginRequest =
          this.parsers.parse(uriOf(request), request.getInputStream());
      } catch (final IOException e) {
        response.setStatus(401);

        final var errorMessage =
          new CAResponseError(
            this.messages.format("errorLoginFailed"),
            401,
            Map.of(),
            List.of(this.formatSimpleException(e))
          );

        this.sendMessage(response, errorMessage);
        this.events.submit(new CAServerLoginFailed());
        return;
      } catch (final ParseException e) {
        response.setStatus(401);

        final var errorDetails =
          e.statusValues()
            .stream()
            .map(this.messages::formatParseStatus)
            .collect(Collectors.toList());

        final var errorMessage =
          new CAResponseError(
            this.messages.format("errorLoginFailed"),
            401,
            Map.of(),
            errorDetails
          );

        this.sendMessage(response, errorMessage);
        this.events.submit(new CAServerLoginFailed());
        return;
      }

      if (loginRequest instanceof CACommandLoginUsernamePassword creds) {
        if (this.checkLogin(creds)) {
          LOG.info("user '{}' logged in", creds.user());
          final var session = request.getSession();
          session.setAttribute("userName", creds.user());
          response.setStatus(200);
          this.sendMessage(response, new CAResponseLoginUsernamePassword());
          this.events.submit(new CAServerLoginSucceeded());
          return;
        }
      }

      LOG.info("authentication failed");
      response.setStatus(401);
      this.sendMessage(
        response,
        new CAResponseError(
          this.messages.format("errorLoginFailed"),
          401,
          Map.of(),
          List.of())
      );
      this.events.submit(new CAServerLoginFailed());
    } catch (final IOException e) {
      LOG.error("i/o: ", e);
      throw e;
    } catch (final CADatabaseException e) {
      LOG.error("database: ", e);
      throw new IOException(e);
    } catch (final GeneralSecurityException e) {
      LOG.error("security: ", e);
      throw new IOException(e);
    } catch (final SerializeException e) {
      LOG.error("serialization: ", e);
      throw new IOException(e);
    } finally {
      MDC.remove("client");
    }
  }

  private String formatSimpleException(
    final Exception e)
  {
    return this.messages.format(
      "errorExceptionSimple",
      e.getClass().getSimpleName(),
      e.getMessage()
    );
  }

  private boolean checkLogin(
    final CACommandLoginUsernamePassword creds)
    throws CADatabaseException, GeneralSecurityException, IOException
  {
    try (var connection = this.database.openConnection()) {
      try (var transaction = connection.beginTransaction()) {
        final var queries =
          transaction.queries(CAModelDatabaseQueriesType.class);

        final var userName = creds.user();
        final var userOpt = queries.userGetByName(userName);
        if (userOpt.isPresent()) {
          return CAUsers.checkUserPassword(userOpt.get(), creds.password());
        }

        LOG.info("no such user '{}'", userName);
        return false;
      }
    }
  }
}
