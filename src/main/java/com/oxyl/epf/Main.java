package com.oxyl.epf;

import com.oxyl.epf.config.DataSourceConfig;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            DataSourceConfig dataSourceConfig = new DataSourceConfig();
            DataSource dataSource = dataSourceConfig.dataSource();

            // Tester la connexion
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM Plante")) {

                System.out.println("Connexion à la base réussie !");
                while (resultSet.next()) {
                    System.out.println("Plante trouvée : " + resultSet.getString("nom"));
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
