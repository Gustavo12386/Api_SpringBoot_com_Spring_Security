-- Criação da extensão uuid-ossp, se ainda não existir
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE product (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name TEXT NOT NULL,
    price INTEGER NOT NULL
);