create table tb_categoria (
    id uuid not null,
    nome varchar(50) not null unique,
    primary key (id)
);

create table tb_comanda (
    id uuid not null,
    primary key (id)
);

create table tb_funcionario (
    setor smallint not null check (setor between 0 and 2),
    data_nascimento timestamp(6) not null,
    cpf varchar(11) not null unique,
    id uuid not null,
    email varchar(50) not null unique,
    nome varchar(100) not null,
    senha varchar(100) not null,
    primary key (id)
);

create table tb_item (
    preco numeric(38,2) not null,
    quantidade_disponivel integer not null,
    categoria_id uuid, id uuid not null,
    nome varchar(50) not null unique,
    descricao text not null,
    primary key (id)
);

create table tb_itens_pedido (
    quantidade integer,
    id uuid not null,
    item_id uuid, pedido_id uuid,
    primary key (id)
);

create table tb_mesa (
    numero integer not null unique,
    id uuid not null,
    cliente varchar(75) not null unique,
    primary key (id)
);

create table tb_pedido (
    codigo integer,
    forma_pagamento smallint not null check (forma_pagamento between 0 and 3),
    status smallint not null check (status between 0 and 2),
    data timestamp(6) with time zone not null,
    comanda_id uuid,
    funcionario_id uuid,
    id uuid not null, mesa_id uuid,
    observacoes text not null,
    primary key (id));

alter table if exists tb_item add constraint FK_categoria_item foreign key (categoria_id) references tb_categoria;
alter table if exists tb_itens_pedido add constraint FK_item_itens_pedido foreign key (item_id) references tb_item;
alter table if exists tb_itens_pedido add constraint FK_pedido_itens_pedido foreign key (pedido_id) references tb_pedido;
alter table if exists tb_pedido add constraint FK_comanda_pedido foreign key (comanda_id) references tb_comanda;
alter table if exists tb_pedido add constraint FK_funcionario_pedido foreign key (funcionario_id) references tb_funcionario;
alter table if exists tb_pedido add constraint FK_mesa_pedido foreign key (mesa_id) references tb_mesa;
