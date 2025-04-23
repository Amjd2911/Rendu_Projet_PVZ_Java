package com.oxyl.epf.service.impl;

import com.oxyl.epf.dao.MapDao;
import com.oxyl.epf.model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MapServiceImplTest {

    @Mock
    private MapDao mapDao;

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
        doNothing().when(mapDao).deleteById(anyInt());

        mapService.deleteById(1);

        verify(mapDao, times(1)).deleteById(1);
    }
}