/*
 * An XML document type.
 * Localname: ResponseItemLocationsList
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemLocationsListDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * A document containing one ResponseItemLocationsList(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public interface ResponseItemLocationsListDocument extends com.io7m.cardant.protocol.inventory.v1.beans.ResponseDocument {
    DocumentFactory<com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemLocationsListDocument> Factory = new DocumentFactory<>(com.io7m.cardant.protocol.inventory.v1.beans.system.sE431AFF67C9477B4270ED45520E13157.TypeSystemHolder.typeSystem, "responseitemlocationslistd411doctype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "ResponseItemLocationsList" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemLocationsListType getResponseItemLocationsList();

    /**
     * Sets the "ResponseItemLocationsList" element
     */
    void setResponseItemLocationsList(com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemLocationsListType responseItemLocationsList);

    /**
     * Appends and returns a new empty "ResponseItemLocationsList" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemLocationsListType addNewResponseItemLocationsList();
}
