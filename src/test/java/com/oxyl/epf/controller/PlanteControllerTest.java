package com.oxyl.epf.controller;

import java.util.Arrays;
import java.util.Optional;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oxyl.epf.dto.PlanteDto;
import com.oxyl.epf.dto.mapper.PlanteMapper;
import com.oxyl.epf.model.Plante;
import com.oxyl.epf.service.PlanteService;

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

        mockMvc.perform(get("/plantes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Tournesol"))
                .andExpect(jsonPath("$[0].idPlante").value(1));

        verify(planteService, times(1)).findAll();
    }

    @Test
    public void testGetPlanteById() throws Exception {
        when(planteService.findById(1)).thenReturn(Optional.of(plante));

        mockMvc.perform(get("/plantes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Tournesol"))
                .andExpect(jsonPath("$.idPlante").value(1));

        verify(planteService, times(1)).findById(1);
    }

    @Test
    public void testCreatePlante() throws Exception {
        doNothing().when(planteService).save(any(Plante.class));

        mockMvc.perform(post("/plantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planteDto)))
                .andExpect(status().isCreated());

        verify(planteService, times(1)).save(any(Plante.class));
    }

    @Test
    public void testUpdatePlante() throws Exception {
        doNothing().when(planteService).update(any(Plante.class));

        mockMvc.perform(put("/plantes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planteDto)))
                .andExpect(status().isOk());

        verify(planteService, times(1)).update(any(Plante.class));
    }

    @Test
    public void testDeletePlante() throws Exception {
        doNothing().when(planteService).deleteById(anyInt());

        mockMvc.perform(delete("/plantes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(planteService, times(1)).deleteById(1);
    }
}