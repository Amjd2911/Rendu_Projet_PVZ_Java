package com.oxyl.epf.dao.impl;

import com.oxyl.epf.dao.PlanteDao;
import com.oxyl.epf.model.Plante;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlanteDaoImpl implements PlanteDao {

    private final JdbcTemplate jdbcTemplate;

    public PlanteDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Plante> planteRowMapper = (rs, rowNum) -> new Plante(
            rs.getInt("id_plante"),
            rs.getString("nom"),
            rs.getInt("point_de_vie"),
            rs.getDouble("attaque_par_seconde"),
            rs.getInt("degat_attaque"),
            rs.getInt("cout"),
            rs.getDouble("soleil_par_seconde"),
            rs.getString("effet"),
            rs.getString("chemin_image")
    );

    @Override
    public List<Plante> findAll() {
        String sql = "SELECT * FROM Plante";
        return jdbcTemplate.query(sql, planteRowMapper);
    }

    @Override
    public Optional<Plante> findById(int id) {
        String sql = "SELECT * FROM Plante WHERE id_plante = ?";
        List<Plante> plantes = jdbcTemplate.query(sql, planteRowMapper, id);
        return plantes.stream().findFirst();
    }

    @Override
    public void save(Plante plante) {
        String sql = "INSERT INTO Plante (nom, point_de_vie, attaque_par_seconde, degat_attaque, cout, soleil_par_seconde, effet, chemin_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, plante.getNom(), plante.getPointDeVie(), plante.getAttaqueParSeconde(),
                plante.getDegatAttaque(), plante.getCout(), plante.getSoleilParSeconde(),
                plante.getEffet(), plante.getCheminImage());
    }

    @Override
    public void update(Plante plante) {
        String sql = "UPDATE Plante SET nom=?, point_de_vie=?, attaque_par_seconde=?, degat_attaque=?, cout=?, soleil_par_seconde=?, effet=?, chemin_image=? WHERE id_plante=?";
        jdbcTemplate.update(sql, plante.getNom(), plante.getPointDeVie(), plante.getAttaqueParSeconde(),
                plante.getDegatAttaque(), plante.getCout(), plante.getSoleilParSeconde(),
                plante.getEffet(), plante.getCheminImage(), plante.getIdPlante());
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM Plante WHERE id_plante = ?";
        jdbcTemplate.update(sql, id);
    }
}
