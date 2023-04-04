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

package com.io7m.cardant.client.transfer.api;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

/**
 * A transfer is downloading.
 *
 * @param id                     The ID
 * @param title                  The title
 * @param expectedOctets         The expected octet count
 * @param receivedOctets         The received octet count
 * @param octetsPerSecondAverage The average number of octets per second
 * @param expectedRemainingTime  The expected remaining time
 */

public record CATransferDownloading(
  UUID id,
  String title,
  long expectedOctets,
  long receivedOctets,
  double octetsPerSecondAverage,
  Optional<Duration> expectedRemainingTime)
  implements CATransferStatusType
{
  @Override
  public double progress()
  {
    if (this.expectedOctets == 0L) {
      return 1.0;
    }

    return (double) this.receivedOctets / (double) this.expectedOctets;
  }
}
