package com.oxyl.epf.service.impl;

import com.oxyl.epf.dao.ZombieDao;
import com.oxyl.epf.model.Zombie;
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

public class ZombieServiceImplTest {

    @Mock
    private ZombieDao zombieDao;

    @InjectMocks
    private ZombieServiceImpl zombieService;

    private Zombie zombie;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        zombie = new Zombie(1, "Zombie Standard", 100, 0.5, 10, 0.8, "zombie.png", 1);
    }

    @Test
    public void testFindAll() {
        List<Zombie> zombies = Arrays.asList(
                zombie,
                new Zombie(2, "Zombie Cone", 200, 0.5, 10, 0.6, "zombie_cone.png", 1)
        );

        when(zombieDao.findAll()).thenReturn(zombies);

        List<Zombie> result = zombieService.findAll();
        assertEquals(2, result.size());
        verify(zombieDao, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(zombieDao.findById(1)).thenReturn(Optional.of(zombie));

        Optional<Zombie> result = zombieService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("Zombie Standard", result.get().getNom());
        verify(zombieDao, times(1)).findById(1);
    }

    @Test
    public void testFindByMapId() {
        List<Zombie> zombies = Arrays.asList(zombie);
        when(zombieDao.findByMapId(1)).thenReturn(zombies);

        List<Zombie> result = zombieService.findByMapId(1);
        assertEquals(1, result.size());
        verify(zombieDao, times(1)).findByMapId(1);
    }

    @Test
    public void testSave() {
        doNothing().when(zombieDao).save(any(Zombie.class));

        zombieService.save(zombie);

        verify(zombieDao, times(1)).save(zombie);
    }

    @Test
    public void testUpdate() {
        doNothing().when(zombieDao).update(any(Zombie.class));

        zombieService.update(zombie);

        verify(zombieDao, times(1)).update(zombie);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(zombieDao).deleteById(anyInt());

        zombieService.deleteById(1);

        verify(zombieDao, times(1)).deleteById(1);
    }
}