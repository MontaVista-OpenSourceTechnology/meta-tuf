DESCRIPTION = "A secure updater framework for Python"
HOMEPAGE = "https://www.updateframework.com"
SECTION = "devel/python"
LICENSE = "Apache-2.0 | MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8cc789b082b3d97e1ccc5261f8594d3f \
		    file://LICENSE-MIT;md5=071ba575edfa3cb3a013d2ea3b15f33a"

# For some reason tuf in the PIP repository doesn't have the licenses.
# These are from the git repository.

SRC_URI += " \
    file://0001-TUF-Split-up-parse_arguments.patch \
    file://0002-TUF-Split-out-write-updated-targets-from-add-targets.patch \
"
PYPI_PACKAGE = "tuf"

SRC_URI[md5sum] = "ad5d10bc22c965da17c4dbbecb1e6248"
SRC_URI[sha256sum] = "e0653e1339031d018212d593879f96152af212aaf07a205ebcfc65d62f76679c"

inherit setuptools3 pypi

do_install_append() {
    mv ${D}${bindir}/client.py ${D}${bindir}/tuf-client
    mv ${D}${bindir}/repo.py ${D}${bindir}/tuf-repo
}

PACKAGES =+ " \
    ${PN}-client \
    ${PN}-repo \
    ${PN}-simple-server \
"

FILES_${PN}-client = " \
    ${bindir}/tuf-client \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/scripts/client.py \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/scripts/client.pyc \
"

FILES_${PN}-repo = " \
    ${bindir}/tuf-repo \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/scripts/repo.py \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/scripts/repo.pyc \
"

FILES_${PN}-simple-server = " \
    ${bindir}/tuf-simple_server \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/scripts/simple_server.py \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/scripts/simple_server.pyc \
"

RDEPENDS_${PN}-client += " \
    ${PYTHON_PN}-tuf \
"

RDEPENDS_${PN}-repo += " \
    ${PYTHON_PN}-tuf \
"

BASE_TUF_RDEPENDS = " \
    ${PYTHON_PN}-securesystemslib \
    ${PYTHON_PN}-cryptography \
    ${PYTHON_PN}-colorama \
    ${PYTHON_PN}-pynacl \
    ${PYTHON_PN}-six \
    ${PYTHON_PN}-iso8601 \
    ${PYTHON_PN}-json \
    ${PYTHON_PN}-unixadmin \
    ${PYTHON_PN}-shell \
    ${PYTHON_PN}-requests \
"

RDEPENDS_${PN} += "${BASE_TUF_RDEPENDS}"

# There is no native[sdk] version of python-misc, but we need it on the target.
RDEPENDS_${PN}_class-target += "${BASE_TUF_RDEPENDS} ${PYTHON_PN}-misc"

BBCLASSEXTEND = "native nativesdk"
