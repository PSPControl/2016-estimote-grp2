FROM ubuntu:16.04

MAINTAINER Pauli Huttunen <pauli.k.huttunen@gmail.com>

# EstimoteServer frontend container

# Get updates
RUN apt-get update && apt-get full-upgrade -y \
    # Install packages
    && apt-get install -y \
    # Utilities
    curl \
    # Apache and PHP \
    apache2 \
    php \
    libapache2-mod-php \
    php-mcrypt \
    php-mysql \
    # Remove default apache index file
    && rm /var/www/html/index.html

# Install CodeIgniter
ARG CI_VERSION="3.1.2"

RUN mkdir -p /usr/local/src/codeigniter \

    # Get sources
    && curl -SL https://github.com/bcit-ci/CodeIgniter/archive/$CI_VERSION.tar.gz \
    | tar -xzC /usr/local/src/codeigniter \

    # Web entrypoint
    && mv /usr/local/src/codeigniter/CodeIgniter_$CI_VERSION/index.php /var/www/html/ \

    # Remove unnecessary web files
    && find /usr/local/src/codeigniter/CodeIgniter-$CI_VERSION/system -type f -name "index.html" -exec rm {} \; \
    && find /usr/local/src/codeigniter/CodeIgniter-$CI_VERSION/system -type f -name ".htaccess" -exec rm {} \; \

    # Install system files
    && mkdir -p /usr/local/share/codeigniter/ \
    && mv /usr/local/share/codeigniter/CodeIgniter-$CI_VERSION/system /usr/local/share/codeigniter/ \

    # Create application directory
    && mkdir /usr/local/share/codeigniter/application \

    # Set CodeIgniter system directory path
    && sed -i -e "s#\$system_path = 'system';#\$system_path = '/usr/local/share/codeigniter/system';#" /var/www/html/index.php \

    # Set CodeIgniter application directory path
    && sed -i -e "s#\$application_folder = 'application';#\$application_folder = '/usr/local/share/codeigniter/application';#" /var/www/html/index.php \

    # Remove unneeded source files
    && rm -rf /usr/local/src/codeigniter

# Install EstimoteServer frontend application


EXPOSE 80
