-- =========================================================
-- Base de datos SpeedFast
-- =========================================================

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS speedfast_db;

-- Seleccionar la base de datos
USE speedfast_db;


-- =========================================================
-- Tabla repartidor
-- =========================================================

CREATE TABLE repartidor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);


-- =========================================================
-- Tabla pedido
-- =========================================================

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(150) NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    distancia DOUBLE NOT NULL DEFAULT 0,
    peso DOUBLE NULL
);


-- =========================================================
-- Tabla entrega
-- =========================================================

CREATE TABLE entrega (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_repartidor INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,

    FOREIGN KEY (id_pedido)
        REFERENCES pedido(id),

    FOREIGN KEY (id_repartidor)
        REFERENCES repartidor(id)
);