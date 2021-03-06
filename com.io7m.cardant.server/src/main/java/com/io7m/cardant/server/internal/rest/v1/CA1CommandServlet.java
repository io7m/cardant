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

package com.io7m.cardant.server.internal.rest.v1;

import com.io7m.anethum.common.ParseException;
import com.io7m.anethum.common.SerializeException;
import com.io7m.cardant.database.api.CADatabaseException;
import com.io7m.cardant.database.api.CADatabaseTransactionType;
import com.io7m.cardant.database.api.CADatabaseType;
import com.io7m.cardant.model.CAByteArray;
import com.io7m.cardant.model.CAIds;
import com.io7m.cardant.model.CAItem;
import com.io7m.cardant.model.CAItemID;
import com.io7m.cardant.model.CAItems;
import com.io7m.cardant.model.CALocations;
import com.io7m.cardant.model.CAModelDatabaseQueriesType;
import com.io7m.cardant.model.CATags;
import com.io7m.cardant.protocol.inventory.api.CACommandType;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandFilePut;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandFileRemove;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandItemAttachmentAdd;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandItemAttachmentRemove;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandItemCreate;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandItemGet;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandItemLocationsList;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandItemMetadataPut;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandItemMetadataRemove;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandItemReposit;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandItemUpdate;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandItemsRemove;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandLocationGet;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandLocationList;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandLocationPut;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandTagsDelete;
import com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandTagsPut;
import com.io7m.cardant.protocol.inventory.api.CAMessageParserFactoryType;
import com.io7m.cardant.protocol.inventory.api.CAMessageSerializerFactoryType;
import com.io7m.cardant.protocol.inventory.api.CAMessageType;
import com.io7m.cardant.protocol.inventory.api.CAResponseType;
import com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseError;
import com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseItemAttachmentAdd;
import com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseItemList;
import com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseItemLocationsList;
import com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseItemReposit;
import com.io7m.cardant.protocol.inventory.api.CATransaction;
import com.io7m.cardant.protocol.inventory.api.CATransactionResponse;
import com.io7m.cardant.server.api.CAServerConfigurationLimits;
import com.io7m.cardant.server.internal.CAServerMessages;
import com.io7m.cardant.server.internal.rest.CAServerCommandExecuted;
import com.io7m.cardant.server.internal.rest.CAServerCommandFailed;
import com.io7m.cardant.server.internal.rest.CAServerEventType;
import com.io7m.junreachable.UnimplementedCodeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.SubmissionPublisher;

import static com.io7m.cardant.database.api.CADatabaseErrorCode.ERROR_NONEXISTENT;
import static com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandItemList;
import static com.io7m.cardant.protocol.inventory.api.CACommandType.CACommandTagList;
import static com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseItemAttachmentRemove;
import static com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseItemCreate;
import static com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseItemGet;
import static com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseItemMetadataPut;
import static com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseItemMetadataRemove;
import static com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseItemUpdate;
import static com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseItemsRemove;
import static com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseLocationList;
import static com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseLocationPut;
import static com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseTagList;
import static com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseTagsDelete;
import static com.io7m.cardant.protocol.inventory.api.CAResponseType.CAResponseTagsPut;
import static com.io7m.cardant.server.internal.rest.CAMediaTypes.applicationCardantXML;

/**
 * A command servlet.
 */

