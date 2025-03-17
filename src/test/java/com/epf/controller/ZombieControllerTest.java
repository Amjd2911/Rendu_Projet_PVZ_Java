package com.epf.controller;

import com.epf.dto.ZombieDto;
import com.epf.dto.mapper.ZombieMapper;
import com.epf.model.Zombie;
import com.epf.service.ZombieService;
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

public class ZombieControllerTest {

    @Mock
    private ZombieService zombieService;

    @InjectMocks
    private ZombieController zombieController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Zombie zombie;
    private ZombieDto zombieDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(zombieController).build();
        objectMapper = new ObjectMapper();

        zombie = new Zombie(1, "Zombie Standard", 100, 0.5, 10, 0.8, "zombie.png", 1);
        zombieDto = ZombieMapper.toDto(zombie);
    }

    @Test
    public void testGetAllZombies() throws Exception {
        when(zombieService.findAll()).thenReturn(Arrays.asList(zombie));

        mockMvc.perform(get("/api/zombies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Zombie Standard"))
                .andExpect(jsonPath("$[0].idZombie").value(1));

        verify(zombieService, times(1)).findAll();
    }

    @Test
    public void testGetZombieById() throws Exception {
        when(zombieService.findById(1)).thenReturn(Optional.of(zombie));

        mockMvc.perform(get("/api/zombies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Zombie Standard"))
                .andExpect(jsonPath("$.idZombie").value(1));

        verify(zombieService, times(1)).findById(1);
    }

    @Test
    public void testGetZombiesByMapId() throws Exception {
        when(zombieService.findByMapId(1)).thenReturn(Arrays.asList(zombie));

        mockMvc.perform(get("/api/zombies/map/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Zombie Standard"));

        verify(zombieService, times(1)).findByMapId(1);
    }

    @Test
    public void testCreateZombie() throws Exception {
        doNothing().when(zombieService).save(any(Zombie.class));

        mockMvc.perform(post("/api/zombies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zombieDto)))
                .andExpect(status().isCreated());

        verify(zombieService, times(1)).save(any(Zombie.class));
    }

    @Test
    public void testUpdateZombie() throws Exception {
        doNothing().when(zombieService).update(any(Zombie.class));

        mockMvc.perform(put("/api/zombies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zombieDto)))
                .andExpect(status().isOk());

        verify(zombieService, times(1)).update(any(Zombie.class));
    }

    @Test
    public void testDeleteZombie() throws Exception {
        doNothing().when(zombieService).deleteById(anyInt());

        mockMvc.perform(delete("/api/zombies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(zombieService, times(1)).deleteById(1);
    }
}