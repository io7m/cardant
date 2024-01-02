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


package com.io7m.cardant.tests.server.controller;

import com.io7m.cardant.database.api.CADatabaseException;
import com.io7m.cardant.database.api.CADatabaseQueriesLocationsType.LocationGetType;
import com.io7m.cardant.database.api.CADatabaseQueriesLocationsType.LocationTypesAssignType;
import com.io7m.cardant.database.api.CADatabaseQueriesTypesType.TypeRecordGetType;
import com.io7m.cardant.model.CALocation;
import com.io7m.cardant.model.CALocationID;
import com.io7m.cardant.model.CATypeField;
import com.io7m.cardant.model.CATypeRecord;
import com.io7m.cardant.model.CATypeScalarType.Integral;
import com.io7m.cardant.model.type_package.CATypePackageIdentifier;
import com.io7m.cardant.protocol.inventory.CAICommandLocationTypesAssign;
import com.io7m.cardant.security.CASecurity;
import com.io7m.cardant.server.controller.command_exec.CACommandExecutionFailure;
import com.io7m.cardant.server.controller.inventory.CAICmdLocationTypesAssign;
import com.io7m.lanark.core.RDottedName;
import com.io7m.medrina.api.MMatchActionType.MMatchActionWithName;
import com.io7m.medrina.api.MMatchObjectType.MMatchObjectWithType;
import com.io7m.medrina.api.MMatchSubjectType.MMatchSubjectWithRolesAny;
import com.io7m.medrina.api.MPolicy;
import com.io7m.medrina.api.MRule;
import com.io7m.medrina.api.MRuleName;
import com.io7m.verona.core.Version;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import static com.io7m.cardant.error_codes.CAStandardErrorCodes.errorNonexistent;
import static com.io7m.cardant.error_codes.CAStandardErrorCodes.errorSecurityPolicyDenied;
import static com.io7m.cardant.error_codes.CAStandardErrorCodes.errorTypeCheckFailed;
import static com.io7m.cardant.security.CASecurityPolicy.INVENTORY_LOCATIONS;
import static com.io7m.cardant.security.CASecurityPolicy.ROLE_INVENTORY_LOCATIONS_WRITER;
import static com.io7m.cardant.security.CASecurityPolicy.WRITE;
import static com.io7m.medrina.api.MRuleConclusion.ALLOW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @see CAICmdLocationTypesAssign
 */

