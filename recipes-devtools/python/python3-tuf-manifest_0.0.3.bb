DESCRIPTION = "A secure updater framework for Python"
HOMEPAGE = "https://www.updateframework.com"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d36b156c33ff4e31d4892fe82a3fb978"

SRC_URI += " \
    file://tuf-rpm-updater \
    file://configparser-namechage.patch \
"

PYPI_PACKAGE = "tuf_manifest"

SRC_URI[md5sum] = "874bdcf202b54a2742db7023b8fddc16"
SRC_URI[sha256sum] = "b62234aca76ba4d84558b0ed5b6498c79287780611e54378488b3e84407a8905"

inherit setuptools3 pypi
inherit tuf-manifest
PR = "1"
do_install_append() {
    mkdir -p ${D}${localstatedir}/tuf-manifest
    cp -r ${TUF_MANIFEST_CLIENT_REPO}/tufrepo ${D}${localstatedir}/tuf-manifest
    mkdir -p ${D}${localstatedir}/tuf-manifest/files
    echo "[Manifest]" >${D}${localstatedir}/tuf-manifest/num
    echo "curr_manifest=${TUF_MANIFEST_FILENR}" >>${D}${localstatedir}/tuf-manifest/num
    mkdir -p ${D}${sysconfdir}
    cp ${TUF_MANIFEST_CONF} ${D}${sysconfdir}/tuf-manifest.conf
    mkdir -p ${D}${libdir}/tuf-manifest/scripts
    cp ${WORKDIR}/tuf-rpm-updater ${D}/${libdir}/tuf-manifest/scripts
    chmod +x ${D}/${libdir}/tuf-manifest/scripts/tuf-rpm-updater
}

PACKAGES =+ " \
    ${PN}-client \
    ${PN}-repo \
"

FILES_${PN}-client = " \
    ${bindir}/tufm-client \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf_manifest/tuf_manifest_client.py \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf_manifest/tuf_manifest_client.pyc \
    ${sysconfdir}/tuf-manifest.conf \
    ${localstatedir}/tuf-manifest \
    ${libdir}/tuf-manifest/scripts/tuf-rpm-updater \
    ${datadir}/LICENSE \
"

CONFFILES_${PN}-client = " \
    ${sysconfdir}/tuf-manifest.conf \
    ${localstatedir}/tuf-manifest \
"

FILES_${PN}-repo = " \
    ${bindir}/tufm-repo \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf_manifest/tuf_manifest_repo.py \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf_manifest/tuf_manifest_repo.pyc \
"

RDEPENDS_${PN}-client += " \
    ${PYTHON_PN}-tuf-client \
    ${PYTHON_PN}-tuf-manifest \
"

RDEPENDS_${PN}-repo += " \
    ${PYTHON_PN}-tuf-repo \
    ${PYTHON_PN}-tuf-manifest \
"

RDEPENDS_${PN} += "${PYTHON_PN}-tuf"

BBCLASSEXTEND = "native nativesdk"
