/*
 * XML Type:  LocationsType
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.LocationsType
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML LocationsType(@urn:com.io7m.cardant.inventory:1).
 *
 * This is a complex type.
 */
public interface LocationsType extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<com.io7m.cardant.protocol.inventory.v1.beans.LocationsType> Factory = new DocumentFactory<>(com.io7m.cardant.protocol.inventory.v1.beans.system.sE431AFF67C9477B4270ED45520E13157.TypeSystemHolder.typeSystem, "locationstype9851type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets a List of "Location" elements
     */
    java.util.List<com.io7m.cardant.protocol.inventory.v1.beans.LocationType> getLocationList();

    /**
     * Gets array of all "Location" elements
     */
    com.io7m.cardant.protocol.inventory.v1.beans.LocationType[] getLocationArray();

    /**
     * Gets ith "Location" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.LocationType getLocationArray(int i);

    /**
     * Returns number of "Location" element
     */
    int sizeOfLocationArray();

    /**
     * Sets array of all "Location" element
     */
    void setLocationArray(com.io7m.cardant.protocol.inventory.v1.beans.LocationType[] locationArray);

    /**
     * Sets ith "Location" element
     */
    void setLocationArray(int i, com.io7m.cardant.protocol.inventory.v1.beans.LocationType location);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "Location" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.LocationType insertNewLocation(int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "Location" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.LocationType addNewLocation();

    /**
     * Removes the ith "Location" element
     */
    void removeLocation(int i);
}
