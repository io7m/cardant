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


package com.io7m.cardant.type_packages.parsers.internal;

import com.io7m.blackthorne.core.BTElementHandlerType;
import com.io7m.blackthorne.core.BTElementParsingContextType;
import com.io7m.cardant.model.CATypeRecordIdentifier;
import com.io7m.cardant.model.CATypeScalarIdentifier;
import com.io7m.cardant.model.type_package.CANameUnqualified;
import com.io7m.cardant.model.type_package.CATypeFieldDeclaration;
import com.io7m.lanark.core.RDottedName;
import org.xml.sax.Attributes;

import java.util.Objects;

/**
 * A parser.
 */

public final class CATP1Field
  implements BTElementHandlerType<Object, CATypeFieldDeclaration>
{
  private final CATypeRecordIdentifier typeRecord;
  private CANameUnqualified name;
  private String description;
  private CANameUnqualified type;
  private boolean required;

  /**
   * A parser.
   *
   * @param context The parse context
   * @param inTypeRecord The record type that owns the field
   */

  public CATP1Field(
    final BTElementParsingContextType context,
    final CATypeRecordIdentifier inTypeRecord)
  {
    this.typeRecord =
      Objects.requireNonNull(inTypeRecord, "typeRecord");
  }

  @Override
  public void onElementStart(
    final BTElementParsingContextType context,
    final Attributes attributes)
  {
    this.name =
      new CANameUnqualified(attributes.getValue("Name"));
    this.description =
      attributes.getValue("Description");
    this.type =
      new CANameUnqualified(attributes.getValue("Type"));

    this.required =
      Boolean.parseBoolean(
        Objects.requireNonNullElse(
          attributes.getValue("Required"),
          "true"
        )
      );
  }

  @Override
  public CATypeFieldDeclaration onElementFinished(
    final BTElementParsingContextType context)
  {
    return new CATypeFieldDeclaration(
      this.name,
      this.description,
      new CATypeScalarIdentifier(
        this.typeRecord.packageName(),
        new RDottedName(this.type.value())
      ),
      this.required
    );
  }
}
