DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT datname FROM pg_catalog.pg_database WHERE datname = 'makeorder'
        ) THEN
            EXECUTE 'CREATE DATABASE makeorder';
        END IF;

        IF NOT EXISTS (
            SELECT FROM pg_catalog.pg_tables WHERE schemaname = 'public' AND tablename = 'tb_categoria'
        ) THEN
            EXECUTE 'create table tb_categoria (
            id uuid not null,
            nome varchar(50) not null unique,
            primary key (id)
        )';
        END IF;

        IF NOT EXISTS (
            SELECT FROM pg_catalog.pg_tables WHERE schemaname = 'public' AND tablename = 'tb_comanda'
        ) THEN
            EXECUTE 'create table tb_comanda (
            id uuid not null,
            primary key (id)
        )';
        END IF;

        IF NOT EXISTS (
            SELECT FROM pg_catalog.pg_tables WHERE schemaname = 'public' AND tablename = 'tb_funcionario'
        ) THEN
            EXECUTE 'create table tb_funcionario (
            setor smallint not null check (setor between 0 and 2),
            data_nascimento timestamp(6) not null,
            cpf varchar(11) not null unique,
            id uuid not null,
            email varchar(50) not null unique,
            nome varchar(100) not null,
            senha varchar(100) not null,
            primary key (id)
        )';
        END IF;

        IF NOT EXISTS (
            SELECT FROM pg_catalog.pg_tables WHERE schemaname = 'public' AND tablename = 'tb_item'
        ) THEN
            EXECUTE 'create table tb_item (
            preco numeric(38,2) not null,
            quantidade_disponivel integer not null,
            categoria_id uuid references tb_categoria(id),
            id uuid not null,
            nome varchar(50) not null unique,
            descricao text not null,
            primary key (id)
        )';
        END IF;

        IF NOT EXISTS (
            SELECT FROM pg_catalog.pg_tables WHERE schemaname = 'public' AND tablename = 'tb_mesa'
        ) THEN
            EXECUTE 'create table tb_mesa (
            numero integer not null unique,
            id uuid not null,
            cliente varchar(75) not null unique,
            primary key (id)
        )';
        END IF;

        IF NOT EXISTS (
            SELECT FROM pg_catalog.pg_tables WHERE schemaname = 'public' AND tablename = 'tb_pedido'
        ) THEN
            EXECUTE 'create table tb_pedido (
            codigo integer,
            forma_pagamento smallint not null check (forma_pagamento between 0 and 3),
            status smallint not null check (status between 0 and 2),
            data timestamp(6) with time zone not null,
            comanda_id uuid references tb_comanda(id),
            funcionario_id uuid references tb_funcionario(id),
            id uuid not null,
            mesa_id uuid references tb_mesa(id),
            observacoes text not null,
            primary key (id)
        )';
        END IF;

        IF NOT EXISTS (
            SELECT FROM pg_catalog.pg_tables WHERE schemaname = 'public' AND tablename = 'tb_itens_pedido'
        ) THEN
            EXECUTE 'create table tb_itens_pedido (
            quantidade integer,
            id uuid not null,
            item_id uuid references tb_item(id),
            pedido_id uuid references tb_pedido(id),
            primary key (id)
        )';
        END IF;

    END
$$;