LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
        file://test-hello-world \
        "

S = "${WORKDIR}"

RDEPENDS:${PN} = " \
    bash \
    "

do_install () {
    install -d ${D}/${sbindir}
    install -m 0755 ${S}/test-hello-world ${D}/${sbindir}
}
