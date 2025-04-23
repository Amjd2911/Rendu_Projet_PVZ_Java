package com.oxyl.epf.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oxyl.epf.dto.ZombieDto;
import com.oxyl.epf.dto.mapper.ZombieMapper;
import com.oxyl.epf.model.Zombie;
import com.oxyl.epf.service.ZombieService;

@RestController
@RequestMapping("/zombies") // Removed /api prefix
public class ZombieController {

    private final ZombieService zombieService;

    public ZombieController(ZombieService zombieService) {
        this.zombieService = zombieService;
    }

    @GetMapping
    public ResponseEntity<List<ZombieDto>> getAllZombies() {
        List<ZombieDto> zombieDtos = zombieService.findAll()
                .stream()
                .map(ZombieMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(zombieDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZombieDto> getZombieById(@PathVariable("id") int id) {
        Optional<Zombie> zombie = zombieService.findById(id);
        return zombie.map(z -> ResponseEntity.ok(ZombieMapper.toDto(z)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/map/{mapId}")
    public ResponseEntity<List<ZombieDto>> getZombiesByMapId(@PathVariable("mapId") int mapId) {
        List<ZombieDto> zombieDtos = zombieService.findByMapId(mapId)
                .stream()
                .map(ZombieMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(zombieDtos);
    }

    @PostMapping
    public ResponseEntity<Void> createZombie(@RequestBody ZombieDto zombieDto) {
        Zombie zombie = ZombieMapper.toEntity(zombieDto);
        zombieService.save(zombie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateZombie(@PathVariable("id") int id, @RequestBody ZombieDto zombieDto) {
        zombieDto.setIdZombie(id);
        Zombie zombie = ZombieMapper.toEntity(zombieDto);
        zombieService.update(zombie);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZombie(@PathVariable("id") int id) {
        zombieService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}