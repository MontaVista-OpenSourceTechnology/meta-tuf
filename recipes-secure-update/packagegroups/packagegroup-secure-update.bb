#
# Copyright (C) 2024 Montavista LLC
#

SUMMARY = "Montavista update framework package"
DESCRIPTION = "Package required to integrate update solutions"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = " \
	${PN} \
        "
ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} = " \
        python3-tuf-manifest-client \
	"
