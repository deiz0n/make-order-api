CREATE EXTENSION IF NOT EXISTS pgcrypto;
SET session_replication_role = "replica";

DELETE FROM tb_categoria;
DELETE FROM tb_comanda;
DELETE FROM tb_funcionario;
DELETE FROM tb_item;
DELETE FROM tb_itens_pedido;
DELETE FROM tb_mesa;
DELETE FROM tb_pedido;

LOCK TABLE makeorder.public.tb_categoria IN ACCESS EXCLUSIVE MODE NOWAIT;
LOCK TABLE makeorder.public.tb_comanda IN ACCESS EXCLUSIVE MODE NOWAIT;
LOCK TABLE makeorder.public.tb_funcionario IN ACCESS EXCLUSIVE MODE NOWAIT;
LOCK TABLE makeorder.public.tb_item IN ACCESS EXCLUSIVE MODE NOWAIT;
LOCK TABLE makeorder.public.tb_mesa IN ACCESS EXCLUSIVE MODE NOWAIT;
LOCK TABLE makeorder.public.tb_pedido IN ACCESS EXCLUSIVE MODE NOWAIT;
LOCK TABLE makeorder.public.tb_itens_pedido IN ACCESS EXCLUSIVE MODE NOWAIT;

SET session_replication_role = "origin";

INSERT INTO tb_categoria (id, nome) VALUES ('3d8b1f72-95b6-4c40-a45a-8d0a5e77e2a4', 'Bebidas'),
                                           ('6a2f41a3-c54c-4b73-ae60-bf5c2f4907e2', 'Comida'),
                                           ('a1d8d2e2-2dd4-43dd-8a40-de7649f1ca1d', 'Sobremesas'),
                                           ('7e446f4a-24fa-42d6-8b4a-976243f4b9d8', 'Vinhos'),
                                           ('2f4a5f71-693e-44c9-8b31-d68e55f2f38a', 'Cervejas');

INSERT INTO tb_comanda (id) VALUES ('550e8400-e29b-41d4-a716-446655440000'),
                                   ('6ba7b810-9dad-11d1-80b4-00c04fd430c8'),
                                   ('6ba7b811-9dad-11d1-80b4-00c04fd430c8'),
                                   ('6ba7b812-9dad-11d1-80b4-00c04fd430c8'),
                                   ('6ba7b814-9dad-11d1-80b4-00c04fd430c8');

INSERT INTO tb_funcionario (setor, data_nascimento, cpf, id, email, nome, senha) VALUES (0, '1980-01-01 00:00:00', '50283944072', '550e8400-e29b-41d4-a716-446655440001', 'funcionario1@email.com', 'Funcionario 1', ENCODE(HMAC('123', 'key', 'sha256'), 'hex')),
                                                                                        (1, '1985-05-05 00:00:00', '95874478086', '6ba7b810-9dad-11d1-80b4-00c04fd430c9', 'funcionario2@email.com', 'Funcionario 2', ENCODE(HMAC('123', 'key', 'sha256'), 'hex')),
                                                                                        (2, '1990-10-10 00:00:00', '17714005017', '6ba7b811-9dad-11d1-80b4-00c04fd430ca', 'funcionario3@email.com', 'Funcionario 3', ENCODE(HMAC('123', 'key', 'sha256'), 'hex')),
                                                                                        (0, '1995-03-15 00:00:00', '81214869041', '6ba7b812-9dad-11d1-80b4-00c04fd430cb', 'funcionario4@email.com', 'Funcionario 4', ENCODE(HMAC('123', 'key', 'sha256'), 'hex')),
                                                                                        (1, '2000-07-20 00:00:00', '13709676088', '6ba7b814-9dad-11d1-80b4-00c04fd430cc', 'funcionario5@email.com', 'Funcionario 5', ENCODE(HMAC('123', 'key', 'sha256'), 'hex'));

