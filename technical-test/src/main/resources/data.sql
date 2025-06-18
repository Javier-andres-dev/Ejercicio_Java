-- Crear roles
INSERT INTO role_entity (id, nombre) VALUES (1, 'ROLE_USER');
INSERT INTO role_entity (id, nombre) VALUES (2, 'ROLE_ADMIN');

-- Crear usuario
INSERT INTO user_entity (id, username, password) 
VALUES (1, 'admin', '$2a$10$6jKQnAoEDnDU6b0l9JjzN.7VZJqp1UtA0S1C6Ng7dyUYMXW2GC87i'); 
-- Password: admin123 (BCrypt)

-- Asignar rol al usuario
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);