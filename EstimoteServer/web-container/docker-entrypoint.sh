#!/bin/bash
set -e

declare -A dbconfig=( \
[hostname]="$DB_HOSTNAME" \
[database]="$DB_DATABASE" \
[username]="$DB_USERNAME" \
[password]="$DB_PASSWORD" \
)

# Check for unconfigured values
for i in ${!dbconfig[@]}; do
    if [ -z "${dbconfig[$i]}" ]; then
        echo >&2 "error: database.php setting $i was not given, set environment variable DB_${$i^^} to configure it."
        exit 1
    fi
done

for i in ${!dbconfig[@]}; do
    sed -i "s/'"$i"' => '.*'/'"$i"' => '"${dbconfig[$i]}"'/" /usr/local/share/codeigniter/application/config/database.php
done

# From php:7.0-apache

: "${APACHE_CONFDIR:=/etc/apache2}"
: "${APACHE_ENVVARS:=$APACHE_CONFDIR/envvars}"
if test -f "$APACHE_ENVVARS"; then
	. "$APACHE_ENVVARS"
fi

: "${APACHE_PID_FILE:=${APACHE_RUN_DIR:=/var/run/apache2}/apache2.pid}"
rm -f "$APACHE_PID_FILE"

exec apache2 -DFOREGROUND "$@"
