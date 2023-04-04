CREATE TABLE loja (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    proprietario VARCHAR(255) 
);

CREATE TABLE transacao (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(100) NOT NULL,
    data_ocorrencia TIMESTAMP WITH TIME ZONE NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    cpf VARCHAR(15) NOT NULL,
    cartao VARCHAR(50) NOT NULL,
    loja_id INTEGER NOT NULL,
    FOREIGN KEY (loja_id) REFERENCES loja (id)
);