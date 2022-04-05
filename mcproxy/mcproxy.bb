DESCRIPTION = "mcproxy"
SECTION = "mcproxy"
LICENSE = "GPL-2.0+"
PR = "r0"

inherit cmake
inherit systemd

LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/mcproxy/mcproxy.git \
	   file://0001-add-cmake.patch \
	   file://0002-rm-stoi.patch \
	   file://0003-add-sourcefilter.patch \
	   file://0004-add-defs.patch \
	   file://004_mld_filter_mode.patch \
	   file://005-checksum_algorithm_fix.patch \
	   file://005_group_retrans_timer.patch \
	   file://006_group_specific_source_address_violation.patch \
	   file://007_sum_macro_wrong_extension.patch \
	   file://config_files"

SRCREV = "93b5ace42268160ebbfff4c61818fb15fa2d9b99"

S = "${WORKDIR}/git"

CXXFLAGS += "-D__MUSL__"

do_install_append() {
	install -d ${D}${sysconfdir}/
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${sysconfdir}/network/if-up.d
	install -d ${D}${systemd_unitdir}/system
	install -m 0755 ${WORKDIR}/config_files/etc/mcproxy.ifu ${D}${sysconfdir}/network/if-up.d/mcproxy
	install -m 0644 ${WORKDIR}/config_files/mcproxy.service ${D}${systemd_unitdir}/system/mcproxy.service
	mv ${D}/usr/bin ${D}/usr/sbin
	mv ${D}/usr/sbin/mcproxy-bin ${D}/usr/sbin/mcproxy
	install -m 0755 ${WORKDIR}/config_files/etc/init.d/mcproxy_init_service ${D}/usr/sbin/mcproxy_init_service
	install -d ${D}/etc
	install -d ${D}/etc/config
	install -m 0755 ${WORKDIR}/config_files/etc/mcproxy.conf ${D}/etc/config/mcproxy
}

FILES_${PN} += " \
	${sysconfdir}/* \
	${systemd_unitdir}/* \
	/usr/sbin/* \
	${systemd_unitdir}/system/mcproxy.service \
	/etc/config \
"

BBCLASSEXTEND += "native"
SYSTEMD_SERVICE_${PN} += "mcproxy.service"
