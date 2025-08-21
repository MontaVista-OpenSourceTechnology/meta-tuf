IMAGE_FEATURES += "ssh-server-openssh "

EXTRA_IMAGE_FEATURES += "package-management"

IMAGE_INSTALL = "packagegroup-core-boot"
IMAGE_INSTALL += "packagegroup-core-full-cmdline"
IMAGE_INSTALL += "packagegroup-core-montavista"
IMAGE_INSTALL += "packagegroup-secure-update"

inherit core-image

