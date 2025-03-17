-- Drop tables in reverse dependency order
DROP TABLE IF EXISTS Zombie;
DROP TABLE IF EXISTS Plante;
DROP TABLE IF EXISTS Map;

-- Create Map table (without UNSIGNED)
CREATE TABLE Map (
    id_map INT AUTO_INCREMENT PRIMARY KEY,
    ligne INT NOT NULL CHECK (ligne >= 0),
    colonne INT NOT NULL CHECK (colonne >= 0),
    chemin_image VARCHAR(255) DEFAULT NULL
);

-- Create Plante table (without UNSIGNED)
CREATE TABLE Plante (
    id_plante INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    point_de_vie INT NOT NULL CHECK (point_de_vie >= 0),
    attaque_par_seconde DECIMAL(5, 2) DEFAULT 0.00,
    degat_attaque INT DEFAULT 0 CHECK (degat_attaque >= 0),
    cout INT NOT NULL CHECK (cout >= 0),
    soleil_par_seconde DECIMAL(5, 2) DEFAULT 0.00,
    effet VARCHAR(20) DEFAULT 'normal',
    chemin_image VARCHAR(255) DEFAULT NULL
);

-- Create Zombie table (without UNSIGNED)
CREATE TABLE Zombie (
    id_zombie INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    point_de_vie INT NOT NULL CHECK (point_de_vie >= 0),
    attaque_par_seconde DECIMAL(5, 2) DEFAULT 0.00,
    degat_attaque INT NOT NULL CHECK (degat_attaque >= 0),
    vitesse_de_deplacement DECIMAL(5, 2) DEFAULT 0.00,
    chemin_image VARCHAR(255) DEFAULT NULL,
    id_map INT,
    FOREIGN KEY (id_map) REFERENCES Map(id_map)
);

-- Insert test data
INSERT INTO Map (ligne, colonne, chemin_image) VALUES (5, 8, 'test_map.png');
INSERT INTO Plante (nom, point_de_vie, attaque_par_seconde, degat_attaque, cout, soleil_par_seconde, effet, chemin_image)
VALUES ('Test Plant', 50, 0.5, 5, 100, 1.0, 'normal', 'test_plant.png');
INSERT INTO Zombie (nom, point_de_vie, attaque_par_seconde, degat_attaque, vitesse_de_deplacement, chemin_image, id_map)
VALUES ('Test Zombie', 100, 1.0, 10, 0.5, 'test_zombie.png', 1);