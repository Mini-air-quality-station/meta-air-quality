inherit systemd useradd

DESCRIPTION = "InfluxDB"
SUMMARY = "InfluxDB is a time series database designed to handle high write and query loads."
HOMEPAGE = "https://www.influxdata.com/products/influxdb-overview/"

SRC_URI = " \
    https://dl.influxdata.com/influxdb/releases/influxdb2-2.7.1-linux-arm64.tar.gz \
    file://influxd.bolt \
    file://config.toml \
    file://influxd-systemd-start.sh \
    file://influxdb.service \
    file://influxdb2 \
    "
SRC_URI[sha256sum] = "b88989dae0c802fdee499fa07aae837139da3c786293c74e9d7c46b8460510d4"

LICENSE="MIT"
PV="2.7.1"

S = "${WORKDIR}/influxdb2_linux_arm64"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=86f93342918318318c795b9999bd8131"

SYSTEMD_SERVICE:${PN} = "influxdb.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

INSANE_SKIP:${PN}:append = "already-stripped"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "--system -d /var/lib/influxdb -m -s /bin/nologin influxdb"

RDEPENDS:${PN} += "bash"

do_unpack() {
    tar -xzvf ${DL_DIR}/influxdb2-2.7.1-linux-arm64.tar.gz -C ${WORKDIR}
    cp ${THISDIR}/files/influxd.bolt ${WORKDIR}
    cp ${THISDIR}/files/config.toml ${WORKDIR}
    cp ${THISDIR}/files/influxd-systemd-start.sh ${WORKDIR}
    cp ${THISDIR}/files/influxdb.service ${WORKDIR}
    cp ${THISDIR}/files/influxdb2 ${WORKDIR}
}

do_install() {
    install -d ${D}/etc/influxdb

    install -d ${D}/usr/bin
    install -m 0755 ${S}/influxd ${D}/usr/bin/

    install -d ${D}/${localstatedir}/lib/influxdb
    install -m 0755 ${WORKDIR}/influxd.bolt ${D}/${localstatedir}/lib/influxdb/influxd.bolt
    chown influxdb:influxdb ${D}/${localstatedir}/lib/influxdb/influxd.bolt

    install -d ${D}${base_libdir}/systemd/system
    install -m 0644 ${WORKDIR}/influxdb.service ${D}${base_libdir}/systemd/system/influxdb.service

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/influxdb2 ${D}${sysconfdir}/default/influxdb2

    install -d ${D}${libdir}/influxdb/scripts/
    install -m 0775 ${WORKDIR}/influxd-systemd-start.sh ${D}${libdir}/influxdb/scripts/influxd-systemd-start.sh

    install -m 0644 ${WORKDIR}/config.toml ${D}/etc/influxdb/config.toml
}