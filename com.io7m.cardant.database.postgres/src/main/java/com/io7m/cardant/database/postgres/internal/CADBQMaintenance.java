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

import com.io7m.cardant.database.api.CADatabaseQueriesMaintenanceType.ExecuteType;
import com.io7m.cardant.database.api.CADatabaseUnit;
import com.io7m.cardant.database.postgres.internal.CADBQueryProviderType.Service;
import com.io7m.cardant.security.CASecurityPolicy;
import com.io7m.lanark.core.RDottedName;
import com.io7m.medrina.api.MRoleName;
import org.jooq.DSLContext;

import static com.io7m.cardant.database.api.CADatabaseUnit.UNIT;
import static com.io7m.cardant.database.postgres.internal.tables.Users.USERS;

/**
 * A query to run maintenance.
 */

public final class CADBQMaintenance
  extends CADBQAbstract<CADatabaseUnit, CADatabaseUnit>
  implements ExecuteType
{
  private static final Service<CADatabaseUnit, CADatabaseUnit, ExecuteType> SERVICE =
    new Service<>(ExecuteType.class, CADBQMaintenance::new);

  /**
   * Construct a query.
   *
   * @param transaction The transaction
   */

  public CADBQMaintenance(
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
    final CADatabaseUnit parameters)
  {
    final var roleArray =
      new String[CASecurityPolicy.ROLES_ALL.size()];

    final var allRoles =
      CASecurityPolicy.ROLES_ALL.stream()
        .sorted()
        .map(MRoleName::value)
        .map(RDottedName::value)
        .toList()
        .toArray(roleArray);

    final var hasAdmin =
      USERS.ROLES.contains(new String[]{
        CASecurityPolicy.ROLE_INVENTORY_ADMIN.value().value(),
      });

    context.update(USERS)
      .set(USERS.ROLES, allRoles)
      .where(hasAdmin)
      .execute();

    return UNIT;
  }
}
