/*
 * XML Type:  ResponseTagListType
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.ResponseTagListType
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import com.io7m.cardant.protocol.inventory.v1.beans.system.s224658FCFC90A14D91039032BDB551D0.TypeSystemHolder;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.schema.DocumentFactory;


/**
 * An XML ResponseTagListType(@urn:com.io7m.cardant.inventory:1).
 *
 * This is a complex type.
 */
public interface ResponseTagListType extends ResponseType
{
  DocumentFactory<ResponseTagListType> Factory = new DocumentFactory<>(
    TypeSystemHolder.typeSystem,
    "responsetaglisttype0cb8type");
  SchemaType type = Factory.getType();


  /**
   * Gets the "Tags" element
   */
  TagsType getTags();

  /**
   * Sets the "Tags" element
   */
  void setTags(TagsType tags);

  /**
   * Appends and returns a new empty "Tags" element
   */
  TagsType addNewTags();
}
