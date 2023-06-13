SUMMARY = "air-quality packagegroup"
DESCRIPTION = "air-quality packagegroup"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = " \
  ${PN}-python \
  ${PN}-system \
  ${PN}-server \
"

RDEPENDS:${PN}-python = " \
  python3 \
  python3-pyyaml \
  python3-pip \
  python3-setuptools \
  python3-luma-core \
  python3-luma-lcd \
  python3-cbor2 \
  python3-spidev \
  python3-dev \
  python3-installer \
  python3-wheel \
  rpi-gpio \
"

RDEPENDS:${PN}-system = " \
  systemd \
  busybox \
  nano \
  sudo \
  linux-firmware-rpidistro-bcm43455 \
  spitools \
  i2c-tools \
  make \
  gcc \
"

RDEPENDS:${PN}-server = " \
  influxdb \
  nginx \
  php-fpm \
  php-cgi \
  grafana \
  curl \
  deploy-server-files \
  hostapd \
"