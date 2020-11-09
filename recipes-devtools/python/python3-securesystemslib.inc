DESCRIPTION = "A library that provides cryptographic and general-purpose routines for Secure Systems Lab projects at NYU"
HOMEPAGE = "http://github.com/secure-systems-lab/securesystemslib"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e9703d169911cdb29305f88411c78717"

inherit pypi

RDEPENDS_${PN} += " \
    ${PYTHON_PN}-cryptography \
    ${PYTHON_PN}-pynacl \
    ${PYTHON_PN}-six \
    ${PYTHON_PN}-colorama \
"

BBCLASSEXTEND = "native nativesdk"

