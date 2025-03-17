package com.epf.controller;

import com.epf.dto.PlanteDto;
import com.epf.dto.mapper.PlanteMapper;
import com.epf.model.Plante;
import com.epf.service.PlanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plantes")
public class PlanteController {

    private final PlanteService planteService;

    public PlanteController(PlanteService planteService) {
        this.planteService = planteService;
    }

    @GetMapping
    public ResponseEntity<List<PlanteDto>> getAllPlantes() {
        List<PlanteDto> planteDtos = planteService.findAll()
                .stream()
                .map(PlanteMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(planteDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanteDto> getPlanteById(@PathVariable("id") int id) {
        Optional<Plante> plante = planteService.findById(id);
        return plante.map(p -> ResponseEntity.ok(PlanteMapper.toDto(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createPlante(@RequestBody PlanteDto planteDto) {
        Plante plante = PlanteMapper.toEntity(planteDto);
        planteService.save(plante);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePlante(@PathVariable("id") int id, @RequestBody PlanteDto planteDto) {
        planteDto.setIdPlante(id);
        Plante plante = PlanteMapper.toEntity(planteDto);
        planteService.update(plante);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlante(@PathVariable("id") int id) {
        planteService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}