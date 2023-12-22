/*
 * Copyright © 2023 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import net.jqwik.api.providers.ArbitraryProvider;

/**
 * Inventory server (Arbitrary instances)
 */

module com.io7m.cardant.tests.arbitraries
{
  requires static org.osgi.annotation.bundle;
  requires static org.osgi.annotation.versioning;

  requires com.io7m.cardant.protocol.inventory;

  requires net.jqwik.api;

  exports com.io7m.cardant.tests.arbitraries;
  exports com.io7m.cardant.tests.arbitraries.model;

  uses ArbitraryProvider;

  provides ArbitraryProvider
    with
      com.io7m.cardant.tests.arbitraries.CAArbCommand,
      com.io7m.cardant.tests.arbitraries.CAArbCommandAuditSearchBegin,
      com.io7m.cardant.tests.arbitraries.CAArbCommandAuditSearchNext,
      com.io7m.cardant.tests.arbitraries.CAArbCommandAuditSearchPrevious,
      com.io7m.cardant.tests.arbitraries.CAArbCommandDebugInvalid,
      com.io7m.cardant.tests.arbitraries.CAArbCommandDebugRandom,
      com.io7m.cardant.tests.arbitraries.CAArbCommandFileGet,
      com.io7m.cardant.tests.arbitraries.CAArbCommandFilePut,
      com.io7m.cardant.tests.arbitraries.CAArbCommandFileRemove,
      com.io7m.cardant.tests.arbitraries.CAArbCommandFileSearchBegin,
      com.io7m.cardant.tests.arbitraries.CAArbCommandFileSearchNext,
      com.io7m.cardant.tests.arbitraries.CAArbCommandFileSearchPrevious,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemAttachmentAdd,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemAttachmentRemove,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemCreate,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemGet,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemLocationsList,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemMetadataPut,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemMetadataRemove,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemReposit,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemSearchBegin,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemSearchNext,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemSearchPrevious,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemSetName,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemTypesAssign,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemTypesRevoke,
      com.io7m.cardant.tests.arbitraries.CAArbCommandItemsRemove,
      com.io7m.cardant.tests.arbitraries.CAArbCommandLocationAttachmentAdd,
      com.io7m.cardant.tests.arbitraries.CAArbCommandLocationAttachmentRemove,
      com.io7m.cardant.tests.arbitraries.CAArbCommandLocationGet,
      com.io7m.cardant.tests.arbitraries.CAArbCommandLocationList,
      com.io7m.cardant.tests.arbitraries.CAArbCommandLocationMetadataPut,
      com.io7m.cardant.tests.arbitraries.CAArbCommandLocationMetadataRemove,
      com.io7m.cardant.tests.arbitraries.CAArbCommandLocationPut,
      com.io7m.cardant.tests.arbitraries.CAArbCommandLocationTypesAssign,
      com.io7m.cardant.tests.arbitraries.CAArbCommandLocationTypesRevoke,
      com.io7m.cardant.tests.arbitraries.CAArbCommandLogin,
      com.io7m.cardant.tests.arbitraries.CAArbCommandRolesAssign,
      com.io7m.cardant.tests.arbitraries.CAArbCommandRolesGet,
      com.io7m.cardant.tests.arbitraries.CAArbCommandRolesRevoke,
      com.io7m.cardant.tests.arbitraries.CAArbCommandTypeDeclarationGet,
      com.io7m.cardant.tests.arbitraries.CAArbCommandTypeDeclarationPut,
      com.io7m.cardant.tests.arbitraries.CAArbCommandTypeDeclarationRemove,
      com.io7m.cardant.tests.arbitraries.CAArbCommandTypeDeclarationSearchBegin,
      com.io7m.cardant.tests.arbitraries.CAArbCommandTypeDeclarationSearchNext,
      com.io7m.cardant.tests.arbitraries.CAArbCommandTypeDeclarationSearchPrevious,
      com.io7m.cardant.tests.arbitraries.CAArbCommandTypeScalarGet,
      com.io7m.cardant.tests.arbitraries.CAArbCommandTypeScalarPut,
      com.io7m.cardant.tests.arbitraries.CAArbCommandTypeScalarRemove,
      com.io7m.cardant.tests.arbitraries.CAArbCommandTypeScalarSearchBegin,
      com.io7m.cardant.tests.arbitraries.CAArbCommandTypeScalarSearchNext,
      com.io7m.cardant.tests.arbitraries.CAArbCommandTypeScalarSearchPrevious,
      com.io7m.cardant.tests.arbitraries.CAArbResponse,
      com.io7m.cardant.tests.arbitraries.CAArbResponseAuditSearch,
      com.io7m.cardant.tests.arbitraries.CAArbResponseError,
      com.io7m.cardant.tests.arbitraries.CAArbResponseFileGet,
      com.io7m.cardant.tests.arbitraries.CAArbResponseFilePut,
      com.io7m.cardant.tests.arbitraries.CAArbResponseFileRemove,
      com.io7m.cardant.tests.arbitraries.CAArbResponseFileSearch,
      com.io7m.cardant.tests.arbitraries.CAArbResponseItemAttachmentAdd,
      com.io7m.cardant.tests.arbitraries.CAArbResponseItemAttachmentRemove,
      com.io7m.cardant.tests.arbitraries.CAArbResponseItemCreate,
      com.io7m.cardant.tests.arbitraries.CAArbResponseItemGet,
      com.io7m.cardant.tests.arbitraries.CAArbResponseItemLocationsList,
      com.io7m.cardant.tests.arbitraries.CAArbResponseItemMetadataPut,
      com.io7m.cardant.tests.arbitraries.CAArbResponseItemMetadataRemove,
      com.io7m.cardant.tests.arbitraries.CAArbResponseItemReposit,
      com.io7m.cardant.tests.arbitraries.CAArbResponseItemSearch,
      com.io7m.cardant.tests.arbitraries.CAArbResponseItemSetName,
      com.io7m.cardant.tests.arbitraries.CAArbResponseItemTypesAssign,
      com.io7m.cardant.tests.arbitraries.CAArbResponseItemTypesRevoke,
      com.io7m.cardant.tests.arbitraries.CAArbResponseItemsRemove,
      com.io7m.cardant.tests.arbitraries.CAArbResponseLocationAttachmentAdd,
      com.io7m.cardant.tests.arbitraries.CAArbResponseLocationAttachmentRemove,
      com.io7m.cardant.tests.arbitraries.CAArbResponseLocationGet,
      com.io7m.cardant.tests.arbitraries.CAArbResponseLocationList,
      com.io7m.cardant.tests.arbitraries.CAArbResponseLocationMetadataPut,
      com.io7m.cardant.tests.arbitraries.CAArbResponseLocationMetadataRemove,
      com.io7m.cardant.tests.arbitraries.CAArbResponseLocationPut,
      com.io7m.cardant.tests.arbitraries.CAArbResponseLocationTypesAssign,
      com.io7m.cardant.tests.arbitraries.CAArbResponseLocationTypesRevoke,
      com.io7m.cardant.tests.arbitraries.CAArbResponseLogin,
      com.io7m.cardant.tests.arbitraries.CAArbResponseRolesAssign,
      com.io7m.cardant.tests.arbitraries.CAArbResponseRolesGet,
      com.io7m.cardant.tests.arbitraries.CAArbResponseRolesRevoke,
      com.io7m.cardant.tests.arbitraries.CAArbResponseTypeDeclarationGet,
      com.io7m.cardant.tests.arbitraries.CAArbResponseTypeDeclarationPut,
      com.io7m.cardant.tests.arbitraries.CAArbResponseTypeDeclarationRemove,
      com.io7m.cardant.tests.arbitraries.CAArbResponseTypeDeclarationSearch,
      com.io7m.cardant.tests.arbitraries.CAArbResponseTypeScalarGet,
      com.io7m.cardant.tests.arbitraries.CAArbResponseTypeScalarPut,
      com.io7m.cardant.tests.arbitraries.CAArbResponseTypeScalarRemove,
      com.io7m.cardant.tests.arbitraries.CAArbResponseTypeScalarSearch,
      com.io7m.cardant.tests.arbitraries.model.CAArbAttachment,
      com.io7m.cardant.tests.arbitraries.model.CAArbAttachmentKey,
      com.io7m.cardant.tests.arbitraries.model.CAArbAuditEvent,
      com.io7m.cardant.tests.arbitraries.model.CAArbAuditSearchParameters,
      com.io7m.cardant.tests.arbitraries.model.CAArbDescriptionMatch,
      com.io7m.cardant.tests.arbitraries.model.CAArbDottedName,
      com.io7m.cardant.tests.arbitraries.model.CAArbErrorCode,
      com.io7m.cardant.tests.arbitraries.model.CAArbFile,
      com.io7m.cardant.tests.arbitraries.model.CAArbFileColumnOrdering,
      com.io7m.cardant.tests.arbitraries.model.CAArbFileID,
      com.io7m.cardant.tests.arbitraries.model.CAArbFileSearchParameters,
      com.io7m.cardant.tests.arbitraries.model.CAArbFileWithoutData,
      com.io7m.cardant.tests.arbitraries.model.CAArbIdName,
      com.io7m.cardant.tests.arbitraries.model.CAArbIds,
      com.io7m.cardant.tests.arbitraries.model.CAArbItem,
      com.io7m.cardant.tests.arbitraries.model.CAArbItemColumnOrdering,
      com.io7m.cardant.tests.arbitraries.model.CAArbItemID,
      com.io7m.cardant.tests.arbitraries.model.CAArbItemLocation,
      com.io7m.cardant.tests.arbitraries.model.CAArbItemLocations,
      com.io7m.cardant.tests.arbitraries.model.CAArbItemReposit,
      com.io7m.cardant.tests.arbitraries.model.CAArbItemRepositAdd,
      com.io7m.cardant.tests.arbitraries.model.CAArbItemRepositMove,
      com.io7m.cardant.tests.arbitraries.model.CAArbItemRepositRemove,
      com.io7m.cardant.tests.arbitraries.model.CAArbItemSearchParameters,
      com.io7m.cardant.tests.arbitraries.model.CAArbItemSummary,
      com.io7m.cardant.tests.arbitraries.model.CAArbListLocationAll,
      com.io7m.cardant.tests.arbitraries.model.CAArbListLocationBehaviour,
      com.io7m.cardant.tests.arbitraries.model.CAArbListLocationExact,
      com.io7m.cardant.tests.arbitraries.model.CAArbListLocationWithDescendants,
      com.io7m.cardant.tests.arbitraries.model.CAArbLocation,
      com.io7m.cardant.tests.arbitraries.model.CAArbLocationID,
      com.io7m.cardant.tests.arbitraries.model.CAArbLocationSummaries,
      com.io7m.cardant.tests.arbitraries.model.CAArbLocationSummary,
      com.io7m.cardant.tests.arbitraries.model.CAArbMRoleName,
      com.io7m.cardant.tests.arbitraries.model.CAArbMediaTypeMatch,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadata,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataElementMatch,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataElementMatchAnd,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataElementMatchOr,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataElementMatchSpecific,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataIntegral,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataMonetary,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataReal,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataText,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataTime,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatch,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatchAnything,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatchIntegral,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatchIntegralWithinRange,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatchMonetary,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatchMonetaryWithCurrency,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatchMonetaryWithinRange,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatchReal,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatchRealWithinRange,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatchText,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatchTextExact,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatchTextSearch,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatchTime,
      com.io7m.cardant.tests.arbitraries.model.CAArbMetadataValueMatchTimeWithinRange,
      com.io7m.cardant.tests.arbitraries.model.CAArbNameMatch,
      com.io7m.cardant.tests.arbitraries.model.CAArbOffsetDateTime,
      com.io7m.cardant.tests.arbitraries.model.CAArbSizeRange,
      com.io7m.cardant.tests.arbitraries.model.CAArbTimeRange,
      com.io7m.cardant.tests.arbitraries.model.CAArbTypeDeclaration,
      com.io7m.cardant.tests.arbitraries.model.CAArbTypeDeclarationSearchParameters,
      com.io7m.cardant.tests.arbitraries.model.CAArbTypeDeclarationSummary,
      com.io7m.cardant.tests.arbitraries.model.CAArbTypeField,
      com.io7m.cardant.tests.arbitraries.model.CAArbTypeMatch,
      com.io7m.cardant.tests.arbitraries.model.CAArbTypeScalar,
      com.io7m.cardant.tests.arbitraries.model.CAArbTypeScalarIntegral,
      com.io7m.cardant.tests.arbitraries.model.CAArbTypeScalarMonetary,
      com.io7m.cardant.tests.arbitraries.model.CAArbTypeScalarReal,
      com.io7m.cardant.tests.arbitraries.model.CAArbTypeScalarSearchParameters,
      com.io7m.cardant.tests.arbitraries.model.CAArbTypeScalarText,
      com.io7m.cardant.tests.arbitraries.model.CAArbTypeScalarTime,
      com.io7m.cardant.tests.arbitraries.model.CAArbUserID
    ;
}
