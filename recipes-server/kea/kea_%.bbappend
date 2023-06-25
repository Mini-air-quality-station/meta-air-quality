FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \ 
    file://kea-dhcp4.conf \
    "

do_install:append() {
    rm ${D}${sysconfdir}/kea/kea-dhcp4.conf
    install -m 0644 ${WORKDIR}/kea-dhcp4.conf ${D}${sysconfdir}/kea/kea-dhcp4.conf
}