package com.oxyl.epf.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.oxyl.epf.dao.ZombieDao;
import com.oxyl.epf.model.Zombie;

@Repository
public class ZombieDaoImpl implements ZombieDao {

    private final JdbcTemplate jdbcTemplate;

    public ZombieDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Zombie> zombieRowMapper = (rs, rowNum) -> new Zombie(
            rs.getInt("id_zombie"),
            rs.getString("nom"),
            rs.getInt("point_de_vie"),
            rs.getDouble("attaque_par_seconde"),
            rs.getInt("degat_attaque"),
            rs.getDouble("vitesse_de_deplacement"),
            rs.getString("chemin_image"),
            rs.getInt("id_map")
    );

    @Override
    public List<Zombie> findAll() {
        String sql = "SELECT * FROM Zombie";
        return jdbcTemplate.query(sql, zombieRowMapper);
    }

    @Override
    public Optional<Zombie> findById(int id) {
        String sql = "SELECT * FROM Zombie WHERE id_zombie = ?";
        List<Zombie> zombies = jdbcTemplate.query(sql, zombieRowMapper, id);
        return zombies.stream().findFirst();
    }
    // Ici j'ai rajouté un test pour vérifier si la map existe avant d'insérer ou de mettre à jour un zombie
    // Si la map n'existe pas, on lève une exception et on affiche un message d'erreur détaillé.
    @Override
    public void save(Zombie zombie) {
        int mappedIdMap = (zombie.getIdMap() == 0) ? 1 : zombie.getIdMap();
        
        String checkMapSql = "SELECT COUNT(*) FROM Map WHERE id_map = ?";
        Integer count = jdbcTemplate.queryForObject(checkMapSql, Integer.class, mappedIdMap);
        
        if (count != null && count > 0) {
            String sql = "INSERT INTO Zombie (nom, point_de_vie, attaque_par_seconde, degat_attaque, vitesse_de_deplacement, chemin_image, id_map) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, zombie.getNom(), zombie.getPointDeVie(), zombie.getAttaqueParSeconde(),
                    zombie.getDegatAttaque(), zombie.getVitesseDeDeplacement(), zombie.getCheminImage(), mappedIdMap);
        } else {
            System.err.println("Tentative d'insérer un zombie avec id_map=" + mappedIdMap + " inexistant");
            throw new IllegalArgumentException("La map avec id=" + mappedIdMap + " n'existe pas dans la base de données");
        }
    }
    //Idem pour la méthode update
    @Override
    public void update(Zombie zombie) {
        int mappedIdMap = (zombie.getIdMap() == 0) ? 1 : zombie.getIdMap();
        
        String checkMapSql = "SELECT COUNT(*) FROM Map WHERE id_map = ?";
        Integer count = jdbcTemplate.queryForObject(checkMapSql, Integer.class, mappedIdMap);
        
        if (count != null && count > 0) {
            String sql = "UPDATE Zombie SET nom=?, point_de_vie=?, attaque_par_seconde=?, degat_attaque=?, vitesse_de_deplacement=?, chemin_image=?, id_map=? WHERE id_zombie=?";
            jdbcTemplate.update(sql, zombie.getNom(), zombie.getPointDeVie(), zombie.getAttaqueParSeconde(),
                    zombie.getDegatAttaque(), zombie.getVitesseDeDeplacement(), zombie.getCheminImage(),
                    mappedIdMap, zombie.getIdZombie());
        } else {
            System.err.println("Tentative de mettre à jour un zombie avec id_map=" + mappedIdMap + " inexistant");
            throw new IllegalArgumentException("La map avec id=" + mappedIdMap + " n'existe pas dans la base de données");
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM Zombie WHERE id_zombie = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Zombie> findByMapId(int mapId) {
        String sql = "SELECT * FROM Zombie WHERE id_map = ?";
        return jdbcTemplate.query(sql, zombieRowMapper, mapId);
    }

    @Override
    public void deleteByMapId(int mapId) {
        String sql = "DELETE FROM Zombie WHERE id_map = ?";
        jdbcTemplate.update(sql, mapId);
    }
}
