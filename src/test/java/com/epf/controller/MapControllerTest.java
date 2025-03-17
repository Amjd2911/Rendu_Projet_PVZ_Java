package com.epf.controller;

import com.epf.dto.MapDto;
import com.epf.dto.mapper.MapMapper;
import com.epf.model.Map;
import com.epf.service.MapService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MapControllerTest {

    @Mock
    private MapService mapService;

    @InjectMocks
    private MapController mapController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Map map;
    private MapDto mapDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mapController).build();
        objectMapper = new ObjectMapper();

        map = new Map(1, 5, 5, "map.png");
        mapDto = MapMapper.toDto(map);
    }

    @Test
    public void testGetAllMaps() throws Exception {
        when(mapService.findAll()).thenReturn(Collections.singletonList(map));

        mockMvc.perform(get("/api/maps")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idMap").value(1))
                .andExpect(jsonPath("$[0].ligne").value(5))
                .andExpect(jsonPath("$[0].colonne").value(5));

        verify(mapService, times(1)).findAll();
    }

    @Test
    public void testGetMapById() throws Exception {
        when(mapService.findById(1)).thenReturn(Optional.of(map));

        mockMvc.perform(get("/api/maps/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idMap").value(1))
                .andExpect(jsonPath("$.ligne").value(5))
                .andExpect(jsonPath("$.colonne").value(5));

        verify(mapService, times(1)).findById(1);
    }

    @Test
    public void testCreateMap() throws Exception {
        doNothing().when(mapService).save(any(Map.class));

        mockMvc.perform(post("/api/maps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mapDto)))
                .andExpect(status().isCreated());

        verify(mapService, times(1)).save(any(Map.class));
    }

    @Test
    public void testUpdateMap() throws Exception {
        doNothing().when(mapService).update(any(Map.class));

        mockMvc.perform(put("/api/maps/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mapDto)))
                .andExpect(status().isOk());

        verify(mapService, times(1)).update(any(Map.class));
    }

    @Test
    public void testDeleteMap() throws Exception {
        doNothing().when(mapService).deleteById(anyInt());

        mockMvc.perform(delete("/api/maps/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(mapService, times(1)).deleteById(1);
    }
}