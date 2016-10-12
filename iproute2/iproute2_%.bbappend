LICENSE = "GPLv2+"

FILESEXTRAPATHS_prepend := "${THISDIR}/:"

do_install_append() {
	install -d ${D}${includedir}
	install -d ${D}${libdir}
	install -m 0755 ${S}/include/libnetlink.h ${D}${includedir}
	install -m 0755 ${S}/lib/libnetlink.a ${D}${libdir}
}
