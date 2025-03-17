package com.epf.dao.impl;

import com.epf.config.TestDataSourceConfig;
import com.epf.model.Plante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestDataSourceConfig.class)
@ActiveProfiles("test")
public class PlanteDaoImplTest {

    @Autowired
    private PlanteDaoImpl planteDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.update("DELETE FROM Zombie");
        jdbcTemplate.update("DELETE FROM Plante");

        jdbcTemplate.update("INSERT INTO Plante (id_plante, nom, point_de_vie, attaque_par_seconde, degat_attaque, cout, soleil_par_seconde, effet, chemin_image) VALUES (1, 'Test Plant', 50, 0.5, 5, 100, 1.0, 'normal', 'test_plant.png')");
    }

    @Test
    public void testFindAll() {
        List<Plante> plantes = planteDao.findAll();
        assertFalse(plantes.isEmpty());
    }

    @Test
    public void testFindById() {
        Optional<Plante> plante = planteDao.findById(1);
        assertTrue(plante.isPresent());
        assertEquals("Test Plant", plante.get().getNom());
    }

    @Test
    public void testSave() {
        Plante plante = new Plante(0, "New Test Plant", 60, 0.6, 6, 120, 1.2, "normal", "new_plant.png");
        planteDao.save(plante);

        List<Plante> plantes = planteDao.findAll();
        assertTrue(plantes.size() > 1);
    }

    @Test
    public void testUpdate() {
        Optional<Plante> optPlante = planteDao.findById(1);
        assertTrue(optPlante.isPresent());

        Plante plante = optPlante.get();
        String newName = "Updated Plant";
        plante.setNom(newName);
        planteDao.update(plante);

        Optional<Plante> updated = planteDao.findById(1);
        assertTrue(updated.isPresent());
        assertEquals(newName, updated.get().getNom());
    }

    @Test
    public void testDeleteById() {
        planteDao.deleteById(1);
        Optional<Plante> deleted = planteDao.findById(1);
        assertFalse(deleted.isPresent());
    }
}