#
# Copyright (C) 2014 Wind River Systems, Inc.
#
DESCRIPTION = "Simple cross-platform colored terminal text in Python"
HOMEPAGE = "http://pypi.python.org/pypi/colorama"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b4936429a56a652b84c5c01280dcaa26"

SRCNAME = "colorama"

SRC_URI[md5sum] = "57b22f2597f63df051b69906fbf310cc"
SRC_URI[sha256sum] = "5941b2b48a20143d2267e95b1c2a7603ce057ee39fd88e7329b0c292aa16869b"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools3 pypi

BBCLASSEXTEND = "native nativesdk"
