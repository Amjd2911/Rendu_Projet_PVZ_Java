package com.oxyl.epf.service;

import com.oxyl.epf.model.Plante;
import java.util.List;
import java.util.Optional;

public interface PlanteService {
    List<Plante> findAll();
    Optional<Plante> findById(int id);
    void save(Plante plante);
    void update(Plante plante);
    void deleteById(int id);
}