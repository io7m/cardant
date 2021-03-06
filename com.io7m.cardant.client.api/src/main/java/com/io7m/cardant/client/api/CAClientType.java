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

package com.io7m.cardant.client.api;

import com.io7m.cardant.model.CAFileID;
import com.io7m.cardant.model.CAFileType;
import com.io7m.cardant.model.CAFileType.CAFileWithData;
import com.io7m.cardant.model.CAIds;
import com.io7m.cardant.model.CAItem;
import com.io7m.cardant.model.CAItemID;
import com.io7m.cardant.model.CAItemLocations;
import com.io7m.cardant.model.CAItemMetadata;
import com.io7m.cardant.model.CAItemRepositType;
import com.io7m.cardant.model.CAItems;
import com.io7m.cardant.model.CAListLocationBehaviourType;
import com.io7m.cardant.model.CALocation;
import com.io7m.cardant.model.CALocations;

import java.io.Closeable;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;

/**
 * The type of client instances.
 */

public interface CAClientType extends Closeable
{
  /**
   * @return {@code true} if the client is connected
   */

  boolean isConnected();

  /**
   * @return A stream of events
   */

  Flow.Publisher<CAClientEventType> events();

  /**
   * List items on the server.
   *
   * @param locationBehaviour The location behaviour
   *
   * @return An item list
   */

  CompletableFuture<CAClientCommandResultType<CAItems>> itemsList(
    CAListLocationBehaviourType locationBehaviour);

  /**
   * Get an item on the server.
   *
   * @param id The item id
   *
   * @return An item
   */

  CompletableFuture<CAClientCommandResultType<CAItem>> itemGet(
    CAItemID id);

  /**
   * Create an item on the server.
   *
   * @param id   The item id
   * @param name The item name
   *
   * @return An item
   */

  CompletableFuture<CAClientCommandResultType<CAItem>> itemCreate(
    CAItemID id,
    String name);

  /**
   * Delete items on the server.
   *
   * @param items The items
   *
   * @return The deleted items
   */

  CompletableFuture<CAClientCommandResultType<CAIds>> itemsDelete(
    Set<CAItemID> items);

  /**
   * Delete metadata from an item on the server.
   *
   * @param id       The item id
   * @param metadata The metadata names
   *
   * @return An item
   */

  CompletableFuture<CAClientCommandResultType<CAItem>> itemMetadataDelete(
    CAItemID id,
    Set<String> metadata);

  /**
   * Update metadata in an item on the server.
   *
   * @param id            The item id
   * @param itemMetadatas The metadata values
   *
   * @return An item
   */

  CompletableFuture<CAClientCommandResultType<CAItem>> itemMetadataUpdate(
    CAItemID id,
    Set<CAItemMetadata> itemMetadatas);

  /**
   * Add an attachment to an item on the server.
   *
   * @param id       The item id
   * @param file     The file
   * @param relation The relation
   *
   * @return An item
   */

  CompletableFuture<CAClientCommandResultType<CAItem>> itemAttachmentAdd(
    CAItemID id,
    CAFileWithData file,
    String relation);

  /**
   * Remove an attachment from an item on the server.
   *
   * @param item     The item id
   * @param file     The file
   * @param relation The relation
   *
   * @return An item
   */

  CompletableFuture<CAClientCommandResultType<CAItem>> itemAttachmentDelete(
    CAItemID item,
    CAFileID file,
    String relation);

  /**
   * Get the file data for a file on the server.
   *
   * @param file The file
   *
   * @return A stream of data
   */

  CompletableFuture<InputStream> fileData(
    CAFileID file);

  /**
   * Create or update a file on the server.
   *
   * @param file The file
   *
   * @return A stream of data
   */

  CompletableFuture<CAClientCommandResultType<CAFileType>> filePut(
    CAFileWithData file);

  /**
   * Create or update a file on the server.
   *
   * @param file The file
   *
   * @return A stream of data
   */

  CompletableFuture<CAClientCommandResultType<CAFileID>> fileDelete(
    CAFileID file);

  /**
   * Create or update a location on the server.
   *
   * @param location The location
   *
   * @return A location
   */

  CompletableFuture<CAClientCommandResultType<CALocation>> locationPut(
    CALocation location);

  /**
   * List locations on the server.
   *
   * @return The locations
   */

  CompletableFuture<CAClientCommandResultType<CALocations>> locationList();

  /**
   * Reposit an item on the server.
   *
   * @param reposit The item reposit
   *
   * @return The item ID
   */

  CompletableFuture<CAClientCommandResultType<CAItemID>> itemReposit(
    CAItemRepositType reposit);

  /**
   * Determine the locations within which an item exists.
   *
   * @param item The item
   *
   * @return The list of locations for the item
   */

  CompletableFuture<CAClientCommandResultType<CAItemLocations>> itemLocationsList(
    CAItemID item);
}
