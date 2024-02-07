DO $$
    BEGIN
        IF EXISTS (
            SELECT FROM pg_catalog.pg_tables
            WHERE  schemaname = 'public'
              AND    tablename  = 'tb_permissao_funcionario'
        ) THEN
            EXECUTE 'DROP TABLE public.tb_permissao_funcionario';
        END IF;
    END $$;

DO $$
    BEGIN
        IF EXISTS (
            SELECT FROM pg_catalog.pg_tables
            WHERE  schemaname = 'public'
              AND    tablename  = 'tb_permissao'
        ) THEN
            EXECUTE 'DROP TABLE public.tb_permissao';
        END IF;
    END $$;
