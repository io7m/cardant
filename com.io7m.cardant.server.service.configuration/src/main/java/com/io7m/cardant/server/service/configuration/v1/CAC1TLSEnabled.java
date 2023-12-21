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


package com.io7m.cardant.server.service.configuration.v1;

import com.io7m.blackthorne.core.BTElementHandlerConstructorType;
import com.io7m.blackthorne.core.BTElementHandlerType;
import com.io7m.blackthorne.core.BTElementParsingContextType;
import com.io7m.blackthorne.core.BTQualifiedName;
import com.io7m.cardant.tls.CATLSEnabled;

import java.util.Map;

import static com.io7m.cardant.server.service.configuration.v1.CAC1Names.tlsQName;
import static java.util.Map.entry;

final class CAC1TLSEnabled
  implements BTElementHandlerType<Object, CATLSEnabled>
{
  private CAC1StoreConfiguration keyStore;
  private CAC1StoreConfiguration trustStore;

  CAC1TLSEnabled(
    final BTElementParsingContextType context)
  {

  }

  @Override
  public Map<BTQualifiedName, BTElementHandlerConstructorType<?, ?>>
  onChildHandlersRequested(
    final BTElementParsingContextType context)
  {
    return Map.ofEntries(
      entry(tlsQName("KeyStore"), CAC1TLSKeyStore::new),
      entry(tlsQName("TrustStore"), CAC1TLSTrustStore::new)
    );
  }

  @Override
  public void onChildValueProduced(
    final BTElementParsingContextType context,
    final Object result)
  {
    switch (result) {
      case final CAC1StoreConfiguration s -> {
        switch (s.semantic()) {
          case "KeyStore" -> {
            this.keyStore = s;
          }
          case "TrustStore" -> {
            this.trustStore = s;
          }
          default -> {
            throw new IllegalArgumentException(
              "Unrecognized semantic: %s".formatted(s.semantic())
            );
          }
        }
      }
      default -> {
        throw new IllegalArgumentException(
          "Unrecognized element: %s".formatted(result)
        );
      }
    }
  }

  @Override
  public CATLSEnabled onElementFinished(
    final BTElementParsingContextType context)
    throws Exception
  {
    return new CATLSEnabled(
      this.keyStore.configuration(),
      this.trustStore.configuration()
    );
  }
}
