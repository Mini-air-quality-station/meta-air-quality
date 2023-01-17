FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "\
    ${@bb.utils.contains('RELEASE', '1', '', 'file://iotop.cfg', d)} \
"
