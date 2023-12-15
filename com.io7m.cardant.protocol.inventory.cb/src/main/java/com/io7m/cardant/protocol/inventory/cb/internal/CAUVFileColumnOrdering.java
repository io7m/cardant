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

import com.io7m.cardant.model.CAFileColumnOrdering;
import com.io7m.cardant.protocol.api.CAProtocolMessageValidatorType;
import com.io7m.cardant.protocol.inventory.cb.CAI1FileColumnOrdering;
import com.io7m.cedarbridge.runtime.api.CBBooleanType;

import static com.io7m.cardant.protocol.inventory.cb.internal.CAUVFileColumn.FILE_COLUMN;

/**
 * A validator.
 */

public enum CAUVFileColumnOrdering
  implements CAProtocolMessageValidatorType<CAFileColumnOrdering, CAI1FileColumnOrdering>
{
  /**
   * A validator.
   */

  FILE_COLUMN_ORDERING;

  @Override
  public CAI1FileColumnOrdering convertToWire(
    final CAFileColumnOrdering parameters)
  {
    return new CAI1FileColumnOrdering(
      FILE_COLUMN.convertToWire(parameters.column()),
      CBBooleanType.fromBoolean(parameters.ascending())
    );
  }

  @Override
  public CAFileColumnOrdering convertFromWire(
    final CAI1FileColumnOrdering message)
  {
    return new CAFileColumnOrdering(
      FILE_COLUMN.convertFromWire(message.fieldColumn()),
      message.fieldAscending().asBoolean()
    );
  }
}