public final class CAICmdLocationTypesAssignTest
  extends CACmdAbstractContract
{
  private static final CATypePackageIdentifier P =
    new CATypePackageIdentifier(
      new RDottedName("com.io7m"),
      Version.of(1, 0, 0)
    );

  private static final CALocationID LOCATION_ID = CALocationID.random();

  /**
   * Editing a location requires the permission to WRITE to INVENTORY_LOCATIONS.
   *
   * @throws Exception On errors
   */

  @Test
  public void testNotAllowed0()
    throws Exception
  {
    /* Arrange. */

    final var context =
      this.createContext();

    /* Act. */

    final var handler =
      new CAICmdLocationTypesAssign();
    final var ex =
      assertThrows(CACommandExecutionFailure.class, () -> {
        handler.execute(
          context,
          new CAICommandLocationTypesAssign(
            LOCATION_ID,
            Set.of(new RDottedName("t")))
        );
      });

    /* Assert. */

    assertEquals(errorSecurityPolicyDenied(), ex.errorCode());
  }

  /**
   * Assigning a type to a location works.
   *
   * @throws Exception On errors
   */

  @Test
  public void testAssign()
    throws Exception
  {
    /* Arrange. */

    final var locationTypeAssign =
      mock(LocationTypesAssignType.class);
    final var typeGet =
      mock(TypeRecordGetType.class);
    final var locationGet =
      mock(LocationGetType.class);
    final var transaction =
      this.transaction();

    when(transaction.queries(LocationTypesAssignType.class))
      .thenReturn(locationTypeAssign);
    when(transaction.queries(LocationGetType.class))
      .thenReturn(locationGet);
    when(transaction.queries(TypeRecordGetType.class))
      .thenReturn(typeGet);

    when(typeGet.execute(any()))
      .thenReturn(Optional.of(
        new CATypeRecord(
          P,
          new RDottedName("t"),
          "A type",
          Map.of()
        )
      ));

    when(locationGet.execute(any()))
      .thenReturn(Optional.of(new CALocation(
        LOCATION_ID,
        Optional.empty(),
        "Location",
        Collections.emptySortedMap(),
        Collections.emptySortedMap(),
        new TreeSet<>(Set.of(new RDottedName("t")))
      )));

    CASecurity.setPolicy(new MPolicy(List.of(
      new MRule(
        MRuleName.of("rule0"),
        "",
        ALLOW,
        new MMatchSubjectWithRolesAny(Set.of(ROLE_INVENTORY_LOCATIONS_WRITER)),
        new MMatchObjectWithType(INVENTORY_LOCATIONS.type()),
        new MMatchActionWithName(WRITE)
      )
    )));

    this.setRoles(ROLE_INVENTORY_LOCATIONS_WRITER);

    final var context =
      this.createContext();

    /* Act. */

    final var handler = new CAICmdLocationTypesAssign();
    handler.execute(
      context,
      new CAICommandLocationTypesAssign(
        LOCATION_ID,
        Set.of(new RDottedName("t"))));

    /* Assert. */

    verify(transaction)
      .queries(LocationGetType.class);
    verify(transaction)
      .queries(TypeRecordGetType.class);
    verify(transaction)
      .queries(LocationTypesAssignType.class);
    verify(locationGet)
      .execute(LOCATION_ID);

    verifyNoMoreInteractions(transaction);
    verifyNoMoreInteractions(locationGet);
  }

  /**
   * Assigning a type fails if type checking fails.
   *
   * @throws Exception On errors
   */

  @Test
  public void testAssignCheckFailed()
    throws Exception
  {
    /* Arrange. */

    final var locationTypeAssign =
      mock(LocationTypesAssignType.class);
    final var typeGet =
      mock(TypeRecordGetType.class);
    final var locationGet =
      mock(LocationGetType.class);
    final var transaction =
      this.transaction();

    when(transaction.queries(LocationTypesAssignType.class))
      .thenReturn(locationTypeAssign);
    when(transaction.queries(LocationGetType.class))
      .thenReturn(locationGet);
    when(transaction.queries(TypeRecordGetType.class))
      .thenReturn(typeGet);

    when(typeGet.execute(any()))
      .thenReturn(Optional.of(
        new CATypeRecord(
          P,
          new RDottedName("t"),
          "A type",
          Map.of(
            new RDottedName("a"),
            new CATypeField(
              new RDottedName("a"),
              "A field",
              new Integral(P, new RDottedName("z"), "x", 23L, 1000L),
              true
            )
          )
        )
      ));

    when(locationGet.execute(any()))
      .thenReturn(Optional.of(new CALocation(
        LOCATION_ID,
        Optional.empty(),
        "Location",
        Collections.emptySortedMap(),
        Collections.emptySortedMap(),
        new TreeSet<>(Set.of(new RDottedName("t")))
      )));

    CASecurity.setPolicy(new MPolicy(List.of(
      new MRule(
        MRuleName.of("rule0"),
        "",
        ALLOW,
        new MMatchSubjectWithRolesAny(Set.of(ROLE_INVENTORY_LOCATIONS_WRITER)),
        new MMatchObjectWithType(INVENTORY_LOCATIONS.type()),
        new MMatchActionWithName(WRITE)
      )
    )));

    this.setRoles(ROLE_INVENTORY_LOCATIONS_WRITER);

    final var context =
      this.createContext();

    /* Act. */

    final var handler = new CAICmdLocationTypesAssign();

    final var ex =
      assertThrows(CACommandExecutionFailure.class, () -> {
        handler.execute(
          context,
          new CAICommandLocationTypesAssign(
            LOCATION_ID,
            Set.of(new RDottedName("t")))
        );
      });

    /* Assert. */

    assertEquals(errorTypeCheckFailed(), ex.errorCode());

    verify(transaction)
      .queries(LocationGetType.class);
    verify(transaction)
      .queries(TypeRecordGetType.class);
    verify(transaction)
      .queries(LocationTypesAssignType.class);
    verify(locationGet)
      .execute(LOCATION_ID);

    verifyNoMoreInteractions(transaction);
    verifyNoMoreInteractions(locationGet);
  }

  /**
   * Assigning a type to a nonexistent location fails.
   *
   * @throws Exception On errors
   */

  @Test
  public void testAssignNonexistent()
    throws Exception
  {
    /* Arrange. */

    final var locationTypeAssign =
      mock(LocationTypesAssignType.class);
    final var locationGet =
      mock(LocationGetType.class);
    final var transaction =
      this.transaction();

    when(transaction.queries(LocationTypesAssignType.class))
      .thenReturn(locationTypeAssign);
    when(transaction.queries(LocationGetType.class))
      .thenReturn(locationGet);

    doThrow(
      new CADatabaseException(
        "Nonexistent.",
        errorNonexistent(),
        Map.of(),
        Optional.empty())
    ).when(locationTypeAssign)
      .execute(any());

    CASecurity.setPolicy(new MPolicy(List.of(
      new MRule(
        MRuleName.of("rule0"),
        "",
        ALLOW,
        new MMatchSubjectWithRolesAny(Set.of(ROLE_INVENTORY_LOCATIONS_WRITER)),
        new MMatchObjectWithType(INVENTORY_LOCATIONS.type()),
        new MMatchActionWithName(WRITE)
      )
    )));

    this.setRoles(ROLE_INVENTORY_LOCATIONS_WRITER);

    final var context =
      this.createContext();

    /* Act. */

    final var handler = new CAICmdLocationTypesAssign();

    final var ex =
      assertThrows(CACommandExecutionFailure.class, () -> {
        handler.execute(
          context,
          new CAICommandLocationTypesAssign(
            LOCATION_ID,
            Set.of(new RDottedName("t")))
        );
      });

    /* Assert. */

    assertEquals(errorNonexistent(), ex.errorCode());
  }
}
