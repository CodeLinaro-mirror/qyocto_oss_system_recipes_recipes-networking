LICENSE = "GPLv2+"

SRC_URI += "file://001-rt_tables.patch"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

do_install_append() {
	install -d ${D}${includedir}
	install -d ${D}${libdir}
	install -m 0755 ${S}/include/libnetlink.h ${D}${includedir}
	install -m 0755 ${S}/lib/libnetlink.a ${D}${libdir}
}
