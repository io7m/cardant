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

package com.io7m.cardant.server.api;

import java.util.Objects;
import java.util.OptionalLong;

/**
 * Server-imposed limits.
 *
 * @param itemAttachmentMaximumSizeOctets The maximum size in bytes of an attachment
 */

public record CAServerConfigurationLimits(
  OptionalLong itemAttachmentMaximumSizeOctets)
{
  /**
   * Server-imposed limits.
   *
   * @param itemAttachmentMaximumSizeOctets The maximum size in bytes of an attachment
   */

  public CAServerConfigurationLimits
  {
    Objects.requireNonNull(
      itemAttachmentMaximumSizeOctets,
      "itemAttachmentMaximumSizeOctets");
  }

  /**
   * @return A configuration using the default settings
   */

  public static CAServerConfigurationLimits create()
  {
    return new CAServerConfigurationLimits(
      OptionalLong.of(1_000_000L)
    );
  }
}