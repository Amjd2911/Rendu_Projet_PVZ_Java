package com.oxyl.epf.service.impl;

import com.oxyl.epf.dao.PlanteDao;
import com.oxyl.epf.model.Plante;
import com.oxyl.epf.service.PlanteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanteServiceImpl implements PlanteService {

    private final PlanteDao planteDao;

    public PlanteServiceImpl(PlanteDao planteDao) {
        this.planteDao = planteDao;
    }

    @Override
    public List<Plante> findAll() {
        return planteDao.findAll();
    }

    @Override
    public Optional<Plante> findById(int id) {
        return planteDao.findById(id);
    }

    @Override
    public void save(Plante plante) {
        planteDao.save(plante);
    }

    @Override
    public void update(Plante plante) {
        planteDao.update(plante);
    }

    @Override
    public void deleteById(int id) {
        planteDao.deleteById(id);
    }
}
