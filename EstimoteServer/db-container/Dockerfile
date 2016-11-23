FROM mariadb:10.1

MAINTAINER Pauli Huttunen <pauli.k.huttunen@gmail.com>

COPY ./sql/*.sql /docker-entrypoint-initdb.d/

ENTRYPOINT ["docker-entrypoint.sh"]

EXPOSE 3306

CMD ["mysqld"]
