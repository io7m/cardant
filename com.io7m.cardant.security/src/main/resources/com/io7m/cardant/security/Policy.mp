#
# Copyright © 2023 Mark Raynsford <code@io7m.com> https://www.io7m.com
#
# Permission to use, copy, modify, and/or distribute this software for any
# purpose with or without fee is hereby granted, provided that the above
# copyright notice and this permission notice appear in all copies.
#
# THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
# WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
# MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
# SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
# WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
# ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
# IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
#

[Medrina 1 0]

#------------------------------------------------------------------------
# Admins
#

[Rule
  [Conclusion    AllowImmediately]
  [MatchSubject  [WithAnyRolesFrom inventory.admin]]
  [MatchObject   True]
  [MatchAction   True]
]

#------------------------------------------------------------------------
# Items
#

[Rule
  [Conclusion   AllowImmediately]
  [MatchSubject [WithAnyRolesFrom inventory.items.writer]]
  [MatchObject  [WithType inventory.items]]
  [MatchAction  [WithName write]]
]

[Rule
  [Conclusion   AllowImmediately]
  [MatchSubject [WithAnyRolesFrom inventory.items.writer]]
  [MatchObject  [WithType inventory.items]]
  [MatchAction  [WithName delete]]
]

[Rule
  [Conclusion   AllowImmediately]
  [MatchSubject [WithAnyRolesFrom inventory.items.reader]]
  [MatchObject  [WithType inventory.items]]
  [MatchAction  [WithName read]]
]

#------------------------------------------------------------------------
# Tags
#

[Rule
  [Conclusion   AllowImmediately]
  [MatchSubject [WithAnyRolesFrom inventory.tags.writer]]
  [MatchObject  [WithType inventory.tags]]
  [MatchAction  [WithName write]]
]

[Rule
  [Conclusion   AllowImmediately]
  [MatchSubject [WithAnyRolesFrom inventory.tags.writer]]
  [MatchObject  [WithType inventory.tags]]
  [MatchAction  [WithName delete]]
]

[Rule
  [Conclusion   AllowImmediately]
  [MatchSubject [WithAnyRolesFrom inventory.tags.reader]]
  [MatchObject  [WithType inventory.tags]]
  [MatchAction  [WithName read]]
]

#------------------------------------------------------------------------
# Files
#

[Rule
  [Conclusion   AllowImmediately]
  [MatchSubject [WithAnyRolesFrom inventory.files.writer]]
  [MatchObject  [WithType inventory.files]]
  [MatchAction  [WithName write]]
]

[Rule
  [Conclusion   AllowImmediately]
  [MatchSubject [WithAnyRolesFrom inventory.files.writer]]
  [MatchObject  [WithType inventory.files]]
  [MatchAction  [WithName delete]]
]

[Rule
  [Conclusion   AllowImmediately]
  [MatchSubject [WithAnyRolesFrom inventory.files.reader]]
  [MatchObject  [WithType inventory.files]]
  [MatchAction  [WithName read]]
]

#------------------------------------------------------------------------
# Locations
#

[Rule
  [Conclusion   AllowImmediately]
  [MatchSubject [WithAnyRolesFrom inventory.locations.writer]]
  [MatchObject  [WithType inventory.locations]]
  [MatchAction  [WithName write]]
]

[Rule
  [Conclusion   AllowImmediately]
  [MatchSubject [WithAnyRolesFrom inventory.locations.writer]]
  [MatchObject  [WithType inventory.locations]]
  [MatchAction  [WithName delete]]
]

[Rule
  [Conclusion   AllowImmediately]
  [MatchSubject [WithAnyRolesFrom inventory.locations.reader]]
  [MatchObject  [WithType inventory.locations]]
  [MatchAction  [WithName read]]
]

#------------------------------------------------------------------------
# Audit
#

[Rule
  [Conclusion   AllowImmediately]
  [MatchSubject [WithAnyRolesFrom audit.reader]]
  [MatchObject  [WithType audit]]
  [MatchAction  [WithName read]]
]
