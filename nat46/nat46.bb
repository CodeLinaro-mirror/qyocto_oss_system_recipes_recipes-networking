DESCRIPTION = "nat46"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/nat46/modules/README;md5=21cb6c973b8a4da216104f707874104e"

inherit module

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "git://github.com/ayourtch/nat46.git;protocol=git;branch=master \
	   file://100-kernel-5.4-compat.patch \
	   file://101-skb-reset.patch \
	   file://102-mapt.patch \
	   file://103-tos.patch \
	   file://104-icmp.patch \
	   file://105-longest-prefix-match.patch \
	   file://106-dummy_header.patch \
	   file://107-stats.patch \
	   file://108-ce_port.patch \
	   file://109-fragment_if_not_df_and_larger_than_mtu.patch \
	   file://110-icmp_error_not_handled.patch \
	   file://111-fix_null_point_reference.patch \
	   file://112-fix_icmp_crash.patch \
	   file://113-fix_delete_race_condition.patch \
	   file://114-fix-get-release-instance-protect.patch \
	   file://115-export-ip6_update_csm-api.patch \
	   file://116-rate-limit-the-print.patch \
	   file://117-fix-icmp-no-payload-bug.patch \
	   file://16-fix-l3_payload-length-wrong-in-fragment-case.patch \
	   file://17-add-support-ipv6-udp-checksum-0.patch \
	   file://18-performance_fix.patch \
	   file://19-mapt-offloading-support.patch \
	   file://20-sleeping_backtrace.patch \
	   file://21-fix-checksum-calculation.patch \
	   "

SRCREV = "1182f30785e4274913f01a8c3d7e1b5437ae3819"

PACKAGES += "kernel-module-nat46"

S = "${WORKDIR}/git"

CXXFLAGS += "-D__MUSL__"

do_compile() {
	unset LDFLAGS
	make -C ${STAGING_KERNEL_BUILDDIR} \
	CROSS_COMPILE="${TARGET_PREFIX}" \
	ARCH="${KARCH}" \
	M="${S}/nat46/modules" \
	MODFLAGS="-DMODULE -mlong-calls" \
	EXTRA_CFLAGS="-DNAT46_VERSION=\\\"${SRCREV}\\\"" \
	modules
}

do_install() {
	install -d ${D}${includedir}/nat46
	install -m 0644 ${S}/nat46/modules/*.h  ${D}${includedir}/nat46/.
	install -m 0644 ${S}/nat46/modules/Module.symvers ${D}${includedir}/nat46/Module.symvers

	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/${PN}
	install -m 0644 ${S}/nat46/modules/nat46${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/${PN}
}

FILES_${PN} = " \
	${includedir}/nat46/*.h \
	"

KERNEL_MODULE_AUTOLOAD += "nat46"
