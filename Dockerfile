FROM mysql:5.7

ENV MYSQL_DATABASE monitotem
ENV MYSQL_ROOT_PASSWORD mysqldb

COPY ./script_sql.sql/ /docker-entrypoint-initdb.d/
EXPOSE 3306