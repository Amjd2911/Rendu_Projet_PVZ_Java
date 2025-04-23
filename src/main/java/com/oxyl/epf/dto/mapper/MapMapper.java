package com.oxyl.epf.dto.mapper;

import com.oxyl.epf.dto.MapDto;
import com.oxyl.epf.model.Map;

public class MapMapper {

    public static MapDto toDto(Map map) {
        return new MapDto(
                map.getIdMap(),
                map.getLigne(),
                map.getColonne(),
                map.getCheminImage()
        );
    }

    public static Map toEntity(MapDto dto) {
        return new Map(
                dto.getIdMap(),
                dto.getLigne(),
                dto.getColonne(),
                dto.getCheminImage()
        );
    }
}