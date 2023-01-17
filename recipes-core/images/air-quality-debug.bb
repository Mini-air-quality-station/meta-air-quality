include recipes-core/images/core-image-minimal.bb

IMAGE_FEATURES += "ssh-server-openssh debug-tweaks allow-root-login allow-empty-password empty-root-password"

COMPATIBLE_MACHINE = "^rpi$|qemuarm$"

IMAGE_INSTALL:append = " \
  packagegroup-core-base-utils \
  packagegroup-rpi \
  iotop \
  nano \
  pciutils \
"

IMAGE_FSTYPES:qemuarm = "ext4"
