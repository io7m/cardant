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

package com.io7m.cardant.tests.server.v1;

import com.io7m.cardant.server.api.CAServerType;
import com.io7m.cardant.tests.server.CAServerExtension;
import com.io7m.idstore.tests.extensions.IdTestExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The health endpoint.
 */

@ExtendWith({IdTestExtension.class, CAServerExtension.class})
public final class CAI1ServerTest
{
  @Test
  public void testHealth(
    final CAServerType server)
    throws Exception
  {
    final var client =
      HttpClient.newHttpClient();

    final var request =
      HttpRequest.newBuilder(server.inventoryAPI().resolve("/health"))
        .GET()
        .build();

    final var response =
      client.send(request, HttpResponse.BodyHandlers.ofString());

    assertEquals(200, response.statusCode());
    assertEquals("OK", response.body());
  }
}
