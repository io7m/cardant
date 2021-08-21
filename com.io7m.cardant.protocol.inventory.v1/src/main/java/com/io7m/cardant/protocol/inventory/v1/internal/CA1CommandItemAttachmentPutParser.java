/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.cardant.protocol.inventory.v1.internal;

import com.io7m.blackthorne.api.BTElementHandlerConstructorType;
import com.io7m.blackthorne.api.BTElementHandlerType;
import com.io7m.blackthorne.api.BTElementParsingContextType;
import com.io7m.blackthorne.api.BTQualifiedName;
import com.io7m.cardant.model.CAItemAttachment;
import com.io7m.cardant.model.CAItemID;
import com.io7m.cardant.model.xml.CAInventoryParsers;
import com.io7m.cardant.protocol.inventory.v1.messages.CA1CommandItemAttachmentPut;
import org.xml.sax.Attributes;

import java.util.Map;

import static com.io7m.cardant.model.xml.CAInventorySchemas.element1;

/**
 * A parser.
 */

public final class CA1CommandItemAttachmentPutParser
  implements BTElementHandlerType<CAItemAttachment, CA1CommandItemAttachmentPut>
{
  private CAItemID itemId;
  private CAItemAttachment attachment;

  /**
   * Create a parser.
   *
   * @param context The parsing context
   */

  public CA1CommandItemAttachmentPutParser(
    final BTElementParsingContextType context)
  {

  }

  @Override
  public Map<BTQualifiedName, BTElementHandlerConstructorType<?, ? extends CAItemAttachment>>
  onChildHandlersRequested(
    final BTElementParsingContextType context)
  {
    return Map.ofEntries(
      Map.entry(
        element1("ItemAttachment"),
        CAInventoryParsers.itemAttachmentParser(this.itemId)
      )
    );
  }

  @Override
  public void onElementStart(
    final BTElementParsingContextType context,
    final Attributes attributes)
    throws Exception
  {
    this.itemId = CAItemID.of(attributes.getValue("item"));
  }

  @Override
  public void onChildValueProduced(
    final BTElementParsingContextType context,
    final CAItemAttachment received)
  {
    this.attachment = received;
  }

  @Override
  public CA1CommandItemAttachmentPut onElementFinished(
    final BTElementParsingContextType context)
  {
    return new CA1CommandItemAttachmentPut(this.attachment);
  }
}