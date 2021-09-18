/*
 * An XML document type.
 * Localname: ItemRepositRemove
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.ItemRepositRemoveDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans.impl;

import com.io7m.cardant.protocol.inventory.v1.beans.ItemRepositRemoveDocument;
import com.io7m.cardant.protocol.inventory.v1.beans.ItemRepositRemoveType;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlObjectBase;

import javax.xml.namespace.QName;

/**
 * A document containing one ItemRepositRemove(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public class ItemRepositRemoveDocumentImpl extends ItemRepositDocumentImpl implements
  ItemRepositRemoveDocument
{
  private static final long serialVersionUID = 1L;
  private static final QName[] PROPERTY_QNAME = {
    new QName("urn:com.io7m.cardant.inventory:1", "ItemRepositRemove"),
  };

  public ItemRepositRemoveDocumentImpl(final SchemaType sType)
  {
    super(sType);
  }

  /**
   * Gets the "ItemRepositRemove" element
   */
  @Override
  public ItemRepositRemoveType getItemRepositRemove()
  {
    synchronized (this.monitor()) {
      this.check_orphaned();
      ItemRepositRemoveType target = null;
      target = (ItemRepositRemoveType) this.get_store().find_element_user(
        PROPERTY_QNAME[0],
        0);
      return target;
    }
  }

  /**
   * Sets the "ItemRepositRemove" element
   */
  @Override
  public void setItemRepositRemove(final ItemRepositRemoveType itemRepositRemove)
  {
    this.generatedSetterHelperImpl(
      itemRepositRemove,
      PROPERTY_QNAME[0],
      0,
      XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
  }

  /**
   * Appends and returns a new empty "ItemRepositRemove" element
   */
  @Override
  public ItemRepositRemoveType addNewItemRepositRemove()
  {
    synchronized (this.monitor()) {
      this.check_orphaned();
      ItemRepositRemoveType target = null;
      target = (ItemRepositRemoveType) this.get_store().add_element_user(
        PROPERTY_QNAME[0]);
      return target;
    }
  }
}