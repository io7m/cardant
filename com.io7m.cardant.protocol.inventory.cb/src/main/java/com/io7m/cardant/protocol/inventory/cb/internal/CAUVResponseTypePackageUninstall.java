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


package com.io7m.cardant.protocol.inventory.cb.internal;

import com.io7m.cardant.protocol.api.CAProtocolMessageValidatorType;
import com.io7m.cardant.protocol.inventory.CAIResponseTypePackageUninstall;
import com.io7m.cardant.protocol.inventory.cb.CAI1ResponseTypePackageUninstall;
import com.io7m.cedarbridge.runtime.api.CBUUID;

/**
 * A validator.
 */

public enum CAUVResponseTypePackageUninstall
  implements CAProtocolMessageValidatorType<
  CAIResponseTypePackageUninstall, CAI1ResponseTypePackageUninstall>
{
  /**
   * A validator.
   */

  RESPONSE_TYPE_PACKAGE_UNINSTALL;

  @Override
  public CAI1ResponseTypePackageUninstall convertToWire(
    final CAIResponseTypePackageUninstall c)
  {
    return new CAI1ResponseTypePackageUninstall(
      new CBUUID(c.requestId())
    );
  }

  @Override
  public CAIResponseTypePackageUninstall convertFromWire(
    final CAI1ResponseTypePackageUninstall c)
  {
    return new CAIResponseTypePackageUninstall(
      c.fieldRequestId().value()
    );
  }
}
