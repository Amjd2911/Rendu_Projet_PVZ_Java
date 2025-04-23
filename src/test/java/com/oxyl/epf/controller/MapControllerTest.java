package com.oxyl.epf.controller;

import java.util.Collections;
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
import com.oxyl.epf.dto.MapDto;
import com.oxyl.epf.dto.mapper.MapMapper;
import com.oxyl.epf.model.Map;
import com.oxyl.epf.service.MapService;

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

        mockMvc.perform(get("/maps")
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

        mockMvc.perform(get("/maps/1")
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

        mockMvc.perform(post("/maps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mapDto)))
                .andExpect(status().isCreated());

        verify(mapService, times(1)).save(any(Map.class));
    }

    @Test
    public void testUpdateMap() throws Exception {
        doNothing().when(mapService).update(any(Map.class));

        mockMvc.perform(put("/maps/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mapDto)))
                .andExpect(status().isOk());

        verify(mapService, times(1)).update(any(Map.class));
    }

    @Test
    public void testDeleteMap() throws Exception {
        doNothing().when(mapService).deleteById(anyInt());

        mockMvc.perform(delete("/maps/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(mapService, times(1)).deleteById(1);
    }
}