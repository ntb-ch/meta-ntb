LICENSE = "CLOSED"

SRC_URI = "file://udhcpc.service"

S = "${WORKDIR}"

do_install() {
	install -d ${D}/etc/systemd/system
	install -m 644 udhcpc.service ${D}/etc/systemd/system/

	# enable uhcpc service
	install -d ${D}/etc/systemd/system/network.target.wants
	cd ${D}
	ln -s etc/systemd/system/udhcpc.service etc/systemd/system/network.target.wants/udhcpc.service
}
