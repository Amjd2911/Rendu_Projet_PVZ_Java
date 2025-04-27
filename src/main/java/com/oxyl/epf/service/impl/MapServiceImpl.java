package com.oxyl.epf.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.oxyl.epf.dao.MapDao;
import com.oxyl.epf.model.Map;
import com.oxyl.epf.service.MapService;
import com.oxyl.epf.service.ZombieService; // Importer ZombieService

@Service
public class MapServiceImpl implements MapService {

    private final MapDao mapDao;
    private final ZombieService zombieService; // Injecter ZombieService

    public MapServiceImpl(MapDao mapDao, ZombieService zombieService) { // Modifier le constructeur
        this.mapDao = mapDao;
        this.zombieService = zombieService;
    }

    @Override
    public List<Map> findAll() {
        return mapDao.findAll();
    }

    @Override
    public Optional<Map> findById(int id) {
        return mapDao.findById(id);
    }

    @Override
    public void save(Map map) {
        mapDao.save(map);
    }

    @Override
    public void update(Map map) {
        mapDao.update(map);
    }

    //Ici, on supprime la carte et tous les zombies associés pour éviter des erreurs ultérieures
    //Ça évite également d'avoir des zombies orphelins dans la base de données qui pourraient causer
    //des problèmes et/ou juste prendre de la place sans ne plus jamais être utilisés.
    @Override
    public void deleteById(int id) {
        zombieService.deleteByMapId(id);
        mapDao.deleteById(id);
    }
}
