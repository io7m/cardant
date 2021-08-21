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

package com.io7m.cardant.database.derby.internal;

import com.io7m.cardant.model.CAIdType;
import com.io7m.cardant.model.CAModelDatabaseEventUpdated;

import java.util.HashSet;
import java.util.Set;

/**
 * A model transaction listener.
 */

public final class CADatabaseModelTransactionListener
  implements CADatabaseTransactionListenerType
{
  private final HashSet<CAIdType> updates;
  private final HashSet<CAIdType> removes;

  /**
   * Construct a model transaction listener.
   */

  public CADatabaseModelTransactionListener()
  {
    this.updates = new HashSet<>();
    this.removes = new HashSet<>();
  }

  @Override
  public void onRollback(
    final CADatabaseDerbyTransaction transaction)
  {
    this.clearChanges();
  }

  private void clearChanges()
  {
    this.updates.clear();
    this.removes.clear();
  }

  @Override
  public void onCommit(
    final CADatabaseDerbyTransaction transaction)
  {
    if (this.updates.isEmpty() && this.removes.isEmpty()) {
      return;
    }

    transaction.connection()
      .database()
      .publishEvent(new CAModelDatabaseEventUpdated(
        Set.copyOf(this.updates),
        Set.copyOf(this.removes)
      ));
    this.clearChanges();
  }

  /**
   * Indicate that the object with the given ID was updated.
   *
   * @param id The object ID
   */

  public void publishUpdate(
    final CAIdType id)
  {
    this.updates.add(id);
  }

  /**
   * Indicate that the object with the given ID was removed.
   *
   * @param id The object ID
   */

  public void publishRemove(
    final CAIdType id)
  {
    this.removes.add(id);
  }
}