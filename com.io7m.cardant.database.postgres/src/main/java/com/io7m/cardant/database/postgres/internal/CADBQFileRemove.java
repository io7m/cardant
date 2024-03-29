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

import com.io7m.cardant.database.api.CADatabaseException;
import com.io7m.cardant.database.api.CADatabaseQueriesFilesType.RemoveType;
import com.io7m.cardant.database.api.CADatabaseUnit;
import com.io7m.cardant.database.postgres.internal.CADBQueryProviderType.Service;
import com.io7m.cardant.model.CAFileID;
import org.jooq.DSLContext;

import java.time.OffsetDateTime;
import java.util.Map;

import static com.io7m.cardant.database.postgres.internal.CADBQAuditEventAdd.auditEvent;
import static com.io7m.cardant.database.postgres.internal.tables.Files.FILES;

/**
 * Remove a file.
 */

public final class CADBQFileRemove
  extends CADBQAbstract<CAFileID, CADatabaseUnit>
  implements RemoveType
{
  private static final Service<CAFileID, CADatabaseUnit, RemoveType> SERVICE =
    new Service<>(RemoveType.class, CADBQFileRemove::new);

  /**
   * Construct a query.
   *
   * @param transaction The transaction
   */

  public CADBQFileRemove(
    final CADatabaseTransaction transaction)
  {
    super(transaction);
  }

  /**
   * @return A query provider
   */

  public static CADBQueryProviderType provider()
  {
    return () -> SERVICE;
  }

  @Override
  protected CADatabaseUnit onExecute(
    final DSLContext context,
    final CAFileID fileID)
    throws CADatabaseException
  {
    final var id = fileID.id();

    final var deleted =
      context.deleteFrom(FILES)
        .where(FILES.FILE_ID.eq(id))
        .execute();

    if (deleted != 0) {
      final var transaction = this.transaction();
      auditEvent(
        context,
        OffsetDateTime.now(transaction.clock()),
        transaction.userId(),
        "FILE_DELETED",
        Map.entry("File", fileID.displayId())
      ).execute();
    }

    return CADatabaseUnit.UNIT;
  }
}
