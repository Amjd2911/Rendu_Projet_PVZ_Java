package com.oxyl.epf.dto.mapper;

import com.oxyl.epf.dto.PlanteDto;
import com.oxyl.epf.model.Plante;

public class PlanteMapper {

    public static PlanteDto toDto(Plante plante) {
        return new PlanteDto(
                plante.getIdPlante(),
                plante.getNom(),
                plante.getPointDeVie(),
                plante.getAttaqueParSeconde(),
                plante.getDegatAttaque(),
                plante.getCout(),
                plante.getSoleilParSeconde(),
                plante.getEffet(),
                plante.getCheminImage()
        );
    }

    public static Plante toEntity(PlanteDto dto) {
        return new Plante(
                dto.getIdPlante(),
                dto.getNom(),
                dto.getPointDeVie(),
                dto.getAttaqueParSeconde(),
                dto.getDegatAttaque(),
                dto.getCout(),
                dto.getSoleilParSeconde(),
                dto.getEffet(),
                dto.getCheminImage()
        );
    }
}