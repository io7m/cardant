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
import com.io7m.cardant.protocol.inventory.CAICommandItemsRemove;
import com.io7m.cardant.protocol.inventory.cb.CAI1CommandItemsRemove;
import com.io7m.cedarbridge.runtime.api.CBUUID;
import com.io7m.cedarbridge.runtime.convenience.CBLists;
import com.io7m.cedarbridge.runtime.convenience.CBSets;

/**
 * A validator.
 */

public enum CAUVCommandItemsRemove
  implements CAProtocolMessageValidatorType<CAICommandItemsRemove, CAI1CommandItemsRemove>
{
  /**
   * A validator.
   */

  COMMAND_ITEMS_REMOVE;

  @Override
  public CAI1CommandItemsRemove convertToWire(
    final CAICommandItemsRemove c)
  {
    return new CAI1CommandItemsRemove(
      CBLists.ofCollection(c.ids(), x -> new CBUUID(x.id()))
    );
  }

  @Override
  public CAICommandItemsRemove convertFromWire(
    final CAI1CommandItemsRemove m)
  {
    return new CAICommandItemsRemove(
      CBSets.toSet(m.fieldItems(), x -> new CAItemID(x.value()))
    );
  }
}
