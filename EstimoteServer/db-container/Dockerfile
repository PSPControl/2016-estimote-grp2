FROM mariadb:10.1

MAINTAINER Pauli Huttunen <pauli.k.huttunen@gmail.com>

RUN apt-get update \
    && apt-get full-upgrade -y

ENTRYPOINT ["docker-entrypoint.sh"]

EXPOSE 3306

CMD ["mysqld"]
