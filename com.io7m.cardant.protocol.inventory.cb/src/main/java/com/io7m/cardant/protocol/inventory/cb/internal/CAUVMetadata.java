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

import com.io7m.cardant.model.CAMetadataType;
import com.io7m.cardant.model.CAMoney;
import com.io7m.cardant.protocol.api.CAProtocolMessageValidatorType;
import com.io7m.cardant.protocol.inventory.cb.CAI1Metadata;
import com.io7m.cedarbridge.runtime.time.CBOffsetDateTime;
import org.joda.money.CurrencyUnit;

import static com.io7m.cardant.protocol.inventory.cb.internal.CAUVTypeRecordFieldIdentifier.TYPE_RECORD_FIELD_IDENTIFIER;
import static com.io7m.cedarbridge.runtime.api.CBCore.float64;
import static com.io7m.cedarbridge.runtime.api.CBCore.signed64;
import static com.io7m.cedarbridge.runtime.api.CBCore.string;

/**
 * A validator.
 */

public enum CAUVMetadata
  implements CAProtocolMessageValidatorType<CAMetadataType, CAI1Metadata>
{
  /**
   * A validator.
   */

  METADATA;

  @Override
  public CAI1Metadata convertToWire(
    final CAMetadataType message)
  {
    return switch (message) {
      case final CAMetadataType.Integral cc -> {
        yield new CAI1Metadata.Integral(
          TYPE_RECORD_FIELD_IDENTIFIER.convertToWire(cc.name()),
          signed64(cc.value())
        );
      }
      case final CAMetadataType.Monetary cc -> {
        yield new CAI1Metadata.Monetary(
          TYPE_RECORD_FIELD_IDENTIFIER.convertToWire(cc.name()),
          string(cc.value().toString()),
          string(cc.currency().getCode())
        );
      }
      case final CAMetadataType.Text cc -> {
        yield new CAI1Metadata.Text(
          TYPE_RECORD_FIELD_IDENTIFIER.convertToWire(cc.name()),
          string(cc.value())
        );
      }
      case final CAMetadataType.Time cc -> {
        yield new CAI1Metadata.Time(
          TYPE_RECORD_FIELD_IDENTIFIER.convertToWire(cc.name()),
          new CBOffsetDateTime(cc.value())
        );
      }
      case final CAMetadataType.Real cc -> {
        yield new CAI1Metadata.Real(
          TYPE_RECORD_FIELD_IDENTIFIER.convertToWire(cc.name()),
          float64(cc.value())
        );
      }
    };
  }

  @Override
  public CAMetadataType convertFromWire(
    final CAI1Metadata message)
  {
    return switch (message) {
      case final CAI1Metadata.Integral ti -> {
        yield new CAMetadataType.Integral(
          TYPE_RECORD_FIELD_IDENTIFIER.convertFromWire(ti.fieldKey()),
          ti.fieldValue().value()
        );
      }
      case final CAI1Metadata.Real ti -> {
        yield new CAMetadataType.Real(
          TYPE_RECORD_FIELD_IDENTIFIER.convertFromWire(ti.fieldKey()),
          ti.fieldValue().value()
        );
      }
      case final CAI1Metadata.Text ti -> {
        yield new CAMetadataType.Text(
          TYPE_RECORD_FIELD_IDENTIFIER.convertFromWire(ti.fieldKey()),
          ti.fieldValue().value()
        );
      }
      case final CAI1Metadata.Time ti -> {
        yield new CAMetadataType.Time(
          TYPE_RECORD_FIELD_IDENTIFIER.convertFromWire(ti.fieldKey()),
          ti.fieldValue().value()
        );
      }
      case final CAI1Metadata.Monetary ti -> {
        yield new CAMetadataType.Monetary(
          TYPE_RECORD_FIELD_IDENTIFIER.convertFromWire(ti.fieldKey()),
          CAMoney.money(ti.fieldValue().value()),
          CurrencyUnit.of(ti.fieldCurrency().value())
        );
      }
    };
  }
}
