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

package com.io7m.cardant.server.controller.inventory;

import com.io7m.cardant.database.api.CADatabaseException;
import com.io7m.cardant.database.api.CADatabaseItemSearchType;
import com.io7m.cardant.database.api.CADatabaseQueriesItemsType;
import com.io7m.cardant.protocol.inventory.CAICommandItemSearchPrevious;
import com.io7m.cardant.protocol.inventory.CAIResponseItemSearch;
import com.io7m.cardant.protocol.inventory.CAIResponseType;
import com.io7m.cardant.server.controller.command_exec.CACommandExecutionFailure;
import com.io7m.cardant.server.controller.security.CASecurityException;

import java.util.Map;

import static com.io7m.cardant.error_codes.CAStandardErrorCodes.errorApiMisuse;
import static com.io7m.cardant.server.controller.inventory.CAISecurityObjects.INVENTORY_ITEMS;
import static com.io7m.cardant.server.controller.inventory.CAISecurityObjects.READ;

/**
 * @see CAICommandItemSearchPrevious
 */

public final class CAICmdItemSearchPrevious
  extends CAICmdAbstract<CAICommandItemSearchPrevious>
{
  /**
   * @see CAICommandItemSearchPrevious
   */

  public CAICmdItemSearchPrevious()
  {

  }

  @Override
  protected CAIResponseType executeActual(
    final CAICommandContext context,
    final CAICommandItemSearchPrevious command)
    throws CASecurityException, CADatabaseException, CACommandExecutionFailure
  {
    context.securityCheck(INVENTORY_ITEMS, READ);

    final var queries =
      context.transaction()
        .queries(CADatabaseQueriesItemsType.class);

    final var search =
      context.session()
        .property(CADatabaseItemSearchType.class)
        .orElseThrow(() -> {
          return context.failFormatted(
            400,
            errorApiMisuse(),
            Map.of(),
            "errorSearchNotActive"
          );
        });

    final var page = search.pagePrevious(queries);
    return new CAIResponseItemSearch(context.requestId(), page);
  }
}
