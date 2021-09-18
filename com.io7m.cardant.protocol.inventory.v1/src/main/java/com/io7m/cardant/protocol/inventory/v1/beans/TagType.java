/*
 * XML Type:  TagType
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.TagType
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import com.io7m.cardant.protocol.inventory.v1.beans.system.sFD186D0BF9A55EE36362F4FDE124660F.TypeSystemHolder;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.schema.DocumentFactory;


/**
 * An XML TagType(@urn:com.io7m.cardant.inventory:1).
 *
 * This is a complex type.
 */
public interface TagType extends XmlObject
{
  DocumentFactory<TagType> Factory = new DocumentFactory<>(
    TypeSystemHolder.typeSystem,
    "tagtypef055type");
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
  TagNameType xgetName();

  /**
   * Sets (as xml) the "name" attribute
   */
  void xsetName(TagNameType name);
}