include recipes-core/images/core-image-base.bb

IMAGE_FEATURES += "ssh-server-openssh debug-tweaks"

#LICENSE = "MIT"
COMPATIBLE_MACHINE = "^rpi$"

IMAGE_INSTALL:append = " \
  packagegroup-core-base-utils \
  busybox \
  deploy-files \
"

IMAGE_FSTYPES = "wic.gz wic.bmap"
