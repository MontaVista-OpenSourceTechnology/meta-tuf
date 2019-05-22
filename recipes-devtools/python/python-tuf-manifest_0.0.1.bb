DESCRIPTION = "A secure updater framework for Python"
HOMEPAGE = "https://www.updateframework.com"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d36b156c33ff4e31d4892fe82a3fb978"

PYPI_PACKAGE = "tuf_manifest"

SRC_URI[md5sum] = "618c2520a1916f63373e02ec88fa8635"
SRC_URI[sha256sum] = "5287617b0ebc33c890b17b83933811d8d37492e542a0a8b1bdca0eb33686764c"

inherit setuptools pypi
inherit tuf-manifest

do_install_append() {
    rm -f ${D}${datadir}/LICENSE
    mkdir -p ${D}${localstatedir}/tuf-manifest
    cp -r ${TUF_MANIFEST_CLIENT_REPO}/tufrepo ${D}${localstatedir}/tuf-manifest
    mkdir -p ${D}${localstatedir}/tuf-manifest/files
    echo ${TUF_MANIFEST_FILENR} >${D}${localstatedir}/tuf-manifest/num
    mkdir -p ${D}${sysconfdir}
    cp ${TUF_MANIFEST_CONF} ${D}${sysconfdir}/tuf-manifest.conf
}

PACKAGES =+ " \
    ${PN}-client \
    ${PN}-repo \
"

FILES_${PN}-client = " \
    ${bindir}/tufm-client \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf_manifest/scripts/tuf_manifest_client.py \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf_manifest/scripts/tuf_manifest_client.pyc \
"

CONFFILES_${PN}-client = " \
    ${sysconfdir}/tuf-manifest.conf \
    ${localstatedir}/tuf-manifest \
"

FILES_${PN}-repo = " \
    ${bindir}/tufm-repo \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf_manifest/scripts/tuf_manifest_repo.py \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf_manifest/scripts/tuf_manifest_repo.pyc \
"

RDEPENDS_${PN}-client += " \
    ${PYTHON_PN}-tuf-client \
"

RDEPENDS_${PN}-repo += " \
    ${PYTHON_PN}-tuf-repo \
"

RDEPENDS_${PN} += "${PYTHON_PN}-tuf"

BBCLASSEXTEND = "native nativesdk"
