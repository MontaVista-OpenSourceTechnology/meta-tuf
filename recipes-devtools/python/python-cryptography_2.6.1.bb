inherit pypi setuptools
require python-cryptography.inc

SRC_URI[md5sum] = "401cc8268f89496643da3f7a48eb4e8e"
SRC_URI[sha256sum] = "26c821cbeb683facb966045e2064303029d572a87ee69ca5a1bf54bf55f93ca6"

SRC_URI += " \
    file://run-ptest \
"

DEPENDS += " \
    ${PYTHON_PN}-enum34 \
"

RDEPENDS_${PN} += " \
    ${PYTHON_PN}-enum34 \
    ${PYTHON_PN}-ipaddress \
"

RDEPENDS_${PN}_class-target += " \
    ${PYTHON_PN}-enum34 \
    ${PYTHON_PN}-ipaddress \
    ${PYTHON_PN}-contextlib \
"
