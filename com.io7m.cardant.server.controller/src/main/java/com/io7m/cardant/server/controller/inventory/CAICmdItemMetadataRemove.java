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
import com.io7m.cardant.database.api.CADatabaseQueriesItemTypesType.TypeDeclarationGetMultipleType;
import com.io7m.cardant.database.api.CADatabaseQueriesItemsType;
import com.io7m.cardant.database.api.CADatabaseQueriesItemsType.MetadataRemoveType.Parameters;
import com.io7m.cardant.model.CAItem;
import com.io7m.cardant.model.CATypeChecking;
import com.io7m.cardant.protocol.inventory.CAICommandItemMetadataRemove;
import com.io7m.cardant.protocol.inventory.CAIResponseItemMetadataRemove;
import com.io7m.cardant.protocol.inventory.CAIResponseType;
import com.io7m.cardant.security.CASecurityException;
import com.io7m.cardant.server.controller.command_exec.CACommandExecutionFailure;
import com.io7m.cardant.strings.CAStringConstantApplied;
import com.io7m.cardant.strings.CAStrings;

import java.util.Set;

import static com.io7m.cardant.error_codes.CAStandardErrorCodes.errorNonexistent;
import static com.io7m.cardant.error_codes.CAStandardErrorCodes.errorTypeCheckFailed;
import static com.io7m.cardant.security.CASecurityPolicy.INVENTORY_ITEMS;
import static com.io7m.cardant.security.CASecurityPolicy.WRITE;
import static com.io7m.cardant.strings.CAStringConstants.ERROR_INDEXED;
import static com.io7m.cardant.strings.CAStringConstants.ERROR_NONEXISTENT;
import static com.io7m.cardant.strings.CAStringConstants.ERROR_TYPE_CHECKING;
import static com.io7m.cardant.strings.CAStringConstants.ITEM_ID;

/**
 * @see CAICommandItemMetadataRemove
 */

public final class CAICmdItemMetadataRemove
  extends CAICmdAbstract<CAICommandItemMetadataRemove>
{
  /**
   * @see CAICommandItemMetadataRemove
   */

  public CAICmdItemMetadataRemove()
  {

  }

  @Override
  protected CAIResponseType executeActual(
    final CAICommandContext context,
    final CAICommandItemMetadataRemove command)
    throws CASecurityException, CADatabaseException, CACommandExecutionFailure
  {
    context.securityCheck(INVENTORY_ITEMS, WRITE);

    final var transaction =
      context.transaction();
    final var metaRemove =
      transaction.queries(CADatabaseQueriesItemsType.MetadataRemoveType.class);
    final var get =
      transaction.queries(CADatabaseQueriesItemsType.GetType.class);
    final var typeGet =
      transaction.queries(TypeDeclarationGetMultipleType.class);

    final var metadatas = command.metadataNames();

    final var itemID = command.item();
    context.setAttribute(ITEM_ID, itemID.displayId());
    metaRemove.execute(new Parameters(itemID, metadatas));

    final var itemOpt = get.execute(itemID);
    if (itemOpt.isEmpty()) {
      throw context.failFormatted(
        400,
        errorNonexistent(),
        context.attributes(),
        ERROR_NONEXISTENT
      );
    }

    final var item = itemOpt.get();
    checkTypes(context, typeGet, item);
    return new CAIResponseItemMetadataRemove(context.requestId(), item);
  }

  static void checkTypes(
    final CAICommandContext context,
    final TypeDeclarationGetMultipleType typeGet,
    final CAItem item)
    throws CADatabaseException, CACommandExecutionFailure
  {
    final var types =
      Set.copyOf(typeGet.execute(item.types()));

    final var checker =
      CATypeChecking.create(
        context.services().requireService(CAStrings.class),
        types,
        Set.copyOf(item.metadata().values())
      );

    final var errors = checker.execute();
    if (!errors.isEmpty()) {
      for (int index = 0; index < errors.size(); ++index) {
        context.setAttribute(
          new CAStringConstantApplied(ERROR_INDEXED, Integer.valueOf(index)),
          errors.get(index).message()
        );
      }
      throw context.failFormatted(
        400,
        errorTypeCheckFailed(),
        context.attributes(),
        ERROR_TYPE_CHECKING
      );
    }
  }
}
