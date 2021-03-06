/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
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
 * Inventory system (GUI)
 */

module com.io7m.cardant.gui
{
  requires static org.osgi.annotation.versioning;
  requires static org.osgi.annotation.bundle;

  requires com.io7m.cardant.client.preferences.api;
  requires com.io7m.cardant.client.preferences.vanilla;
  requires com.io7m.cardant.client.transfer.api;
  requires com.io7m.cardant.client.transfer.vanilla;
  requires com.io7m.cardant.client.vanilla;

  requires com.io7m.jade.api;
  requires com.io7m.jade.vanilla;
  requires com.io7m.jaffirm.core;
  requires com.io7m.junreachable.core;
  requires com.io7m.jwheatsheaf.api;
  requires com.io7m.jwheatsheaf.filter.glob;
  requires com.io7m.jwheatsheaf.oxygen;
  requires com.io7m.jwheatsheaf.ui;
  requires com.io7m.jxtrand.vanilla;
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires org.apache.commons.io;
  requires org.apache.commons.lang3;
  requires org.slf4j;

  requires transitive com.io7m.cardant.client.api;
  requires transitive com.io7m.cardant.model;
  requires transitive com.io7m.cardant.services.api;

  opens com.io7m.cardant.gui.internal
    to com.io7m.jxtrand.vanilla, javafx.fxml;
  opens com.io7m.cardant.gui.internal.model
    to com.io7m.jxtrand.vanilla, javafx.fxml;
  opens com.io7m.cardant.gui.internal.views
    to com.io7m.jxtrand.vanilla, javafx.fxml;

  exports com.io7m.cardant.gui;
  exports com.io7m.cardant.gui.internal.views;
}
