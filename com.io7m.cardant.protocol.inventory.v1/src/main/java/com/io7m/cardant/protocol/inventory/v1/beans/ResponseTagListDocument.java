/*
 * An XML document type.
 * Localname: ResponseTagList
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.ResponseTagListDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * A document containing one ResponseTagList(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public interface ResponseTagListDocument extends com.io7m.cardant.protocol.inventory.v1.beans.ResponseDocument {
    DocumentFactory<com.io7m.cardant.protocol.inventory.v1.beans.ResponseTagListDocument> Factory = new DocumentFactory<>(com.io7m.cardant.protocol.inventory.v1.beans.system.sB4E2B3A435FC84169BAD368044F7CCA6.TypeSystemHolder.typeSystem, "responsetaglist73e2doctype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "ResponseTagList" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.ResponseTagListType getResponseTagList();

    /**
     * Sets the "ResponseTagList" element
     */
    void setResponseTagList(com.io7m.cardant.protocol.inventory.v1.beans.ResponseTagListType responseTagList);

    /**
     * Appends and returns a new empty "ResponseTagList" element
     */
    com.io7m.cardant.protocol.inventory.v1.beans.ResponseTagListType addNewResponseTagList();
}
