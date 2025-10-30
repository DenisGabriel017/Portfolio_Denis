-- Garante que o ID 1 da Pessoa exista
INSERT INTO pessoa (id, nome, email, telefone, resumo, link_linkedin, link_github)
VALUES (1, 'Alexandre Santos', 'alexandre.santos@email.com', '11987654321', 'Desenvolvedor Java/Spring Boot focado em arquiteturas robustas e Thymeleaf para SSR.', 'https://linkedin.com/in/alexandresantos', 'https://github.com/alexandresantos')
ON CONFLICT (id) DO UPDATE SET
    nome = EXCLUDED.nome,
    email = EXCLUDED.email,
    telefone = EXCLUDED.telefone,
    resumo = EXCLUDED.resumo,
    link_linkedin = EXCLUDED.link_linkedin,
    link_github = EXCLUDED.link_github; -- Atualiza se já existir

--------------------------------------------------------------------------------
-- 2. HABILIDADES
--------------------------------------------------------------------------------

INSERT INTO habilidade (id, nome, nivel, pessoa_id) VALUES
(1, 'Spring Boot', 'AVANCADO', 1),
(2, 'PostgreSQL', 'INTERMEDIARIO', 1),
(3, 'Thymeleaf', 'AVANCADO', 1),
(4, 'HTML & CSS', 'INTERMEDIARIO', 1)
ON CONFLICT (id) DO UPDATE SET
    nome = EXCLUDED.nome,
    nivel = EXCLUDED.nivel,
    pessoa_id = EXCLUDED.pessoa_id;


--------------------------------------------------------------------------------
-- 3. EXPERIÊNCIAS
--------------------------------------------------------------------------------

INSERT INTO experiencia (id, empresa_instituicao, titulo, descricao, data_inicio, data_fim, pessoa_id) VALUES
(1, 'Tech Solutions Ltda', 'Desenvolvedor Backend Sênior', 'Liderança técnica na migração de microserviços para Spring Boot 3.0 e integração com Kafka. Responsável pela performance do banco de dados.', '2022-01-15', NULL, 1),
(2, 'Start-Up Inovação', 'Desenvolvedor Júnior', 'Implementação de APIs REST e manutenção de sistemas legados. Primeiro contato com JPA e Spring Data.', '2020-05-01', '2021-12-31', 1)
ON CONFLICT (id) DO UPDATE SET
    empresa_instituicao = EXCLUDED.empresa_instituicao,
    titulo = EXCLUDED.titulo,
    descricao = EXCLUDED.descricao,
    data_inicio = EXCLUDED.data_inicio,
    data_fim = EXCLUDED.data_fim,
    pessoa_id = EXCLUDED.pessoa_id;


--------------------------------------------------------------------------------
-- 4. PROJETOS
--------------------------------------------------------------------------------

-- Projeto 1: Sistema de Gerenciamento de Tarefas
INSERT INTO projeto (id, titulo, descricao, data_conclusao, link_repositorio, pessoa_id)
VALUES (1, 'Task Manager SSR', 'Sistema completo de gerenciamento de tarefas usando Spring MVC e Thymeleaf para renderização no lado do servidor.', '2024-10-25', 'https://github.com/user/task-manager-ssr', 1)
ON CONFLICT (id) DO UPDATE SET
    titulo = EXCLUDED.titulo,
    descricao = EXCLUDED.descricao,
    data_conclusao = EXCLUDED.data_conclusao,
    link_repositorio = EXCLUDED.link_repositorio,
    pessoa_id = EXCLUDED.pessoa_id;


-- Projeto 2: API de Blog
INSERT INTO projeto (id, titulo, descricao, data_conclusao, link_repositorio, pessoa_id)
VALUES (2, 'API de Blog com Segurança', 'Desenvolvimento de uma API REST para um blog, incluindo autenticação JWT e testes de integração com Testcontainers.', '2023-08-10', 'https://github.com/user/blog-api-security', 1)
ON CONFLICT (id) DO UPDATE SET
    titulo = EXCLUDED.titulo,
    descricao = EXCLUDED.descricao,
    data_conclusao = EXCLUDED.data_conclusao,
    link_repositorio = EXCLUDED.link_repositorio,
    pessoa_id = EXCLUDED.pessoa_id;

--------------------------------------------------------------------------------
-- 5. LIGAÇÃO PROJETO X HABILIDADE (Tabela de Relacionamento N:M)
--------------------------------------------------------------------------------

INSERT INTO projeto_habilidade (projeto_id, habilidade_id) VALUES
(1, 1), -- Task Manager usa Spring Boot
(1, 3), -- Task Manager usa Thymeleaf
(1, 2), -- Task Manager usa PostgreSQL
(2, 1), -- Blog API usa Spring Boot
(2, 2)  -- Blog API usa PostgreSQL
ON CONFLICT DO NOTHING;

--------------------------------------------------------------------------------
-- 6. IMAGENS (Para Projeto 1)
--------------------------------------------------------------------------------

INSERT INTO imagem (id, nome_arquivo, url, projeto_id) VALUES
(1, 'screenshot_login', 'https://via.placeholder.com/150/007bff/FFFFFF?text=Login', 1),
(2, 'screenshot_dashboard', 'https://via.placeholder.com/150/28a745/FFFFFF?text=Dashboard', 1)
ON CONFLICT (id) DO UPDATE SET
    nome_arquivo = EXCLUDED.nome_arquivo,
    url = EXCLUDED.url,
    projeto_id = EXCLUDED.projeto_id;