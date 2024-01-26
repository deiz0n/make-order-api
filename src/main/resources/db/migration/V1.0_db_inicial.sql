create table tb_categoria (id bigserial not null,
                           nome varchar(50) not null unique,
                           primary key (id)
                          );

create table tb_comanda (id bigserial not null,
                         primary key (id)
                        );

create table tb_funcionario (data_nascimento timestamp(6) not null,
                             id bigserial not null,
                             cpf varchar(11) not null unique,
                             senha varchar(30) not null,
                             cargo varchar(50) not null,
                             email varchar(50) not null unique,
                             nome varchar(100) not null unique,
                             primary key (id)
                            );

create table tb_item (preco numeric(38,2) not null,
                      quantidade integer not null,
                      categoria_id bigint,
                      id bigserial not null,
                      nome varchar(50) not null unique,
                      escricao text not null, primary key (id)
                     );

create table tb_mesa (numero integer not null unique,
                      id bigserial not null,
                      cliente varchar(75) not null unique,
                      observacoes text not null,
                      primary key (id)
                     );

create table tb_pedido (forma_pagamento smallint not null check (forma_pagamento between 0 and 3),
                        status_pedido smallint not null check (status_pedido between 0 and 2),
                        comanda_id bigint, data timestamp(6) with time zone not null,
                        funcionario_id bigint,
                        id bigserial not null,
                        mesa_id bigint,
                        primary key (id)
                       );

create table tb_permissao (id bigserial not null,
                           nome varchar(50) not null unique,
                           descricao text not null,
                           primary key (id)
                          );

create table tb_permissao_funcionario (funcionario_id bigint not null,
                                       permissao_id bigint not null
                                      );

alter table if exists tb_item add constraint FK_item_categoria foreign key (categoria_id) references tb_categoria;
alter table if exists tb_pedido add constraint FK_pedido_comanda foreign key (comanda_id) references tb_comanda;
alter table if exists tb_pedido add constraint FK_pedido_funcionario foreign key (funcionario_id) references tb_funcionario;
alter table if exists tb_pedido add constraint FK_pedido_mesa foreign key (mesa_id) references tb_mesa;
alter table if exists tb_permissao_funcionario add constraint FK_permissao_funcionario_funcionario foreign key (funcionario_id) references tb_funcionario;
alter table if exists tb_permissao_funcionario add constraint FK_permissao_funcionario_permissao  foreign key (permissao_id) references tb_permissao;