DESCRIPTION = "A secure updater framework for Python"
HOMEPAGE = "https://www.updateframework.com"
SECTION = "devel/python"
LICENSE = "Apache-2.0 | MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8cc789b082b3d97e1ccc5261f8594d3f \
		    file://LICENSE-MIT;md5=071ba575edfa3cb3a013d2ea3b15f33a"

# For some reason tuf in the PIP repository doesn't have the licenses.
# These are from the git repository.
SRC_URI += " \
    file://LICENSE \
    file://LICENSE-MIT \
"
PYPI_PACKAGE = "tuf"

SRC_URI[md5sum] = "c26ef0f0bda6e2dbfa4ed0160e0625d4"
SRC_URI[sha256sum] = "65d5f87a41830494bf585f8a5082618ab26015d156a67f23e37552419e427cf1"

inherit setuptools pypi

do_install_append() {
    mv ${D}${bindir}/client.py ${D}${bindir}/tuf-client
    mv ${D}${bindir}/repo.py ${D}${bindir}/tuf-repo
    mv ${D}${bindir}/simple_server.py ${D}${bindir}/tuf-simple_server
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
    ${PYTHON_PN}-argparse \
"

RDEPENDS_${PN}-repo += " \
    ${PYTHON_PN}-argparse \
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
"

RDEPENDS_${PN} += "${BASE_TUF_RDEPENDS}"

# There is no native[sdk] version of python-misc, but we need it on the target.
RDEPENDS_${PN}_class-target += "${BASE_TUF_RDEPENDS} ${PYTHON_PN}-misc"

do_unpack_tuf_licenses() {
    cp ${WORKDIR}/LICENSE ${S}
    cp ${WORKDIR}/LICENSE-MIT ${S}
}

python do_unpack_append() {
    bb.build.exec_func('do_unpack_tuf_licenses', d)
}

BBCLASSEXTEND = "native nativesdk"
