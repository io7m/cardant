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

import com.io7m.anethum.common.ParseStatus;
import com.io7m.anethum.common.SerializeException;
import com.io7m.cardant.protocol.inventory.api.CAMessageSerializerFactoryType;
import com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseError;
import com.io7m.cardant.server.internal.CAServerMessages;
import com.io7m.cardant.server.internal.rest.CAServerEventType;
import com.io7m.cardant.server.internal.rest.CAServerLoginFailed;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Collectors;

import static com.io7m.cardant.server.internal.rest.CAMediaTypes.applicationCardantXML;

/**
 * The type of servlets that require authentication.
 */

public abstract class CA1AuthenticatedServlet extends HttpServlet
{
  private final CAServerMessages messages;
  private final SubmissionPublisher<CAServerEventType> events;
  private final CAMessageSerializerFactoryType serializers;
  private URI clientURI;
  private HttpServletResponse response;

  protected CA1AuthenticatedServlet(
    final SubmissionPublisher<CAServerEventType> inEvents,
    final CAMessageSerializerFactoryType inSerializers,
    final CAServerMessages inMessages)
  {
    this.events =
      Objects.requireNonNull(inEvents, "inEvents");
    this.serializers =
      Objects.requireNonNull(inSerializers, "serializers");
    this.messages =
      Objects.requireNonNull(inMessages, "messages");
  }

  private static String clientOf(
    final HttpServletRequest servletRequest,
    final String user)
  {
    return new StringBuilder(64)
      .append('[')
      .append(servletRequest.getRemoteAddr())
      .append(':')
      .append(servletRequest.getRemotePort())
      .append(' ')
      .append(user)
      .append(']')
      .toString();
  }

  private static String clientOf(
    final HttpServletRequest servletRequest)
  {
    return new StringBuilder(64)
      .append('[')
      .append(servletRequest.getRemoteAddr())
      .append(':')
      .append(servletRequest.getRemotePort())
      .append(']')
      .toString();
  }

  private static URI makeClientURI(
    final HttpServletRequest servletRequest)
  {
    return URI.create(
      new StringBuilder(64)
        .append("client:")
        .append(servletRequest.getRemoteAddr())
        .append(":")
        .append(servletRequest.getRemotePort())
        .toString()
    );
  }

  private static URI makeClientURI(
    final HttpServletRequest servletRequest,
    final String userName)
  {
    return URI.create(
      new StringBuilder(64)
        .append("client:")
        .append(servletRequest.getRemoteAddr())
        .append(":")
        .append(servletRequest.getRemotePort())
        .append(":")
        .append(userName)
        .toString()
    );
  }

  /**
   * @return A serializer of inventory messages
   */

  public final CAMessageSerializerFactoryType serializers()
  {
    return this.serializers;
  }

  protected abstract Logger logger();

  protected abstract void serviceAuthenticated(
    HttpServletRequest request,
    HttpServletResponse servletResponse,
    HttpSession session)
    throws Exception;

  /**
   * @return The current client URI
   */

  public final URI clientURI()
  {
    return this.clientURI;
  }

  @Override
  protected final void service(
    final HttpServletRequest request,
    final HttpServletResponse servletResponse)
    throws IOException
  {
    MDC.put("client", clientOf(request));
    this.clientURI = makeClientURI(request);
    this.response = servletResponse;

    try {
      final var session = request.getSession(false);
      if (session != null) {
        final var userName = (String) session.getAttribute("userName");
        MDC.put("client", clientOf(request, userName));
        this.clientURI = makeClientURI(request, userName);
        this.serviceAuthenticated(request, servletResponse, session);
        return;
      }

      this.logger().debug("unauthenticated!");
      this.sendError(401, this.messages.format("errorUnauthorized"));
      this.events.submit(new CAServerLoginFailed());
    } catch (final Exception e) {
      this.logger().trace("exception: ", e);
      throw new IOException(e);
    } finally {
      MDC.remove("client");
    }
  }

  protected final void sendError(
    final int status,
    final String summary,
    final Map<String, String> attributes,
    final List<String> details)
  {
    try {
      this.response.setStatus(status);
      this.response.setContentType(applicationCardantXML());
      this.serializers.serialize(
        this.clientURI(),
        this.response.getOutputStream(),
        new CAResponseError(summary, status, attributes, details)
      );
    } catch (final SerializeException | IOException e) {
      this.logger().error("error sending error response: ", e);
    }
  }

  protected final void sendError(
    final int status,
    final String summary)
  {
    this.sendError(status, summary, Map.of(), List.of());
  }

  protected final void sendErrorIOException(
    final int status,
    final IOException exception)
  {
    this.sendError(
      status,
      this.messages.format("errorIO", exception.getClass().getSimpleName()),
      Map.of(),
      List.of(exception.getMessage())
    );
  }

  protected final void sendErrorParsing(
    final int status,
    final String message,
    final List<ParseStatus> details)
  {
    final var errorDetails =
      details.stream()
        .map(this.messages::formatParseStatus)
        .collect(Collectors.toList());

    this.sendError(
      status,
      message,
      Map.of(),
      errorDetails
    );
  }

  protected final CAServerMessages messages()
  {
    return this.messages;
  }
}
