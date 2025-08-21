inherit setuptools3
require python3-securesystemslib.inc

SRC_URI[md5sum] = "1c13bb90c631a8be87985ec3551ca66a"
SRC_URI[sha256sum] = "34fa63e3296a0540b122a13bf51722ecd015be00c1d2ed45b23442e718920e76"

do_configure:prepend() {
cat > ${S}/setup.py <<-EOF
from setuptools import setup

setup(
       name="${PYPI_PACKAGE}",
       version="${PV}",
       license="${LICENSE}",
)
EOF
}
