SUMMARY = "A component library to support SBC display drivers"
DESCRIPTION = "A component library to support SBC display drivers"
HOMEPAGE = "https://github.com/rm-hull/luma.core"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=d6b1bb82a4a0919f1fb9ca9f633d78a3"

inherit pypi python_setuptools_build_meta

SRC_URI[sha256sum] = "4da1dfcb7b8ad57fe1bcd7417a50e1f8bba044ced6fabdca6d5f3577c460fff8"

PYPI_PACKAGE = "luma.lcd"

RDEPENDS:${PN} += " \
	${PYTHON_PN}-pillow \
	${PYTHON_PN}-threading \
	${PYTHON_PN}-smbus2 \
"