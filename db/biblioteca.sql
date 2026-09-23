-- Esquema de la base de datos que el proyecto espera encontrar.
-- El repositorio nunca lo incluyo: esta deducido de las entidades JPA
-- Llibre, Usuari y Prestec, incluidas sus claves ajenas.
--
-- Importar con:  mysql -u root < db/biblioteca.sql

CREATE DATABASE IF NOT EXISTS biblioteca
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE biblioteca;

CREATE TABLE IF NOT EXISTS llibre (
  Isbn      VARCHAR(20)  NOT NULL,
  titol     VARCHAR(255) DEFAULT NULL,
  categoria VARCHAR(100) DEFAULT NULL,
  preu      DOUBLE       DEFAULT NULL,
  editorial VARCHAR(150) DEFAULT NULL,
  autor     VARCHAR(150) DEFAULT NULL,
  PRIMARY KEY (Isbn)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS usuari (
  DNI     VARCHAR(15)  NOT NULL,
  nom     VARCHAR(150) DEFAULT NULL,
  telefon INT          DEFAULT NULL,
  correu  VARCHAR(150) DEFAULT NULL,
  PRIMARY KEY (DNI)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- retornat es un TINYINT porque la entidad lo mapea a Byte, no a boolean.
CREATE TABLE IF NOT EXISTS prestec (
  codi         INT         NOT NULL,
  ISBN         VARCHAR(20) DEFAULT NULL,
  DNI          VARCHAR(15) DEFAULT NULL,
  data_prestec DATE        DEFAULT NULL,
  data_retorn  DATE        DEFAULT NULL,
  retornat     TINYINT     DEFAULT 0,
  PRIMARY KEY (codi),
  KEY fk_prestec_llibre (ISBN),
  KEY fk_prestec_usuari (DNI),
  CONSTRAINT fk_prestec_llibre FOREIGN KEY (ISBN) REFERENCES llibre (Isbn),
  CONSTRAINT fk_prestec_usuari FOREIGN KEY (DNI)  REFERENCES usuari (DNI)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Datos de ejemplo para que las pantallas no salgan vacias.
INSERT IGNORE INTO llibre (Isbn, titol, categoria, preu, editorial, autor) VALUES
  ('9788499089515', 'La ciutat invisible',     'Novel-la',  18.50, 'Proa',      'Italo Calvino'),
  ('9788417016203', 'Mecanoscrit del segon origen', 'Ciencia-ficcio', 12.95, 'Educaula', 'Manuel de Pedrolo'),
  ('9788429776690', 'Solitud',                 'Classics',  14.00, 'Cruilla',   'Victor Catala'),
  ('9788483430255', 'El cami',                 'Novel-la',  10.75, 'Destino',   'Miguel Delibes'),
  ('9788478887200', 'Introduccio a Java',      'Tecnic',    39.90, 'Anaya',     'Herbert Schildt');

INSERT IGNORE INTO usuari (DNI, nom, telefon, correu) VALUES
  ('12345678A', 'Alex Aiguade',   666112233, 'alex@example.com'),
  ('87654321B', 'Marta Puig',     677445566, 'marta@example.com'),
  ('11223344C', 'Joan Serra',     688990011, 'joan@example.com');

INSERT IGNORE INTO prestec (codi, ISBN, DNI, data_prestec, data_retorn, retornat) VALUES
  (1, '9788499089515', '12345678A', '2026-09-01', '2026-09-15', 1),
  (2, '9788417016203', '87654321B', '2026-09-10', NULL,         0),
  (3, '9788478887200', '11223344C', '2026-09-18', NULL,         0);
