package com.epf.service;

import com.epf.model.Zombie;
import java.util.List;
import java.util.Optional;

public interface ZombieService {
    List<Zombie> findAll();
    Optional<Zombie> findById(int id);
    void save(Zombie zombie);
    void update(Zombie zombie);
    void deleteById(int id);
    List<Zombie> findByMapId(int mapId);
}
