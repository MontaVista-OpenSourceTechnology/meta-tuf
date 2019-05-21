#
# This class creates a "image.rootfs.tuf-manifest" file in the format
# that tufm uses.
#

ROOTFS_POSTUNINSTALL_COMMAND =+ "tuf_manifest ; "
TUF_MANIFEST = "${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.tuf-manifest"

python tuf_manifest () {
    from oe.rootfs import image_list_installed_packages

    deploy_dir = d.getVar('IMGDEPLOYDIR')
    link_name = d.getVar('IMAGE_LINK_NAME')
    manifest_name = d.getVar('TUF_MANIFEST')

    if not manifest_name:
        return

    pkgs = image_list_installed_packages(d)
    with open(manifest_name, 'w+') as image_manifest:
        list = []
        for pkg in sorted(pkgs):
            list.append("%s %s %s" % (pkg, pkgs[pkg]["ver"], pkgs[pkg]["filename"]))
        list = '\n'.join(list)
        image_manifest.write(list)
        image_manifest.write("\n")

    if os.path.exists(manifest_name):
        manifest_link = deploy_dir + "/" + link_name + ".tuf-manifest"
        if os.path.lexists(manifest_link):
            os.remove(manifest_link)
        os.symlink(os.path.basename(manifest_name), manifest_link)
}

TUF_MANIFEST_DIR ?= "${DEPLOY_DIR_IMAGE}"
TUF_MANIFEST_USER_FILENAME ?= "manifest"
TUF_MANIFEST_CURRENT_FILENR = "${@tuf_manifest_get_filenr(d)}"
TUF_MANIFEST_FILENR ?= "${TUF_MANIFEST_CURRENT_FILENR}"

def tuf_manifest_get_filenr(d):
    import os
    deploy_dir = d.getVar('TUF_MANIFEST_DIR')
    fname = d.getVar('TUF_MANIFEST_USER_FILENAME')
    i = 1
    while os.path.exists(os.path.join(deploy_dir, fname + '.' + str(i))):
        i = i + 1
    return str(i)
