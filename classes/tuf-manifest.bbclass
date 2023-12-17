#
# This class creates a "image.rootfs.tuf-manifest" file in the format
# that tufm uses.
#

ROOTFS_POSTUNINSTALL_COMMAND =+ "tuf_manifest ; "
TUF_MANIFEST = "${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.tuf-manifest"

python tuf_manifest () {
    from oe.rootfs import image_list_installed_packages
    from os import listdir
    from os.path import islink, join

    deploy_dir = d.getVar('IMGDEPLOYDIR')
    link_name = d.getVar('IMAGE_LINK_NAME')
    manifest_name = d.getVar('TUF_MANIFEST')
    kdeploy_dir = d.getVar('TUF_MANIFEST_DIR')


    if not manifest_name:
        return

    pkgs = image_list_installed_packages(d)
    with open("/tmp/dump", 'w+') as dump:
      
      dump.write(str(pkgs))
    with open(manifest_name, 'w+') as image_manifest:
        list = []
        for pkg in sorted(pkgs):
            version = "-".join(".".join((pkgs[pkg]["filename"].split('.')[:-2])).split('-')[-2:])
            list.append("%s %s %s" % (pkg, version, pkgs[pkg]["filename"]))
        list = '\n'.join(list)
        image_manifest.write(list)
        image_manifest.write("\n")

    with open(manifest_name, 'a') as image_manifest:
      kimage_file = {}
      kimage_file['name'] = [ f for f in listdir(kdeploy_dir) if not islink(join('tmp/deploy/images/raspberrypi4-64/', f )) 
                                                                   and f.startswith("Image") ][0].split('-')[0]
      kimage_file['version'] = "-".join([ f for f in listdir(kdeploy_dir) if not islink(join('tmp/deploy/images/raspberrypi4-64/', f )) 
                                                                   and f.startswith("Image") ][0].split('-')[2:5])

      kimage_file['full_name'] = [ f for f in listdir(kdeploy_dir) if not islink(join('tmp/deploy/images/raspberrypi4-64/', f )) 
                                                                   and f.startswith("Image") ][0]
      image_manifest.write("%s %s %s\n" % (kimage_file['name'], kimage_file['version'], kimage_file['full_name']))


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
