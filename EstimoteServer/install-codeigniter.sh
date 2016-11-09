#!/bin/bash

SOURCE_URL="https://github.com/bcit-ci/CodeIgniter/archive/3.1.2.zip"

INSTALL_DIR="/var/www/html"

INSTALL_NAME="codeigniter"

install() {
    cd $INSTALL_DIR

    echo "Downloading CodeIgniter source archive..."
    wget -O codeignite.zip $SOURCE_URL

    echo "Extracting source files from archive..."
    unzip codeigniter.zip -d ./$INSTALL_NAME

    echo "Deleting source archive..."
    rm codeigniter.zip

    echo "Installation complete"
}

help() {
    echo ""
    echo " Usage: $(basename $0) [OPTIONS]"
    echo -e "-n <string>\tInstall files to $INSTALL_DIR/<string> (codeigniter by default)"
    echo -e "-h\tShow this help message"
}

while getopts "n:h" opt; do
    case $opt in
        h)
            help
            exit 0
            ;;
        n)
            INSTALL_NAME="$OPTARG"
            ;;
        \?)
            echo "Invalid option: -$OPTARG"
            exit 1
            ;;
        :)
            echo "Option -$OPTARG requires an argument"
            exit 1
            ;;
    esac
done

install
