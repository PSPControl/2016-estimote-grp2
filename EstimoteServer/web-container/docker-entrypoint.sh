#!/bin/bash

declare -A dbconfig=( \
[host]="$DB_HOST" \
[name]="$DB_NAME" \
[user]="$DB_USER" \
[password]="$DB_PASSWORD" \
)

# Check for unconfigured values
for i in ${!dbconfig[@]}; do
    if [ -z "${dbconfig[$i]}" ]; then
        echo >&2 "error: database $i was not given"
        echo >&2 "set environment variable DB_${i^^} to configure database $i"
        exit 1
    fi
done

for i in ${!dbconfig[@]}; do
    sed -i -e "s/^'${!dbconfig[$i]}' => '.*'$/^'${!dbconfig[$i]}' => '${dbconfig[$i]}'$/" /usr/local/share/codeigniter/application/config/database.php
done

exec apache2 -DFOREGROUND
