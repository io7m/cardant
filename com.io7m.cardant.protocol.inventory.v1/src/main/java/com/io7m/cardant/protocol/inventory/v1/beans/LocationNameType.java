/*
 * XML Type:  LocationNameType
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.LocationNameType
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import com.io7m.cardant.protocol.inventory.v1.beans.system.sFD186D0BF9A55EE36362F4FDE124660F.TypeSystemHolder;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlNormalizedString;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML LocationNameType(@urn:com.io7m.cardant.inventory:1).
 *
 * This is an atomic type that is a restriction of com.io7m.cardant.protocol.inventory.v1.beans.LocationNameType.
 */
public interface LocationNameType extends XmlNormalizedString
{
  SimpleTypeFactory<LocationNameType> Factory = new SimpleTypeFactory<>(
    TypeSystemHolder.typeSystem,
    "locationnametypededdtype");
  SchemaType type = Factory.getType();

}
