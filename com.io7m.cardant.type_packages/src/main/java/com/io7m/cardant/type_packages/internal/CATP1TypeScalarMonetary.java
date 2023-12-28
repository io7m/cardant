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


package com.io7m.cardant.type_packages.internal;

import com.io7m.blackthorne.core.BTElementHandlerType;
import com.io7m.blackthorne.core.BTElementParsingContextType;
import com.io7m.cardant.model.CATypeScalarType;
import com.io7m.lanark.core.RDottedName;
import org.xml.sax.Attributes;

import java.math.BigDecimal;

/**
 * A parser.
 */

public final class CATP1TypeScalarMonetary
  implements BTElementHandlerType<Object, CATypeScalarType>
{
  private RDottedName name;
  private String description;
  private BigDecimal rangeLower;
  private BigDecimal rangeUpper;

  /**
   * A parser.
   *
   * @param context The parse context
   */

  public CATP1TypeScalarMonetary(
    final BTElementParsingContextType context)
  {

  }

  @Override
  public void onElementStart(
    final BTElementParsingContextType context,
    final Attributes attributes)
  {
    this.name =
      new RDottedName(attributes.getValue("Name"));
    this.description =
      attributes.getValue("Description");
    this.rangeLower =
      new BigDecimal(attributes.getValue("RangeLower"));
    this.rangeUpper =
      new BigDecimal(attributes.getValue("RangeUpper"));
  }

  @Override
  public CATypeScalarType onElementFinished(
    final BTElementParsingContextType context)
  {
    return new CATypeScalarType.Monetary(
      this.name,
      this.description,
      this.rangeLower,
      this.rangeUpper
    );
  }
}