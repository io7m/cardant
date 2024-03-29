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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type of expressions that can match sets.
 *
 * @param <T> The type of values
 */

public sealed interface CAComparisonSetType<T>
  extends CAComparisonType
{
  /**
   * Produce a new comparison over values transformed with {@code f}.
   *
   * @param f   The transform
   * @param <U> The type of results
   *
   * @return A new comparison
   */

  <U> CAComparisonSetType<U> map(Function<T, U> f);

  /**
   * @param other The set
   *
   * @return {@code true} if this comparison matches the given set
   */

  boolean matches(Set<T> other);

  /**
   * Match any set.
   *
   * @param <T> The type of values
   */

  record Anything<T>() implements CAComparisonSetType<T>
  {
    @Override
    public <U> CAComparisonSetType<U> map(
      final Function<T, U> f)
    {
      return new Anything<>();
    }

    @Override
    public boolean matches(
      final Set<T> other)
    {
      return true;
    }
  }

  /**
   * Match values that are subsets of the given set.
   *
   * @param value The value
   * @param <T>   The type of values
   */

  record IsSubsetOf<T>(Set<T> value)
    implements CAComparisonSetType<T>
  {
    /**
     * Match values that are subsets of the given set.
     */

    public IsSubsetOf
    {
      Objects.requireNonNull(value, "value");
    }

    @Override
    public <U> IsSubsetOf<U> map(
      final Function<T, U> f)
    {
      return new IsSubsetOf<>(
        this.value.stream().map(f).collect(Collectors.toUnmodifiableSet())
      );
    }

    @Override
    public boolean matches(
      final Set<T> other)
    {
      return other.containsAll(this.value);
    }
  }

  /**
   * Match values that are supersets of the given set.
   *
   * @param value The value
   * @param <T>   The type of values
   */

  record IsSupersetOf<T>(Set<T> value)
    implements CAComparisonSetType<T>
  {
    /**
     * Match values that are supersets of the given set.
     */

    public IsSupersetOf
    {
      Objects.requireNonNull(value, "value");
    }

    @Override
    public <U> IsSupersetOf<U> map(
      final Function<T, U> f)
    {
      return new IsSupersetOf<>(
        this.value.stream().map(f).collect(Collectors.toUnmodifiableSet())
      );
    }

    @Override
    public boolean matches(
      final Set<T> other)
    {
      return this.value.containsAll(other);
    }
  }

  /**
   * Match values that overlap the given set.
   *
   * @param value The value
   * @param <T>   The type of values
   */

  record IsOverlapping<T>(Set<T> value)
    implements CAComparisonSetType<T>
  {
    /**
     * Match values that overlap the given set.
     */

    public IsOverlapping
    {
      Objects.requireNonNull(value, "value");
    }

    @Override
    public <U> IsOverlapping<U> map(
      final Function<T, U> f)
    {
      return new IsOverlapping<>(
        this.value.stream().map(f).collect(Collectors.toUnmodifiableSet())
      );
    }

    @Override
    public boolean matches(
      final Set<T> other)
    {
      final var intersect = new HashSet<>(other);
      intersect.retainAll(this.value);
      return !intersect.isEmpty();
    }
  }

  /**
   * Match a value exactly.
   *
   * @param value The value
   * @param <T>   The type of values
   */

  record IsEqualTo<T>(Set<T> value)
    implements CAComparisonSetType<T>
  {
    /**
     * Match a value exactly.
     */

    public IsEqualTo
    {
      Objects.requireNonNull(value, "value");
    }

    @Override
    public <U> IsEqualTo<U> map(
      final Function<T, U> f)
    {
      return new IsEqualTo<>(
        this.value.stream().map(f).collect(Collectors.toUnmodifiableSet())
      );
    }

    @Override
    public boolean matches(
      final Set<T> other)
    {
      return this.value.equals(other);
    }
  }

  /**
   * Match a value exactly.
   *
   * @param value The value
   * @param <T>   The type of values
   */

  record IsNotEqualTo<T>(Set<T> value)
    implements CAComparisonSetType<T>
  {
    /**
     * Match a value exactly.
     */

    public IsNotEqualTo
    {
      Objects.requireNonNull(value, "value");
    }

    @Override
    public <U> IsNotEqualTo<U> map(
      final Function<T, U> f)
    {
      return new IsNotEqualTo<>(
        this.value.stream().map(f).collect(Collectors.toUnmodifiableSet())
      );
    }

    @Override
    public boolean matches(
      final Set<T> other)
    {
      return !this.value.equals(other);
    }
  }
}
