#!/bin/bash

set -e

# Get database config from docker run environment variables
declare -A dbConfig=( \
[hostname]="$DB_HOSTNAME" \
[database]="$DB_DATABASE" \
[username]="$DB_USERNAME" \
[password]="$DB_PASSWORD" \
)

# Check for empty database config values
for i in ${!dbConfig[@]}; do
    if [ -z "${dbConfig[$i]}" ]; then
        echo >&2 "error: database.php setting $i was not given, set environment variable DB_${i^^} to configure it."
        FAILED_CONFIG=true
    fi
done

if [ $FAILED_CONFIG ]; then
    exit 1
fi

# Apply settings to application/config/database.php
for i in ${!dbConfig[@]}; do
    sed -i "s/'"$i"' => '.*'/'"$i"' => '"${dbConfig[$i]}"'/" /usr/local/share/codeigniter/application/config/database.php
done

# From here same as php:7-apache
: "${APACHE_CONFDIR:=/etc/apache2}"
: "${APACHE_ENVVARS:=$APACHE_CONFDIR/envvars}"
if test -f "$APACHE_ENVVARS"; then
	. "$APACHE_ENVVARS"
fi

: "${APACHE_PID_FILE:=${APACHE_RUN_DIR:=/var/run/apache2}/apache2.pid}"
rm -f "$APACHE_PID_FILE"

exec apache2 -DFOREGROUND "$@"
