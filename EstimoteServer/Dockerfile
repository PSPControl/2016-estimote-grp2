MAINTAINER Pauli Huttunen

FROM ubuntu:16.04

# Add MariaDB repository
RUN apt-get install software-properties-common \
    apt-key adv --recv-keys --keyserver hkp://keyserver.ubuntu.com:80 0xF1656F24C74CD1D8 \
    add-apt-repository 'deb [arch=amd64,i386,ppc64el] http://mirror.netinch.com/pub/mariadb/repo/10.1/ubuntu xenial main'

# Update the container
RUN apt-get update && \
    apt-get full-upgrade -y

# Install LAMP
RUN apt-get install \
            apache2 \
            mariadb-server \
            php libapache2-mod-php php-mcrypt php-mysql

# Install CodeIgniter
ADD ./install-codeigniter.sh /usr/bin/
RUN /usr/bin/install-codeigniter.sh

# Enable apache2
RUN systemctl enable apache2

#
