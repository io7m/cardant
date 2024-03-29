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

import com.io7m.cardant.model.CATypeField;
import com.io7m.cardant.protocol.api.CAProtocolMessageValidatorType;
import com.io7m.cardant.protocol.inventory.cb.CAI1TypeField;
import com.io7m.cedarbridge.runtime.api.CBBooleanType;

import static com.io7m.cardant.protocol.inventory.cb.internal.CAUVTypeRecordFieldIdentifier.TYPE_RECORD_FIELD_IDENTIFIER;
import static com.io7m.cardant.protocol.inventory.cb.internal.CAUVTypeScalar.TYPE_SCALAR;
import static com.io7m.cedarbridge.runtime.api.CBCore.string;

/**
 * A validator.
 */

public enum CAUVTypeField
  implements CAProtocolMessageValidatorType<CATypeField, CAI1TypeField>
{
  /**
   * A validator.
   */

  TYPE_FIELD;


  @Override
  public CAI1TypeField convertToWire(
    final CATypeField message)
  {
    return new CAI1TypeField(
      TYPE_RECORD_FIELD_IDENTIFIER.convertToWire(message.name()),
      string(message.description()),
      TYPE_SCALAR.convertToWire(message.type()),
      CBBooleanType.fromBoolean(message.isRequired())
    );
  }

  @Override
  public CATypeField convertFromWire(
    final CAI1TypeField message)
  {
    return new CATypeField(
      TYPE_RECORD_FIELD_IDENTIFIER.convertFromWire(message.fieldName()),
      message.fieldDescription().value(),
      TYPE_SCALAR.convertFromWire(message.fieldType()),
      message.fieldRequired().asBoolean()
    );
  }
}
