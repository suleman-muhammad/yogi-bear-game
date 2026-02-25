package com.game.yogibear.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ScoreManager {


    private String DB_URL;
    private String DB_USER;
    private String DB_PASS;
    /**
     * Constructor. Attempts to connect and create the table if needed.
     */
    public ScoreManager() {
        loadConfig();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connected Successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("CRITICAL ERROR: MySQL JDBC Driver not found!");
            System.err.println("Did you add the mysql-connector-java jar to your project classpath?");
            e.printStackTrace();
        }
    }

    /**
     * loads the credentials from config.properties file in root directory.
     */
    public void loadConfig(){
        Properties prop = new Properties();

        try(InputStream input = new FileInputStream("config.properties")){
            prop.load(input);
            this.DB_URL = prop.getProperty("db.url");
            this.DB_USER = prop.getProperty("db.user");
            this.DB_PASS = prop.getProperty("db.password");
        }catch (IOException e){
            System.out.println("Failed to Load config.properties. Ensure it exists in root dir.");
            e.printStackTrace();
        }
    }


    /**
     * Inserts a new score entry into the database.
     * Uses PreparedStatement to prevent SQL injection.
     */
    public void addScoreToDatabase(String playerName, int score) {

        String insertSQL = "INSERT INTO high_scores (name, scores) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {


            pstmt.setString(1, playerName);
            pstmt.setInt(2, score);


            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Score saved! Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("Failed to save score to database.");
            e.printStackTrace();
        }
    }


    /**
     * Retrieves the top 10 highest scores, sorted descending.
     *
     * @return A list of ScoreEntry objects.
     */
    public List<ScoreEntry> getTop10Scores() {
        List<ScoreEntry> topScoresList = new ArrayList<>();


        String selectSQL = "SELECT name, scores FROM high_scores ORDER BY scores DESC LIMIT 10";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {


            while (rs.next()) {

                String nameFromDB = rs.getString("name");
                int scoreFromDB = rs.getInt("scores");


                topScoresList.add(new ScoreEntry(nameFromDB, scoreFromDB));
            }

        } catch (SQLException e) {
            System.err.println("Failed to retrieve top scores.");
            e.printStackTrace();
        }

        return topScoresList;
    }
}
