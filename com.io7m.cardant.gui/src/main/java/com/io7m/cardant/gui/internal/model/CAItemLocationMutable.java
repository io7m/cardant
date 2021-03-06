/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

package com.io7m.cardant.gui.internal.model;

import com.io7m.cardant.model.CAItemID;
import com.io7m.cardant.model.CAItemLocation;
import com.io7m.cardant.model.CALocationID;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public record CAItemLocationMutable(
  CAItemID item,
  CALocationID location,
  SimpleStringProperty locationName,
  SimpleLongProperty count)
{
  public CAItemLocationMutable
  {
    Objects.requireNonNull(item, "item");
    Objects.requireNonNull(location, "location");
    Objects.requireNonNull(locationName, "locationName");
    Objects.requireNonNull(count, "count");
  }

  public static CAItemLocationMutable of(
    final String location,
    final CAItemLocation value)
  {
    Objects.requireNonNull(location, "location");
    Objects.requireNonNull(value, "value");

    return new CAItemLocationMutable(
      value.item(),
      value.location(),
      new SimpleStringProperty(location),
      new SimpleLongProperty(value.count())
    );
  }
}
