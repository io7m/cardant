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


package com.io7m.cardant.parsers;

import com.io7m.cardant.error_codes.CAException;
import com.io7m.cardant.model.CAMetadataElementMatchType;
import com.io7m.cardant.model.CAMetadataNameMatchType;
import com.io7m.cardant.model.CAMetadataValueMatchType;
import com.io7m.cardant.model.CAMetadataValueMatchType.IntegralMatchType;
import com.io7m.cardant.model.CAMetadataValueMatchType.IntegralMatchType.WithinRange;
import com.io7m.cardant.model.CAMetadataValueMatchType.MonetaryMatchType;
import com.io7m.cardant.model.CAMetadataValueMatchType.RealMatchType;
import com.io7m.cardant.model.CAMetadataValueMatchType.TextMatchType;
import com.io7m.cardant.model.CAMetadataValueMatchType.TimeMatchType;
import com.io7m.cardant.model.CAMoney;
import com.io7m.cardant.strings.CAStringConstantType;
import com.io7m.cardant.strings.CAStrings;
import com.io7m.jsx.SExpressionType;
import com.io7m.jsx.SExpressionType.SAtomType;
import com.io7m.jsx.SExpressionType.SList;
import com.io7m.jsx.SExpressionType.SQuotedString;
import com.io7m.jsx.SExpressionType.SSymbol;
import com.io7m.lanark.core.RDottedName;
import org.joda.money.CurrencyUnit;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.io7m.cardant.model.CAMetadataNameMatchType.AnyName.ANY_NAME;
import static com.io7m.cardant.model.CAMetadataValueMatchType.AnyValue.ANY_VALUE;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_MATCH;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_MATCH_NAME;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_NAME_MATCH;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_NAME_MATCH_EXACT;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_NAME_MATCH_EXACT_NAME;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_NAME_MATCH_NAME;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_NAME_MATCH_SEARCH;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_NAME_MATCH_SEARCH_NAME;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_INTEGRAL_RANGE;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_INTEGRAL_RANGE_NAME;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_MONEY_CURRENCY;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_MONEY_CURRENCY_NAME;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_MONEY_RANGE;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_MONEY_RANGE_NAME;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_REAL_RANGE;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_REAL_RANGE_NAME;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_TEXT_EXACT;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_TEXT_EXACT_NAME;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_TEXT_SEARCH;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_TEXT_SEARCH_NAME;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_TIME_RANGE;
import static com.io7m.cardant.strings.CAStringConstants.SYNTAX_METADATA_VALUE_MATCH_TIME_RANGE_NAME;
import static com.io7m.jlexing.core.LexicalPositions.zero;
import static java.util.Map.entry;

/**
 * Expression parsers for metadata match expressions.
 */

public final class CAMetadataMatchExpressions extends CAExpressions
{
  private static final Map<CAStringConstantType, CAStringConstantType> SYNTAX =
    Map.ofEntries(
      entry(
        SYNTAX_METADATA_VALUE_MATCH_INTEGRAL_RANGE_NAME,
        SYNTAX_METADATA_VALUE_MATCH_INTEGRAL_RANGE),
      entry(
        SYNTAX_METADATA_VALUE_MATCH_REAL_RANGE_NAME,
        SYNTAX_METADATA_VALUE_MATCH_REAL_RANGE),
      entry(
        SYNTAX_METADATA_VALUE_MATCH_MONEY_RANGE_NAME,
        SYNTAX_METADATA_VALUE_MATCH_MONEY_RANGE),
      entry(
        SYNTAX_METADATA_VALUE_MATCH_TIME_RANGE_NAME,
        SYNTAX_METADATA_VALUE_MATCH_TIME_RANGE),
      entry(
        SYNTAX_METADATA_VALUE_MATCH_MONEY_CURRENCY_NAME,
        SYNTAX_METADATA_VALUE_MATCH_MONEY_CURRENCY),
      entry(
        SYNTAX_METADATA_VALUE_MATCH_TEXT_EXACT_NAME,
        SYNTAX_METADATA_VALUE_MATCH_TEXT_EXACT),
      entry(
        SYNTAX_METADATA_VALUE_MATCH_TEXT_SEARCH_NAME,
        SYNTAX_METADATA_VALUE_MATCH_TEXT_SEARCH),
      entry(
        SYNTAX_METADATA_NAME_MATCH_EXACT_NAME,
        SYNTAX_METADATA_NAME_MATCH_EXACT),
      entry(
        SYNTAX_METADATA_NAME_MATCH_SEARCH_NAME,
        SYNTAX_METADATA_NAME_MATCH_SEARCH),
      entry(
        SYNTAX_METADATA_NAME_MATCH_NAME,
        SYNTAX_METADATA_NAME_MATCH),
      entry(
        SYNTAX_METADATA_MATCH_NAME,
        SYNTAX_METADATA_MATCH)
    );

