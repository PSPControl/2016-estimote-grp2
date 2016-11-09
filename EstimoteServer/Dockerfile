FROM ubuntu:16.04

MAINTAINER Pauli Huttunen

ARG MARIADB_PASSWORD="unused"
ENV MARIADB_MAJOR "10.1"

# Add MariaDB repository
RUN apt-key adv --recv-keys --keyserver hkp://keyserver.ubuntu.com:80 0xF1656F24C74CD1D8

RUN echo "# http://downloads.mariadb.org/mariadb/repositories/\n\
deb [arch=amd64,i386] http://mirror.netinch.com/pub/mariadb/repo/10.1/ubuntu xenial main\n\
deb-src http://mirror.netinch.com/pub/mariadb/repo/10.1/ubuntu xenial main" > /etc/apt/sources.list.d/MariaDB.list

RUN { \
		echo mariadb-server-$MARIADB_MAJOR mysql-server/root_password password $MARIADB_PASSWORD; \
		echo mariadb-server-$MARIADB_MAJOR mysql-server/root_password_again password $MARIADB_PASSWORD; \
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

# Install CodeIgniter
ADD ./install-codeigniter.sh /usr/bin/
RUN /usr/bin/install-codeigniter.sh
