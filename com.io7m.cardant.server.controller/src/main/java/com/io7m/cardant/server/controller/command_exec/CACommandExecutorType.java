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

package com.io7m.cardant.server.controller.command_exec;

import com.io7m.cardant.protocol.api.CAProtocolMessageType;

import java.io.IOException;

/**
 * The type of command executors.
 *
 * @param <C> The type of command contexts
 * @param <M> The type of accepted commands
 * @param <R> The type of responses
 */

public interface CACommandExecutorType<
  C extends CACommandContext<R>,
  M extends CAProtocolMessageType,
  R extends CAProtocolMessageType>
{
  /**
   * Execute a command.
   *
   * @param context The execution context
   * @param command The command
   *
   * @return The result of execution
   *
   * @throws CACommandExecutionFailure On errors
   * @throws InterruptedException      On interruption
   * @throws IOException               On I/O errors
   */

  R execute(
    C context,
    M command)
    throws CACommandExecutionFailure, IOException, InterruptedException;
}
