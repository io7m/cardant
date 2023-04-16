/*
 * Copyright © 2022 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import com.io7m.cardant.database.api.CADatabaseException;
import com.io7m.cardant.database.api.CADatabaseQueriesUsersType;
import com.io7m.cardant.database.postgres.internal.cardant.tables.records.UsersRecord;
import com.io7m.cardant.model.CAUser;
import com.io7m.medrina.api.MRoleName;
import com.io7m.medrina.api.MSubject;
import org.jooq.exception.DataAccessException;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.io7m.cardant.database.postgres.internal.CADatabaseExceptions.handleDatabaseException;
import static com.io7m.cardant.database.postgres.internal.cardant.Tables.USERS;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

final class CADatabaseQueriesUsers
  extends CABaseQueries
  implements CADatabaseQueriesUsersType
{
  CADatabaseQueriesUsers(
    final CADatabaseTransaction inTransaction)
  {
    super(inTransaction);
  }

  @Override
  public void userPut(
    final CAUser user)
    throws CADatabaseException
  {
    final var transaction =
      this.transaction();
    final var context =
      transaction.createContext();
    final var querySpan =
      transaction.createQuerySpan("CADatabaseUsersQueries.userPut");

    try {
      var userRec = context.fetchOne(USERS, USERS.ID.eq(user.userId()));
      if (userRec == null) {
        userRec = context.newRecord(USERS);
        userRec.set(USERS.ID, user.userId());
        userRec.set(USERS.INITIAL, FALSE);
      }
      userRec.store();
    } catch (final DataAccessException e) {
      querySpan.recordException(e);
      throw handleDatabaseException(transaction, e);
    } finally {
      querySpan.end();
    }
  }

  @Override
  public Optional<CAUser> userGet(
    final UUID id)
    throws CADatabaseException
  {
    final var transaction =
      this.transaction();
    final var context =
      transaction.createContext();
    final var querySpan =
      transaction.createQuerySpan("CADatabaseUsersQueries.userGet");

    try {
      final var userRec = context.fetchOne(USERS, USERS.ID.eq(id));
      if (userRec == null) {
        return Optional.empty();
      }

      return Optional.of(
        new CAUser(
          id,
          new MSubject(
            Stream.of(userRec.getRoles())
              .map(MRoleName::new)
              .collect(Collectors.toUnmodifiableSet())
          )
        )
      );
    } catch (final DataAccessException e) {
      querySpan.recordException(e);
      throw handleDatabaseException(transaction, e);
    } finally {
      querySpan.end();
    }
  }

  @Override
  public void userInitialSet(
    final UUID id)
    throws CADatabaseException
  {
    Objects.requireNonNull(id, "id");

    final var transaction =
      this.transaction();
    final var context =
      transaction.createContext();
    final var querySpan =
      transaction.createQuerySpan("CADatabaseUsersQueries.userInitialSet");

    try {
      var userRec = context.fetchOne(USERS, USERS.ID.eq(id));
      if (userRec == null) {
        userRec = context.newRecord(USERS);
        userRec.set(USERS.ID, id);
      }
      userRec.set(USERS.INITIAL, TRUE);
      userRec.store();
    } catch (final DataAccessException e) {
      querySpan.recordException(e);
      throw handleDatabaseException(transaction, e);
    } finally {
      querySpan.end();
    }
  }

  @Override
  public Optional<UUID> userInitial()
    throws CADatabaseException
  {
    final var transaction =
      this.transaction();
    final var context =
      transaction.createContext();
    final var querySpan =
      transaction.createQuerySpan("CADatabaseUsersQueries.userInitial");

    try {
      return context.selectFrom(USERS)
        .where(USERS.INITIAL.eq(TRUE))
        .fetchOptional()
        .map(UsersRecord::getId);
    } catch (final DataAccessException e) {
      querySpan.recordException(e);
      throw handleDatabaseException(transaction, e);
    } finally {
      querySpan.end();
    }
  }

  @Override
  public void userInitialUnset(
    final UUID id)
    throws CADatabaseException
  {
    Objects.requireNonNull(id, "id");

    final var transaction =
      this.transaction();
    final var context =
      transaction.createContext();
    final var querySpan =
      transaction.createQuerySpan("CADatabaseUsersQueries.userInitialUnset");

    try {
      final var userRec = context.fetchOne(USERS, USERS.ID.eq(id));
      if (userRec == null) {
        return;
      }
      userRec.set(USERS.INITIAL, FALSE);
      userRec.store();
    } catch (final DataAccessException e) {
      querySpan.recordException(e);
      throw handleDatabaseException(transaction, e);
    } finally {
      querySpan.end();
    }
  }
}
