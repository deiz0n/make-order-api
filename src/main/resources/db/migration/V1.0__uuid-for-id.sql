create table tb_categoria (id uuid not null, nome varchar(50) not null unique, primary key (id));
create table tb_comanda (id uuid not null, primary key (id));
create table tb_funcionario (data_nascimento timestamp(6) not null, cpf varchar(11) not null unique, id uuid not null, senha varchar(30) not null, cargo varchar(50) not null, email varchar(50) not null unique, nome varchar(100) not null unique, primary key (id));
create table tb_item (preco numeric(38,2) not null, quantidade integer not null, categoria_id uuid, id uuid not null, nome varchar(50) not null unique, descricao text not null, primary key (id));
create table tb_mesa (numero integer not null unique, id uuid not null, cliente varchar(75) not null unique, observacoes text not null, primary key (id));
create table tb_pedido (forma_pagamento smallint not null check (forma_pagamento between 0 and 3), status_pedido smallint not null check (status_pedido between 0 and 2), data timestamp(6) with time zone not null, comanda_id uuid, funcionario_id uuid, id uuid not null, mesa_id uuid, primary key (id));
create table tb_permissao (id uuid not null, nome varchar(50) not null unique, descricao text not null, primary key (id));
create table tb_permissao_funcionario (funcionario_id uuid not null, permissao_id uuid not null);
alter table if exists tb_item add constraint FKe3ur7twkk9mkypikhae9nurd0 foreign key (categoria_id) references tb_categoria;
alter table if exists tb_pedido add constraint FKgsukko9j1sxbpk6hb9ojjcvel foreign key (comanda_id) references tb_comanda;
alter table if exists tb_pedido add constraint FKfce22f0yft6feqbu6twvd7oj0 foreign key (funcionario_id) references tb_funcionario;
alter table if exists tb_pedido add constraint FKegikwff6r7h0f3ihcy2g3idaj foreign key (mesa_id) references tb_mesa;
alter table if exists tb_permissao_funcionario add constraint FKghptnc6v3ng9ac7is66xtlyxe foreign key (funcionario_id) references tb_funcionario;
alter table if exists tb_permissao_funcionario add constraint FKgbvriui59fbopn2ha7hjvryp7 foreign key (permissao_id) references tb_permissao;


