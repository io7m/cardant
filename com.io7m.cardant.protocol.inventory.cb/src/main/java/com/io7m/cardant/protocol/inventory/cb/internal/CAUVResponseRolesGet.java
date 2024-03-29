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
import com.io7m.cardant.protocol.inventory.CAIResponseRolesGet;
import com.io7m.cardant.protocol.inventory.cb.CAI1ResponseRolesGet;
import com.io7m.cedarbridge.runtime.api.CBString;
import com.io7m.cedarbridge.runtime.api.CBUUID;
import com.io7m.cedarbridge.runtime.convenience.CBLists;
import com.io7m.cedarbridge.runtime.convenience.CBSets;
import com.io7m.medrina.api.MRoleName;

/**
 * A validator.
 */

public enum CAUVResponseRolesGet
  implements CAProtocolMessageValidatorType<
  CAIResponseRolesGet, CAI1ResponseRolesGet>
{
  /**
   * A validator.
   */

  RESPONSE_ROLES_GET;

  @Override
  public CAI1ResponseRolesGet convertToWire(
    final CAIResponseRolesGet c)
  {
    return new CAI1ResponseRolesGet(
      new CBUUID(c.requestId()),
      CBLists.ofCollection(c.roles(), x -> new CBString(x.value().value()))
    );
  }

  @Override
  public CAIResponseRolesGet convertFromWire(
    final CAI1ResponseRolesGet m)
  {
    return new CAIResponseRolesGet(
      m.fieldRequestId().value(),
      CBSets.toSet(m.fieldRoles(), x -> MRoleName.of(x.value()))
    );
  }
}
