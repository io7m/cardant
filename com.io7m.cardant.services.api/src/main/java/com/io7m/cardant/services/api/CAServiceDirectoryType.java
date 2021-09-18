/*
 * Copyright © 2020 Mark Raynsford <code@io7m.com> http://io7m.com
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

package com.io7m.cardant.services.api;

import java.io.Closeable;
import java.util.List;
import java.util.Optional;

/**
 * The type of service directories.
 */

public interface CAServiceDirectoryType extends Closeable
{
  /**
   * Get an optional reference to the given service.
   *
   * @param clazz The service interface
   * @param <T>   The service type
   *
   * @return A service reference, if a service exists
   */

  <T extends CAServiceType> Optional<T> optionalService(
    Class<T> clazz);

  /**
   * Get a required reference to the given service.
   *
   * @param clazz The service interface
   * @param <T>   The service type
   *
   * @return A service reference
   *
   * @throws CAServiceException If no service exists
   */

  <T extends CAServiceType> T requireService(
    Class<T> clazz)
    throws CAServiceException;

  /**
   * Get references to the given services.
   *
   * @param clazz The service interface
   * @param <T>   The service type
   *
   * @return A service list, if services exist
   *
   * @throws CAServiceException If no required
   */

  <T extends CAServiceType> List<? extends T> optionalServices(
    Class<T> clazz)
    throws CAServiceException;
}
