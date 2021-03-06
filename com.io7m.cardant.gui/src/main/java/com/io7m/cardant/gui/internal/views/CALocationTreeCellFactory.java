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

package com.io7m.cardant.gui.internal.views;

import com.io7m.cardant.gui.internal.CAMainStrings;
import com.io7m.cardant.gui.internal.model.CALocationItemType;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.util.Callback;

import java.util.Objects;

public final class CALocationTreeCellFactory
  implements Callback<
  TreeView<CALocationItemType>,
  TreeCell<CALocationItemType>>
{
  private final Image imageLocation;
  private final Image imageAll;
  private final CAMainStrings strings;

  public CALocationTreeCellFactory(
    final CAMainStrings inStrings)
  {
    this.strings =
      Objects.requireNonNull(inStrings, "strings");
    this.imageLocation =
      new Image(
        CALocationTreeCell.class.getResource(
            "/com/io7m/cardant/gui/internal/location16.png")
          .toString()
      );
    this.imageAll =
      new Image(
        CALocationTreeCell.class.getResource(
            "/com/io7m/cardant/gui/internal/locationAll16.png")
          .toString()
      );
  }

  @Override
  public TreeCell<CALocationItemType> call(
    final TreeView<CALocationItemType> param)
  {
    return new CALocationTreeCell(
      this.strings,
      this.imageLocation,
      this.imageAll
    );
  }
}
