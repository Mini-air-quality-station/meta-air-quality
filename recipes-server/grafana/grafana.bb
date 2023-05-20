DESCRIPTION = "The tool for beautiful monitoring and metric analytics & dashboards for Graphite, InfluxDB & Prometheus & More"

S = "${WORKDIR}/grafana-9.5.1"

SRC_URI:armv8a = "https://dl.grafana.com/oss/release/grafana-9.5.1.linux-arm64.tar.gz;name=arm64"

SRC_URI:append = " \
    file://dashboards.yaml \
    file://grafana.ini \
    file://influx.yaml \
    file://mini_air_quality.json \
    file://grafana-server \
    file://grafana.service \
"

SRC_URI[arm64.sha256sum] = "ed6c692be378e2d9a1a730169867ea4f3da83d814f123a1a340579644032c93e"

LICENSE = "AGPLv3"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=eb1e647870add0502f8f010b19de32af"

inherit systemd useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "--system -d /usr/share/grafana -m -s /bin/false grafana"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/bin/grafana ${D}${bindir}/grafana
    install -m 0755 ${S}/bin/grafana-cli ${D}${bindir}/grafana-cli
    install -m 0755 ${S}/bin/grafana-server ${D}${bindir}/grafana-server

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/grafana.service ${D}${systemd_system_unitdir}/grafana.service
    
    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/grafana-server ${D}${sysconfdir}/default/
    
    install -d ${D}${sysconfdir}/grafana
    install -m 0644 ${WORKDIR}/grafana.ini ${D}${sysconfdir}/grafana/grafana.ini

    install -d ${D}${sysconfdir}/grafana/provisioning/dashboards
    install -m 0644 ${WORKDIR}/dashboards.yaml ${D}${sysconfdir}/grafana/provisioning/dashboards/dashboards.yaml

    install -d ${D}${sysconfdir}/grafana/provisioning/datasources
    install -m 0644 ${WORKDIR}/influx.yaml ${D}${sysconfdir}/grafana/provisioning/datasources/influx.yaml

    install -d ${D}${sysconfdir}/grafana/provisioning/notifiers
    install -m 0644 ${S}/conf/provisioning/notifiers/sample.yaml ${D}${sysconfdir}/grafana/provisioning/notifiers/sample.yaml

    install -d ${D}/var/lib/grafana/dashboards
    install -m 0644 ${WORKDIR}/mini_air_quality.json ${D}/var/lib/grafana/dashboards/mini_air_quality.json

    chown -R grafana:grafana ${D}/var/lib/grafana

    # install frontend
    install -d ${D}${datadir}/grafana
    cp -R --no-dereference --preserve=mode,links -v \
    	${S}/public \
	${S}/conf \
	${S}/LICENSE \
	${S}/VERSION \
    	${D}${datadir}/grafana/

    install -d ${D}${datadir}/grafana/bin
    cp ${D}${bindir}/grafana ${D}${datadir}/grafana/bin/grafana
    cp ${D}${bindir}/grafana-cli ${D}${datadir}/grafana/bin/grafana-cli
    cp ${D}${bindir}/grafana-server ${D}${datadir}/grafana/bin/grafana-server
}

INSANE_SKIP:${PN} += "ldflags already-stripped build-deps"

SYSTEMD_SERVICE:${PN} = " \
   grafana.service  \
"

SYSTEMD_AUTO_ENABLE:${PN} = "enable"

FILES:${PN} += "\
    ${systemd_unitdir} \
    ${sysconfdir}/grafana \
    ${sysconfdir}/default \
"