  /**
   * Expression parsers for metadata match expressions.
   *
   * @param inStrings The string resources
   */

  public CAMetadataMatchExpressions(
    final CAStrings inStrings)
  {
    super(inStrings);
  }

  @Override
  protected Map<CAStringConstantType, CAStringConstantType> syntax()
  {
    return SYNTAX;
  }

  /**
   * Parse a match expression for metadata.
   *
   * @param text The input text
   *
   * @return A match expression
   *
   * @throws CAException On errors
   */

  public CAMetadataElementMatchType metadataMatch(
    final String text)
    throws CAException
  {
    return this.metadataMatchExpr(CAExpressions.parse(text));
  }

  private CAMetadataElementMatchType metadataMatchExpr(
    final SExpressionType expression)
    throws CAException
  {
    if (expression instanceof final SAtomType atom) {
      return switch (atom.text().toUpperCase(Locale.ROOT)) {
        case "ANYTHING" -> {
          yield CAMetadataElementMatchType.ANYTHING;
        }
        default -> {
          throw this.createParseError(expression);
        }
      };
    }

    if (expression instanceof final SList list
      && list.size() >= 2
      && list.get(0) instanceof final SAtomType head) {

      return switch (head.text().toUpperCase(Locale.ROOT)) {
        case "AND" -> {
          yield this.metadataMatchExprAnd(list);
        }
        case "OR" -> {
          yield this.metadataMatchExprOr(list);
        }
        case "MATCH" -> {
          yield this.metadataMatchExprSpecific(list);
        }
        default -> throw this.createParseError(head);
      };
    }

    throw this.createParseError(expression);
  }

  private CAMetadataElementMatchType metadataMatchExprSpecific(
    final SList list)
    throws CAException
  {
    if (list.size() == 3) {
      return new CAMetadataElementMatchType.Specific(
        this.metadataNameMatchExpr(list.get(1)),
        this.metadataValueMatchExpr(list.get(2))
      );
    }
    throw this.createParseError(list);
  }

  private CAMetadataNameMatchType metadataNameMatchExpr(
    final SExpressionType expression)
    throws CAException
  {
    if (expression instanceof final SAtomType atom) {
      return switch (atom.text().toUpperCase(Locale.ROOT)) {
        case "ANY-NAME" -> {
          yield ANY_NAME;
        }
        default -> {
          throw this.createParseError(expression);
        }
      };
    }

    if (expression instanceof final SList list
      && list.size() >= 2
      && list.get(0) instanceof final SAtomType head) {

      return switch (head.text().toUpperCase(Locale.ROOT)) {
        case "WITH-NAME-EXACT" -> {
          yield this.metadataNameMatchExprExact(list);
        }
        case "WITH-NAME-SEARCH" -> {
          yield this.metadataNameMatchExprSearch(list);
        }
        default -> throw this.createParseError(head);
      };
    }

    throw this.createParseError(expression);
  }

  private CAMetadataNameMatchType metadataNameMatchExprSearch(
    final SList list)
    throws CAException
  {
    if (list.size() == 2) {
      return new CAMetadataNameMatchType.SearchName(
        this.text(list.get(1))
      );
    }
    throw this.createParseError(list);
  }

  private CAMetadataNameMatchType metadataNameMatchExprExact(
    final SList list)
    throws CAException
  {
    if (list.size() == 2) {
      return new CAMetadataNameMatchType.ExactName(
        new RDottedName(this.text(list.get(1)))
      );
    }
    throw this.createParseError(list);
  }

