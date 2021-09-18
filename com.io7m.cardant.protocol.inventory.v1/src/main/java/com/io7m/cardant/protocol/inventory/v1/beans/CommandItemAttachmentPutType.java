/*
 * XML Type:  CommandItemAttachmentPutType
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.CommandItemAttachmentPutType
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import com.io7m.cardant.protocol.inventory.v1.beans.system.sFD186D0BF9A55EE36362F4FDE124660F.TypeSystemHolder;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.schema.DocumentFactory;


/**
 * An XML CommandItemAttachmentPutType(@urn:com.io7m.cardant.inventory:1).
 *
 * This is a complex type.
 */
public interface CommandItemAttachmentPutType extends CommandType
{
  DocumentFactory<CommandItemAttachmentPutType> Factory = new DocumentFactory<>(
    TypeSystemHolder.typeSystem,
    "commanditemattachmentputtypede6ftype");
  SchemaType type = Factory.getType();


  /**
   * Gets the "ItemAttachment" element
   */
  ItemAttachmentType getItemAttachment();

  /**
   * Sets the "ItemAttachment" element
   */
  void setItemAttachment(ItemAttachmentType itemAttachment);

  /**
   * Appends and returns a new empty "ItemAttachment" element
   */
  ItemAttachmentType addNewItemAttachment();

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