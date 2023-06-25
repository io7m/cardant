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


package com.io7m.cardant.client.basic.internal;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.Flow;

import static java.lang.Integer.toUnsignedString;

final class CATransferStatisticsSubscriber
  implements Flow.Subscriber<ByteBuffer>
{
  private final CATransferStatisticsTracker tracker;
  private Flow.Subscription subscription;

  CATransferStatisticsSubscriber(
    final CATransferStatisticsTracker inTracker)
  {
    this.tracker = Objects.requireNonNull(inTracker, "tracker");
  }

  @Override
  public void onSubscribe(
    final Flow.Subscription inSubscription)
  {
    this.subscription =
      Objects.requireNonNull(inSubscription, "subscription");

    this.subscription.request(Long.MAX_VALUE);
  }

  @Override
  public void onNext(
    final ByteBuffer item)
  {
    this.tracker.add(Integer.toUnsignedLong(item.remaining()));
  }

  @Override
  public void onError(
    final Throwable throwable)
  {

  }

  @Override
  public void onComplete()
  {
    this.subscription.cancel();
  }

  @Override
  public String toString()
  {
    return "[CATransferStatisticsSubscriber 0x%s]"
      .formatted(toUnsignedString(this.hashCode(), 16));
  }
}
