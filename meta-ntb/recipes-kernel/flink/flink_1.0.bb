DESCRIPTION = "FPGA Loader Kernel Module"
SECTION = "ntb"
DEPENDS = ""
RDEPENDS_${PN} = "flinklib fpga-loader"

LICENSE = "CLOSED"
#LICENSE = "MIT"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=96af5705d6f64a88e035781ef00e98a8"

#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# SRCREV = "cbe8838b27c1446260d0ba97157364eff46206a9"
SRCREV = "f6794756fb448ea38fe7ae575178b8729f512ec9"
SRC_URI = "gitsm://github.com/zechenturm/flinklinux.git \
file://cb20.rbf \
file://blacklist.conf \
file://initflink \
file://flink.service "

S = "${WORKDIR}/git"

inherit module

export KERNELDIR="${KERNEL_SRC}"

# OEEXTRA_CFLAGS += "-D DBG"

FILES_${PN} += "/lib/firmware/cb20.rbf \
				/etc/modprobe.d/blacklist.conf \
				/usr/bin/initflink \
				/etc/systemd \
				/etc/systemd/system \
				/etc/systemd/system/flink.service \
				/etc/systemd/system/multi-user.target.wants \
				/etc/systemd/system/multi-user.target.wants/flink.service"

do_install_append () {
	install -d ${D}/lib/firmware
	
	install -d ${D}/etc/modprobe.d
	install -m 644 ../blacklist.conf ${D}/etc/modprobe.d/

	install -d ${D}/usr/bin
	install -m 755 ../initflink ${D}/usr/bin/
	
	# working dir is ${WORKDIR}/git, but cb20.rbf is in ${WORKDIR}
	install -m 644 ../cb20.rbf ${D}/lib/firmware/

	# install flink service
	install -d ${D}/etc/systemd/system
	install -m 644 ../flink.service ${D}/etc/systemd/system/

	# enable flink service
	install -d ${D}/etc/systemd/system/multi-user.target.wants
	cd ${D}
	ln -s etc/systemd/system/udhcpc.service etc/systemd/system/multi-user.target.wants/flink.service
}
