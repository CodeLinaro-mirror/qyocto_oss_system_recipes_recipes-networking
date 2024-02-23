LICENSE = "GPLv2+"

SRC_URI += "file://001-rt_tables.patch \
	file://100-configure.patch \
	file://110-darwin_fixes.patch \
	file://115-add-config-xtlibdir.patch \
	file://120-no_arpd.patch \
	file://135-sync-iptables-header.patch \
	file://140-allow_pfifo_fast.patch \
	file://140-keep_libmnl_optional.patch \
	file://145-keep_libelf_optional.patch \
	file://150-keep_libcap_optional.patch \
	file://160-libnetlink-pic.patch \
	file://175-reduce-dynamic-syms.patch \
	file://180-drop_FAILED_POLICY.patch \
	file://200-drop_libbsd_dependency.patch \
	file://500-add-nssmirred.patch \
"

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
