/*
 * An XML document type.
 * Localname: ResponseItemRemove
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemRemoveDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import com.io7m.cardant.protocol.inventory.v1.beans.system.sFD186D0BF9A55EE36362F4FDE124660F.TypeSystemHolder;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.schema.DocumentFactory;


/**
 * A document containing one ResponseItemRemove(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public interface ResponseItemRemoveDocument extends ResponseDocument
{
  DocumentFactory<ResponseItemRemoveDocument> Factory = new DocumentFactory<>(
    TypeSystemHolder.typeSystem,
    "responseitemremove076fdoctype");
  SchemaType type = Factory.getType();


  /**
   * Gets the "ResponseItemRemove" element
   */
  ResponseItemRemoveType getResponseItemRemove();

  /**
   * Sets the "ResponseItemRemove" element
   */
  void setResponseItemRemove(ResponseItemRemoveType responseItemRemove);

  /**
   * Appends and returns a new empty "ResponseItemRemove" element
   */
  ResponseItemRemoveType addNewResponseItemRemove();
}