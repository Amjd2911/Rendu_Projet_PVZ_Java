package com.oxyl.epf.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.oxyl.epf.dao.ZombieDao;
import com.oxyl.epf.model.Zombie;
import com.oxyl.epf.service.ZombieService;

@Service
public class ZombieServiceImpl implements ZombieService {

    private final ZombieDao zombieDao;

    public ZombieServiceImpl(ZombieDao zombieDao) {
        this.zombieDao = zombieDao;
    }

    @Override
    public List<Zombie> findAll() {
        return zombieDao.findAll();
    }

    @Override
    public Optional<Zombie> findById(int id) {
        return zombieDao.findById(id);
    }

    @Override
    public void save(Zombie zombie) {
        zombieDao.save(zombie);
    }

    @Override
    public void update(Zombie zombie) {
        zombieDao.update(zombie);
    }

    @Override
    public void deleteById(int id) {
        zombieDao.deleteById(id);
    }

    @Override
    public List<Zombie> findByMapId(int mapId) {
        return zombieDao.findByMapId(mapId);
    }

    @Override
    public void deleteByMapId(int mapId) {
        zombieDao.deleteByMapId(mapId);
    }
}
