FROM php:7-apache

MAINTAINER Pauli Huttunen <pauli.k.huttunen@gmail.com>

# EstimoteServer frontend container

# Install needed PHP extensions
RUN docker-php-ext-install pdo_mysql mysqli

# Install CodeIgniter
ARG CI_VERSION="3.1.2"

RUN mkdir -p /usr/local/src/codeigniter \

    # Get sources
    && curl -SL https://github.com/bcit-ci/CodeIgniter/archive/$CI_VERSION.tar.gz \
    | tar -xzC /usr/local/src/codeigniter \

    # index.php
    && mv /usr/local/src/codeigniter/CodeIgniter-$CI_VERSION/index.php /var/www/html/ \

    # Remove unnecessary web files
    && find /usr/local/src/codeigniter/CodeIgniter-$CI_VERSION/system -type f -name "index.html" -exec rm {} \; \
    && find /usr/local/src/codeigniter/CodeIgniter-$CI_VERSION/system -type f -name ".htaccess" -exec rm {} \; \

    # Install system files
    && mkdir -p /usr/local/share/codeigniter/ \
    && mv /usr/local/src/codeigniter/CodeIgniter-$CI_VERSION/system /usr/local/share/codeigniter/system \

    # Application and log directory
    && mkdir -p /usr/local/share/codeigniter/application/logs \
    && chown www-data:www-data /usr/local/share/codeigniter/application/logs \

    # Set CodeIgniter system directory path
    && sed -i -e "s#\$system_path = 'system';#\$system_path = '/usr/local/share/codeigniter/system';#" /var/www/html/index.php \

    # Set CodeIgniter application directory path
    && sed -i -e "s#\$application_folder = 'application';#\$application_folder = '/usr/local/share/codeigniter/application';#" /var/www/html/index.php \

    # Remove unneeded source files
    && rm -rf /usr/local/src/codeigniter

# Install EstimoteServer application
COPY ./src/application /usr/local/share/codeigniter/application

# Install php files
# TODO: integrate functionality of these files directly to the application API
COPY ./src/php /var/www/html/php

# Install assets
COPY ./src/assets /var/www/html/assets

# Install entrypoint script
COPY ./container-init.sh /usr/local/bin/

WORKDIR /var/www/html

EXPOSE 80

CMD ["container-init.sh"]
