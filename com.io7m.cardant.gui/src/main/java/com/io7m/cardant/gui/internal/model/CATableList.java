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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.Objects;
import java.util.function.Predicate;

public final class CATableList<V>
{
  private final ObservableList<V> asListReadOnly;
  private final FilteredList<V> asListFiltered;
  private final SortedList<V> asListSorted;
  private final ObservableList<V> baseList;

  public CATableList(
    final ObservableList<V> inBaseMap)
  {
    this.baseList =
      Objects.requireNonNull(inBaseMap, "inBaseMap");
    this.asListReadOnly =
      FXCollections.unmodifiableObservableList(this.baseList);
    this.asListFiltered =
      new FilteredList<>(this.asListReadOnly);
    this.asListSorted =
      new SortedList<>(this.asListFiltered);
  }

  public SortedList<V> readable()
  {
    return this.asListSorted;
  }

  public void setPredicate(
    final Predicate<V> predicate)
  {
    this.asListFiltered.setPredicate(
      Objects.requireNonNull(predicate, "predicate"));
  }
}
