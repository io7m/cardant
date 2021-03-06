/*
 * XML Type:  ResponseLocationPutType
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.ResponseLocationPutType
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML ResponseLocationPutType(@urn:com.io7m.cardant.inventory:1).
 *
 * This is a complex type.
 */
public interface ResponseLocationPutType extends com.io7m.cardant.protocol.inventory.v1.beans.ResponseType {
    DocumentFactory<com.io7m.cardant.protocol.inventory.v1.beans.ResponseLocationPutType> Factory = new DocumentFactory<>(com.io7m.cardant.protocol.inventory.v1.beans.system.sE431AFF67C9477B4270ED45520E13157.TypeSystemHolder.typeSystem, "responselocationputtype1d36type");
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
