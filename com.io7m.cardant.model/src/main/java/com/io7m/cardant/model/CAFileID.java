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

package com.io7m.cardant.model;

import java.util.Objects;
import java.util.UUID;

/**
 * The unique ID of an file.
 *
 * @param id The ID value
 */

public record CAFileID(UUID id)
  implements Comparable<CAFileID>, CAIdType
{
  /**
   * The unique ID of an file.
   *
   * @param id The ID value
   */

  public CAFileID
  {
    Objects.requireNonNull(id, "id");
  }

  @Override
  public String toString()
  {
    return this.id.toString();
  }

  /**
   * Construct an ID.
   *
   * @param id The ID value
   *
   * @return An ID
   */

  public static CAFileID of(
    final String id)
  {
    return new CAFileID(UUID.fromString(id));
  }

  /**
   * Construct an ID.
   *
   * @param id The ID value
   *
   * @return An ID
   */

  public static CAFileID of(
    final UUID id)
  {
    return new CAFileID(id);
  }

  /**
   * Construct an ID using a pseudorandom value.
   *
   * @return An ID
   */

  public static CAFileID random()
  {
    return new CAFileID(UUID.randomUUID());
  }

  @Override
  public int compareTo(
    final CAFileID other)
  {
    return this.id.compareTo(other.id);
  }
}
