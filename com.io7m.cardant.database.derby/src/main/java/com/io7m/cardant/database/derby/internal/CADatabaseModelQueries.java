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

package com.io7m.cardant.database.derby.internal;

import com.io7m.cardant.database.api.CADatabaseException;
import com.io7m.cardant.model.CAFileID;
import com.io7m.cardant.model.CAFileType;
import com.io7m.cardant.model.CAItem;
import com.io7m.cardant.model.CAItemAttachment;
import com.io7m.cardant.model.CAItemID;
import com.io7m.cardant.model.CAItemLocations;
import com.io7m.cardant.model.CAItemMetadata;
import com.io7m.cardant.model.CAItemRepositType;
import com.io7m.cardant.model.CAListLocationBehaviourType;
import com.io7m.cardant.model.CALocation;
import com.io7m.cardant.model.CALocationID;
import com.io7m.cardant.model.CAModelDatabaseQueriesType;
import com.io7m.cardant.model.CATag;
import com.io7m.cardant.model.CATagID;
import com.io7m.cardant.model.CAUser;
import com.io7m.cardant.model.CAUserID;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

/**
 * Internal database calls for the inventory.
 */

public final class CADatabaseModelQueries
  implements CAModelDatabaseQueriesType
{
  private final CADatabaseModelQueriesLocations locations;
  private final CADatabaseModelQueriesTags tags;
  private final CADatabaseModelQueriesItems items;
  private final CADatabaseModelQueriesUsers users;
  private final CADatabaseModelQueriesFiles files;

  CADatabaseModelQueries(
    final CADatabaseDerbyTransaction inTransaction)
  {
    this.locations =
      new CADatabaseModelQueriesLocations(inTransaction);
    this.tags =
      new CADatabaseModelQueriesTags(inTransaction);
    this.files =
      new CADatabaseModelQueriesFiles(inTransaction);
    this.items =
      new CADatabaseModelQueriesItems(
        this.locations,
        this.files,
        this.tags,
        inTransaction
      );
    this.users =
      new CADatabaseModelQueriesUsers(inTransaction);
  }

  @Override
  public void itemReposit(final CAItemRepositType reposit)
    throws CADatabaseException
  {
    this.items.itemReposit(reposit);
  }

  @Override
  public CAItemLocations itemLocations(
    final CAItemID id)
    throws CADatabaseException
  {
    return this.items.itemLocations(id);
  }

  @Override
  public Optional<CAItem> itemGet(final CAItemID id)
    throws CADatabaseException
  {
    return this.items.itemGet(id);
  }

  @Override
  public void itemCreate(final CAItemID id)
    throws CADatabaseException
  {
    this.items.itemCreate(id);
  }

  @Override
  public void itemNameSet(
    final CAItemID id,
    final String name)
    throws CADatabaseException
  {
    this.items.itemNameSet(id, name);
  }

  @Override
  public Set<CAItem> itemList(
    final CAListLocationBehaviourType locationBehaviour)
    throws CADatabaseException
  {
    return this.items.itemList(locationBehaviour);
  }

  @Override
  public Set<CAItemID> itemListDeleted()
    throws CADatabaseException
  {
    return this.items.itemListDeleted();
  }

  @Override
  public void itemsDelete(
    final Collection<CAItemID> inItems)
    throws CADatabaseException
  {
    this.items.itemsDelete(inItems);
  }

  @Override
  public void itemsDeleteMarkOnly(
    final Collection<CAItemID> inItems)
    throws CADatabaseException
  {
    this.items.itemsDeleteMarkOnly(inItems);
  }

  @Override
  public void itemTagAdd(
    final CAItemID item,
    final CATag tag)
    throws CADatabaseException
  {
    this.items.itemTagAdd(item, tag);
  }

  @Override
  public void itemTagRemove(
    final CAItemID item,
    final CATag tag)
    throws CADatabaseException
  {
    this.items.itemTagRemove(item, tag);
  }

  @Override
  public SortedSet<CATag> itemTagList(
    final CAItemID item)
    throws CADatabaseException
  {
    return this.items.itemTagList(item);
  }

  @Override
  public void itemMetadataPut(
    final CAItemID item,
    final CAItemMetadata metadata)
    throws CADatabaseException
  {
    this.items.itemMetadataPut(item, metadata);
  }

  @Override
  public SortedMap<String, CAItemMetadata> itemMetadata(
    final CAItemID item)
    throws CADatabaseException
  {
    return this.items.itemMetadata(item);
  }

  @Override
  public void itemMetadataRemove(
    final CAItemID item,
    final String name)
    throws CADatabaseException
  {
    this.items.itemMetadataRemove(item, name);
  }

  @Override
  public void itemAttachmentAdd(
    final CAItemID item,
    final CAFileID file,
    final String relation)
    throws CADatabaseException
  {
    this.items.itemAttachmentAdd(item, file, relation);
  }

  @Override
  public void itemAttachmentRemove(
    final CAItemID item,
    final CAFileID file,
    final String relation)
    throws CADatabaseException
  {
    this.items.itemAttachmentRemove(item, file, relation);
  }

  @Override
  public Set<CAItemAttachment> itemAttachments(
    final CAItemID item,
    final boolean withData)
    throws CADatabaseException
  {
    return this.items.itemAttachments(item, withData);
  }

  @Override
  public void userPut(final CAUser user)
    throws CADatabaseException
  {
    this.users.userPut(user);
  }

  @Override
  public Optional<CAUser> userGet(final CAUserID id)
    throws CADatabaseException
  {
    return this.users.userGet(id);
  }

  @Override
  public Optional<CAUser> userGetByName(final String name)
    throws CADatabaseException
  {
    return this.users.userGetByName(name);
  }

  @Override
  public Map<CAUserID, CAUser> userList()
    throws CADatabaseException
  {
    return this.users.userList();
  }

  @Override
  public Optional<CATag> tagGet(
    final CATagID id)
    throws CADatabaseException
  {
    return this.tags.tagGet(id);
  }

  @Override
  public void tagPut(final CATag tag)
    throws CADatabaseException
  {
    this.tags.tagPut(tag);
  }

  @Override
  public void tagDelete(final CATag tag)
    throws CADatabaseException
  {
    this.tags.tagDelete(tag);
  }

  @Override
  public SortedSet<CATag> tagList()
    throws CADatabaseException
  {
    return this.tags.tagList();
  }

  @Override
  public void locationPut(final CALocation location)
    throws CADatabaseException
  {
    this.locations.locationPut(location);
  }

  @Override
  public Optional<CALocation> locationGet(final CALocationID id)
    throws CADatabaseException
  {
    return this.locations.locationGet(id);
  }

  @Override
  public SortedMap<CALocationID, CALocation> locationList()
    throws CADatabaseException
  {
    return this.locations.locationList();
  }

  @Override
  public void filePut(final CAFileType file)
    throws CADatabaseException
  {
    this.files.filePut(file);
  }

  @Override
  public Optional<CAFileType> fileGet(
    final CAFileID file,
    final boolean withData)
    throws CADatabaseException
  {
    return this.files.fileGet(file, withData);
  }

  @Override
  public void fileRemove(final CAFileID file)
    throws CADatabaseException
  {
    this.files.fileRemove(file);
  }
}
