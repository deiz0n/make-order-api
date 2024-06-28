DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT datname FROM pg_catalog.pg_database WHERE datname = 'makeorder'
        ) THEN
            EXECUTE 'CREATE DATABASE makeorder';
        END IF;
    END
$$;

CREATE TABLE IF NOT EXISTS tb_categoria(
       id uuid not null primary key,
       nome varchar(50)
);

CREATE TABLE IF NOT EXISTS tb_comanda(
       id uuid not null primary key,
       id_pedido uuid
);

CREATE TABLE IF NOT EXISTS tb_funcionario(
      id uuid not null primary key,
      nome varchar(100) not null,
      cpf varchar(11) not null unique,
      email varchar(100) not null unique,
      senha varchar(255) not null,
      data_nascimento timestamp not null,
      setor smallint not null check (setor between 0 and 2),
      sub_id uuid not null,
      id_pedido uuid,
      foreign key (id_pedido) references tb_pedido(id)
);

CREATE TABLE IF NOT EXISTS tb_item(
       id uuid not null primary key,
       nome varchar(50) not null,
       preco numeric(38,2) not null,
       descricao text not null,
       quantidade_disponivel integer not null,
       id_categoria uuid,
       foreign key(id_categoria) references tb_categoria(id)
);

CREATE TABLE IF NOT EXISTS tb_itens_pedido(
       id uuid not null primary key,
       quantidade integer not null,
       id_item uuid,
       id_pedido uuid,
       foreign key(id_item) references tb_item(id),
       foreign key(id_pedido) references tb_pedido(id)
);

CREATE TABLE IF NOT EXISTS tb_mesa(
       id uuid not null primary key,
       numero integer not null,
       cliente varchar(50)
);

CREATE TABLE IF NOT EXISTS tb_pedido(
     id uuid not null primary key,
     data timestamp not null,
     forma_pagamento smallint check (forma_pagamento between 0 and 3) not null,
     status smallint check (status between 0 and 2) not null,
     codigo serial not null,
     observacoes text not null,
     id_comanda uuid,
     id_funcionario uuid,
     id_mesa uuid,
     foreign key(id_comando) references tb_comanda(id),
     foreign key(id_funcionario) references tb_funcionario(id),
     foreign key(id_mesa) references tb_mesa(id)
);