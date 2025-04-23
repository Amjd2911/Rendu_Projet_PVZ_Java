package com.oxyl.epf.service.impl;

import com.oxyl.epf.dao.PlanteDao;
import com.oxyl.epf.model.Plante;
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

public class PlanteServiceImplTest {

    @Mock
    private PlanteDao planteDao;

    @InjectMocks
    private PlanteServiceImpl planteService;

    private Plante plante;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        plante = new Plante(1, "Tournesol", 100, 0, 0, 50, 1.0, "Produit du soleil", "tournesol.jpg");
    }

    @Test
    public void testFindAll() {
        List<Plante> plantes = Arrays.asList(
                plante,
                new Plante(2, "Pois", 100, 1.0, 10, 100, 0, "Tir simple", "pois.jpg")
        );

        when(planteDao.findAll()).thenReturn(plantes);

        List<Plante> result = planteService.findAll();
        assertEquals(2, result.size());
        verify(planteDao, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(planteDao.findById(1)).thenReturn(Optional.of(plante));

        Optional<Plante> result = planteService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("Tournesol", result.get().getNom());
        verify(planteDao, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        doNothing().when(planteDao).save(any(Plante.class));

        planteService.save(plante);

        verify(planteDao, times(1)).save(plante);
    }

    @Test
    public void testUpdate() {
        doNothing().when(planteDao).update(any(Plante.class));

        planteService.update(plante);

        verify(planteDao, times(1)).update(plante);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(planteDao).deleteById(anyInt());

        planteService.deleteById(1);

        verify(planteDao, times(1)).deleteById(1);
    }
}