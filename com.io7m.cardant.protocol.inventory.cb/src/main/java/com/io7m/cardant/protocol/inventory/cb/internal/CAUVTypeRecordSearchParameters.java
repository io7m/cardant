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

import com.io7m.cardant.model.CATypeRecordSearchParameters;
import com.io7m.cardant.protocol.api.CAProtocolException;
import com.io7m.cardant.protocol.api.CAProtocolMessageValidatorType;
import com.io7m.cardant.protocol.inventory.cb.CAI1TypeRecordSearchParameters;
import com.io7m.cedarbridge.runtime.api.CBCore;
import com.io7m.cedarbridge.runtime.api.CBString;

import static com.io7m.cardant.protocol.inventory.cb.internal.CAUVStrings.STRINGS;

/**
 * A validator.
 */

public enum CAUVTypeRecordSearchParameters
  implements CAProtocolMessageValidatorType<CATypeRecordSearchParameters, CAI1TypeRecordSearchParameters>
{
  /**
   * A validator.
   */

  TYPE_DECLARATION_SEARCH_PARAMETERS;

  private static final CAUVComparisonsFuzzy<String, CBString> FUZZY_VALIDATOR =
    new CAUVComparisonsFuzzy<>(STRINGS);

  @Override
  public CAI1TypeRecordSearchParameters convertToWire(
    final CATypeRecordSearchParameters parameters)
    throws CAProtocolException
  {
    return new CAI1TypeRecordSearchParameters(
      FUZZY_VALIDATOR.convertToWire(parameters.nameQuery()),
      FUZZY_VALIDATOR.convertToWire(parameters.descriptionQuery()),
      CBCore.unsigned32(parameters.pageSize())
    );
  }

  @Override
  public CATypeRecordSearchParameters convertFromWire(
    final CAI1TypeRecordSearchParameters p)
    throws CAProtocolException
  {
    return new CATypeRecordSearchParameters(
      FUZZY_VALIDATOR.convertFromWire(p.fieldNameSearch()),
      FUZZY_VALIDATOR.convertFromWire(p.fieldDescriptionSearch()),
      p.fieldLimit().value()
    );
  }
}
