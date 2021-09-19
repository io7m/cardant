/*
 * XML Type:  CommandItemUpdateType
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.CommandItemUpdateType
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import com.io7m.cardant.protocol.inventory.v1.beans.system.s224658FCFC90A14D91039032BDB551D0.TypeSystemHolder;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlString;
import org.apache.xmlbeans.impl.schema.DocumentFactory;


/**
 * An XML CommandItemUpdateType(@urn:com.io7m.cardant.inventory:1).
 *
 * This is a complex type.
 */
public interface CommandItemUpdateType extends CommandType
{
  DocumentFactory<CommandItemUpdateType> Factory = new DocumentFactory<>(
    TypeSystemHolder.typeSystem,
    "commanditemupdatetype78e8type");
  SchemaType type = Factory.getType();


  /**
   * Gets the "id" attribute
   */
  String getId();

  /**
   * Sets the "id" attribute
   */
  void setId(String id);

  /**
   * Gets (as xml) the "id" attribute
   */
  UUIDType xgetId();

  /**
   * Sets (as xml) the "id" attribute
   */
  void xsetId(UUIDType id);

  /**
   * Gets the "name" attribute
   */
  String getName();

  /**
   * Sets the "name" attribute
   */
  void setName(String name);

  /**
   * Gets (as xml) the "name" attribute
   */
  XmlString xgetName();

  /**
   * Sets (as xml) the "name" attribute
   */
  void xsetName(XmlString name);
}
