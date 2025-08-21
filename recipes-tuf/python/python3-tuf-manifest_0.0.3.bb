DESCRIPTION = "A secure updater framework for Python"
HOMEPAGE = "https://www.updateframework.com"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d36b156c33ff4e31d4892fe82a3fb978"

SRC_URI += " \
    file://tuf-rpm-updater \
    file://tuf-manifest.conf \
    file://change-configparser-for-python3.patch \
    file://Initialize-to-empty-string-in-place-of-None.patch \
    file://0001-Removing-hardcoded-repo-name.patch \
    file://0001-Updates-to-make-TUF-5.0.0-compatibile.patch \
    file://0001-Add-progress-bar-to-show-download-status.patch \
"

PYPI_PACKAGE = "tuf_manifest"

SRC_URI[md5sum] = "874bdcf202b54a2742db7023b8fddc16"
SRC_URI[sha256sum] = "b62234aca76ba4d84558b0ed5b6498c79287780611e54378488b3e84407a8905"

TUF_MANIFEST_CLIENT_REPO ??= "${TOPDIR}/tuf-manifest"
TUF_MANIFEST_CONF ??= "${TOPDIR}/tuf-manifest.conf"

inherit setuptools3 pypi
inherit tuf-manifest
PR = "3.5"
do_install:append() {
    mkdir -p ${D}${localstatedir}/tuf-manifest
    cp -r ${TUF_MANIFEST_CLIENT_REPO}/* ${D}${localstatedir}/tuf-manifest
    mkdir -p ${D}${localstatedir}/tuf-manifest/files
    echo "[Manifest]" >${D}${localstatedir}/tuf-manifest/num
    echo "curr_manifest=${TUF_MANIFEST_FILENR}" >>${D}${localstatedir}/tuf-manifest/num
    mkdir -p ${D}${sysconfdir}
    #test -n "${TUF_WEBSERVER}"
    #sed "s%WEBSERVER%${TUF_WEBSERVER}%" <${WORKDIR}/tuf-manifest.conf >${D}${sysconfdir}/tuf-manifest.conf
    cp ${WORKDIR}/tuf-manifest.conf ${D}${sysconfdir}/tuf-manifest.conf
    mkdir -p ${D}${nonarch_libdir}/tuf-manifest/scripts
    cp ${WORKDIR}/tuf-rpm-updater ${D}/${nonarch_libdir}/tuf-manifest/scripts
    chmod +x ${D}${nonarch_libdir}/tuf-manifest/scripts/tuf-rpm-updater
    rm ${D}/usr/LICENSE
}

PACKAGES =+ " \
    ${PN}-client \
    ${PN}-repo \
"

FILES:${PN}-client = " \
    ${bindir}/tufm-client \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf_manifest/tuf_manifest_client.py \
    ${sysconfdir}/tuf-manifest.conf \
    ${localstatedir}/tuf-manifest \
    ${nonarch_libdir}/tuf-manifest/scripts/tuf-rpm-updater \
"

CONFFILES:${PN}-client = " \
    ${sysconfdir}/tuf-manifest.conf \
    ${localstatedir}/tuf-manifest \
"

FILES:${PN}-repo = " \
    ${bindir}/tufm-repo \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf_manifest/tuf_manifest_repo.py \
    ${libdir}/${PYTHON_DIR}/site-packages/tuf_manifest/tuf_manifest_repo.pyc \
"

RDEPENDS:${PN}-client += " \
    ${PYTHON_PN}-tuf \
    ${PYTHON_PN}-tuf-manifest \
    ${PYTHON_PN}-tqdm \
    ${PYTHON_PN}-figlet \
"

RDEPENDS:${PN}-repo += " \
    ${PYTHON_PN}-tuf \
    ${PYTHON_PN}-tuf-manifest \
"

RDEPENDS:${PN} += "${PYTHON_PN}-tuf ${PYTHON_PN}-tqdm ${PYTHON_PN}-figlet"

BBCLASSEXTEND = "native nativesdk"
