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

package com.io7m.cardant.database.postgres.internal;


import com.io7m.cardant.database.api.CADatabaseConnectionType;
import com.io7m.cardant.database.api.CADatabaseException;
import com.io7m.cardant.database.api.CADatabaseRole;
import com.io7m.cardant.database.api.CADatabaseType;
import com.zaxxer.hikari.HikariDataSource;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.SQLException;
import java.time.Clock;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

import static com.io7m.cardant.error_codes.CAStandardErrorCodes.errorSql;
import static io.opentelemetry.semconv.trace.attributes.SemanticAttributes.DB_SYSTEM;
import static io.opentelemetry.semconv.trace.attributes.SemanticAttributes.DbSystemValues.POSTGRESQL;

/**
 * The default postgres server database implementation.
 */

public final class CADatabase implements CADatabaseType
{
  private final OpenTelemetry telemetry;
  private final Clock clock;
  private final HikariDataSource dataSource;
  private final Settings settings;
  private final Tracer tracer;
  private final LongCounter transactions;
  private final LongCounter transactionCommits;
  private final LongCounter transactionRollbacks;
  private final CADatabaseMessages messages;

  /**
   * The default postgres server database implementation.
   *
   * @param locale The locale
   * @param inOpenTelemetry A telemetry interface
   * @param inClock         The clock
   * @param inDataSource    A pooled data source
   */

  public CADatabase(
    final Locale locale,
    final OpenTelemetry inOpenTelemetry,
    final Clock inClock,
    final HikariDataSource inDataSource)
  {
    try {
      this.messages = new CADatabaseMessages(locale);
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }

    this.telemetry =
      Objects.requireNonNull(inOpenTelemetry, "inOpenTelemetry");
    this.tracer =
      this.telemetry.getTracer("com.io7m.cardant.database.postgres", version());
    this.clock =
      Objects.requireNonNull(inClock, "clock");
    this.dataSource =
      Objects.requireNonNull(inDataSource, "dataSource");
    this.settings =
      new Settings().withRenderNameCase(RenderNameCase.LOWER);

    final var meters =
      this.telemetry.meterBuilder(
        "com.io7m.cardant.database.postgres")
        .build();

    this.transactions =
      meters.counterBuilder("CADatabase.transactions")
        .build();
    this.transactionCommits =
      meters.counterBuilder("CADatabase.commits")
        .build();
    this.transactionRollbacks =
      meters.counterBuilder("CADatabase.commits")
        .build();
  }

  private static String version()
  {
    final var p =
      CADatabase.class.getPackage();
    final var v =
      p.getImplementationVersion();

    if (v == null) {
      return "0.0.0";
    }
    return v;
  }

  LongCounter counterTransactions()
  {
    return this.transactions;
  }

  LongCounter counterTransactionCommits()
  {
    return this.transactionCommits;
  }

  LongCounter counterTransactionRollbacks()
  {
    return this.transactionRollbacks;
  }

  @Override
  public void close()
  {
    this.dataSource.close();
  }

  /**
   * @return The OpenTelemetry tracer
   */

  public Tracer tracer()
  {
    return this.tracer;
  }

  @Override
  public CADatabaseConnectionType openConnection(
    final CADatabaseRole role)
    throws CADatabaseException
  {
    final var span =
      this.tracer
        .spanBuilder("CADatabaseConnection")
        .setSpanKind(SpanKind.SERVER)
        .setAttribute(DB_SYSTEM, POSTGRESQL)
        .startSpan();

    try {
      final var conn = this.dataSource.getConnection();
      conn.setAutoCommit(false);
      return new CADatabaseConnection(this, conn, role, span);
    } catch (final SQLException e) {
      span.recordException(e);
      span.end();
      throw new CADatabaseException(
        errorSql(),
        e.getMessage(),
        e,
        Collections.emptySortedMap()
      );
    }
  }

  /**
   * @return The jooq SQL settings
   */

  public Settings settings()
  {
    return this.settings;
  }

  /**
   * @return The clock used for time-related queries
   */

  public Clock clock()
  {
    return this.clock;
  }

  @Override
  public String description()
  {
    return "Server database service.";
  }

  @Override
  public String toString()
  {
    return "[CADatabase 0x%s]"
      .formatted(Long.toUnsignedString(this.hashCode(), 16));
  }

  CADatabaseMessages messages()
  {
    return this.messages;
  }
}
