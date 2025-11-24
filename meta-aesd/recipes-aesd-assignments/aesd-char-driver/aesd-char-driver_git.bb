# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit update-rc.d

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-ckappelmann.git;protocol=ssh;branch=main \
           file://aesd-char-driver.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "538dd69727f5912ec23486ecedbda0686ce88067"

S = "${WORKDIR}/git/aesd-char-driver"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "aesd-char-driver"
INITSCRIPT_PARAMS = "defaults"

inherit module
FILES:${PN} += "${sysconfdir}/init.d/aesd-char-driver"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_install () {
	install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra
	install -m 0644 ${S}/aesdchar.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
	
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/aesd-char-driver.sh ${D}${sysconfdir}/init.d/aesd-char-driver
}
