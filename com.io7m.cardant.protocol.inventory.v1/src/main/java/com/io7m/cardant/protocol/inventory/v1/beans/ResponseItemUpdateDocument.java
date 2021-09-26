/*
 * An XML document type.
 * Localname: ResponseItemUpdate
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemUpdateDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * A document containing one ResponseItemUpdate(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public interface ResponseItemUpdateDocument extends com.io7m.cardant.protocol.inventory.v1.beans.ResponseDocument {
    DocumentFactory<com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemUpdateDocument> Factory = new DocumentFactory<>(com.io7m.cardant.protocol.inventory.v1.beans.system.sE8AC4557B7260DDF20EBB7BA8F2F0FBA.TypeSystemHolder.typeSystem, "responseitemupdatea3cadoctype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "ResponseItemUpdate" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemUpdateType getResponseItemUpdate();

    /**
     * Sets the "ResponseItemUpdate" element
     */
    void setResponseItemUpdate(com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemUpdateType responseItemUpdate);

    /**
     * Appends and returns a new empty "ResponseItemUpdate" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemUpdateType addNewResponseItemUpdate();
}
