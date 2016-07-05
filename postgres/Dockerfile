FROM postgres:9.5

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres

ADD sample-architectures.sql /var/tmp/sample-architectures.sql
ADD sample-metrics.sql /var/tmp/sample-metrics.sql

ADD 00-create_databases.sql /docker-entrypoint-initdb.d/
ADD 01-load-sample-data.sh /docker-entrypoint-initdb.d/
