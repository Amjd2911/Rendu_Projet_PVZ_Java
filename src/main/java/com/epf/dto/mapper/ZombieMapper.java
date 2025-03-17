package com.epf.dto.mapper;

import com.epf.dto.ZombieDto;
import com.epf.model.Zombie;

public class ZombieMapper {

    public static ZombieDto toDto(Zombie zombie) {
        return new ZombieDto(
                zombie.getIdZombie(),
                zombie.getNom(),
                zombie.getPointDeVie(),
                zombie.getAttaqueParSeconde(),
                zombie.getDegatAttaque(),
                zombie.getVitesseDeDeplacement(),
                zombie.getCheminImage(),
                zombie.getIdMap()
        );
    }

    public static Zombie toEntity(ZombieDto dto) {
        return new Zombie(
                dto.getIdZombie(),
                dto.getNom(),
                dto.getPointDeVie(),
                dto.getAttaqueParSeconde(),
                dto.getDegatAttaque(),
                dto.getVitesseDeDeplacement(),
                dto.getCheminImage(),
                dto.getIdMap()
        );
    }
}