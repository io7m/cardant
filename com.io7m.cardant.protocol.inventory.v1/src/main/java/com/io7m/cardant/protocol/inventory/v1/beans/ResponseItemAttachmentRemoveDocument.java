/*
 * An XML document type.
 * Localname: ResponseItemAttachmentRemove
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemAttachmentRemoveDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import com.io7m.cardant.protocol.inventory.v1.beans.system.sFD186D0BF9A55EE36362F4FDE124660F.TypeSystemHolder;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.schema.DocumentFactory;


/**
 * A document containing one ResponseItemAttachmentRemove(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public interface ResponseItemAttachmentRemoveDocument extends ResponseDocument
{
  DocumentFactory<ResponseItemAttachmentRemoveDocument> Factory = new DocumentFactory<>(
    TypeSystemHolder.typeSystem,
    "responseitemattachmentremovef10cdoctype");
  SchemaType type = Factory.getType();


  /**
   * Gets the "ResponseItemAttachmentRemove" element
   */
  ResponseItemAttachmentRemoveType getResponseItemAttachmentRemove();

  /**
   * Sets the "ResponseItemAttachmentRemove" element
   */
  void setResponseItemAttachmentRemove(ResponseItemAttachmentRemoveType responseItemAttachmentRemove);

  /**
   * Appends and returns a new empty "ResponseItemAttachmentRemove" element
   */
  ResponseItemAttachmentRemoveType addNewResponseItemAttachmentRemove();
}
