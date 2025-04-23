package com.oxyl.epf.dao.impl;

import com.oxyl.epf.config.TestDataSourceConfig;
import com.oxyl.epf.model.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig(TestDataSourceConfig.class)
@ActiveProfiles("test")
public class MapDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MapDaoImpl mapDao;

    @Test
    public void testFindAll() {
        List<Map> maps = mapDao.findAll();
        assertFalse(maps.isEmpty());
        assertTrue(maps.size() > 0);
    }

    @Test
    public void testFindById() {
        Optional<Map> map = mapDao.findById(1);
        assertTrue(map.isPresent());
        assertNotNull(map.get().getCheminImage());
    }

    @Test
    public void testUpdate() {
        Optional<Map> Map = mapDao.findById(2);
        assertTrue(Map.isPresent());

        Map map = Map.get();
        int newLigne = map.getLigne() + 1;
        map.setLigne(newLigne);

        mapDao.update(map);

        Optional<Map> updated = mapDao.findById(2);
        assertTrue(updated.isPresent());
        assertEquals(newLigne, updated.get().getLigne());
    }

    @Test
    public void testSave() {
        Map map = new Map(2, 5, 9, "map_test.png");
        int initialCount = mapDao.findAll().size();
        mapDao.save(map);

        assertEquals(initialCount + 1, mapDao.findAll().size());
    }

    @Test
    public void testDeleteById() {
        jdbcTemplate.update("DELETE FROM Zombie WHERE id_map = 1");
        mapDao.deleteById(1);
        Optional<Map> deleted = mapDao.findById(1);
        assertFalse(deleted.isPresent());
    }
}