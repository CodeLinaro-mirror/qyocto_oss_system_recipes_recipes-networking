DESCRIPTION = "QCA Multicast snooper"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${WORKDIR}/copyright;md5=3d92b2738307d98bbf9c982ca535b499"

inherit module

CLEANBROKEN = "1"

SOC="${@d.getVar('SOC_FAMILY', d, 1).split(':')[1]}"
SOC_TYPE = "${@d.getVar('SOC', d, 0).split('_')[0]}"

FILESPATH =+ "${TOPDIR}/../opensource/:"

SRC_URI = "file://qca-mcs-lkm \
           file://copyright \
	   "

DEPENDS = "virtual/kernel qca-ovsmgr"

S = "${WORKDIR}/qca-mcs-lkm"

PACKAGES += "kernel-module-qca-mcs"

setup_build_variables() {
	unset LDFLAGS
	export LINUX_VERSION="${KERNEL_VERSION}";
	export KERNELPATH="${STAGING_KERNEL_BUILDDIR}/source"
	export KBUILDPATH="${STAGING_KERNEL_BUILDDIR}"
	export KERNELRELEASE=1
	export CONFIG_SUPPORT_MLD=y
}

EXTRA_CFLAGS += "-I${STAGING_INCDIR}/qca-ovsmgr \
		 "

MODULE_EXTRA_SYMBOLS ="${STAGING_INCDIR}/qca-ovsmgr/Module.symvers"

# ${STAGING_INCDIR}/qca-mcs/Module.symvers

MCS_MAKE_OPTS:${SOC} = " CONFIG_SUPPORT_OVS=y"
MCS_MAKE_OPTS:ipq807x:remove = "CONFIG_SUPPORT_OVS=y"
MCS_MAKE_OPTS:ipq807x_64:remove = "CONFIG_SUPPORT_OVS=y"

do_compile() {
	setup_build_variables
	 make -C  "${STAGING_KERNEL_BUILDDIR}" \
	 	CROSS_COMPILE=${TARGET_PREFIX} \
		KBUILD_EXTRA_SYMBOLS="${MODULE_EXTRA_SYMBOLS}" \
		EXTRA_CFLAGS="${EXTRA_CFLAGS}" \
		ARCH=${ARCH} \
		SoC="${SOC_TYPE}" \
		${MCS_MAKE_OPTS} \
		M="${S}" \
		modules
}

do_install() {
	install -d ${D}/${includedir}/qca-mcs
	install -m 0644 ${S}/mc_api.h ${D}${includedir}/qca-mcs/mc_api.h
	install -m 0644 ${S}/mc_ecm.h ${D}${includedir}/qca-mcs/mc_ecm.h
	install -m 0644 ${S}/Module.symvers ${D}${includedir}/qca-mcs/Module.symvers
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/net/${PN}
	install -m 0644 qca-mcs${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/net/${PN}
}

KERNEL_MODULE_AUTOLOAD += "qca-mcs"
module_autoload_qca-mcs = "qca-mcs"
