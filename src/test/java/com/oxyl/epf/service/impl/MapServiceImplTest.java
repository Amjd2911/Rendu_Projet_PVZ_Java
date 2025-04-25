package com.oxyl.epf.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional; // Importer ZombieService

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.oxyl.epf.dao.MapDao;
import com.oxyl.epf.model.Map;
import com.oxyl.epf.service.ZombieService;

public class MapServiceImplTest {

    @Mock
    private MapDao mapDao;

    @Mock // Ajouter un mock pour ZombieService
    private ZombieService zombieService;

    @InjectMocks
    private MapServiceImpl mapService;

    private Map map;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        map = new Map(1, 5, 9, "jardin.png");
    }

    @Test
    public void testFindAll() {
        List<Map> maps = Arrays.asList(
                map,
                new Map(2, 5, 9, "piscine.png")
        );

        when(mapDao.findAll()).thenReturn(maps);

        List<Map> result = mapService.findAll();
        assertEquals(2, result.size());
        verify(mapDao, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(mapDao.findById(1)).thenReturn(Optional.of(map));

        Optional<Map> result = mapService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("jardin.png", result.get().getCheminImage());
        verify(mapDao, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        doNothing().when(mapDao).save(any(Map.class));

        mapService.save(map);

        verify(mapDao, times(1)).save(map);
    }

    @Test
    public void testUpdate() {
        doNothing().when(mapDao).update(any(Map.class));

        mapService.update(map);

        verify(mapDao, times(1)).update(map);
    }

    @Test
    public void testDeleteById() {
        // Configurer les mocks pour ne rien faire lorsqu'ils sont appelés
        doNothing().when(zombieService).deleteByMapId(anyInt());
        doNothing().when(mapDao).deleteById(anyInt());

        // Appeler la méthode à tester
        mapService.deleteById(1);

        // Vérifier que deleteByMapId de zombieService est appelé en premier
        verify(zombieService, times(1)).deleteByMapId(1);
        // Vérifier que deleteById de mapDao est appelé ensuite
        verify(mapDao, times(1)).deleteById(1);
    }
}