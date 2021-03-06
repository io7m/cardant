/*
 * An XML document type.
 * Localname: ResponseItemAttachmentRemove
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemAttachmentRemoveDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;

/**
 * A document containing one ResponseItemAttachmentRemove(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public class ResponseItemAttachmentRemoveDocumentImpl extends com.io7m.cardant.protocol.inventory.v1.beans.impl.ResponseDocumentImpl implements com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemAttachmentRemoveDocument {
    private static final long serialVersionUID = 1L;

    public ResponseItemAttachmentRemoveDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("urn:com.io7m.cardant.inventory:1", "ResponseItemAttachmentRemove"),
    };


    /**
     * Gets the "ResponseItemAttachmentRemove" element
     */
    @Override
    public com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemAttachmentRemoveType getResponseItemAttachmentRemove() {
        synchronized (monitor()) {
            check_orphaned();
            com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemAttachmentRemoveType target = null;
            target = (com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemAttachmentRemoveType)get_store().find_element_user(PROPERTY_QNAME[0], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * Sets the "ResponseItemAttachmentRemove" element
     */
    @Override
    public void setResponseItemAttachmentRemove(com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemAttachmentRemoveType responseItemAttachmentRemove) {
        generatedSetterHelperImpl(responseItemAttachmentRemove, PROPERTY_QNAME[0], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "ResponseItemAttachmentRemove" element
     */
    @Override
    public com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemAttachmentRemoveType addNewResponseItemAttachmentRemove() {
        synchronized (monitor()) {
            check_orphaned();
            com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemAttachmentRemoveType target = null;
            target = (com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemAttachmentRemoveType)get_store().add_element_user(PROPERTY_QNAME[0]);
            return target;
        }
    }
}
