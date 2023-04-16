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

package com.io7m.cardant.server.api;

import com.io7m.cardant.database.api.CADatabaseType;

/**
 * The type of servers.
 */

public interface CAServerType extends AutoCloseable
{
  /**
   * Start the server instance. Can be called multiple times redundantly,
   * and can be called before or after #close() has been called.
   *
   * @throws CAServerException On errors
   */

  void start()
    throws CAServerException;

  /**
   * @return The server's database instance
   */

  CADatabaseType database();

  /**
   * @return {@code true} if the server is closed
   *
   * @see #start()
   * @see #close()
   */

  boolean isClosed();

  @Override
  void close()
    throws CAServerException;
}
