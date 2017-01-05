DESCRIPTION = "QCA Multicast snooper"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${WORKDIR}/copyright;md5=3d92b2738307d98bbf9c982ca535b499"

inherit module

FILESPATH =+ "${TOPDIR}/../opensource/:"
#FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "file://qca-mcs-lkm \
           file://copyright \
	   "

DEPENDS = "virtual/kernel"

DEPENDS_${PN} = "kernel-module-netfilter"

S = "${WORKDIR}/qca-mcs-lkm"

PACKAGES += "kernel-module-mcs-lkm"

setup_build_variables() {
	export LINUX_VERSION="${KERNEL_VERSION}"; 
	export KERNELPATH="${STAGING_KERNEL_BUILDDIR}/source"
	export KBUILDPATH="${STAGING_KERNEL_BUILDDIR}" 
	export KERNELRELEASE=1 
	export MC_SUPPORT_MLD=1 
}

do_compile() {
	setup_build_variables
	 make -C  "${STAGING_KERNEL_BUILDDIR}" \
	 	CROSS_COMPILE=${TARGET_PREFIX} \
		ARCH=${ARCH} \
		SUBDIRS="${S}" \
		modules
}

do_install() {
	install -d ${D}/${includedir}/qca-mcs
	install -m 0644 ${S}/mc_api.h ${D}${includedir}/qca-mcs/mc_api.h
	install -m 0644 ${S}/mc_ecm.h ${D}${includedir}/qca-mcs/mc_ecm.h
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/net/${PN}
	install -m 0644 qca-mcs${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/net/${PN}
}

KERNEL_MODULE_AUTOLOAD += "qca-mcs"
