/*
 * An XML document type.
 * Localname: ResponseItemAttachmentPut
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemAttachmentPutDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans.impl;

import com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemAttachmentPutDocument;
import com.io7m.cardant.protocol.inventory.v1.beans.ResponseItemAttachmentPutType;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlObjectBase;

import javax.xml.namespace.QName;

/**
 * A document containing one ResponseItemAttachmentPut(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public class ResponseItemAttachmentPutDocumentImpl extends ResponseDocumentImpl implements
  ResponseItemAttachmentPutDocument
{
  private static final long serialVersionUID = 1L;
  private static final QName[] PROPERTY_QNAME = {
    new QName("urn:com.io7m.cardant.inventory:1", "ResponseItemAttachmentPut"),
  };

  public ResponseItemAttachmentPutDocumentImpl(final SchemaType sType)
  {
    super(sType);
  }

  /**
   * Gets the "ResponseItemAttachmentPut" element
   */
  @Override
  public ResponseItemAttachmentPutType getResponseItemAttachmentPut()
  {
    synchronized (this.monitor()) {
      this.check_orphaned();
      ResponseItemAttachmentPutType target = null;
      target = (ResponseItemAttachmentPutType) this.get_store().find_element_user(
        PROPERTY_QNAME[0],
        0);
      return target;
    }
  }

  /**
   * Sets the "ResponseItemAttachmentPut" element
   */
  @Override
  public void setResponseItemAttachmentPut(final ResponseItemAttachmentPutType responseItemAttachmentPut)
  {
    this.generatedSetterHelperImpl(
      responseItemAttachmentPut,
      PROPERTY_QNAME[0],
      0,
      XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
  }

  /**
   * Appends and returns a new empty "ResponseItemAttachmentPut" element
   */
  @Override
  public ResponseItemAttachmentPutType addNewResponseItemAttachmentPut()
  {
    synchronized (this.monitor()) {
      this.check_orphaned();
      ResponseItemAttachmentPutType target = null;
      target = (ResponseItemAttachmentPutType) this.get_store().add_element_user(
        PROPERTY_QNAME[0]);
      return target;
    }
  }
}