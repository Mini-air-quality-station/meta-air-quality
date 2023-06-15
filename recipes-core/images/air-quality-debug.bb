include recipes-core/images/core-image-minimal.bb

IMAGE_FEATURES += "ssh-server-openssh debug-tweaks"

#LICENSE = "MIT"
COMPATIBLE_MACHINE = "^rpi$"

IMAGE_INSTALL:append = " \
  packagegroup-core-base-utils \
  packagegroup-air-quality-system \
  packagegroup-air-quality-server \
  packagegroup-air-quality-python \
  packagegroup-core-buildessential \
  packagegroup-base-extended \
  deploy-files \
  libgpiod \
  libgpiod-dev \
  libgpiod-tools \
  libftdi \
"

IMAGE_FSTYPES = "wic.gz wic.bmap"

change_php_fpm_config() {
  sed -i 's/^listen = 127.0.0.1:9000$/listen=\/run\/php\/php-fpm.sock/' ${IMAGE_ROOTFS}/${sysconfdir}/php-fpm.conf
  sed -i 's/^user = nobody$/user = www-data/' ${IMAGE_ROOTFS}/${sysconfdir}/php-fpm.conf
  sed -i '$a/usr/local/lib' ${IMAGE_ROOTFS}/${sysconfdir}/ld.so.conf
  sed -i 's/^ssid=test$/ssid=MiniAirQuality/' ${IMAGE_ROOTFS}/${sysconfdir}/hostapd.conf
  sed -i 's/^#country_code=US$/country_code=PL/' ${IMAGE_ROOTFS}/${sysconfdir}/hostapd.conf
  sed -i 's/^channel=1$/channel=11/' ${IMAGE_ROOTFS}/${sysconfdir}/hostapd.conf
}

ROOTFS_POSTPROCESS_COMMAND += "change_php_fpm_config; "