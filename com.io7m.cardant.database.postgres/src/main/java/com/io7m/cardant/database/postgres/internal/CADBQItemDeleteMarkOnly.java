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
import com.io7m.cardant.database.api.CADatabaseQueriesItemsType.DeleteMarkOnlyType;
import com.io7m.cardant.database.api.CADatabaseUnit;
import com.io7m.cardant.database.postgres.internal.CADBQueryProviderType.Service;
import com.io7m.cardant.model.CAItemID;
import org.jooq.DSLContext;
import org.jooq.Query;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static com.io7m.cardant.database.postgres.internal.CADBQAuditEventAdd.auditEvent;
import static com.io7m.cardant.database.postgres.internal.Tables.ITEMS;
import static java.lang.Boolean.TRUE;

/**
 * Mark the given items as deleted.
 */

public final class CADBQItemDeleteMarkOnly
  extends CADBQAbstract<Collection<CAItemID>, CADatabaseUnit>
  implements DeleteMarkOnlyType
{
  private static final Service<Collection<CAItemID>, CADatabaseUnit, DeleteMarkOnlyType> SERVICE =
    new Service<>(DeleteMarkOnlyType.class, CADBQItemDeleteMarkOnly::new);

  /**
   * Construct a query.
   *
   * @param transaction The transaction
   */

  public CADBQItemDeleteMarkOnly(
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
    final Collection<CAItemID> items)
    throws CADatabaseException
  {
    final var transaction = this.transaction();
    final var updates =
      new ArrayList<Query>(items.size());
    for (final var item : items) {
      updates.add(
        context.update(ITEMS)
          .set(ITEMS.ITEM_DELETED, TRUE)
          .where(ITEMS.ITEM_ID.eq(item.id()))
      );
      updates.add(
        auditEvent(
          context,
          OffsetDateTime.now(transaction.clock()),
          transaction.userId(),
          "ITEM_MARKED_DELETED",
          Map.entry("Item", item.displayId())
        )
      );
    }
    context.batch(updates).execute();
    return CADatabaseUnit.UNIT;
  }
}
