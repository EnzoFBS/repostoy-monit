FROM mysql:5.7

ENV MYSQL_DATABASE monitotem
ENV MYSQL_ROOT_PASSWORD mysqldb

WORKDIR /banco-mysql
COPY ./script_sql.sql/ .
EXPOSE 3306
