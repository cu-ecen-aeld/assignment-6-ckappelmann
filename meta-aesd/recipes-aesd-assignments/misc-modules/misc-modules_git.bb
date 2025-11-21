# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

inherit update-rc.d

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-ckappelmann.git;protocol=ssh;branch=main \
           file://0001-Modified-Makefile-for-only-misc-and-scull.patch \
		   file://misc-modules.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "43444726401c8211ed6ebf80686a915b211568eb"

S = "${WORKDIR}/git"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "misc-modules"
INITSCRIPT_PARAMS = "defaults"

inherit module
FILES:${PN} += "${sysconfdir}/init.d/misc-modules"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_install () {
	install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra
	install -m 0644 ${S}/misc-modules/hello.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/hello.ko
	install -m 0644 ${S}/misc-modules/faulty.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/faulty.ko

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/misc-modules.sh ${D}${sysconfdir}/init.d/misc-modules
}
