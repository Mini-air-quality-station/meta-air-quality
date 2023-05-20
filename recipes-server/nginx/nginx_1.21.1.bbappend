FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \ 
    file://default \
    file://fastcgi-php.conf \
    "

do_install:append() {
    rm ${D}${sysconfdir}/nginx/sites-available/default_server
    rm ${D}${sysconfdir}/nginx/sites-enabled/default_server

    install -Dm 0644 ${WORKDIR}/default ${D}${sysconfdir}/nginx/sites-available/default_server
    sed -i 's,/var/,${localstatedir}/,g' ${D}${sysconfdir}/nginx/sites-available/default_server
    ln -s ../sites-available/default_server ${D}${sysconfdir}/nginx/sites-enabled/

    install -d ${D}/${sysconfdir}/nginx/snippets
    install ${WORKDIR}/fastcgi-php.conf ${D}/${sysconfdir}/nginx/snippets/fastcgi-php.conf

    install -d ${D}/var/www/html
    chmod -R 775 ${D}/var/www/html
    chown -R www-data:www-data ${D}/var/www/html
}