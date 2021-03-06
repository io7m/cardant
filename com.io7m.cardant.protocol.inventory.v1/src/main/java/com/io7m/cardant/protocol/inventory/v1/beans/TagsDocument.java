/*
 * An XML document type.
 * Localname: Tags
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.TagsDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * A document containing one Tags(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public interface TagsDocument extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<com.io7m.cardant.protocol.inventory.v1.beans.TagsDocument> Factory = new DocumentFactory<>(com.io7m.cardant.protocol.inventory.v1.beans.system.sE431AFF67C9477B4270ED45520E13157.TypeSystemHolder.typeSystem, "tags384edoctype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "Tags" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.TagsType getTags();

    /**
     * Sets the "Tags" element
     */
    void setTags(com.io7m.cardant.protocol.inventory.v1.beans.TagsType tags);

    /**
     * Appends and returns a new empty "Tags" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.TagsType addNewTags();
}
