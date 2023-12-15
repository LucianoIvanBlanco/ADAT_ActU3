-- Creación de la tabla Authors
CREATE TABLE authors
(
    dni    SERIAL PRIMARY KEY,
    name   VARCHAR(255) NOT NULL
);

-- Creación de la tabla Books
CREATE TABLE books
(
    id        SERIAL PRIMARY KEY,
    title    VARCHAR(255) NOT NULL,
    publication_date DATE NOT NULL,
    dni_author INT REFERENCES authors (dni) ON DELETE SET NULL,
    editorial VARCHAR(255) NOT NULL
);
