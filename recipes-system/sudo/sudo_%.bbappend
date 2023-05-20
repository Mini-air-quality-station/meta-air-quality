FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://www-data"

do_install:append () {
    install -d -m 750 ${D}${sysconfdir}/sudoers.d
    install -m 440 ${WORKDIR}/www-data ${D}${sysconfdir}/sudoers.d/www-data
}

FILES:${PN} += "${sysconfdir}/sudoers.d/www-data"