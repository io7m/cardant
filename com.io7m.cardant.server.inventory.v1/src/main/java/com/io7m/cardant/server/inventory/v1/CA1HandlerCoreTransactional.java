/*
 * Copyright © 2023 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

package com.io7m.cardant.server.inventory.v1;

import com.io7m.cardant.database.api.CADatabaseException;
import com.io7m.cardant.database.api.CADatabaseType;
import com.io7m.cardant.protocol.inventory.cb.CAI1Messages;
import com.io7m.cardant.server.http.CAHTTPHandlerFunctionalCoreTransactionalType;
import com.io7m.cardant.server.http.CAHTTPHandlerFunctionalCoreType;
import com.io7m.cardant.server.http.CAHTTPRequestInformation;
import com.io7m.cardant.server.http.CAHTTPResponseType;
import com.io7m.repetoir.core.RPServiceDirectoryType;
import io.helidon.webserver.http.ServerRequest;

import java.util.Objects;

import static com.io7m.cardant.database.api.CADatabaseRole.CARDANT;
import static com.io7m.cardant.protocol.inventory.CAIResponseBlame.BLAME_SERVER;
import static com.io7m.cardant.server.service.telemetry.api.CAServerTelemetryServiceType.setSpanErrorCode;

/**
 * A servlet core that executes the given core with a database transaction.
 */

public final class CA1HandlerCoreTransactional
  implements CAHTTPHandlerFunctionalCoreType
{
  private final CAHTTPHandlerFunctionalCoreTransactionalType core;
  private final CADatabaseType database;
  private final CAI1Messages messages;

  private CA1HandlerCoreTransactional(
    final RPServiceDirectoryType services,
    final CAHTTPHandlerFunctionalCoreTransactionalType inCore)
  {
    Objects.requireNonNull(services, "services");

    this.core =
      Objects.requireNonNull(inCore, "core");
    this.database =
      services.requireService(CADatabaseType.class);
    this.messages =
      services.requireService(CAI1Messages.class);
  }

  /**
   * @param inServices The services
   * @param inCore     The core
   *
   * @return A servlet core that executes the given core with a database
   * transaction
   */

  public static CAHTTPHandlerFunctionalCoreType withTransaction(
    final RPServiceDirectoryType inServices,
    final CAHTTPHandlerFunctionalCoreTransactionalType inCore)
  {
    return new CA1HandlerCoreTransactional(inServices, inCore);
  }

  @Override
  public CAHTTPResponseType execute(
    final ServerRequest request,
    final CAHTTPRequestInformation information)
  {
    try (var connection = this.database.openConnection(CARDANT)) {
      try (var transaction = connection.openTransaction()) {
        return this.core.executeTransactional(
          request,
          information,
          transaction);
      }
    } catch (final CADatabaseException e) {
      setSpanErrorCode(e.errorCode());
      return CA1Errors.errorResponseOf(
        this.messages,
        information,
        BLAME_SERVER,
        e);
    }
  }
}
