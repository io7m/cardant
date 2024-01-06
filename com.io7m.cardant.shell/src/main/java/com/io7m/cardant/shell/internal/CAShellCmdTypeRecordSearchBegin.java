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


package com.io7m.cardant.shell.internal;

import com.io7m.cardant.client.api.CAClientException;
import com.io7m.cardant.model.CADescriptionMatch;
import com.io7m.cardant.model.CANameMatch;
import com.io7m.cardant.model.CATypeRecordSearchParameters;
import com.io7m.cardant.model.comparisons.CAComparisonFuzzyType;
import com.io7m.cardant.protocol.inventory.CAICommandTypeRecordSearchBegin;
import com.io7m.cardant.protocol.inventory.CAIResponseTypeRecordSearch;
import com.io7m.quarrel.core.QCommandContextType;
import com.io7m.quarrel.core.QCommandMetadata;
import com.io7m.quarrel.core.QCommandStatus;
import com.io7m.quarrel.core.QParameterNamed1;
import com.io7m.quarrel.core.QParameterNamedType;
import com.io7m.quarrel.core.QStringType.QConstant;
import com.io7m.repetoir.core.RPServiceDirectoryType;

import java.util.List;
import java.util.Optional;

import static com.io7m.quarrel.core.QCommandStatus.SUCCESS;

/**
 * "type-record-search-begin"
 */

public final class CAShellCmdTypeRecordSearchBegin
  extends CAShellCmdAbstractCR<
  CAICommandTypeRecordSearchBegin, CAIResponseTypeRecordSearch>
{
  private static final QParameterNamed1<CANameMatch> NAME_MATCH =
    new QParameterNamed1<>(
      "--name-match",
      List.of(),
      new QConstant(
        "Only include types that have names matching the given expression."),
      Optional.of(new CANameMatch(new CAComparisonFuzzyType.Anything<>())),
      CANameMatch.class
    );

  private static final QParameterNamed1<CADescriptionMatch> DESCRIPTION_MATCH =
    new QParameterNamed1<>(
      "--description-match",
      List.of(),
      new QConstant(
        "Only include types that have descriptions matching the given expression."),
      Optional.of(new CADescriptionMatch(new CAComparisonFuzzyType.Anything<>())),
      CADescriptionMatch.class
    );

  private static final QParameterNamed1<Integer> LIMIT =
    new QParameterNamed1<>(
      "--limit",
      List.of(),
      new QConstant("The maximum number of results per page."),
      Optional.of(Integer.valueOf(100)),
      Integer.class
    );

  /**
   * Construct a command.
   *
   * @param inServices The shell context
   */

  public CAShellCmdTypeRecordSearchBegin(
    final RPServiceDirectoryType inServices)
  {
    super(
      inServices,
      new QCommandMetadata(
        "type-record-search-begin",
        new QConstant("Start searching for types"),
        Optional.empty()
      ),
      CAICommandTypeRecordSearchBegin.class
    );
  }


  @Override
  public List<QParameterNamedType<?>> onListNamedParameters()
  {
    return List.of(
      NAME_MATCH,
      DESCRIPTION_MATCH,
      LIMIT);
  }

  @Override
  public QCommandStatus onExecute(
    final QCommandContextType context)
    throws Exception
  {
    final var client =
      this.client();

    final var nameMatch =
      context.parameterValue(NAME_MATCH);
    final var descriptionMatch =
      context.parameterValue(DESCRIPTION_MATCH);
    final var limit =
      context.parameterValue(LIMIT);

    final var type =
      ((CAIResponseTypeRecordSearch) client.executeOrElseThrow(
        new CAICommandTypeRecordSearchBegin(
          new CATypeRecordSearchParameters(
            nameMatch.expression(),
            descriptionMatch.expression(),
            limit.longValue()
          )
        ),
        CAClientException::ofError
      )).data();

    this.formatter().formatTypeDeclarationPage(type);
    return SUCCESS;
  }
}