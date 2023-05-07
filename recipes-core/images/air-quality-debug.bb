include recipes-core/images/core-image-minimal.bb

IMAGE_FEATURES += "ssh-server-openssh debug-tweaks"

#LICENSE = "MIT"
COMPATIBLE_MACHINE = "^rpi$"

IMAGE_INSTALL:append = " \
  packagegroup-core-base-utils \
  packagegroup-air-quality-system \
  packagegroup-air-quality-server \
  packagegroup-air-quality-python \
  deploy-files \
"

IMAGE_FSTYPES = "wic.gz wic.bmap"
