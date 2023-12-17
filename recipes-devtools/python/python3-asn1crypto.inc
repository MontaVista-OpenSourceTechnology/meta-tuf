DESCRIPTION = "Python binding to the Networking and Cryptography (NaCl) library"
HOMEPAGE = "https://github.com/pyca/pynacl"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7439e38f5e04ff62fae436184786b7ca"

PYPI_PACKAGE = "asn1crypto"

SRC_URI[md5sum] = "00bf5b72d37778e62cc73b1d8909ae27"
SRC_URI[sha256sum] = "f4f6e119474e58e04a2b1af817eb585b4fd72bdd89b998624712b5c99be7641c"

inherit pypi
BBCLASSEXTEND = "native nativesdk"
