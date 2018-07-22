DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS projects;

DROP SEQUENCE IF EXISTS all_seq;
DROP SEQUENCE IF EXISTS user_seq;
DROP TYPE IF EXISTS group_type;
DROP TYPE IF EXISTS user_flag;


CREATE SEQUENCE all_seq START 100000;

CREATE TABLE projects (
  id INTEGER PRIMARY KEY DEFAULT  nextval('all_seq'),
  name TEXT NOT NULL,
  description TEXT
);
CREATE UNIQUE INDEX project_idx ON projects (name);


CREATE TYPE group_type AS ENUM ('CURRENT', 'FINISHED');

CREATE TABLE groups (
  name TEXT PRIMARY KEY,
  project_id INTEGER REFERENCES projects(id),
  type group_type NOT NULL
);

CREATE TABLE cities (
  id INTEGER PRIMARY KEY DEFAULT  nextval('all_seq'),
  full_name TEXT
);
CREATE UNIQUE INDEX city_idx ON cities (full_name);

CREATE TYPE user_flag AS ENUM ('active', 'deleted', 'superuser');

CREATE SEQUENCE user_seq START 100000;

CREATE TABLE users (
  id        INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  full_name TEXT NOT NULL,
  email     TEXT NOT NULL,
  flag      user_flag NOT NULL,
  city_id   INTEGER REFERENCES cities(id)
);

CREATE UNIQUE INDEX email_idx ON users (email);

