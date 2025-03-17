package com.epf.controller;

import com.epf.dto.MapDto;
import com.epf.dto.mapper.MapMapper;
import com.epf.model.Map;
import com.epf.service.MapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/maps")
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