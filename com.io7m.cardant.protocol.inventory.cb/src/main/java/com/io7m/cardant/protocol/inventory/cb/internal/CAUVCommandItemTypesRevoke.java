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


package com.io7m.cardant.protocol.inventory.cb.internal;

import com.io7m.cardant.model.CAItemID;
import com.io7m.cardant.protocol.api.CAProtocolMessageValidatorType;
import com.io7m.cardant.protocol.inventory.CAICommandItemTypesRevoke;
import com.io7m.cardant.protocol.inventory.cb.CAI1CommandItemTypesRevoke;
import com.io7m.cedarbridge.runtime.api.CBUUID;
import com.io7m.cedarbridge.runtime.convenience.CBLists;
import com.io7m.cedarbridge.runtime.convenience.CBSets;

import static com.io7m.cardant.protocol.inventory.cb.internal.CAUVTypeRecordIdentifier.TYPE_RECORD_IDENTIFIER;

/**
 * A validator.
 */

public enum CAUVCommandItemTypesRevoke
  implements CAProtocolMessageValidatorType<CAICommandItemTypesRevoke, CAI1CommandItemTypesRevoke>
{
  /**
   * A validator.
   */

  COMMAND_ITEM_TYPES_REVOKE;

  @Override
  public CAI1CommandItemTypesRevoke convertToWire(
    final CAICommandItemTypesRevoke c)
  {
    return new CAI1CommandItemTypesRevoke(
      new CBUUID(c.item().id()),
      CBLists.ofCollection(c.types(), TYPE_RECORD_IDENTIFIER::convertToWire)
    );
  }

  @Override
  public CAICommandItemTypesRevoke convertFromWire(
    final CAI1CommandItemTypesRevoke c)
  {
    return new CAICommandItemTypesRevoke(
      new CAItemID(c.fieldItem().value()),
      CBSets.toSet(c.fieldTypes(), TYPE_RECORD_IDENTIFIER::convertFromWire)
    );
  }
}
