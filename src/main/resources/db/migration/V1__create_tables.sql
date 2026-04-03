CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');

CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    authority user_role NOT NULL DEFAULT 'USER'
);

CREATE INDEX idx_users ON users(authority, enabled);

CREATE TYPE task_status AS ENUM ('TODO', 'DOING', 'DONE');

CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status task_status NOT NULL DEFAULT 'TODO',
    owner_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    enabled BOOLEAN DEFAULT true,

    CONSTRAINT fk_owner
          FOREIGN KEY (owner_id)
          REFERENCES users(id)
);

CREATE INDEX idx_tasks ON tasks(owner_id, status, enabled);
