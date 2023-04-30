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

package com.io7m.cardant.database.postgres;

import com.io7m.anethum.common.ParseException;
import com.io7m.cardant.database.api.CADatabaseConfiguration;
import com.io7m.cardant.database.api.CADatabaseException;
import com.io7m.cardant.database.api.CADatabaseFactoryType;
import com.io7m.cardant.database.api.CADatabaseType;
import com.io7m.cardant.database.postgres.internal.CADatabase;
import com.io7m.trasco.api.TrEventExecutingSQL;
import com.io7m.trasco.api.TrEventType;
import com.io7m.trasco.api.TrEventUpgrading;
import com.io7m.trasco.api.TrException;
import com.io7m.trasco.api.TrExecutorConfiguration;
import com.io7m.trasco.api.TrSchemaRevisionSet;
import com.io7m.trasco.vanilla.TrExecutors;
import com.io7m.trasco.vanilla.TrSchemaRevisionSetParsers;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.opentelemetry.api.OpenTelemetry;
import org.postgresql.util.PSQLState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static com.io7m.cardant.error_codes.CAStandardErrorCodes.errorIo;
import static com.io7m.cardant.error_codes.CAStandardErrorCodes.errorSql;
import static com.io7m.cardant.error_codes.CAStandardErrorCodes.errorTrasco;
import static com.io7m.trasco.api.TrExecutorUpgrade.FAIL_INSTEAD_OF_UPGRADING;
import static com.io7m.trasco.api.TrExecutorUpgrade.PERFORM_UPGRADES;
import static java.math.BigInteger.valueOf;

/**
 * The default postgres server database implementation.
 */

public final class CAPGDatabases implements CADatabaseFactoryType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(CAPGDatabases.class);

  private static final String DATABASE_APPLICATION_ID =
    "com.io7m.cardant";

  /**
   * The default postgres server database implementation.
   */

  public CAPGDatabases()
  {

  }

  private static void schemaVersionSet(
    final BigInteger version,
    final Connection connection)
    throws SQLException
  {
    final String statementText;
    if (Objects.equals(version, BigInteger.ZERO)) {
      statementText = "insert into schema_version (version_application_id, version_number) values (?, ?)";
      try (var statement =
             connection.prepareStatement(statementText)) {
        statement.setString(1, DATABASE_APPLICATION_ID);
        statement.setLong(2, version.longValueExact());
        statement.execute();
      }
    } else {
      statementText = "update schema_version set version_number = ?";
      try (var statement =
             connection.prepareStatement(statementText)) {
        statement.setLong(1, version.longValueExact());
        statement.execute();
      }
    }
  }

  private static Optional<BigInteger> schemaVersionGet(
    final Connection connection)
    throws SQLException
  {
    Objects.requireNonNull(connection, "connection");

    try {
      final var statementText =
        "SELECT version_application_id, version_number FROM schema_version";
      LOG.debug("execute: {}", statementText);

      try (var statement = connection.prepareStatement(statementText)) {
        try (var result = statement.executeQuery()) {
          if (!result.next()) {
            throw new SQLException("schema_version table is empty!");
          }
          final var applicationId =
            result.getString(1);
          final var version =
            result.getLong(2);

          if (!Objects.equals(applicationId, DATABASE_APPLICATION_ID)) {
            throw new SQLException(
              String.format(
                "Database application ID is %s but should be %s",
                applicationId,
                DATABASE_APPLICATION_ID
              )
            );
          }

          return Optional.of(valueOf(version));
        }
      }
    } catch (final SQLException e) {
      final var state = e.getSQLState();
      if (state == null) {
        throw e;
      }
      if (state.equals(PSQLState.UNDEFINED_TABLE.getState())) {
        connection.rollback();
        return Optional.empty();
      }

      throw e;
    }
  }

  @Override
  public String kind()
  {
    return "POSTGRESQL";
  }

  @Override
  public CADatabaseType open(
    final CADatabaseConfiguration configuration,
    final OpenTelemetry openTelemetry,
    final Consumer<String> startupMessages)
    throws CADatabaseException
  {
    Objects.requireNonNull(configuration, "configuration");
    Objects.requireNonNull(openTelemetry, "openTelemetry");
    Objects.requireNonNull(startupMessages, "startupMessages");

    try {
      final var url = new StringBuilder(128);
      url.append("jdbc:postgresql://");
      url.append(configuration.address());
      url.append(":");
      url.append(configuration.port());
      url.append("/");
      url.append(configuration.databaseName());

      final var config = new HikariConfig();
      config.setJdbcUrl(url.toString());
      config.setUsername(configuration.user());
      config.setPassword(configuration.password());
      config.setAutoCommit(false);

      final var dataSource = new HikariDataSource(config);
      final var parsers = new TrSchemaRevisionSetParsers();

      final TrSchemaRevisionSet revisions;
      try (var stream = CAPGDatabases.class.getResourceAsStream(
        "/com/io7m/cardant/database/postgres/internal/database.xml")) {
        revisions = parsers.parse(URI.create("urn:source"), stream);
      }

      try (var connection = dataSource.getConnection()) {
        connection.setAutoCommit(false);

        new TrExecutors().create(
          new TrExecutorConfiguration(
            CAPGDatabases::schemaVersionGet,
            CAPGDatabases::schemaVersionSet,
            event -> publishTrEvent(startupMessages, event),
            revisions,
            switch (configuration.upgrade()) {
              case UPGRADE_DATABASE -> PERFORM_UPGRADES;
              case DO_NOT_UPGRADE_DATABASE -> FAIL_INSTEAD_OF_UPGRADING;
            },
            connection
          )
        ).execute();
        connection.commit();
      }

      return new CADatabase(
        configuration.locale(),
        openTelemetry,
        configuration.clock(),
        dataSource
      );
    } catch (final IOException e) {
      throw new CADatabaseException(
        e.getMessage(),
        e,
        errorIo(),
        Collections.emptySortedMap(),
        Optional.empty()
      );
    } catch (final TrException | ParseException e) {
      throw new CADatabaseException(
        e.getMessage(),
        e,
        errorTrasco(),
        Collections.emptySortedMap(),
        Optional.empty()
      );
    } catch (final SQLException e) {
      throw new CADatabaseException(
        e.getMessage(),
        e,
        errorSql(),
        Collections.emptySortedMap(),
        Optional.empty()
      );
    }
  }

  private static void publishEvent(
    final Consumer<String> startupMessages,
    final String message)
  {
    try {
      LOG.trace("{}", message);
      startupMessages.accept(message);
    } catch (final Exception e) {
      LOG.error("ignored consumer exception: ", e);
    }
  }

  private static void publishTrEvent(
    final Consumer<String> startupMessages,
    final TrEventType event)
  {
    if (event instanceof TrEventExecutingSQL sql) {
      publishEvent(
        startupMessages,
        String.format("Executing SQL: %s", sql.statement())
      );
      return;
    }

    if (event instanceof TrEventUpgrading upgrading) {
      publishEvent(
        startupMessages,
        String.format(
          "Upgrading database from version %s -> %s",
          upgrading.fromVersion(),
          upgrading.toVersion())
      );
      return;
    }
  }
}