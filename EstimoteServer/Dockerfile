FROM ubuntu:16.04

MAINTAINER Pauli Huttunen <pauli.k.huttunen@gmail.com>

ARG CI_VERSION="3.1.2"

ARG MARIADB_VERSION="10.1"
ARG MARIADB_PASSWORD="unused"

# Add MariaDB repository
RUN apt-key adv --recv-keys --keyserver hkp://keyserver.ubuntu.com:80 0xF1656F24C74CD1D8 && \
	echo "# http://downloads.mariadb.org/mariadb/repositories/\n\
		deb [arch=amd64,i386] http://mirror.netinch.com/pub/mariadb/repo/$MARIADB_VERSION/ubuntu xenial main\n\
		deb-src http://mirror.netinch.com/pub/mariadb/repo/$MARIADB_VERSION/ubuntu xenial main" > /etc/apt/sources.list.d/MariaDB.list

RUN { \
		echo mariadb-server-$MARIADB_VERSION mysql-server/root_password password $MARIADB_PASSWORD; \
		echo mariadb-server-$MARIADB_VERSION mysql-server/root_password_again password $MARIADB_PASSWORD; \
	} | debconf-set-selections

# Update the container
RUN apt-get update && \
    apt-get full-upgrade -y

#Install utilities
RUN apt-get install -y \
    wget \
    unzip

# Install LAMP
RUN apt-get install -y \
    apache2 \
    mariadb-server \
    php libapache2-mod-php php-mcrypt php-mysql

# Remove default apache index file
RUN rm /var/www/html/index.html

# Install CodeIgniter
ADD ./codeigniter-install.sh /usr/bin/codeigniter-install
RUN codeigniter-install -v $CI_VERSION
