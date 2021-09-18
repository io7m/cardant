/*
 * An XML document type.
 * Localname: Tags
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.TagsDocument
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans.impl;

import com.io7m.cardant.protocol.inventory.v1.beans.TagsDocument;
import com.io7m.cardant.protocol.inventory.v1.beans.TagsType;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.values.XmlObjectBase;

import javax.xml.namespace.QName;

/**
 * A document containing one Tags(@urn:com.io7m.cardant.inventory:1) element.
 *
 * This is a complex type.
 */
public class TagsDocumentImpl extends XmlComplexContentImpl implements
  TagsDocument
{
  private static final long serialVersionUID = 1L;
  private static final QName[] PROPERTY_QNAME = {
    new QName("urn:com.io7m.cardant.inventory:1", "Tags"),
  };

  public TagsDocumentImpl(final SchemaType sType)
  {
    super(sType);
  }

  /**
   * Gets the "Tags" element
   */
  @Override
  public TagsType getTags()
  {
    synchronized (this.monitor()) {
      this.check_orphaned();
      TagsType target = null;
      target = (TagsType) this.get_store().find_element_user(
        PROPERTY_QNAME[0],
        0);
      return target;
    }
  }

  /**
   * Sets the "Tags" element
   */
  @Override
  public void setTags(final TagsType tags)
  {
    this.generatedSetterHelperImpl(
      tags,
      PROPERTY_QNAME[0],
      0,
      XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
  }

  /**
   * Appends and returns a new empty "Tags" element
   */
  @Override
  public TagsType addNewTags()
  {
    synchronized (this.monitor()) {
      this.check_orphaned();
      TagsType target = null;
      target = (TagsType) this.get_store().add_element_user(
        PROPERTY_QNAME[0]);
      return target;
    }
  }
}