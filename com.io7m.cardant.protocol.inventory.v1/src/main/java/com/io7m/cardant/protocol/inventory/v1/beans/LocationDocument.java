/*
 * An XML document type.
 * Localname: Location
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.LocationDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * A document containing one Location(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public interface LocationDocument extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<com.io7m.cardant.protocol.inventory.v1.beans.LocationDocument> Factory = new DocumentFactory<>(com.io7m.cardant.protocol.inventory.v1.beans.system.sB4E2B3A435FC84169BAD368044F7CCA6.TypeSystemHolder.typeSystem, "locationd4b2doctype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "Location" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.LocationType getLocation();

    /**
     * Sets the "Location" element
     */
    void setLocation(com.io7m.cardant.protocol.inventory.v1.beans.LocationType location);

    /**
     * Appends and returns a new empty "Location" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.LocationType addNewLocation();
}
