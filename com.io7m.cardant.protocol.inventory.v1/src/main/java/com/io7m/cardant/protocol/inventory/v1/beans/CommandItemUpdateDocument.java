/*
 * An XML document type.
 * Localname: CommandItemUpdate
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.CommandItemUpdateDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * A document containing one CommandItemUpdate(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public interface CommandItemUpdateDocument extends com.io7m.cardant.protocol.inventory.v1.beans.CommandDocument {
    DocumentFactory<com.io7m.cardant.protocol.inventory.v1.beans.CommandItemUpdateDocument> Factory = new DocumentFactory<>(com.io7m.cardant.protocol.inventory.v1.beans.system.sB4E2B3A435FC84169BAD368044F7CCA6.TypeSystemHolder.typeSystem, "commanditemupdate3412doctype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "CommandItemUpdate" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.CommandItemUpdateType getCommandItemUpdate();

    /**
     * Sets the "CommandItemUpdate" element
     */
    void setCommandItemUpdate(com.io7m.cardant.protocol.inventory.v1.beans.CommandItemUpdateType commandItemUpdate);

    /**
     * Appends and returns a new empty "CommandItemUpdate" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.CommandItemUpdateType addNewCommandItemUpdate();
}
