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

package com.io7m.cardant.shell;

import org.jline.terminal.Terminal;

import java.time.Clock;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * The shell configuration.
 *
 * @param locale   The locale
 * @param clock    The clock service
 * @param terminal The terminal
 */

public record CAShellConfiguration(
  Locale locale,
  Clock clock,
  Optional<Terminal> terminal)
{
  /**
   * The shell configuration.
   *
   * @param locale   The locale
   * @param clock    The clock service
   * @param terminal The terminal
   */

  public CAShellConfiguration
  {
    Objects.requireNonNull(locale, "locale");
    Objects.requireNonNull(clock, "clock");
    Objects.requireNonNull(terminal, "terminal");
  }
}
