package com.epf.controller;

import com.epf.dto.PlanteDto;
import com.epf.dto.mapper.PlanteMapper;
import com.epf.model.Plante;
import com.epf.service.PlanteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PlanteControllerTest {

    @Mock
    private PlanteService planteService;

    @InjectMocks
    private PlanteController planteController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Plante plante;
    private PlanteDto planteDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(planteController).build();
        objectMapper = new ObjectMapper();

        plante = new Plante(1, "Tournesol", 100, 0, 0, 50, 1.0, "Produit du soleil", "tournesol.jpg");
        planteDto = PlanteMapper.toDto(plante);
    }

    @Test
    public void testGetAllPlantes() throws Exception {
        when(planteService.findAll()).thenReturn(Arrays.asList(plante));

        mockMvc.perform(get("/api/plantes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Tournesol"))
                .andExpect(jsonPath("$[0].idPlante").value(1));

        verify(planteService, times(1)).findAll();
    }

    @Test
    public void testGetPlanteById() throws Exception {
        when(planteService.findById(1)).thenReturn(Optional.of(plante));

        mockMvc.perform(get("/api/plantes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Tournesol"))
                .andExpect(jsonPath("$.idPlante").value(1));

        verify(planteService, times(1)).findById(1);
    }

    @Test
    public void testCreatePlante() throws Exception {
        doNothing().when(planteService).save(any(Plante.class));

        mockMvc.perform(post("/api/plantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planteDto)))
                .andExpect(status().isCreated());

        verify(planteService, times(1)).save(any(Plante.class));
    }

    @Test
    public void testUpdatePlante() throws Exception {
        doNothing().when(planteService).update(any(Plante.class));

        mockMvc.perform(put("/api/plantes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planteDto)))
                .andExpect(status().isOk());

        verify(planteService, times(1)).update(any(Plante.class));
    }

    @Test
    public void testDeletePlante() throws Exception {
        doNothing().when(planteService).deleteById(anyInt());

        mockMvc.perform(delete("/api/plantes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(planteService, times(1)).deleteById(1);
    }
}