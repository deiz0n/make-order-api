drop table tb_permissao_funcionario;
drop table tb_permissao;

ALTER TABLE tb_funcionario
ALTER COLUMN cargo
TYPE smallint
USING cargo::smallint;

ALTER TABLE tb_funcionario
ADD CONSTRAINT cargo_check
CHECK (cargo BETWEEN 0 AND 2);


