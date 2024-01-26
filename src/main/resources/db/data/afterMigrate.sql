-- tb_categoria
INSERT INTO tb_categoria (nome) VALUES ('Categoria 1');
INSERT INTO tb_categoria (nome) VALUES ('Categoria 2');
INSERT INTO tb_categoria (nome) VALUES ('Categoria 3');
INSERT INTO tb_categoria (nome) VALUES ('Categoria 4');
INSERT INTO tb_categoria (nome) VALUES ('Categoria 5');

-- tb_comanda
INSERT INTO tb_comanda (id) VALUES (1);
INSERT INTO tb_comanda (id) VALUES (2);
INSERT INTO tb_comanda (id) VALUES (3);
INSERT INTO tb_comanda (id) VALUES (4);
INSERT INTO tb_comanda (id) VALUES (5);

-- tb_funcionario
INSERT INTO tb_funcionario (data_nascimento, cpf, senha, cargo, email, nome) VALUES ('1980-01-01 00:00:00', '12345678901', 'senha1', 'Cargo 1', 'funcionario1@email.com', 'Funcionario 1');
INSERT INTO tb_funcionario (data_nascimento, cpf, senha, cargo, email, nome) VALUES ('1981-02-02 00:00:00', '23456789012', 'senha2', 'Cargo 2', 'funcionario2@email.com', 'Funcionario 2');
INSERT INTO tb_funcionario (data_nascimento, cpf, senha, cargo, email, nome) VALUES ('1982-03-03 00:00:00', '34567890123', 'senha3', 'Cargo 3', 'funcionario3@email.com', 'Funcionario 3');
INSERT INTO tb_funcionario (data_nascimento, cpf, senha, cargo, email, nome) VALUES ('1983-04-04 00:00:00', '45678901234', 'senha4', 'Cargo 4', 'funcionario4@email.com', 'Funcionario 4');
INSERT INTO tb_funcionario (data_nascimento, cpf, senha, cargo, email, nome) VALUES ('1984-05-05 00:00:00', '56789012345', 'senha5', 'Cargo 5', 'funcionario5@email.com', 'Funcionario 5');

-- tb_item
INSERT INTO tb_item (preco, quantidade, categoria_id, nome, descricao) VALUES (10.0, 5, 1, 'Item 1', 'Descrição do Item 1');
INSERT INTO tb_item (preco, quantidade, categoria_id, nome, descricao) VALUES (20.0, 4, 2, 'Item 2', 'Descrição do Item 2');
INSERT INTO tb_item (preco, quantidade, categoria_id, nome, descricao) VALUES (30.0, 3, 3, 'Item 3', 'Descrição do Item 3');
INSERT INTO tb_item (preco, quantidade, categoria_id, nome, descricao) VALUES (40.0, 2, 4, 'Item 4', 'Descrição do Item 4');
INSERT INTO tb_item (preco, quantidade, categoria_id, nome, descricao) VALUES (50.0, 1, 5, 'Item 5', 'Descrição do Item 5');

-- tb_mesa
INSERT INTO tb_mesa (numero, cliente, observacoes) VALUES (1, 'Cliente 1', 'Observações 1');
INSERT INTO tb_mesa (numero, cliente, observacoes) VALUES (2, 'Cliente 2', 'Observações 2');
INSERT INTO tb_mesa (numero, cliente, observacoes) VALUES (3, 'Cliente 3', 'Observações 3');
INSERT INTO tb_mesa (numero, cliente, observacoes) VALUES (4, 'Cliente 4', 'Observações 4');
INSERT INTO tb_mesa (numero, cliente, observacoes) VALUES (5, 'Cliente 5', 'Observações 5');

-- tb_pedido
INSERT INTO tb_pedido (forma_pagamento, status_pedido, comanda_id, data, funcionario_id, mesa_id) VALUES (0, 0, 1, '2024-01-01 00:00:00', 1, 1);
INSERT INTO tb_pedido (forma_pagamento, status_pedido, comanda_id, data, funcionario_id, mesa_id) VALUES (1, 1, 2, '2024-02-02 00:00:00', 2, 2);
INSERT INTO tb_pedido (forma_pagamento, status_pedido, comanda_id, data, funcionario_id, mesa_id) VALUES (2, 2, 3, '2024-03-03 00:00:00', 3, 3);
INSERT INTO tb_pedido (forma_pagamento, status_pedido, comanda_id, data, funcionario_id, mesa_id) VALUES (3, 0, 4, '2024-04-04 00:00:00', 4, 4);
INSERT INTO tb_pedido (forma_pagamento, status_pedido, comanda_id, data, funcionario_id, mesa_id) VALUES (0, 1, 5, '2024-05-05 00:00:00', 5, 5);

-- tb_permissao
INSERT INTO tb_permissao (nome, descricao) VALUES ('Permissão 1', 'Descrição da Permissão 1');
INSERT INTO tb_permissao (nome, descricao) VALUES ('Permissão 2', 'Descrição da Permissão 2');
INSERT INTO tb_permissao (nome, descricao) VALUES ('Permissão 3', 'Descrição da Permissão 3');
INSERT INTO tb_permissao (nome, descricao) VALUES ('Permissão 4', 'Descrição da Permissão 4');
INSERT INTO tb_permissao (nome, descricao) VALUES ('Permissão 5', 'Descrição da Permissão 5');

-- tb_permissao_funcionario
INSERT INTO tb_permissao_funcionario (funcionario_id, permissao_id) VALUES (1, 1);
INSERT INTO tb_permissao_funcionario (funcionario_id, permissao_id) VALUES (2, 2);
INSERT INTO tb_permissao_funcionario (funcionario_id, permissao_id) VALUES (3, 3);
INSERT INTO tb_permissao_funcionario (funcionario_id, permissao_id) VALUES (4, 4);
INSERT INTO tb_permissao_funcionario (funcionario_id, permissao_id) VALUES (5, 5);
