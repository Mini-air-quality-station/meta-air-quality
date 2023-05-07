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
"

RDEPENDS:${PN}-system = " \
  systemd \
  busybox \
  nano \
"

RDEPENDS:${PN}-server = " \
  influxdb \
  nginx \
  php-fpm \
  php-cgi \
  grafana \
  curl \
"