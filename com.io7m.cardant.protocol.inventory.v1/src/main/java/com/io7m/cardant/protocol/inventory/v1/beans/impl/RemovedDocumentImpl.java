/*
 * An XML document type.
 * Localname: Removed
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.RemovedDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans.impl;

import com.io7m.cardant.protocol.inventory.v1.beans.RemovedDocument;
import com.io7m.cardant.protocol.inventory.v1.beans.RemovedType;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.values.XmlObjectBase;

import javax.xml.namespace.QName;

/**
 * A document containing one Removed(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public class RemovedDocumentImpl extends XmlComplexContentImpl implements
  RemovedDocument
{
  private static final long serialVersionUID = 1L;
  private static final QName[] PROPERTY_QNAME = {
    new QName("urn:com.io7m.cardant.inventory:1", "Removed"),
  };

  public RemovedDocumentImpl(final SchemaType sType)
  {
    super(sType);
  }

  /**
   * Gets the "Removed" element
   */
  @Override
  public RemovedType getRemoved()
  {
    synchronized (this.monitor()) {
      this.check_orphaned();
      RemovedType target = null;
      target = (RemovedType) this.get_store().find_element_user(
        PROPERTY_QNAME[0],
        0);
      return target;
    }
  }

  /**
   * Sets the "Removed" element
   */
  @Override
  public void setRemoved(final RemovedType removed)
  {
    this.generatedSetterHelperImpl(
      removed,
      PROPERTY_QNAME[0],
      0,
      XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
  }

  /**
   * Appends and returns a new empty "Removed" element
   */
  @Override
  public RemovedType addNewRemoved()
  {
    synchronized (this.monitor()) {
      this.check_orphaned();
      RemovedType target = null;
      target = (RemovedType) this.get_store().add_element_user(
        PROPERTY_QNAME[0]);
      return target;
    }
  }
}
