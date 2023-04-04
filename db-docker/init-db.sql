DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'db_transacoes_loja') THEN
        CREATE DATABASE db_transacoes_loja;
    END IF;
END $$;
