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

import com.oxyl.epf.dto.MapDto;
import com.oxyl.epf.dto.mapper.MapMapper;
import com.oxyl.epf.model.Map;
import com.oxyl.epf.service.MapService;

@RestController
@RequestMapping("/maps") // Removed /api prefix
public class MapController {

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping
    public ResponseEntity<List<MapDto>> getAllMaps() {
        List<MapDto> mapDtos = mapService.findAll()
                .stream()
                .map(MapMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mapDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MapDto> getMapById(@PathVariable("id") int id) {
        Optional<Map> map = mapService.findById(id);
        return map.map(m -> ResponseEntity.ok(MapMapper.toDto(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createMap(@RequestBody MapDto mapDto) {
        Map map = MapMapper.toEntity(mapDto);
        mapService.save(map);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMap(@PathVariable("id") int id, @RequestBody MapDto mapDto) {
        mapDto.setIdMap(id);
        Map map = MapMapper.toEntity(mapDto);
        mapService.update(map);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMap(@PathVariable("id") int id) {
        mapService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}