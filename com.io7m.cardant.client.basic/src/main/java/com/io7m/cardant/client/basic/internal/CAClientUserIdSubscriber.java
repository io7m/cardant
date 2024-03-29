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

import com.io7m.cardant.client.api.CAClientCredentials;
import com.io7m.cardant.model.CAUserID;
import com.io7m.cardant.protocol.inventory.CAIResponseError;
import com.io7m.cardant.protocol.inventory.CAIResponseLogin;
import com.io7m.cardant.protocol.inventory.CAIResponseType;
import com.io7m.hibiscus.api.HBStateType;
import com.io7m.hibiscus.api.HBStateType.HBStateDisconnected;
import com.io7m.hibiscus.api.HBStateType.HBStateExecutingLoginSucceeded;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Flow;

import static java.lang.Integer.toUnsignedString;

/**
 * A subscriber that tracks user IDs.
 */

public final class CAClientUserIdSubscriber
  implements Flow.Subscriber<HBStateType<
  ?, CAIResponseType, CAIResponseError, CAClientCredentials>>,
  AutoCloseable
{
  private Flow.Subscription subscription;
  private volatile Optional<CAUserID> userId;

  /**
   * A subscriber that tracks user IDs.
   */

  public CAClientUserIdSubscriber()
  {
    this.userId = Optional.empty();
  }

  /**
   * @return The current user ID
   */

  public Optional<CAUserID> getUserId()
  {
    return this.userId;
  }

  @Override
  public void onSubscribe(
    final Flow.Subscription newSubscription)
  {
    this.subscription =
      Objects.requireNonNull(newSubscription, "newSubscription");

    this.subscription.request(1L);
  }

  @Override
  public void onNext(
    final HBStateType<?, CAIResponseType, CAIResponseError, CAClientCredentials> item)
  {
    try {
      if (item instanceof final HBStateExecutingLoginSucceeded<
        ?, CAIResponseType, CAIResponseError, CAClientCredentials> c) {
        final var login = (CAIResponseLogin) c.response();
        this.userId = Optional.of(login.userId());
        return;
      }

      if (item instanceof HBStateDisconnected<
        ?, CAIResponseType, CAIResponseError, CAClientCredentials>) {
        this.userId = Optional.empty();
        return;
      }
    } finally {
      this.subscription.request(1L);
    }
  }

  @Override
  public void onError(
    final Throwable throwable)
  {

  }

  @Override
  public void onComplete()
  {

  }

  @Override
  public void close()
  {
    this.subscription.cancel();
  }

  @Override
  public String toString()
  {
    return "[CAClientUserIdSubscriber 0x%s]"
      .formatted(toUnsignedString(this.hashCode(), 16));
  }
}
