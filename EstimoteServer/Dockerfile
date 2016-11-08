FROM ubuntu:16.04

# Server app repository

env REPO "https://github.com/OAMK-TVT14SNO/2016-estimote-grp2.git"

# Update the container

RUN apt update && \
    apt full-upgrade -y && \
    apt-get autoremove -y && \
    apt-get autoclean

# Install server app denpendecies
