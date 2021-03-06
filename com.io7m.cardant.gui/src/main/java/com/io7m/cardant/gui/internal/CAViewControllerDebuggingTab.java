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

package com.io7m.cardant.gui.internal;

import com.io7m.cardant.client.api.CAClientHostileType;
import com.io7m.cardant.client.transfer.api.CATransferServiceType;
import com.io7m.cardant.services.api.CAServiceDirectoryType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import org.apache.commons.io.input.BrokenInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public final class CAViewControllerDebuggingTab implements Initializable
{
  private static final Logger LOG =
    LoggerFactory.getLogger(CAViewControllerDebuggingTab.class);

  private final CAMainEventBusType events;
  private final CAMainController controller;
  private final CATransferServiceType transfers;
  private volatile CAClientHostileType clientNow;

  @FXML private CheckBox slowTransfers;

  public CAViewControllerDebuggingTab(
    final CAServiceDirectoryType mainServices,
    final Stage stage)
  {
    this.events =
      mainServices.requireService(CAMainEventBusType.class);
    this.controller =
      mainServices.requireService(CAMainController.class);
    this.transfers =
      mainServices.requireService(CATransferServiceType.class);
  }

  @Override
  public void initialize(
    final URL url,
    final ResourceBundle resourceBundle)
  {
    this.controller.connectedClient()
      .addListener((observable, oldValue, newValue) -> {
        this.onClientConnectionChanged(newValue);
      });

    this.slowTransfers.setSelected(this.transfers.isSlowTransfers());
  }

  private void onClientConnectionChanged(
    final Optional<CAClientHostileType> newValue)
  {
    if (newValue.isPresent()) {
      this.clientNow = newValue.get();
    } else {
      this.clientNow = null;
    }
  }

  @FXML
  private void onSlowTransfersSelected()
  {
    this.transfers.setSlowTransfers(this.slowTransfers.isSelected());
  }

  @FXML
  private void onInvalidTransferSelected()
  {
    this.transfers.transfer(
      new BrokenInputStream(),
      "Broken Transfer",
      1000L,
      "SHA-256",
      "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
    );
  }

  @FXML
  private void onSendGarbageSelected()
  {
    this.clientNow.garbageCommand();
  }

  @FXML
  private void onSendInvalidSelected()
  {
    this.clientNow.invalidCommand();
  }

}