  private CAMetadataElementMatchType metadataMatchExprAnd(
    final SList list)
    throws CAException
  {
    if (list.size() == 3) {
      return new CAMetadataElementMatchType.And(
        this.metadataMatchExpr(list.get(1)),
        this.metadataMatchExpr(list.get(2))
      );
    }
    throw this.createParseError(list);
  }

  private CAMetadataElementMatchType metadataMatchExprOr(
    final SList list)
    throws CAException
  {
    if (list.size() == 3) {
      return new CAMetadataElementMatchType.Or(
        this.metadataMatchExpr(list.get(1)),
        this.metadataMatchExpr(list.get(2))
      );
    }
    throw this.createParseError(list);
  }

  /**
   * Parse a metadata value match expression.
   *
   * @param text The expression text
   *
   * @return A value match expression
   *
   * @throws CAException On errors
   */

  public CAMetadataValueMatchType metadataValueMatch(
    final String text)
    throws CAException
  {
    return this.metadataValueMatchExpr(CAExpressions.parse(text));
  }

  private CAMetadataValueMatchType metadataValueMatchExpr(
    final SExpressionType expression)
    throws CAException
  {
    if (expression instanceof final SAtomType atom) {
      return switch (atom.text().toUpperCase(Locale.ROOT)) {
        case "ANY-VALUE" -> {
          yield ANY_VALUE;
        }
        default -> {
          throw this.createParseError(expression);
        }
      };
    }

    if (expression instanceof final SList list
      && list.size() >= 2
      && list.get(0) instanceof final SAtomType head) {

      return switch (head.text().toUpperCase(Locale.ROOT)) {
        case "WITHIN-RANGE-INTEGRAL" -> {
          yield this.metadataValueMatchExprWithinRangeIntegral(list);
        }
        case "WITHIN-RANGE-REAL" -> {
          yield this.metadataValueMatchExprWithinRangeReal(list);
        }
        case "WITHIN-RANGE-TIME" -> {
          yield this.metadataValueMatchExprWithinRangeTime(list);
        }
        case "WITHIN-RANGE-MONETARY" -> {
          yield this.metadataValueMatchExprWithinRangeMonetary(list);
        }
        case "WITH-CURRENCY" -> {
          yield this.metadataValueMatchExprWithCurrency(list);
        }
        case "WITH-TEXT-EXACT" -> {
          yield this.metadataValueMatchExprWithTextExact(list);
        }
        case "WITH-TEXT-SEARCH" -> {
          yield this.metadataValueMatchExprWithTextSearch(list);
        }
        default -> throw this.createParseError(head);
      };
    }

    throw this.createParseError(expression);
  }

  private CAMetadataValueMatchType metadataValueMatchExprWithTextExact(
    final SList list)
    throws CAException
  {
    if (list.size() == 2) {
      return new TextMatchType.ExactTextValue(
        this.text(list.get(1))
      );
    }
    throw this.createParseError(list);
  }

  private CAMetadataValueMatchType metadataValueMatchExprWithTextSearch(
    final SList list)
    throws CAException
  {
    if (list.size() == 2) {
      return new TextMatchType.Search(
        this.text(list.get(1))
      );
    }
    throw this.createParseError(list);
  }

  private String text(
    final SExpressionType expr)
    throws CAException
  {
    if (expr instanceof final SAtomType atom) {
      return atom.text();
    }

    throw this.createParseError(expr);
  }

  private CAMetadataValueMatchType metadataValueMatchExprWithCurrency(
    final SList list)
    throws CAException
  {
    if (list.size() == 2) {
      return new MonetaryMatchType.WithCurrency(
        this.currency(list.get(1))
      );
    }
    throw this.createParseError(list);
  }

  private CurrencyUnit currency(
    final SExpressionType expr)
    throws CAException
  {
    if (expr instanceof final SAtomType atom) {
      try {
        return CurrencyUnit.of(atom.text());
      } catch (final Exception e) {
        throw this.createParseError(expr, e);
      }
    }

    throw this.createParseError(expr);
  }

  private CAMetadataValueMatchType metadataValueMatchExprWithinRangeMonetary(
    final SList list)
    throws CAException
  {
    if (list.size() == 3) {
      return new MonetaryMatchType.WithinRange(
        this.monetary(list.get(1)),
        this.monetary(list.get(2))
      );
    }
    throw this.createParseError(list);
  }

