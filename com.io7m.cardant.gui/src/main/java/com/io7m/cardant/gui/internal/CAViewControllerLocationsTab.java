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
import com.io7m.cardant.gui.internal.model.CALocationItemAll;
import com.io7m.cardant.gui.internal.model.CALocationItemDefined;
import com.io7m.cardant.gui.internal.model.CALocationItemType;
import com.io7m.cardant.gui.internal.views.CALocationTreeCellFactory;
import com.io7m.cardant.services.api.CAServiceDirectoryType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public final class CAViewControllerLocationsTab implements Initializable
{
  private static final Logger LOG =
    LoggerFactory.getLogger(CAViewControllerLocationsTab.class);

  private final CAMainController controller;
  private final CAMainEventBusType events;
  private final CAMainStrings strings;
  private final CAServiceDirectoryType services;
  private CAPerpetualSubscriber<CAMainEventType> subscriber;
  private CAClientHostileType clientNow;

  @FXML
  private TreeView<CALocationItemType> locationTreeView;
  @FXML
  private Button locationReparent;
  @FXML
  private Button locationCreate;
  @FXML
  private TextField locationSearch;

  public CAViewControllerLocationsTab(
    final CAServiceDirectoryType mainServices,
    final Stage stage)
  {
    this.events =
      mainServices.requireService(CAMainEventBusType.class);
    this.strings =
      mainServices.requireService(CAMainStrings.class);
    this.controller =
      mainServices.requireService(CAMainController.class);

    this.services = mainServices;
  }

  private static boolean allowsReparenting(
    final Optional<CALocationItemType> selectedItem)
  {
    if (selectedItem.isPresent()) {
      final var item = selectedItem.get();
      if (item instanceof CALocationItemDefined) {
        return true;
      }
      if (item instanceof CALocationItemAll) {
        return false;
      }
      return false;
    }
    return false;
  }

  @Override
  public void initialize(
    final URL url,
    final ResourceBundle resourceBundle)
  {
    this.subscriber = new CAPerpetualSubscriber<>(this::onMainEvent);
    this.events.subscribe(this.subscriber);

    this.locationSearch.textProperty()
      .bindBidirectional(this.controller.locationSearchProperty());

    this.locationTreeView.setRoot(
      this.controller.locationTree()
        .root()
    );
    this.locationTreeView.setShowRoot(false);
    this.locationTreeView.setCellFactory(
      new CALocationTreeCellFactory(this.strings));
    this.locationTreeView.getSelectionModel()
      .selectedItemProperty()
      .addListener((observable, oldValue, newValue) -> {
        this.onLocationSelectionChanged();
      });

    this.controller.connectedClient()
      .addListener((observable, oldValue, newValue) -> {
        this.onClientConnectionChanged(newValue);
      });
  }

  private void onLocationSelectionChanged()
  {
    final var selectionModel =
      this.locationTreeView.getSelectionModel();

    final var selectedItem =
      Optional.ofNullable(selectionModel.getSelectedItem())
        .map(TreeItem::getValue);

    this.controller.locationTreeSelect(selectedItem);
    this.locationReparent.setDisable(!allowsReparenting(selectedItem));
  }

  private void onMainEvent(
    final CAMainEventType event)
  {

  }

  @FXML
  private void onLocationSearchFieldChanged()
  {
    this.controller.locationTree()
      .setFilter(this.getFilter());
  }

  private Optional<String> getFilter()
  {
    final var filterText =
      this.locationSearch.getText()
        .trim()
        .toUpperCase(Locale.ROOT);

    if (filterText.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(filterText);
  }

  private void onClientConnectionChanged(
    final Optional<CAClientHostileType> clientOpt)
  {
    this.clientNow = clientOpt.orElse(null);
  }

  @FXML
  private void onCreateLocationSelected()
    throws IOException
  {
    final var stage = new Stage();

    final var connectXML =
      CAViewControllerMain.class.getResource("locationCreate.fxml");

    final var resources = this.strings.resources();
    final var loader = new FXMLLoader(connectXML, resources);

    loader.setControllerFactory(
      clazz -> CAViewControllers.createController(clazz, stage, this.services)
    );

    final AnchorPane pane = loader.load();
    final CAViewControllerLocationCreate create = loader.getController();

    final var selectionModel =
      this.locationTreeView.getSelectionModel();
    final var selectedItem =
      Optional.ofNullable(selectionModel.getSelectedItem())
        .map(TreeItem::getValue)
        .filter(t -> t instanceof CALocationItemDefined)
        .map(CALocationItemDefined.class::cast)
        .map(CALocationItemDefined::toLocation);

    selectedItem.ifPresent(create::setParent);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(new Scene(pane));
    stage.setTitle(this.strings.format("locations.create"));
    stage.showAndWait();

    final var locationOpt = create.result();
    locationOpt.ifPresent(location -> {
      this.clientNow.locationPut(location);
    });
  }

  @FXML
  private void onReparentLocationSelected()
    throws IOException
  {
    final var stage = new Stage();

    final var connectXML =
      CAViewControllerMain.class.getResource("locationSetParent.fxml");

    final var resources = this.strings.resources();
    final var loader = new FXMLLoader(connectXML, resources);

    loader.setControllerFactory(
      clazz -> CAViewControllers.createController(clazz, stage, this.services)
    );

    final Pane pane = loader.load();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(new Scene(pane));
    stage.setTitle(this.strings.format("locations.setParent"));
    stage.showAndWait();
  }
}
