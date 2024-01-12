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

import com.io7m.cardant.model.CALocationID;
import com.io7m.cardant.protocol.api.CAProtocolMessageValidatorType;
import com.io7m.cardant.protocol.inventory.CAICommandLocationTypesAssign;
import com.io7m.cardant.protocol.inventory.cb.CAI1CommandLocationTypesAssign;
import com.io7m.cedarbridge.runtime.api.CBUUID;
import com.io7m.cedarbridge.runtime.convenience.CBLists;
import com.io7m.cedarbridge.runtime.convenience.CBSets;

import static com.io7m.cardant.protocol.inventory.cb.internal.CAUVTypeRecordIdentifier.TYPE_RECORD_IDENTIFIER;

/**
 * A validator.
 */

public enum CAUVCommandLocationTypesAssign
  implements CAProtocolMessageValidatorType<CAICommandLocationTypesAssign, CAI1CommandLocationTypesAssign>
{
  /**
   * A validator.
   */

  COMMAND_LOCATION_TYPES_ASSIGN;

  @Override
  public CAI1CommandLocationTypesAssign convertToWire(
    final CAICommandLocationTypesAssign c)
  {
    return new CAI1CommandLocationTypesAssign(
      new CBUUID(c.location().id()),
      CBLists.ofCollection(c.types(), TYPE_RECORD_IDENTIFIER::convertToWire)
    );
  }

  @Override
  public CAICommandLocationTypesAssign convertFromWire(
    final CAI1CommandLocationTypesAssign c)
  {
    return new CAICommandLocationTypesAssign(
      new CALocationID(c.fieldLocation().value()),
      CBSets.toSet(c.fieldTypes(), TYPE_RECORD_IDENTIFIER::convertFromWire)
    );
  }
}
