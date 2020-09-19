ALTER TABLE usuario ADD COLUMN acesso DATETIME NOT NULL;
UPDATE usuario SET acesso = now() WHERE id = 1;
UPDATE usuario SET acesso = now() WHERE id = 2;