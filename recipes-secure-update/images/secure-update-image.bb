DESCRIPTION = "Secure Update Image" 

LICENSE = "MIT"

inherit secure-update-image

IMAGE_LINGUAS = " "

IMAGE_ROOTFS_SIZE ?= "8192"

# Create a bootable partitioned image
IMAGE_FSTYPES = "wic ext4"
IMAGE_FSTYPES:remove = "ext2.gz"
IMAGE_TYPEDEP:wic = "ext4"
WKS_FILE:x86-64 = "secure-update-image.wks"

IMAGE_INSTALL:append = " vim"

#IMAGE_PREPROCESS_COMMAND += "fix_printk"
