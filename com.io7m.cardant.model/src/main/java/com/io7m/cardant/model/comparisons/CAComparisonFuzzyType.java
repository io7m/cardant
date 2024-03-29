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


package com.io7m.cardant.model.comparisons;

import java.util.Objects;

/**
 * The type of expressions that match values exactly or via similarity.
 *
 * @param <T> The type of values
 */

public sealed interface CAComparisonFuzzyType<T>
  extends CAComparisonType
{
  /**
   * Match any value.
   *
   * @param <T> The type of values
   */

  record Anything<T>() implements CAComparisonFuzzyType<T>
  {

  }

  /**
   * Match a value exactly.
   *
   * @param value The value
   * @param <T>   The type of values
   */

  record IsEqualTo<T>(T value)
    implements CAComparisonFuzzyType<T>
  {
    /**
     * Match a value exactly.
     */

    public IsEqualTo
    {
      Objects.requireNonNull(value, "value");
    }
  }

  /**
   * Match a value exactly.
   *
   * @param value The value
   * @param <T>   The type of values
   */

  record IsNotEqualTo<T>(T value)
    implements CAComparisonFuzzyType<T>
  {
    /**
     * Match a value exactly.
     */

    public IsNotEqualTo
    {
      Objects.requireNonNull(value, "value");
    }
  }

  /**
   * Match similar values.
   *
   * @param value The value
   * @param <T>   The type of values
   */

  record IsSimilarTo<T>(T value)
    implements CAComparisonFuzzyType<T>
  {
    /**
     * Match similar values.
     */

    public IsSimilarTo
    {
      Objects.requireNonNull(value, "value");
    }
  }

  /**
   * Match similar values.
   *
   * @param value The value
   * @param <T>   The type of values
   */

  record IsNotSimilarTo<T>(T value)
    implements CAComparisonFuzzyType<T>
  {
    /**
     * Match similar values.
     */

    public IsNotSimilarTo
    {
      Objects.requireNonNull(value, "value");
    }
  }
}
