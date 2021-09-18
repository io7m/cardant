/*
 * XML Type:  CommandItemMetadataPutType
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.CommandItemMetadataPutType
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import com.io7m.cardant.protocol.inventory.v1.beans.system.sFD186D0BF9A55EE36362F4FDE124660F.TypeSystemHolder;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.schema.DocumentFactory;


/**
 * An XML CommandItemMetadataPutType(@urn:com.io7m.cardant.inventory:1).
 *
 * This is a complex type.
 */
public interface CommandItemMetadataPutType extends CommandType
{
  DocumentFactory<CommandItemMetadataPutType> Factory = new DocumentFactory<>(
    TypeSystemHolder.typeSystem,
    "commanditemmetadataputtype6dbbtype");
  SchemaType type = Factory.getType();


  /**
   * Gets the "ItemMetadatas" element
   */
  ItemMetadatasType getItemMetadatas();

  /**
   * Sets the "ItemMetadatas" element
   */
  void setItemMetadatas(ItemMetadatasType itemMetadatas);

  /**
   * Appends and returns a new empty "ItemMetadatas" element
   */
  ItemMetadatasType addNewItemMetadatas();

  /**
   * Gets the "item" attribute
   */
  String getItem();

  /**
   * Sets the "item" attribute
   */
  void setItem(String item);

  /**
   * Gets (as xml) the "item" attribute
   */
  UUIDType xgetItem();

  /**
   * Sets (as xml) the "item" attribute
   */
  void xsetItem(UUIDType item);
}