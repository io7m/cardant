/*
 * XML Type:  ResponseItemUpdateType
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemUpdateType
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import com.io7m.cardant.protocol.inventory.v1.beans.system.sFD186D0BF9A55EE36362F4FDE124660F.TypeSystemHolder;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.schema.DocumentFactory;


/**
 * An XML ResponseItemUpdateType(@urn:com.io7m.cardant.inventory:1).
 *
 * This is a complex type.
 */
public interface ResponseItemUpdateType extends ResponseType
{
  DocumentFactory<ResponseItemUpdateType> Factory = new DocumentFactory<>(
    TypeSystemHolder.typeSystem,
    "responseitemupdatetypeaec0type");
  SchemaType type = Factory.getType();


  /**
   * Gets the "Item" element
   */
  ItemType getItem();

  /**
   * Sets the "Item" element
   */
  void setItem(ItemType item);

  /**
   * Appends and returns a new empty "Item" element
   */
  ItemType addNewItem();
}