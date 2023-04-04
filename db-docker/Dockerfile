FROM postgres:13

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=db_transacoes_loja

COPY init-db.sql /docker-entrypoint-initdb.d/
