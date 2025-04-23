package com.oxyl.epf.service;

import com.oxyl.epf.model.Map;
import java.util.List;
import java.util.Optional;

public interface MapService {
    List<Map> findAll();
    Optional<Map> findById(int id);
    void save(Map map);
    void update(Map map);
    void deleteById(int id);
}
