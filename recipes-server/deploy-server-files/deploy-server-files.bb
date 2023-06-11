LICENSE = "MIT"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

inherit useradd systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "php-init.service"


LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=5c4f15bc8568030112779b0fc53a2c9a"

SRC_URI = " \
        git://github.com/Mini-air-quality-station/web-server.git;protocol=https;branch=master \
        file://php-init.service \
        file://php-initscript \
        "

SRC_URI[sha256sum] = "e47d6907616e32dd3266d59861afc60383fd857381bd81c2daf48dd9d3683863"
SRCREV = "976559757901b2b31b64b914ba01d2b8c53e95dd"
S = "${WORKDIR}/git"

RDEPENDS:${PN} += "bash"

do_install() {
    install -d ${D}/var/www

    cp -r ${S}/html ${D}/var/www/html

    chmod -R 775 ${D}/var/www/html
    chown -R www-data:www-data ${D}/var/www/html

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/php-initscript ${D}${bindir}/

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/php-init.service ${D}${systemd_system_unitdir}/
}

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = " \
    --system --no-create-home \
    --home ${localstatedir}/www/localhost \
    --groups www-data \
    --user-group www"

FILES:${PN} += " \
    ${bindir}/php-initscript \
    ${systemd_system_unitdir}/php-init.service \
    "
