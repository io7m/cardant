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

package com.io7m.cardant.error_codes;

/**
 * <p>The standard error codes.</p>
 * <p>Note: This file is generated from codes.txt and should not be
 * hand-edited.</p>
 */
public final class CAStandardErrorCodes
{
  private CAStandardErrorCodes()
  {
  }

  private static final CAErrorCode ERROR_AUTHENTICATION =
    new CAErrorCode("error-authentication");

  /**
   * Authentication failed.
   *
   * @return The error code
   */
  public static CAErrorCode errorAuthentication()
  {
    return ERROR_AUTHENTICATION;
  }

  private static final CAErrorCode ERROR_CYCLIC =
    new CAErrorCode("error-cyclic");

  /**
   * A cycle was introduced into a structure that is not supposed to be cyclic.
   *
   * @return The error code
   */
  public static CAErrorCode errorCyclic()
  {
    return ERROR_CYCLIC;
  }

  private static final CAErrorCode ERROR_DUPLICATE =
    new CAErrorCode("error-duplicate");

  /**
   * An object already exists.
   *
   * @return The error code
   */
  public static CAErrorCode errorDuplicate()
  {
    return ERROR_DUPLICATE;
  }

  private static final CAErrorCode ERROR_HTTP_METHOD =
    new CAErrorCode("error-http-method");

  /**
   * The wrong HTTP method was used.
   *
   * @return The error code
   */
  public static CAErrorCode errorHttpMethod()
  {
    return ERROR_HTTP_METHOD;
  }

  private static final CAErrorCode ERROR_IO =
    new CAErrorCode("error-io");

  /**
   * An internal I/O error.
   *
   * @return The error code
   */
  public static CAErrorCode errorIo()
  {
    return ERROR_IO;
  }

  private static final CAErrorCode ERROR_NONEXISTENT =
    new CAErrorCode("error-nonexistent");

  /**
   * A requested object was not found.
   *
   * @return The error code
   */
  public static CAErrorCode errorNonexistent()
  {
    return ERROR_NONEXISTENT;
  }

  private static final CAErrorCode ERROR_NOT_LOGGED_IN =
    new CAErrorCode("error-not-logged-in");

  /**
   * A user is trying to perform an operation without having logged in.
   *
   * @return The error code
   */
  public static CAErrorCode errorNotLoggedIn()
  {
    return ERROR_NOT_LOGGED_IN;
  }

  private static final CAErrorCode ERROR_NO_SUPPORTED_PROTOCOLS =
    new CAErrorCode("error-no-supported-protocols");

  /**
   * The client and server have no supported protocols in common.
   *
   * @return The error code
   */
  public static CAErrorCode errorNoSupportedProtocols()
  {
    return ERROR_NO_SUPPORTED_PROTOCOLS;
  }

  private static final CAErrorCode ERROR_OPERATION_NOT_PERMITTED =
    new CAErrorCode("error-operation-not-permitted");

  /**
   * A generic "operation not permitted" error.
   *
   * @return The error code
   */
  public static CAErrorCode errorOperationNotPermitted()
  {
    return ERROR_OPERATION_NOT_PERMITTED;
  }

  private static final CAErrorCode ERROR_PROTOCOL =
    new CAErrorCode("error-protocol");

  /**
   * A client sent a broken message of some kind.
   *
   * @return The error code
   */
  public static CAErrorCode errorProtocol()
  {
    return ERROR_PROTOCOL;
  }

  private static final CAErrorCode ERROR_RESOURCE_CLOSE_FAILED =
    new CAErrorCode("error-resource-close-failed");

  /**
   * One or more resources failed to close.
   *
   * @return The error code
   */
  public static CAErrorCode errorResourceCloseFailed()
  {
    return ERROR_RESOURCE_CLOSE_FAILED;
  }

  private static final CAErrorCode ERROR_SECURITY_POLICY_DENIED =
    new CAErrorCode("error-security-policy-denied");

  /**
   * An operation was denied by the security policy.
   *
   * @return The error code
   */
  public static CAErrorCode errorSecurityPolicyDenied()
  {
    return ERROR_SECURITY_POLICY_DENIED;
  }

  private static final CAErrorCode ERROR_SQL_FOREIGN_KEY =
    new CAErrorCode("error-sql-foreign-key");

  /**
   * A violation of an SQL foreign key integrity constraint.
   *
   * @return The error code
   */
  public static CAErrorCode errorSqlForeignKey()
  {
    return ERROR_SQL_FOREIGN_KEY;
  }

  private static final CAErrorCode ERROR_SQL_REVISION =
    new CAErrorCode("error-sql-revision");

  /**
   * An internal SQL database error relating to database revisioning.
   *
   * @return The error code
   */
  public static CAErrorCode errorSqlRevision()
  {
    return ERROR_SQL_REVISION;
  }

  private static final CAErrorCode ERROR_SQL_UNIQUE =
    new CAErrorCode("error-sql-unique");

  /**
   * A violation of an SQL uniqueness constraint.
   *
   * @return The error code
   */
  public static CAErrorCode errorSqlUnique()
  {
    return ERROR_SQL_UNIQUE;
  }

  private static final CAErrorCode ERROR_SQL_UNSUPPORTED_QUERY_CLASS =
    new CAErrorCode("error-sql-unsupported-query-class");

  /**
   * An attempt was made to use a query class that is unsupported.
   *
   * @return The error code
   */
  public static CAErrorCode errorSqlUnsupportedQueryClass()
  {
    return ERROR_SQL_UNSUPPORTED_QUERY_CLASS;
  }

  private static final CAErrorCode ERROR_SQL =
    new CAErrorCode("error-sql");

  /**
   * An internal SQL database error.
   *
   * @return The error code
   */
  public static CAErrorCode errorSql()
  {
    return ERROR_SQL;
  }

  private static final CAErrorCode ERROR_TRASCO =
    new CAErrorCode("error-trasco");

  /**
   * An error raised by the Trasco database versioning library.
   *
   * @return The error code
   */
  public static CAErrorCode errorTrasco()
  {
    return ERROR_TRASCO;
  }

  private static final CAErrorCode ERROR_USER_NONEXISTENT =
    new CAErrorCode("error-user-nonexistent");

  /**
   * An attempt was made to reference a user that does not exist.
   *
   * @return The error code
   */
  public static CAErrorCode errorUserNonexistent()
  {
    return ERROR_USER_NONEXISTENT;
  }
}
