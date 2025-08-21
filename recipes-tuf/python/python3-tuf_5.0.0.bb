DESCRIPTION = "A secure updater framework for Python"
HOMEPAGE = "https://www.updateframework.com"
SECTION = "devel/python"
LICENSE = "Apache-2.0 | MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8cc789b082b3d97e1ccc5261f8594d3f \
		    file://LICENSE-MIT;md5=0905c600830081fce64622608c4a2628"

# For some reason tuf in the PIP repository doesn't have the licenses.
# These are from the git repository.

SRC_URI += " \
    file://LICENSE \
    file://LICENSE-MIT \
"
PYPI_PACKAGE = "tuf"

SRC_URI[md5sum] = "5adaa6f311330adac2226891480c2e7e"
SRC_URI[sha256sum] = "9c5d87d3822ae2f83c756d5a208c6942a2829ae1ea63c18c363124497d04da4f"

inherit pypi python_hatchling 
#inherit pypi setuptools3 
#inherit pypi python_flit_core
#inherit pypi python_setuptools_build_meta

#do_configure:prepend() {
#cat > ${S}/setup.py <<-EOF
#from setuptools import setup

#setup(
#       name="${PYPI_PACKAGE}",
#       version="${PV}",
#       license="${LICENSE}",
#)
#EOF
#}


do_install() {
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/tuf/
    cp -r ${S}/tuf/__init__.py ${D}${libdir}/${PYTHON_DIR}/site-packages/tuf/

    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/tuf/ngclient
    cp -r ${S}/tuf/ngclient/*.py ${D}${libdir}/${PYTHON_DIR}/site-packages/tuf/ngclient/
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/tuf/ngclient/_internal
    cp -r ${S}/tuf/ngclient/_internal/*.py ${D}${libdir}/${PYTHON_DIR}/site-packages/tuf/ngclient/_internal/

    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/tuf/api
    cp -r ${S}/tuf/api/*.py ${D}${libdir}/${PYTHON_DIR}/site-packages/tuf/api/
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/tuf/api/serialization
    cp -r ${S}/tuf/api/serialization/*.py ${D}${libdir}/${PYTHON_DIR}/site-packages/tuf/api/serialization/
}


PACKAGES:${PN} =+ " \
    ${PN}-ngclient \
    ${PN}-api \
"

FILES:${PN}-ngclient = " \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/ngclient/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/ngclient/_internal/*.py \
"

FILES:${PN}-api = " \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/api/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/api/serialization/*.py \
"

FILES_${PN} += " \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/ngclient \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/api \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/ngclient/_internal \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf/api/serialization \
"

RDEPENDS:${PN}-ngclient += " \
    ${PYTHON_PN}-tuf \
"

RDEPENDS:${PN}-api += " \
    ${PYTHON_PN}-tuf \
"

BASE_TUF_RDEPENDS = " \
    ${PYTHON_PN}-securesystemslib \
    ${PYTHON_PN}-cryptography \
    ${PYTHON_PN}-colorama \
    ${PYTHON_PN}-requests \
"

RDEPENDS:${PN} += "${BASE_TUF_RDEPENDS}"

# There is no native[sdk] version of python-misc, but we need it on the target.
RDEPENDS:${PN}:class-target += "${BASE_TUF_RDEPENDS} ${PYTHON_PN}-misc"

BBCLASSEXTEND = "native nativesdk"
