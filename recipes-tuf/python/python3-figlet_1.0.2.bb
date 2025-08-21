SUMMARY = "Python3 interface to the 'figlet' utility"
DESCRIPTION = "python3-figlet allows to create ASCII art text"
HOMEPAGE = "https://github.com/pwaller/pyfiglet"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f37a72c457e560fc4853ae67f3f9cc0e"

SRC_URI[md5sum] = "0cb431d9b6b11de98a635f5466455a63"
SRC_URI[sha256sum] = "758788018ab8faaddc0984e1ea05ff330d3c64be663c513cc1f105f6a3066dab"

PYPI_PACKAGE = "pyfiglet"

inherit pypi setuptools3

RDEPENDS:${PN} += "python3-figlet"

BBCLASSEXTEND = "native nativesdk"
