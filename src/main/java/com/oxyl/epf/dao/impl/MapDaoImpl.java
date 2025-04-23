package com.oxyl.epf.dao.impl;

import com.oxyl.epf.dao.MapDao;
import com.oxyl.epf.model.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MapDaoImpl implements MapDao {

    private final JdbcTemplate jdbcTemplate;

    public MapDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Map> mapRowMapper = (rs, rowNum) -> new Map(
            rs.getInt("id_map"),
            rs.getInt("ligne"),
            rs.getInt("colonne"),
            rs.getString("chemin_image")
    );

    @Override
    public List<Map> findAll() {
        String sql = "SELECT * FROM Map";
        return jdbcTemplate.query(sql, mapRowMapper);
    }

    @Override
    public Optional<Map> findById(int id) {
        String sql = "SELECT * FROM Map WHERE id_map = ?";
        List<Map> maps = jdbcTemplate.query(sql, mapRowMapper, id);
        return maps.stream().findFirst();
    }

    @Override
    public void save(Map map) {
        String sql = "INSERT INTO Map (ligne, colonne, chemin_image) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, map.getLigne(), map.getColonne(), map.getCheminImage());
    }

    @Override
    public void update(Map map) {
        String sql = "UPDATE Map SET ligne=?, colonne=?, chemin_image=? WHERE id_map=?";
        jdbcTemplate.update(sql, map.getLigne(), map.getColonne(), map.getCheminImage(), map.getIdMap());
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM Map WHERE id_map = ?";
        jdbcTemplate.update(sql, id);
    }
}
