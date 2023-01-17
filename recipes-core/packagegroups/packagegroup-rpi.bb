DESCRIPTION = "RaspberryPi Packagegroup"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

COMPATIBLE_MACHINE = "^rpi$|qemuarm$"

RDEPENDS:${PN} = "\
    python3-adafruit-circuitpython-register \
    python3-adafruit-platformdetect \
    python3-adafruit-pureio \
    python3-rtimu \
    python3-psutil \
    python3 \
    wireless-regdb-static \
    wpa-supplicant \
    busybox \
"

RDEPENDS:${PN}:rpi = "\
    rpi-gpio \
"
