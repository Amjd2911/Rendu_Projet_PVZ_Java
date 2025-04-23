package com.oxyl.epf.service;

import java.util.List;
import java.util.Optional;

import com.oxyl.epf.model.Zombie;

public interface ZombieService {
    List<Zombie> findAll();
    Optional<Zombie> findById(int id);
    void save(Zombie zombie);
    void update(Zombie zombie);
    void deleteById(int id);
    List<Zombie> findByMapId(int mapId);
}
