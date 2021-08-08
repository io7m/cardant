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

package com.io7m.cardant.protocol.inventory.v1.messages;

import java.util.Objects;

/**
 * The "login using username and password" message.
 *
 * @param user     The username
 * @param password The password
 */

public record CA1CommandLoginUsernamePassword(
  String user,
  String password
) implements CA1InventoryCommandType
{
  /**
   * Construct a message.
   *
   * @param user     The username
   * @param password The password
   */

  public CA1CommandLoginUsernamePassword
  {
    Objects.requireNonNull(user, "user");
    Objects.requireNonNull(password, "password");
  }
}