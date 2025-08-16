-- Extensiones necesarias
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabla de roles
CREATE TABLE roles (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       name TEXT UNIQUE NOT NULL,
                       description TEXT,
                       permissions JSONB NOT NULL DEFAULT '{}',
                       created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de usuarios
CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       email TEXT UNIQUE NOT NULL,
                       password_hash TEXT NOT NULL,
                       full_name TEXT,
                       role_id UUID REFERENCES roles(id),
                       created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de notas
CREATE TABLE notes (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       user_id UUID REFERENCES users(id) ON DELETE CASCADE,
                       title TEXT NOT NULL,
                       content TEXT,
                       is_archived BOOLEAN DEFAULT FALSE,
                       created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de etiquetas
CREATE TABLE tags (
                      id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                      user_id UUID REFERENCES users(id) ON DELETE CASCADE,
                      name TEXT NOT NULL,
                      color TEXT,
                      created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                      UNIQUE(user_id, name) -- Evita duplicados por usuario
);

-- Tabla intermedia nota-etiqueta (muchos a muchos)
CREATE TABLE note_tags (
                           note_id UUID REFERENCES notes(id) ON DELETE CASCADE,
                           tag_id UUID REFERENCES tags(id) ON DELETE CASCADE,
                           PRIMARY KEY (note_id, tag_id)
);

-- Tabla de versiones de nota
CREATE TABLE note_versions (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               note_id UUID REFERENCES notes(id) ON DELETE CASCADE,
                               title TEXT NOT NULL,
                               content TEXT,
                               version_number INT NOT NULL,
                               created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de estado de filtros por usuario
CREATE TABLE filter_states (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               user_id UUID UNIQUE REFERENCES users(id) ON DELETE CASCADE,
                               filters JSONB NOT NULL DEFAULT '{}',
                               updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- 1. Roles
INSERT INTO roles (id, name, description, permissions, created_at, updated_at) VALUES
                                                                                   (uuid_generate_v4(), 'ADMIN', 'Administrador del sistema', '{"canEdit": true, "canDelete": true, "canManageRoles": true}', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                   (uuid_generate_v4(), 'USER', 'Usuario estándar', '{"canEdit": true, "canDelete": false}', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 2. Usuarios
-- Asignamos roles por nombre usando subconsultas
INSERT INTO users (id, email, password_hash, full_name, role_id, created_at, updated_at) VALUES
                                                                                             (uuid_generate_v4(), 'admin@example.com', '$2a$10$adminHash...', 'Admin Root',
                                                                                              (SELECT id FROM roles WHERE name = 'ADMIN'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                             (uuid_generate_v4(), 'user1@example.com', '$2a$10$user1Hash...', 'Juan Pérez',
                                                                                              (SELECT id FROM roles WHERE name = 'USER'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                             (uuid_generate_v4(), 'user2@example.com', '$2a$10$user2Hash...', 'María Gómez',
                                                                                              (SELECT id FROM roles WHERE name = 'USER'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 3. Notas
INSERT INTO notes (id, user_id, title, content, is_archived, created_at, updated_at) VALUES
                                                                                         (uuid_generate_v4(), (SELECT id FROM users WHERE email = 'user1@example.com'), 'Plan de viaje', 'Organizar itinerario y vuelos', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                         (uuid_generate_v4(), (SELECT id FROM users WHERE email = 'user1@example.com'), 'Ideas de negocio', 'App de recetas saludables', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                         (uuid_generate_v4(), (SELECT id FROM users WHERE email = 'user2@example.com'), 'Notas de reunión', 'Resumen de acuerdos del equipo', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 4. Etiquetas
INSERT INTO tags (id, user_id, name, color, created_at) VALUES
                                                            (uuid_generate_v4(), (SELECT id FROM users WHERE email = 'user1@example.com'), 'personal', '#FF5733', CURRENT_TIMESTAMP),
                                                            (uuid_generate_v4(), (SELECT id FROM users WHERE email = 'user1@example.com'), 'startup', '#33C1FF', CURRENT_TIMESTAMP),
                                                            (uuid_generate_v4(), (SELECT id FROM users WHERE email = 'user2@example.com'), 'equipo', '#85C1E9', CURRENT_TIMESTAMP);

-- 5. Relaciones nota-etiqueta
-- Asignamos etiquetas a notas específicas
INSERT INTO note_tags (note_id, tag_id) VALUES
                                            ((SELECT id FROM notes WHERE title = 'Plan de viaje'), (SELECT id FROM tags WHERE name = 'personal' AND user_id = (SELECT id FROM users WHERE email = 'user1@example.com'))),
                                            ((SELECT id FROM notes WHERE title = 'Ideas de negocio'), (SELECT id FROM tags WHERE name = 'startup' AND user_id = (SELECT id FROM users WHERE email = 'user1@example.com'))),
                                            ((SELECT id FROM notes WHERE title = 'Notas de reunión'), (SELECT id FROM tags WHERE name = 'equipo' AND user_id = (SELECT id FROM users WHERE email = 'user2@example.com')));

-- 6. Versiones de notas
INSERT INTO note_versions (id, note_id, title, content, version_number, created_at) VALUES
                                                                                        (uuid_generate_v4(), (SELECT id FROM notes WHERE title = 'Plan de viaje'), 'Plan de viaje', 'Versión inicial del itinerario', 1, CURRENT_TIMESTAMP),
                                                                                        (uuid_generate_v4(), (SELECT id FROM notes WHERE title = 'Plan de viaje'), 'Plan de viaje', 'Itinerario completo con hoteles', 2, CURRENT_TIMESTAMP),
                                                                                        (uuid_generate_v4(), (SELECT id FROM notes WHERE title = 'Ideas de negocio'), 'Ideas de negocio', 'Propuesta inicial de app', 1, CURRENT_TIMESTAMP);

-- 7. Estado de filtros por usuario
INSERT INTO filter_states (id, user_id, filters, updated_at) VALUES
                                                                 (uuid_generate_v4(), (SELECT id FROM users WHERE email = 'user1@example.com'), '{"tags":["personal"],"isArchived":false}', CURRENT_TIMESTAMP),
                                                                 (uuid_generate_v4(), (SELECT id FROM users WHERE email = 'user2@example.com'), '{"tags":["equipo"],"isArchived":true}', CURRENT_TIMESTAMP);
