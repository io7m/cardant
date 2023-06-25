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

package com.io7m.cardant.server.service.reqlimit;

import com.io7m.cardant.server.service.configuration.CAConfigurationServiceType;
import com.io7m.repetoir.core.RPServiceType;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.input.BoundedInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Function;

/**
 * Methods to handle request size limits.
 */

public final class CARequestLimits implements RPServiceType
{
  private final Function<Long, String> requestTooLargeMessage;
  private final CAConfigurationServiceType configuration;

  /**
   * Methods to handle request size limits.
   *
   * @param inConfiguration          The configuration service
   * @param inRequestTooLargeMessage A function that formats a message
   */

  public CARequestLimits(
    final CAConfigurationServiceType inConfiguration,
    final Function<Long, String> inRequestTooLargeMessage)
  {
    this.configuration =
      Objects.requireNonNull(inConfiguration, "inConfiguration");
    this.requestTooLargeMessage =
      Objects.requireNonNull(
        inRequestTooLargeMessage, "requestTooLargeMessage");
  }

  /**
   * Bound the given servlet request to the given maximum size, raising an
   * exception if the incoming Content-Length is larger than this size.
   *
   * @param request The request
   * @param maximum The maximum size
   *
   * @return A bounded input stream
   *
   * @throws IOException            On errors
   * @throws CARequestLimitExceeded On errors
   */

  private InputStream boundedMaximumInput(
    final HttpServletRequest request,
    final long maximum)
    throws IOException, CARequestLimitExceeded
  {
    final long size;
    final var specifiedLength = request.getContentLengthLong();
    if (specifiedLength == -1L) {
      size = maximum;
    } else {
      if (Long.compareUnsigned(specifiedLength, maximum) > 0) {
        throw new CARequestLimitExceeded(
          this.requestTooLargeMessage.apply(
            Long.valueOf(specifiedLength)
          ),
          maximum,
          specifiedLength
        );
      }
      size = specifiedLength;
    }

    final var baseStream = request.getInputStream();
    return new BoundedInputStream(baseStream, size);
  }

  /**
   * Bound the given servlet request to the given maximum size for file
   * uploads.
   *
   * @param request The request
   *
   * @return A bounded input stream
   *
   * @throws IOException            On errors
   * @throws CARequestLimitExceeded On errors
   */

  public InputStream boundedMaximumInputForFileUpload(
    final HttpServletRequest request)
    throws IOException, CARequestLimitExceeded
  {
    return this.boundedMaximumInput(
      request,
      this.configuration.configuration()
        .limitsConfiguration()
        .maximumFileUploadSizeOctets()
    );
  }

  /**
   * Bound the given servlet request to the given maximum size for commands.
   *
   * @param request The request
   *
   * @return A bounded input stream
   *
   * @throws IOException            On errors
   * @throws CARequestLimitExceeded On errors
   */

  public InputStream boundedMaximumInputForCommand(
    final HttpServletRequest request)
    throws IOException, CARequestLimitExceeded
  {
    return this.boundedMaximumInput(
      request,
      this.configuration.configuration()
        .limitsConfiguration()
        .maximumCommandSizeOctets()
    );
  }

  /**
   * Bound the given servlet request to the given maximum size for login commands.
   *
   * @param request The request
   *
   * @return A bounded input stream
   *
   * @throws IOException            On errors
   * @throws CARequestLimitExceeded On errors
   */

  public InputStream boundedMaximumInputForLoginCommand(
    final HttpServletRequest request)
    throws IOException, CARequestLimitExceeded
  {
    return this.boundedMaximumInput(
      request,
      1024L
    );
  }

  @Override
  public String description()
  {
    return "Request limiting service.";
  }

  @Override
  public String toString()
  {
    return "[CARequestLimits 0x%s]"
      .formatted(Long.toUnsignedString(this.hashCode(), 16));
  }
}