public final class CA1CommandServlet
  extends CA1AuthenticatedTransactionalServlet
{
  private static final Logger LOG =
    LoggerFactory.getLogger(CA1CommandServlet.class);

  private final SubmissionPublisher<CAServerEventType> events;
  private final CAServerConfigurationLimits limits;
  private CADatabaseTransactionType transaction;
  private HttpServletResponse response;
  private CAModelDatabaseQueriesType queries;

  /**
   * Construct a command servlet.
   *
   * @param inEvents      The event publisher
   * @param inParsers     The parsers
   * @param inSerializers The serializers
   * @param inDatabase    The database
   * @param inMessages    The server string resources
   * @param inLimits      The server limits
   */

  public CA1CommandServlet(
    final SubmissionPublisher<CAServerEventType> inEvents,
    final CAMessageParserFactoryType inParsers,
    final CAMessageSerializerFactoryType inSerializers,
    final CAServerConfigurationLimits inLimits,
    final CAServerMessages inMessages,
    final CADatabaseType inDatabase)
  {
    super(inEvents, inParsers, inSerializers, inMessages, inDatabase);
    this.events = Objects.requireNonNull(inEvents, "inEvents");
    this.limits = Objects.requireNonNull(inLimits, "limits");
  }

  private static boolean exceedsSizeLimit(
    final long sizeLimit,
    final CAByteArray data)
  {
    final var sizeReceived =
      Integer.toUnsignedLong(data.data().length);

    return Long.compareUnsigned(sizeReceived, sizeLimit) > 0;
  }

  @Override
  protected Logger logger()
  {
    return LOG;
  }

  @Override
  protected void serviceTransactional(
    final CADatabaseTransactionType dbTransaction,
    final HttpServletRequest request,
    final HttpServletResponse httpResponse,
    final HttpSession session)
  {
    this.transaction = dbTransaction;
    this.response = httpResponse;

    final CAMessageType message;
    try {
      this.queries =
        dbTransaction.queries(CAModelDatabaseQueriesType.class);
      message =
        this.parsers().parse(this.clientURI(), request.getInputStream());
    } catch (final CADatabaseException e) {
      this.logger().trace("exception: ", e);
      this.sendError(500, e.getMessage(), e.attributes(), List.of());
      return;
    } catch (final IOException e) {
      this.logger().trace("exception: ", e);
      this.sendErrorIOException(500, e);
      return;
    } catch (final ParseException e) {
      this.logger().trace("exception: ", e);
      this.sendErrorParsing(500, e.getMessage(), e.statusValues());
      return;
    }

    final var msgResponse =
      this.executeMessage(message);

    try {
      if (msgResponse instanceof CATransactionResponse transactionResponse) {
        if (transactionResponse.failed()) {
          this.transaction.rollback();
          this.response.setStatus(500);
          this.response.setContentType(applicationCardantXML());
          this.serializers()
            .serialize(
              this.clientURI(),
              this.response.getOutputStream(),
              msgResponse
            );
          return;
        }
      }

      if (msgResponse instanceof CAResponseError error) {
        this.transaction.rollback();
        this.response.setStatus(error.statusCode());
        this.response.setContentType(applicationCardantXML());
        this.serializers()
          .serialize(
            this.clientURI(),
            this.response.getOutputStream(),
            msgResponse
          );
        return;
      }

      this.transaction.commit();
      this.response.setStatus(200);
      this.response.setContentType(applicationCardantXML());
      this.serializers()
        .serialize(
          this.clientURI(),
          this.response.getOutputStream(),
          msgResponse
        );
    } catch (final CADatabaseException e) {
      this.logger().trace("exception: ", e);
      this.sendError(500, e.getMessage(), e.attributes(), List.of());
    } catch (final SerializeException e) {
      this.sendError(500, e.getMessage());
    } catch (final IOException e) {
      this.logger().trace("exception: ", e);
      this.sendErrorIOException(500, e);
    }
  }

  private CAMessageType executeMessage(
    final CAMessageType message)
  {
    if (message instanceof CATransaction received) {
      return this.executeTransaction(received);
    }
    if (message instanceof CACommandType received) {
      return this.executeCommandAccounted(received);
    }
    if (message instanceof CAResponseType received) {
      return this.executeResponse(received);
    }

    throw new IllegalStateException(
      "Unrecognized message: %s".formatted(message));
  }

  private CAResponseType executeResponse(
    final CAResponseType received)
  {
    return this.error(
      400,
      this.messages().format("errorUnexpectedInput"),
      Map.of(),
      List.of()
    );
  }

  private CATransactionResponse executeTransaction(
    final CATransaction msgTransaction)
  {
    final var responses =
      new ArrayList<CAResponseType>(msgTransaction.commands().size());

    boolean failed = false;
    final var commands = msgTransaction.commands();
    for (int index = 0; index < commands.size(); ++index) {
      final var command =
        commands.get(index);
      final var mostRecent =
        this.executeCommandAccounted(command);

      if (mostRecent instanceof CAResponseError) {
        failed = true;
      }
      responses.add(mostRecent);
    }
    return new CATransactionResponse(failed, responses);
  }

  private CAResponseType executeCommandAccounted(
    final CACommandType command)
  {
    final var result = this.executeCommand(command);
    if (result instanceof CAResponseError) {
      this.commandFailed();
      return result;
    }

    this.commandExecuted();
    return result;
  }

  private void commandFailed()
  {
    this.events.submit(new CAServerCommandFailed());
  }

  private void commandExecuted()
  {
    this.events.submit(new CAServerCommandExecuted());
  }

  private CAResponseType executeCommand(
    final CACommandType command)
  {
    if (command instanceof CACommandTagList) {
      return this.executeCommandTagList();
    }
    if (command instanceof CACommandTagsPut tags) {
      return this.executeCommandTagsPut(tags);
    }
    if (command instanceof CACommandTagsDelete tags) {
      return this.executeCommandTagsDelete(tags);
    }
    if (command instanceof CACommandItemCreate itemCreate) {
      return this.executeCommandItemCreate(itemCreate);
    }
    if (command instanceof CACommandItemUpdate itemUpdate) {
      return this.executeCommandItemUpdate(itemUpdate);
    }
    if (command instanceof CACommandItemsRemove itemRemove) {
      return this.executeCommandItemRemove(itemRemove);
    }
    if (command instanceof CACommandItemList itemList) {
      return this.executeCommandItemList(itemList);
    }
    if (command instanceof CACommandItemAttachmentAdd itemAttachmentAdd) {
      return this.executeCommandItemAttachmentAdd(itemAttachmentAdd);
    }
    if (command instanceof CACommandItemAttachmentRemove itemAttachmentRemove) {
      return this.executeCommandItemAttachmentRemove(itemAttachmentRemove);
    }
    if (command instanceof CACommandItemMetadataPut itemMetadataPut) {
      return this.executeCommandItemMetadataPut(itemMetadataPut);
    }
    if (command instanceof CACommandItemMetadataRemove itemMetadataRemove) {
      return this.executeCommandItemMetadataRemove(itemMetadataRemove);
    }
    if (command instanceof CACommandLocationPut locationPut) {
      return this.executeCommandLocationPut(locationPut);
    }
    if (command instanceof CACommandItemReposit itemReposit) {
      return this.executeCommandItemReposit(itemReposit);
    }
    if (command instanceof CACommandItemGet itemGet) {
      return this.executeCommandItemGet(itemGet);
    }
    if (command instanceof CACommandLocationGet locationGet) {
      return this.executeCommandLocationGet(locationGet);
    }
    if (command instanceof CACommandLocationList locationList) {
      return this.executeCommandLocationList(locationList);
    }
    if (command instanceof CACommandItemLocationsList itemLocations) {
      return this.executeCommandItemLocationsList(itemLocations);
    }
    if (command instanceof CACommandFilePut filePut) {
      return this.executeCommandFilePut(filePut);
    }
    if (command instanceof CACommandFileRemove fileRemove) {
      return this.executeCommandFileRemove(fileRemove);
    }

    throw new IllegalStateException();
  }

  private CAResponseType executeCommandFilePut(
    final CACommandFilePut filePut)
  {
    try {
      this.queries.filePut(filePut.data());
      return new CAResponseType.CAResponseFilePut(filePut.data().withoutData());
    } catch (final CADatabaseException e) {
      return this.errorDatabase(500, e);
    }
  }

  private CAResponseType executeCommandFileRemove(
    final CACommandFileRemove fileRemove)
  {
    try {
      this.queries.fileRemove(fileRemove.data());
      return new CAResponseType.CAResponseFileRemove(fileRemove.data());
    } catch (final CADatabaseException e) {
      return this.errorDatabase(500, e);
    }
  }

  private CAResponseType executeCommandItemLocationsList(
    final CACommandItemLocationsList itemLocations)
  {
    try {
      final var locations =
        this.queries.itemLocations(itemLocations.item());

      return new CAResponseItemLocationsList(locations);
    } catch (final CADatabaseException e) {
      return this.errorDatabase(500, e);
    }
  }

  private CAResponseType executeCommandLocationList(
    final CACommandLocationList locationList)
  {
    try {
      final var locations =
        this.queries.locationList();

      return new CAResponseLocationList(new CALocations(locations));
    } catch (final CADatabaseException e) {
      return this.errorDatabase(500, e);
    }
  }

  private CAResponseType executeCommandLocationGet(
    final CACommandLocationGet command)
  {
    throw new UnimplementedCodeException();
  }

  private CAResponseType executeCommandItemGet(
    final CACommandItemGet itemGet)
  {
    try {
      final var itemOpt =
        this.queries.itemGet(itemGet.id());

      if (itemOpt.isEmpty()) {
        final var attributes = new HashMap<String, String>();
        attributes.put(
          this.messages().format("item"),
          itemGet.id().displayId());

        return this.error(
          404,
          this.messages().format("errorNoSuchItem"),
          attributes,
          List.of()
        );
      }

      return new CAResponseItemGet(itemOpt.get());
    } catch (final CADatabaseException e) {
      return this.errorDatabase(500, e);
    }
  }

  private CAResponseType executeCommandItemReposit(
    final CACommandItemReposit itemReposit)
  {
    try {
      final var reposit = itemReposit.reposit();
      this.queries.itemReposit(reposit);
      return new CAResponseItemReposit(reposit.item());
    } catch (final CADatabaseException e) {
      return this.errorDatabase(500, e);
    }
  }

  private CAResponseType executeCommandLocationPut(
    final CACommandLocationPut locationPut)
  {
    try {
      this.queries.locationPut(locationPut.location());
      return new CAResponseLocationPut(locationPut.location());
    } catch (final CADatabaseException e) {
      return switch (e.errorCode()) {
        case ERROR_NONEXISTENT,
          ERROR_PARAMETERS_INVALID -> this.errorDatabase(400, e);
        default -> this.errorDatabase(500, e);
      };
    }
  }

  private CAResponseType executeCommandItemMetadataPut(
    final CACommandItemMetadataPut itemMetadataPut)
  {
    try {
      final var metadatas =
        itemMetadataPut.metadatas();
      final var itemId =
        itemMetadataPut.item();

      for (final var metadata : metadatas) {
        this.queries.itemMetadataPut(itemId, metadata);
      }

      return new CAResponseItemMetadataPut(this.fetchItem(itemId));
    } catch (final CADatabaseException e) {
      return switch (e.errorCode()) {
        case ERROR_NONEXISTENT,
          ERROR_PARAMETERS_INVALID -> this.errorDatabase(400, e);
        default -> this.errorDatabase(500, e);
      };
    }
  }

  private CAResponseType executeCommandItemMetadataRemove(
    final CACommandItemMetadataRemove itemMetadataRemove)
  {
    try {
      final var metadatas = itemMetadataRemove.metadataNames();
      for (final var metadata : metadatas) {
        this.queries.itemMetadataRemove(itemMetadataRemove.item(), metadata);
      }

      return new CAResponseItemMetadataRemove(
        this.fetchItem(itemMetadataRemove.item())
      );
    } catch (final CADatabaseException e) {
      return switch (e.errorCode()) {
        case ERROR_NONEXISTENT,
          ERROR_PARAMETERS_INVALID -> this.errorDatabase(400, e);
        default -> this.errorDatabase(500, e);
      };
    }
  }

  private CAResponseType executeCommandItemAttachmentAdd(
    final CACommandItemAttachmentAdd command)
  {
    try {
      this.queries.itemAttachmentAdd(
        command.item(),
        command.file(),
        command.relation()
      );
      return new CAResponseItemAttachmentAdd(
        this.fetchItem(command.item())
      );
    } catch (final CADatabaseException e) {
      return switch (e.errorCode()) {
        case ERROR_NONEXISTENT,
          ERROR_PARAMETERS_INVALID -> this.errorDatabase(400, e);
        default -> this.errorDatabase(500, e);
      };
    }
  }

  private CAResponseType executeCommandItemAttachmentRemove(
    final CACommandItemAttachmentRemove itemAttachmentRemove)
  {
    try {
      this.queries.itemAttachmentRemove(
        itemAttachmentRemove.item(),
        itemAttachmentRemove.file(),
        itemAttachmentRemove.relation());
      return new CAResponseItemAttachmentRemove(
        this.fetchItem(itemAttachmentRemove.item())
      );
    } catch (final CADatabaseException e) {
      return this.errorDatabase(500, e);
    }
  }

  private CAItem fetchItem(
    final CAItemID item)
    throws CADatabaseException
  {
    try {
      return this.queries.itemGet(item)
        .orElseThrow(NoSuchElementException::new);
    } catch (final NoSuchElementException e) {
      final var attributes = new HashMap<String, String>();
      attributes.put(
        this.messages().format("item"),
        item.displayId());

      throw new CADatabaseException(
        ERROR_NONEXISTENT,
        attributes,
        "Updated item could not be retrieved"
      );
    }
  }

  private CAResponseType executeCommandTagsDelete(
    final CACommandTagsDelete tags)
  {
    try {
      for (final var tag : tags.tags().tags()) {
        this.queries.tagDelete(tag);
      }
      return new CAResponseTagsDelete(tags.tags());
    } catch (final CADatabaseException e) {
      return this.errorDatabase(500, e);
    }
  }

  private CAResponseType executeCommandItemList(
    final CACommandItemList command)
  {
    try {
      return new CAResponseItemList(
        new CAItems(this.queries.itemList(command.locationBehaviour())));
    } catch (final CADatabaseException e) {
      return this.errorDatabase(500, e);
    }
  }

  private CAResponseType executeCommandItemCreate(
    final CACommandItemCreate itemCreate)
  {
    try {
      final var itemId = itemCreate.id();
      this.queries.itemCreate(itemId);
      this.queries.itemNameSet(itemId, itemCreate.name());
      return new CAResponseItemCreate(this.fetchItem(itemId));
    } catch (final CADatabaseException e) {
      return switch (e.errorCode()) {
        case ERROR_DUPLICATE,
          ERROR_PARAMETERS_INVALID -> this.errorDatabase(400, e);
        default -> this.errorDatabase(500, e);
      };
    }
  }

  private CAResponseError errorDatabase(
    final int status,
    final CADatabaseException e)
  {
    return this.error(status, e.getMessage(), e.attributes(), List.of());
  }

  private CAResponseError error(
    final int status,
    final String message,
    final Map<String, String> attributes,
    final List<String> details)
  {
    return new CAResponseError(message, status, attributes, details);
  }

  private CAResponseType executeCommandItemUpdate(
    final CACommandItemUpdate itemUpdate)
  {
    try {
      final var itemId = itemUpdate.id();
      this.queries.itemNameSet(itemId, itemUpdate.name());
      final var updated = this.fetchItem(itemId);
      return new CAResponseItemUpdate(updated);
    } catch (final CADatabaseException e) {
      return switch (e.errorCode()) {
        case ERROR_NONEXISTENT,
          ERROR_PARAMETERS_INVALID -> this.errorDatabase(400, e);
        default -> this.errorDatabase(500, e);
      };
    }
  }

  private CAResponseType executeCommandItemRemove(
    final CACommandItemsRemove itemRemove)
  {
    try {
      this.queries.itemsDeleteMarkOnly(itemRemove.ids());
      return new CAResponseItemsRemove(new CAIds(Set.copyOf(itemRemove.ids())));
    } catch (final CADatabaseException e) {
      return this.errorDatabase(500, e);
    }
  }

  private CAResponseType executeCommandTagsPut(
    final CACommandTagsPut tags)
  {
    try {
      for (final var tag : tags.tags().tags()) {
        this.queries.tagPut(tag);
      }
      return new CAResponseTagsPut(tags.tags());
    } catch (final CADatabaseException e) {
      return this.errorDatabase(500, e);
    }
  }

  private CAResponseType executeCommandTagList()
  {
    try {
      return new CAResponseTagList(new CATags(this.queries.tagList()));
    } catch (final CADatabaseException e) {
      return this.errorDatabase(500, e);
    }
  }
}
