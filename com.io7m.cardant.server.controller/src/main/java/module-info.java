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

/**
 * Inventory system (Server controller).
 */

module com.io7m.cardant.server.controller
{
  requires static org.osgi.annotation.versioning;
  requires static org.osgi.annotation.bundle;

  requires com.io7m.cardant.database.api;
  requires com.io7m.cardant.protocol.inventory;
  requires com.io7m.cardant.security;
  requires com.io7m.cardant.server.service.clock;
  requires com.io7m.cardant.server.service.sessions;
  requires com.io7m.cardant.server.service.telemetry.api;
  requires com.io7m.cardant.strings;
  requires com.io7m.cardant.type_packages.compiler.api;
  requires com.io7m.cardant.type_packages.resolver.api;

  requires com.io7m.anethum.api;
  requires com.io7m.medrina.api;
  requires com.io7m.repetoir.core;
  requires io.opentelemetry.api;
  requires io.opentelemetry.context;
  requires org.slf4j;

  exports com.io7m.cardant.server.controller.command_exec;
  exports com.io7m.cardant.server.controller.inventory;
}
