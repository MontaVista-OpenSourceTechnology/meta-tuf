#
# Copyright (C) 2014 Wind River Systems, Inc.
#
DESCRIPTION = "Simple cross-platform colored terminal text in Python"
HOMEPAGE = "http://pypi.python.org/pypi/colorama"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=14d0b64047ed8f510b51ce0495995358"

PR = "r0"
SRCNAME = "colorama"

SRC_URI[md5sum] = "3a0e415259690f4dd7455c2683ee5850"
SRC_URI[sha256sum] = "48eb22f4f8461b1df5734a074b57042430fb06e1d61bd1e11b078c0fe6d7a1f1"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools pypi

BBCLASSEXTEND = "native nativesdk"
