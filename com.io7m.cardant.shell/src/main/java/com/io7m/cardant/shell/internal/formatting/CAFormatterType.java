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

package com.io7m.cardant.shell.internal.formatting;

import com.io7m.cardant.model.CAFileType;
import com.io7m.cardant.model.CAFileType.CAFileWithoutData;
import com.io7m.cardant.model.CAItem;
import com.io7m.cardant.model.CAItemSummary;
import com.io7m.cardant.model.CAPage;

/**
 * A shell formatter for data.
 */

public interface CAFormatterType
{
  /**
   * Format a file.
   *
   * @param file the file
   *
   * @throws Exception On errors
   */

  void formatFile(
    CAFileType file)
    throws Exception;

  /**
   * Format a page of file summaries.
   *
   * @param files The page
   *
   * @throws Exception On errors
   */

  void formatFilesPage(
    CAPage<CAFileWithoutData> files)
    throws Exception;

  /**
   * Format an item.
   *
   * @param item The item
   *
   * @throws Exception On errors
   */

  void formatItem(
    CAItem item)
    throws Exception;

  /**
   * Format a page of item summaries.
   *
   * @param items The items
   *
   * @throws Exception On errors
   */

  void formatItemsPage(
    CAPage<CAItemSummary> items)
    throws Exception;
}