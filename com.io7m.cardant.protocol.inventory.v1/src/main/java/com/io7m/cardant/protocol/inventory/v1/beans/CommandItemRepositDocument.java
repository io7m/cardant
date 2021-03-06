/*
 * An XML document type.
 * Localname: CommandItemReposit
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.CommandItemRepositDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * A document containing one CommandItemReposit(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public interface CommandItemRepositDocument extends com.io7m.cardant.protocol.inventory.v1.beans.CommandDocument {
    DocumentFactory<com.io7m.cardant.protocol.inventory.v1.beans.CommandItemRepositDocument> Factory = new DocumentFactory<>(com.io7m.cardant.protocol.inventory.v1.beans.system.sE431AFF67C9477B4270ED45520E13157.TypeSystemHolder.typeSystem, "commanditemreposit0d79doctype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "CommandItemReposit" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.CommandItemRepositType getCommandItemReposit();

    /**
     * Sets the "CommandItemReposit" element
     */
    void setCommandItemReposit(com.io7m.cardant.protocol.inventory.v1.beans.CommandItemRepositType commandItemReposit);

    /**
     * Appends and returns a new empty "CommandItemReposit" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.CommandItemRepositType addNewCommandItemReposit();
}
