/*
 * XML Type:  HashAlgorithmType
 * Namespace: urn:com.io7m.cardant.inventory:1
 * Java type: com.io7m.cardant.protocol.inventory.v1.beans.HashAlgorithmType
 *
 * Automatically generated - do not modify.
 */
package com.io7m.cardant.protocol.inventory.v1.beans;

import com.io7m.cardant.protocol.inventory.v1.beans.system.sFD186D0BF9A55EE36362F4FDE124660F.TypeSystemHolder;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlNormalizedString;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML HashAlgorithmType(@urn:com.io7m.cardant.inventory:1).
 *
 * This is an atomic type that is a restriction of com.io7m.cardant.protocol.inventory.v1.beans.HashAlgorithmType.
 */
public interface HashAlgorithmType extends XmlNormalizedString
{
  SimpleTypeFactory<HashAlgorithmType> Factory = new SimpleTypeFactory<>(
    TypeSystemHolder.typeSystem,
    "hashalgorithmtype9a0etype");
  SchemaType type = Factory.getType();

}