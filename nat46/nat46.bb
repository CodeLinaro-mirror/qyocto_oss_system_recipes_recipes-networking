DESCRIPTION = "nat46"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/nat46/modules/README;md5=21cb6c973b8a4da216104f707874104e"

inherit module

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "git://github.com/ayourtch/nat46.git;protocol=git;branch=master \
	   file://01-mapt.patch \
	   file://02-tos.patch \
	   file://03-icmp.patch \
	   file://04-longest_prefix_match.patch \
	   file://05-dummy_header.patch \
	   "

SRCREV = "8ff2ae59ec9840a7b8b45f976c51cae80abe0226"

PACKAGES += "kernel-module-nat46"

S = "${WORKDIR}/git"

CXXFLAGS += "-D__MUSL__"

do_compile() {
	make -C ${STAGING_KERNEL_BUILDDIR} \
	CROSS_COMPILE="${TARGET_PREFIX}" \
	ARCH="${KARCH}" \
	SUBDIRS="${S}/nat46/modules" \
	MODFLAGS="-DMODULE -mlong-calls" \
	EXTRA_CFLAGS="-DNAT46_VERSION=\\\"${SRCREV}\\\"" \
	modules
}

do_install() {
	install -d ${D}${includedir}/nat46
	install -m 0644 ${S}/nat46/modules/*.h  ${D}${includedir}/nat46/.

	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/${PN}
	install -m 0644 ${S}/nat46/modules/nat46${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/${PN}
}

FILES_${PN} = " \
	${includedir}/nat46/*.h \
	"

KERNEL_MODULE_AUTOLOAD += "nat46"
