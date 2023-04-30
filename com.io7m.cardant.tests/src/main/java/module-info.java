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
 * Test suite.
 */

open module com.io7m.cardant.tests
{
  requires com.io7m.cardant.client.api;
  requires com.io7m.cardant.client.basic;
  requires com.io7m.cardant.client.preferences.api;
  requires com.io7m.cardant.client.preferences.vanilla;
  requires com.io7m.cardant.client.transfer.api;
  requires com.io7m.cardant.client.transfer.vanilla;
  requires com.io7m.cardant.database.api;
  requires com.io7m.cardant.database.postgres;
  requires com.io7m.cardant.error_codes;
  requires com.io7m.cardant.gui.main;
  requires com.io7m.cardant.gui;
  requires com.io7m.cardant.model;
  requires com.io7m.cardant.protocol.api;
  requires com.io7m.cardant.protocol.inventory.cb;
  requires com.io7m.cardant.protocol.inventory;
  requires com.io7m.cardant.security;
  requires com.io7m.cardant.server.api;
  requires com.io7m.cardant.server.basic;
  requires com.io7m.cardant.server.controller;
  requires com.io7m.cardant.server.http;
  requires com.io7m.cardant.server.inventory.v1;
  requires com.io7m.cardant.server.main;
  requires com.io7m.cardant.server.service.clock;
  requires com.io7m.cardant.server.service.configuration;
  requires com.io7m.cardant.server.service.idstore;
  requires com.io7m.cardant.server.service.maintenance;
  requires com.io7m.cardant.server.service.reqlimit;
  requires com.io7m.cardant.server.service.sessions;
  requires com.io7m.cardant.server.service.telemetry.api;
  requires com.io7m.cardant.server.service.telemetry.otp;
  requires com.io7m.cardant.server.service.verdant;

  requires com.io7m.jmulticlose.core;
  requires com.io7m.verdant.core.cb;
  requires com.io7m.verdant.core;
  requires jakarta.mail;
  requires java.net.http;
  requires java.sql;
  requires jul.to.slf4j;
  requires org.apache.commons.io;
  requires org.eclipse.jetty.server;
  requires org.eclipse.jetty.servlet;
  requires org.mockito;
  requires org.slf4j;

  requires transitive org.junit.jupiter.api;
  requires transitive org.junit.jupiter.engine;
  requires transitive org.junit.platform.commons;
  requires transitive org.junit.platform.engine;
}