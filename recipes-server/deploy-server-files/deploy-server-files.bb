LICENSE = "MIT"

inherit useradd

LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=5c4f15bc8568030112779b0fc53a2c9a"

SRC_URI = " \
        git://github.com/Mini-air-quality-station/web-server.git;protocol=https;branch=change-scripts-bash-dir \
        "

SRC_URI[sha256sum] = "e47d6907616e32dd3266d59861afc60383fd857381bd81c2daf48dd9d3683863"
SRCREV = "963ae500fe38f3508a936812e776a5a2a2350d65"
S = "${WORKDIR}/git"

RDEPENDS:${PN} += "bash"

do_install () {
    install -d ${D}/var/www

    cp -r ${S}/html ${D}/var/www/html

    chmod -R 775 ${D}/var/www/html
    chown -R www-data:www-data ${D}/var/www/html
}

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = " \
    --system --no-create-home \
    --home ${localstatedir}/www/localhost \
    --groups www-data \
    --user-group www"
