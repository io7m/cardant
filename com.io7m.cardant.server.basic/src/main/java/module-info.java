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

import com.io7m.cardant.server.api.CAServerFactoryType;
import com.io7m.cardant.server.basic.CAServers;
import com.io7m.cardant.server.service.telemetry.api.CAServerTelemetryServiceFactoryType;

/**
 * Identity server (Server basic implementation)
 */

module com.io7m.cardant.server.basic
{
  requires static org.osgi.annotation.bundle;
  requires static org.osgi.annotation.versioning;

  uses CAServerTelemetryServiceFactoryType;

  requires com.io7m.cardant.database.api;
  requires com.io7m.cardant.protocol.inventory.cb;
  requires com.io7m.cardant.protocol.inventory;
  requires com.io7m.cardant.security;
  requires com.io7m.cardant.server.api;
  requires com.io7m.cardant.server.inventory.v1;
  requires com.io7m.cardant.server.service.clock;
  requires com.io7m.cardant.server.service.configuration;
  requires com.io7m.cardant.server.service.health;
  requires com.io7m.cardant.server.service.idstore;
  requires com.io7m.cardant.server.service.maintenance;
  requires com.io7m.cardant.server.service.reqlimit;
  requires com.io7m.cardant.server.service.sessions;
  requires com.io7m.cardant.server.service.telemetry.api;
  requires com.io7m.cardant.server.service.tls;
  requires com.io7m.cardant.server.service.verdant;
  requires com.io7m.cardant.type_packages.checker.api;
  requires com.io7m.cardant.type_packages.checkers;
  requires com.io7m.cardant.type_packages.compiler.api;
  requires com.io7m.cardant.type_packages.compilers;
  requires com.io7m.cardant.type_packages.parser.api;
  requires com.io7m.cardant.type_packages.parsers;

  requires com.io7m.jmulticlose.core;
  requires io.helidon.webserver;
  requires io.opentelemetry.api;

  provides CAServerFactoryType
    with CAServers;

  exports com.io7m.cardant.server.basic;
}
