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

package com.io7m.cardant.database.api;

import com.io7m.cardant.model.CALocation;
import com.io7m.cardant.model.CALocationID;

import java.util.Optional;
import java.util.SortedMap;

/**
 * Model database queries (Locations).
 */

public non-sealed interface CADatabaseQueriesLocationsType
  extends CADatabaseQueriesType
{
  /**
   * Create or update the given location.
   *
   * @param location The location
   *
   * @throws CADatabaseException On database errors
   */

  void locationPut(CALocation location)
    throws CADatabaseException;

  /**
   * @param id The location id
   *
   * @return The location with the given ID
   *
   * @throws CADatabaseException On database errors
   */

  Optional<CALocation> locationGet(CALocationID id)
    throws CADatabaseException;

  /**
   * @return The list of locations in the database
   *
   * @throws CADatabaseException On database errors
   */

  SortedMap<CALocationID, CALocation> locationList()
    throws CADatabaseException;
}