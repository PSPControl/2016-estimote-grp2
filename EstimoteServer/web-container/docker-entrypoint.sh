#!/bin/bash

if [ -z "$ESTIMOTESERVER_DB_HOST" ]; then
    echo >&2 "error: ESTIMOTESERVER_DB_USER needs to be specified"
    exit 1
fi

if [ -z "$ESTIMOTESERVER_DB_USER" ]; then
    echo >&2 "error: ESTIMOTESERVER_DB_USER needs to be specified"
    exit 1
fi

if [ -z "$ESTIMOTESERVER_DB_PASSWORD" ]; then
    echo >&2 "error: ESTIMOTESERVER_DB_PASSWORD needs to be specified"
    exit 1
fi
