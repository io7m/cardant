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


package com.io7m.cardant.tests.arbitraries.model;

import com.io7m.cardant.model.CAItemID;
import com.io7m.cardant.model.CAItemLocation;
import com.io7m.cardant.model.CAItemLocations;
import com.io7m.cardant.model.CALocationID;
import com.io7m.cardant.tests.arbitraries.CAArbAbstract;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;

import java.util.SortedMap;
import java.util.TreeMap;

public final class CAArbItemLocations extends CAArbAbstract<CAItemLocations>
{
  public CAArbItemLocations()
  {
    super(
      CAItemLocations.class,
      () -> locations().map(CAItemLocations::new)
    );
  }


  @SuppressWarnings("unchecked")
  private static Arbitrary<SortedMap<CALocationID, SortedMap<CAItemID, CAItemLocation>>> locations()
  {
    return Arbitraries.defaultFor(CAItemLocation.class)
      .set()
      .map(locations -> {

        final var results =
          new TreeMap<CALocationID, TreeMap<CAItemID, CAItemLocation>>();

        for (final var location : locations) {
          var forLocation =
            results.get(location.location());

          if (forLocation == null) {
            forLocation = new TreeMap<CAItemID, CAItemLocation>();
          }

          forLocation.put(location.item(), location);
          results.put(location.location(), forLocation);
        }

        return (SortedMap<CALocationID, SortedMap<CAItemID, CAItemLocation>>) (Object) results;
      });
  }
}