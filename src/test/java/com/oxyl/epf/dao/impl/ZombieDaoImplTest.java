package com.oxyl.epf.dao.impl;

import com.oxyl.epf.config.TestDataSourceConfig;
import com.oxyl.epf.model.Zombie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestDataSourceConfig.class)
@ActiveProfiles("test")
public class ZombieDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ZombieDaoImpl zombieDao;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.update("DELETE FROM Zombie");
        jdbcTemplate.update("DELETE FROM Map");

        jdbcTemplate.update("INSERT INTO Map (id_map, ligne, colonne, chemin_image) VALUES (1, 5, 8, 'test_map.png')");
        jdbcTemplate.update("INSERT INTO Zombie (id_zombie, nom, point_de_vie, attaque_par_seconde, degat_attaque, vitesse_de_deplacement, chemin_image, id_map) VALUES (1, 'Test Zombie', 100, 1.0, 10, 0.5, 'test_zombie.png', 1)");
    }

    @Test
    public void testFindAll() {
        List<Zombie> zombies = zombieDao.findAll();
        assertFalse(zombies.isEmpty());
    }

    @Test
    public void testFindById() {
        Optional<Zombie> zombie = zombieDao.findById(1);
        assertTrue(zombie.isPresent());
    }

    @Test
    public void testFindByMapId() {
        List<Zombie> zombies = zombieDao.findByMapId(1);
        assertFalse(zombies.isEmpty());
    }

    @Test
    public void testSave() {
        Zombie zombie = new Zombie(3, "New Zombie", 150, 0.7, 15, 0.8, "new_zombie.png", 1);
        zombieDao.save(zombie);

        List<Zombie> zombies = zombieDao.findAll();
        System.out.println("Zombies content:");
        for (Zombie z : zombies) {
            System.out.println("ID: " + z.getIdZombie() +
                    ", Name: " + z.getNom() +
                    ", HP: " + z.getPointDeVie() +
                    ", Map ID: " + z.getIdMap());
        }
        System.out.println("Raw database content:");
        List<Map<String, Object>> rawData = jdbcTemplate.queryForList("SELECT * FROM Zombie");
        for (Map<String, Object> row : rawData) {
            System.out.println(row);
        }
        assertTrue(zombies.size() > 1);
    }

    @Test
    public void testUpdate() {
        Optional<Zombie> optionalZombie = zombieDao.findById(1);
        assertTrue(optionalZombie.isPresent());

        Zombie zombie = optionalZombie.get();
        String newName = "Updated Zombie";
        zombie.setNom(newName);
        zombieDao.update(zombie);

        Optional<Zombie> updated = zombieDao.findById(1);
        assertTrue(updated.isPresent());
        assertEquals(newName, updated.get().getNom());
    }

    @Test
    public void testDeleteById() {
        zombieDao.deleteById(1);
        Optional<Zombie> deleted = zombieDao.findById(1);
        assertFalse(deleted.isPresent());
    }
}