LICENSE = "GPLv2+"

SRC_URI += "file://001-rt_tables.patch \
	file://010-bridge-mst-fix-a-musl-build-issue.patch \
	file://011-bridge-mst-fix-a-further-musl-build-issue.patch \
	file://012-libnetlink-fix-build-with-musl-and-gcc-14.patch \
	file://110-darwin_fixes.patch \
	file://115-add-config-xtlibdir.patch \
	file://120-no_arpd_ifstat_rtacct_lnstat.patch \
	file://130-no_netem_tipc_dcb_man_vdpa.patch \
	file://140-allow_pfifo_fast.patch \
	file://145-keep_libelf_optional.patch \
	file://150-keep_libcap_optional.patch \
	file://155-keep_tirpc_optional.patch \
	file://160-libnetlink-pic.patch \
	file://170-ip_tiny.patch \
	file://180-drop_FAILED_POLICY.patch \
	file://190-fix-nls-rpath-link.patch \
	file://200-drop_libbsd_dependency.patch \
	file://300-selinux-configurable.patch \
	file://400-rdma-include-libgen.h-for-basename.patch \
	file://601-ignore_t_bit.patch \
	file://602-mcuc_hw_offload.patch \
	file://603-eht_dump.patch \
	file://604-mcast_rule.patch \
"
PACKAGECONFIG ??= "tipc elf devlink mnl"
PACKAGECONFIG[mnl] = ",,libmnl"

SRC_URI += " ${@bb.utils.contains('DISTRO_CODENAME', 'dunfell', 'file://130-no_netem.patch', '', d)}"
SRC_URI += " ${@bb.utils.contains('DISTRO_CODENAME', 'dunfell', 'file://170-ip_tiny.patch', '', d)}"
SRC_URI += " ${@bb.utils.contains('DISTRO_CODENAME', 'dunfell', 'file://400-add-nss-qdisc.patch', '', d)}"
SRC_URI += " ${@bb.utils.contains('DISTRO_CODENAME', 'dunfell', 'file://600-add-ppe-qdisc.patch', '', d)}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"


do_install:append() {
	install -d ${D}${includedir}
	install -d ${D}${libdir}
	install -m 0755 ${S}/include/libnetlink.h ${D}${includedir}
	install -m 0755 ${S}/lib/libnetlink.a ${D}${libdir}
}
