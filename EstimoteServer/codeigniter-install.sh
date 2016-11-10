#!/bin/bash

WEB_DIR="/var/www/html"

CI_VERSION="3.1.2"
CI_SOURCE_URL="https://github.com/bcit-ci/CodeIgniter/archive/$CI_VERSION.zip"

CI_ROOT_DIR="/usr/local/share/codeigniter"
CI_SYSTEM_DIR="$CI_ROOT_DIR/system"
CI_APP_DIR="$CI_ROOT_DIR/applications"

install() {
    mkdir /tmp/codeigniter
    cd /tmp/codeigniter

    echo "Downloading CodeIgniter $CI_VERSION source archive..."
    wget $CI_SOURCE_URL

    echo "Creating directory structure..."
    mkdir -p $CI_ROOT_DIR
    mkdir $CI_SYSTEM_DIR
    mkdir $CI_APP_DIR

    echo "Extracting source archive..."
    unzip -q $CI_VERSION.zip

    echo "Removing unnecessary files..."
    find CodeIgniter-$CI_VERSION/system -type f -name "index.html" -exec rm {} \;
    find CodeIgniter-$CI_VERSION/system -type f -name ".htaccess" -exec rm {} \;

    echo "Installing CodeIgniter system files..."
    mv CodeIgniter-$CI_VERSION/system $CI_SYSTEM_DIR

    echo "Installing web resource files..."
    mv CodeIgniter-$CI_VERSION/index.php $WEB_DIR/index.php

    echo "Setting CodeIgniter system path..."
    sed -i -e "s#\$system_path = 'system';#\$system_path = '$CI_SYSTEM_DIR';#" $WEB_DIR/index.php

    echo "Setting CodeIgniter applications path..."
    sed -i -e "s#\$application_folder = 'application';#\$application_folder = '$CI_APP_DIR';#" $WEB_DIR/index.php

    echo "Deleting temporary files..."
    cd /tmp
    rm -rf /tmp/codeigniter

    echo "CodeIgniter installation complete"
}

help() {
    echo ""
    echo "Usage: $(basename $0) [OPTIONS]"
    echo ""
    echo -e "-w <path>\tSystem web directorys."
    echo -e "-v <version>\tForce different CodeIgniter version. Version $CI_VERSION by default."
    echo -e "-h\t\tShow this help message and exit."
    echo ""
}

while getopts "w:v:h" opt; do
    case $opt in
        h)
            help
            exit 0
            ;;
        w)
            WEB_DIR="$OPTARG"
            ;;
        v)
            CI_VERSION="$OPTARG"
            ;;
        \?)
            echo "Invalid option: -$OPTARG"
            help
            exit 1
            ;;
        :)
            echo "Option -$OPTARG requires an argument"
            help
            exit 1
            ;;
    esac
done

install
exit 0