INSERT INTO tb_item (preco, quantidade_disponivel, categoria_id, id, nome, descricao) VALUES (10.00, 100, '3d8b1f72-95b6-4c40-a45a-8d0a5e77e2a4', '550e8400-e29b-41d4-a716-446655440002', 'Item 1', 'Descrição do Item 1'),
                                                                                             (15.00, 200, '6a2f41a3-c54c-4b73-ae60-bf5c2f4907e2', '6ba7b810-9dad-11d1-80b4-00c04fd430d0', 'Item 2', 'Descrição do Item 2'),
                                                                                             (20.00, 150, 'a1d8d2e2-2dd4-43dd-8a40-de7649f1ca1d', '6ba7b811-9dad-11d1-80b4-00c04fd430d1', 'Item 3', 'Descrição do Item 3'),
                                                                                             (25.00, 300, '7e446f4a-24fa-42d6-8b4a-976243f4b9d8', '6ba7b812-9dad-11d1-80b4-00c04fd430d2', 'Item 4', 'Descrição do Item 4'),
                                                                                             (30.00, 250, '2f4a5f71-693e-44c9-8b31-d68e55f2f38a', '6ba7b814-9dad-11d1-80b4-00c04fd430d3', 'Item 5', 'Descrição do Item 5');

INSERT INTO tb_mesa (numero, id, cliente) VALUES (1, '550e8400-e29b-41d4-a716-446655440004', 'Cliente 1'),
                                                 (2, '6ba7b810-9dad-11d1-80b4-00c04fd430f0', 'Cliente 2'),
                                                 (3, '6ba7b811-9dad-11d1-80b4-00c04fd430f1', 'Cliente 3'),
                                                 (4, '6ba7b812-9dad-11d1-80b4-00c04fd430f2', 'Cliente 4'),
                                                 (5, '6ba7b814-9dad-11d1-80b4-00c04fd430f3', 'Cliente 5');

INSERT INTO tb_pedido (codigo, forma_pagamento, status, data, comanda_id, funcionario_id, id, mesa_id, observacoes) VALUES (1, 0, 0, '2024-05-10 19:18:59', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440004', 'Observações do Pedido 1'),
                                                                                                                           (2, 1, 1, '2024-05-10 19:18:59', '6ba7b810-9dad-11d1-80b4-00c04fd430c8', '6ba7b810-9dad-11d1-80b4-00c04fd430c9', '6ba7b810-9dad-11d1-80b4-00c04fd430f4', '6ba7b810-9dad-11d1-80b4-00c04fd430f0', 'Observações do Pedido 2'),
                                                                                                                           (3, 2, 2, '2024-05-10 19:18:59', '6ba7b811-9dad-11d1-80b4-00c04fd430c8', '6ba7b811-9dad-11d1-80b4-00c04fd430ca', '6ba7b811-9dad-11d1-80b4-00c04fd430f5', '6ba7b811-9dad-11d1-80b4-00c04fd430f1', 'Observações do Pedido 3'),
                                                                                                                           (4, 3, 0, '2024-05-10 19:18:59', '6ba7b812-9dad-11d1-80b4-00c04fd430c8', '6ba7b812-9dad-11d1-80b4-00c04fd430cb', '6ba7b812-9dad-11d1-80b4-00c04fd430f6', '6ba7b812-9dad-11d1-80b4-00c04fd430f2', 'Observações do Pedido 4'),
                                                                                                                           (5, 0, 1, '2024-05-10 19:18:59', '6ba7b814-9dad-11d1-80b4-00c04fd430c8', '6ba7b814-9dad-11d1-80b4-00c04fd430cc', '6ba7b814-9dad-11d1-80b4-00c04fd430f7', '6ba7b814-9dad-11d1-80b4-00c04fd430f3', 'Observações do Pedido 5');

INSERT INTO tb_itens_pedido (quantidade, id, item_id, pedido_id) VALUES (2, '550e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440005'),
                                                                        (3, '6ba7b810-9dad-11d1-80b4-00c04fd430e0', '6ba7b810-9dad-11d1-80b4-00c04fd430d0', '6ba7b810-9dad-11d1-80b4-00c04fd430f4'),
                                                                        (1, '6ba7b811-9dad-11d1-80b4-00c04fd430e1', '6ba7b811-9dad-11d1-80b4-00c04fd430d1', '6ba7b811-9dad-11d1-80b4-00c04fd430f5'),
                                                                        (4, '6ba7b812-9dad-11d1-80b4-00c04fd430e2', '6ba7b812-9dad-11d1-80b4-00c04fd430d2', '6ba7b812-9dad-11d1-80b4-00c04fd430f6'),
                                                                        (5, '6ba7b814-9dad-11d1-80b4-00c04fd430e3', '6ba7b814-9dad-11d1-80b4-00c04fd430d3', '6ba7b814-9dad-11d1-80b4-00c04fd430f7');