  private CAMetadataValueMatchType metadataValueMatchExprWithinRangeIntegral(
    final SList list)
    throws CAException
  {
    if (list.size() == 3) {
      return new WithinRange(
        this.integer(list.get(1)),
        this.integer(list.get(2))
      );
    }
    throw this.createParseError(list);
  }

  private CAMetadataValueMatchType metadataValueMatchExprWithinRangeReal(
    final SList list)
    throws CAException
  {
    if (list.size() == 3) {
      return new RealMatchType.WithinRange(
        this.real(list.get(1)),
        this.real(list.get(2))
      );
    }
    throw this.createParseError(list);
  }

  private CAMetadataValueMatchType metadataValueMatchExprWithinRangeTime(
    final SList list)
    throws CAException
  {
    if (list.size() == 3) {
      return new TimeMatchType.WithinRange(
        this.time(list.get(1)),
        this.time(list.get(2))
      );
    }
    throw this.createParseError(list);
  }

  private BigDecimal monetary(
    final SExpressionType expr)
    throws CAException
  {
    if (expr instanceof final SAtomType atom) {
      try {
        return CAMoney.money(atom.text());
      } catch (final Exception e) {
        throw this.createParseError(expr, e);
      }
    }

    throw this.createParseError(expr);
  }

  private OffsetDateTime time(
    final SExpressionType expr)
    throws CAException
  {
    if (expr instanceof final SAtomType atom) {
      try {
        return OffsetDateTime.parse(atom.text());
      } catch (final Exception e) {
        throw this.createParseError(expr, e);
      }
    }

    throw this.createParseError(expr);
  }

  private double real(
    final SExpressionType expr)
    throws CAException
  {
    if (expr instanceof final SAtomType atom) {
      try {
        return Double.parseDouble(atom.text());
      } catch (final Exception e) {
        throw this.createParseError(expr, e);
      }
    }

    throw this.createParseError(expr);
  }

  private long integer(
    final SExpressionType expr)
    throws CAException
  {
    if (expr instanceof final SAtomType atom) {
      try {
        return Long.parseLong(atom.text());
      } catch (final Exception e) {
        throw this.createParseError(expr, e);
      }
    }

    throw this.createParseError(expr);
  }

  /**
   * Serialize a match expression to a string.
   *
   * @param value The expression
   *
   * @return The serialized text
   *
   * @throws CAException On errors
   */

  public String metadataMatchSerializeToString(
    final CAMetadataElementMatchType value)
    throws CAException
  {
    return CAExpressions.serialize(this.metadataMatchSerialize(value));
  }

  /**
   * Serialize a match expression.
   *
   * @param value The expression
   *
   * @return The serialized expression
   */

  public SExpressionType metadataMatchSerialize(
    final CAMetadataElementMatchType value)
  {
    if (value instanceof final CAMetadataElementMatchType.Specific specific) {
      if (specific.value() == ANY_VALUE && specific.name() == ANY_NAME) {
        return new SSymbol(zero(), "anything");
      }

      return new SList(
        zero(),
        true,
        List.of(
          new SSymbol(zero(), "match"),
          metadataMatchNameSerialize(specific.name()),
          metadataMatchValueSerialize(specific.value())
        )
      );
    }

    if (value instanceof final CAMetadataElementMatchType.Or or) {
      return new SList(
        zero(),
        true,
        List.of(
          new SSymbol(zero(), "or"),
          this.metadataMatchSerialize(or.e0()),
          this.metadataMatchSerialize(or.e1())
        )
      );
    }

    if (value instanceof final CAMetadataElementMatchType.And and) {
      return new SList(
        zero(),
        true,
        List.of(
          new SSymbol(zero(), "and"),
          this.metadataMatchSerialize(and.e0()),
          this.metadataMatchSerialize(and.e1())
        )
      );
    }

    throw new IllegalStateException();
  }

