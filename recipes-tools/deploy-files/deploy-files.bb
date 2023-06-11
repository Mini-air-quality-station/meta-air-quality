LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=5c4f15bc8568030112779b0fc53a2c9a"

SRC_URI = " \
        git://github.com/Mini-air-quality-station/sensors-and-screen-controller.git;protocol=https;branch=main \
        file://packages \
        file://pip-env-var \
        file://sensors-initscript \
        file://start-sensors.service \
        "
SRCREV = "40a83796e654b8f3c30f81adc3c235de52098100"
S = "${WORKDIR}/git"

inherit systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "start-sensors.service"

RDEPENDS:${PN} = " \
    bash \
    python3 \
    "

do_install () {
    install -d ${D}/${sysconfdir}/pip-packages/
    cp -r ${WORKDIR}/packages ${D}/${sysconfdir}/pip-packages

    install -d ${D}/${sysconfdir}/mini-air-quality/
    install -d ${D}/${sysconfdir}/mini-air-quality/sensor-spec/

    install -m 0766 ${S}/sensor_specs/sensors_config.ini ${D}/${sysconfdir}/mini-air-quality/sensors_config.ini
    install -m 0766 ${S}/sensor_specs/envs.lock ${D}/${sysconfdir}/mini-air-quality/envs.lock
    install -m 0766 ${WORKDIR}/pip-env-var ${D}/${sysconfdir}/mini-air-quality/pip-env-var

    install -m 0755 ${S}/sensor_specs/humidity_sensor.yaml ${D}/${sysconfdir}/mini-air-quality/sensor-spec/humidity_sensor.yaml
    install -m 0755 ${S}/sensor_specs/particles_sensor.yaml ${D}/${sysconfdir}/mini-air-quality/sensor-spec/particles_sensor.yaml
    install -m 0755 ${S}/sensor_specs/pressure_sensor.yaml ${D}/${sysconfdir}/mini-air-quality/sensor-spec/pressure_sensor.yaml
    install -m 0755 ${S}/sensor_specs/temperature_sensor.yaml ${D}/${sysconfdir}/mini-air-quality/sensor-spec/temperature_sensor.yaml

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/sensors-initscript ${D}${bindir}/

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/start-sensors.service ${D}${systemd_system_unitdir}/

    install -d ${D}/${prefix}/src/sensors-and-screen-controller/
    install -m 0755 ${S}/LICENSE ${D}/${prefix}/src/sensors-and-screen-controller/LICENSE
    install -m 0755 ${S}/display.py ${D}/${prefix}/src/sensors-and-screen-controller/display.py
    install -m 0755 ${S}/display_config.ini ${D}/${prefix}/src/sensors-and-screen-controller/display_config.ini
    install -m 0755 ${S}/menu.py ${D}/${prefix}/src/sensors-and-screen-controller/menu.py
    install -m 0755 ${S}/sensor_main.py ${D}/${prefix}/src/sensors-and-screen-controller/sensor_main.py
    install -m 0755 ${S}/sensor_menu.py ${D}/${prefix}/src/sensors-and-screen-controller/sensor_menu.py
    install -m 0755 ${S}/sensors.py ${D}/${prefix}/src/sensors-and-screen-controller/sensors.py
    install -m 0755 ${S}/test.py ${D}/${prefix}/src/sensors-and-screen-controller/test.py
    install -m 0755 ${S}/util.py ${D}/${prefix}/src/sensors-and-screen-controller/util.py    
}

FILES:${PN} += " \
    ${prefix} \
    "
