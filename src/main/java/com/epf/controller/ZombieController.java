package com.epf.controller;

import com.epf.dto.ZombieDto;
import com.epf.dto.mapper.ZombieMapper;
import com.epf.model.Zombie;
import com.epf.service.ZombieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/zombies")
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