  private static SExpressionType metadataMatchValueSerialize(
    final CAMetadataValueMatchType value)
  {
    if (value instanceof CAMetadataValueMatchType.AnyValue) {
      return new SSymbol(zero(), "any-value");
    }

    if (value instanceof final MonetaryMatchType.WithCurrency w) {
      return metadataValueMatchSerializeWithCurrency(w);
    }

    if (value instanceof final MonetaryMatchType.WithinRange w) {
      return metadataValueMatchSerializeWithinRangeMonetary(w);
    }

    if (value instanceof final IntegralMatchType.WithinRange w) {
      return metadataValueMatchSerializeWithinRangeIntegral(w);
    }

    if (value instanceof final RealMatchType.WithinRange w) {
      return metadataValueMatchSerializeWithinRangeReal(w);
    }

    if (value instanceof final TimeMatchType.WithinRange w) {
      return metadataValueMatchSerializeWithinRangeTime(w);
    }

    if (value instanceof final TextMatchType.ExactTextValue w) {
      return metadataValueMatchSerializeTextExact(w);
    }

    if (value instanceof final TextMatchType.Search w) {
      return metadataValueMatchSerializeTextSearch(w);
    }

    throw new IllegalStateException();
  }

  private static SList metadataValueMatchSerializeTextSearch(
    final TextMatchType.Search w)
  {
    return new SList(
      zero(),
      true,
      List.of(
        new SSymbol(zero(), "with-text-search"),
        new SQuotedString(zero(), w.query())
      )
    );
  }

  private static SList metadataValueMatchSerializeTextExact(
    final TextMatchType.ExactTextValue w)
  {
    return new SList(
      zero(),
      true,
      List.of(
        new SSymbol(zero(), "with-text-exact"),
        new SQuotedString(zero(), w.text())
      )
    );
  }

  private static SList metadataValueMatchSerializeWithinRangeTime(
    final TimeMatchType.WithinRange w)
  {
    return new SList(
      zero(),
      true,
      List.of(
        new SSymbol(zero(), "within-range-time"),
        new SSymbol(zero(), w.lower().toString()),
        new SSymbol(zero(), w.upper().toString())
      )
    );
  }

  private static SList metadataValueMatchSerializeWithinRangeReal(
    final RealMatchType.WithinRange w)
  {
    return new SList(
      zero(),
      true,
      List.of(
        new SSymbol(zero(), "within-range-real"),
        new SSymbol(zero(), Double.toString(w.lower())),
        new SSymbol(zero(), Double.toString(w.upper()))
      )
    );
  }

  private static SList metadataValueMatchSerializeWithinRangeIntegral(
    final WithinRange w)
  {
    return new SList(
      zero(),
      true,
      List.of(
        new SSymbol(zero(), "within-range-integral"),
        new SSymbol(zero(), Long.toString(w.lower())),
        new SSymbol(zero(), Long.toString(w.upper()))
      )
    );
  }

  private static SList metadataValueMatchSerializeWithinRangeMonetary(
    final MonetaryMatchType.WithinRange w)
  {
    return new SList(
      zero(),
      true,
      List.of(
        new SSymbol(zero(), "within-range-monetary"),
        new SSymbol(zero(), w.lower().toString()),
        new SSymbol(zero(), w.upper().toString())
      )
    );
  }

  private static SList metadataValueMatchSerializeWithCurrency(
    final MonetaryMatchType.WithCurrency w)
  {
    return new SList(
      zero(),
      true,
      List.of(
        new SSymbol(zero(), "with-currency"),
        new SSymbol(zero(), w.currency().getCode())
      )
    );
  }

  private static SExpressionType metadataMatchNameSerialize(
    final CAMetadataNameMatchType name)
  {
    if (name instanceof CAMetadataNameMatchType.AnyName) {
      return new SSymbol(zero(), "any-name");
    }

    if (name instanceof final CAMetadataNameMatchType.ExactName w) {
      return metadataMatchNameSerializeExact(w);
    }

    if (name instanceof final CAMetadataNameMatchType.SearchName w) {
      return metadataMatchNameSerializeSearch(w);
    }

    throw new IllegalStateException();
  }

  private static SList metadataMatchNameSerializeSearch(
    final CAMetadataNameMatchType.SearchName w)
  {
    return new SList(
      zero(),
      true,
      List.of(
        new SSymbol(zero(), "with-name-search"),
        new SQuotedString(zero(), w.query())
      )
    );
  }

  private static SList metadataMatchNameSerializeExact(
    final CAMetadataNameMatchType.ExactName w)
  {
    return new SList(
      zero(),
      true,
      List.of(
        new SSymbol(zero(), "with-name-exact"),
        new SQuotedString(zero(), w.name().value())
      )
    );
  }
}