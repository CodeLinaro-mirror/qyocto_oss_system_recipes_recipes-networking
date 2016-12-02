DESCRIPTION = "mcproxy"
SECTION = "mcproxy"
LICENSE = "GPL-2.0+"
PR = "r0"

inherit cmake

LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/mcproxy/mcproxy.git \
	   file://0001-add-cmake.patch \
	   file://0002-uclibc-rm-stoi.patch \
	   file://0003-uclibc-add-sourcefilter.patch \
	   file://0004-uclibc-add-defs.patch \
	   file://004_mld_filter_mode.patch \
	   file://005-checksum_algorithm_fix.patch \
	   file://005_group_retrans_timer.patch \
	   file://config_files"

SRCREV = "b7bd2d0809a0d1f177181c361b9a6c83e193b79a"

S = "${WORKDIR}/git"

do_install_append() {
	cp -r ${WORKDIR}/config_files/* ${D}/
	mv ${D}/usr/bin ${D}/usr/sbin
	mv ${D}/usr/sbin/mcproxy-bin ${D}/usr/sbin/mcproxy
}

INITSCRIPT_PACKAGES = "mcproxy"
INITSCRIPT_NAME = "mcproxy"
INITSCRIPT_PARAMS = "start 98 S . stop 98 0 6 1 ."
