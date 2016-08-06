package com.timemanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUtil {

    private static final Logger log = LogManager.getLogger(DbUtil.class.getName());

    private DataSource dataSource;

    public DbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    private void createActivityTable(String login) throws SQLException {
        Connection myConn = null;
        Statement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "CREATE TABLE `time_manager_db`.`" + login + "_activity` (" +
                    "  `activity_id` INT NOT NULL AUTO_INCREMENT," +
                    "  `category` VARCHAR(45) NULL," +
                    "  `subcategory` VARCHAR(45) NULL," +
                    "  `start_time` DATETIME NULL," +
                    "  `finish_time` DATETIME NULL," +
                    "  `description` VARCHAR(100) NULL," +
                    "  PRIMARY KEY (`activity_id`));";
            myStmt = myConn.createStatement();
            myStmt.execute(sql);
            log.info("Activity table created successfully");
        } finally {
            close(myConn, myStmt, null);
        }
    }

    private void createSubcategoriesTable(String login) throws SQLException {
        Connection myConn = null;
        Statement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "CREATE TABLE `time_manager_db`.`" + login + "_subcategories` (" +
                    " `subcategory_id` INT NOT NULL AUTO_INCREMENT," +
                    " `subcategory` VARCHAR(45) NULL," +
                    " PRIMARY KEY (`subcategory_id`));";
            myStmt = myConn.createStatement();
            myStmt.execute(sql);
            log.info("Subcategories table created successfully");
        } finally {
            close(myConn, myStmt, null);
        }
    }

    private void createCategoriesTable(String login) throws SQLException {
        Connection myConn = null;
        Statement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "CREATE TABLE `time_manager_db`.`" + login + "_categories` (" +
                    " `category_id` INT NOT NULL AUTO_INCREMENT," +
                    " `category` VARCHAR(45) NULL," +
                    " PRIMARY KEY (`category_id`));";
            myStmt = myConn.createStatement();
            myStmt.execute(sql);
            log.info("Categories table created successfully");
        } finally {
            close(myConn, myStmt, null);
        }
    }

    public boolean createNewUser(String userName, String login, String password) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        boolean isOk = false;
        try {
            myConn = dataSource.getConnection();
            String sql = "INSERT INTO `time_manager_db`.`users`" +
                    " (`login`, `username`, `pwd`)" +
                    " VALUES (?, ?, ?);";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, login);
            myStmt.setString(2, userName);
            myStmt.setString(3, password);
            myStmt.execute();
            createActivityTable(login);
            createCategoriesTable(login);
            createSubcategoriesTable(login);
            isOk = true;
        } finally {
            close(myConn, myStmt, null);
        }
        return isOk;
    }

    public boolean isAuthorized(String login, String password) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "SELECT * FROM `time_manager_db`.`users` WHERE `login`=? AND `pwd`=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, login);
            myStmt.setString(2, password);
            myRs = myStmt.executeQuery();
            while (myRs.next()) {
                if(myRs.getString("login").equals(login)&& myRs.getString("pwd").equals(password))
                    return true;
            }
        } finally {
            close(myConn, myStmt, myRs);
        }
        return false;
    }

    public List<String> getCategoryList(String login) throws SQLException {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        List<String> categoriesList = new ArrayList<>();
        try {
            myConn = dataSource.getConnection();
            String sql = "SELECT `category` FROM `" + login + "_categories`";
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(sql);
            while (myRs.next()) {
                categoriesList.add(myRs.getString("category"));
            }
        } catch (SQLException exc) {
            log.error("Error while connecting to DB in getCategory()!", exc);
        } finally {
            close(myConn, myStmt, myRs);
        }
        if(categoriesList.size()==0)
            return null;
        else return categoriesList;
    }

    public List<String> getSubcategoryList(String login) throws SQLException {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        List<String> subcategoriesList = new ArrayList<>();
        try {
            myConn = dataSource.getConnection();
            String sql = "SELECT subcategory FROM " + login + "_subcategories";
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(sql);
            while (myRs.next()) {
                subcategoriesList.add(myRs.getString("subcategory"));
            }
        } finally {
            close(myConn, myStmt, myRs);
        }
        if(subcategoriesList.size()==0)
            return null;
        else return subcategoriesList;
    }

    public void addCategory(String category, String login) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "INSERT INTO `time_manager_db`.`" + login + "_categories` (`category`) VALUES (?);";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, category);
            myStmt.execute();
        } finally {
            close(myConn, myStmt, null);
        }
    }

    public void addSubcategory(String subcategory, String login) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "INSERT INTO `time_manager_db`.`" + login + "_subcategories` (`subcategory`) VALUES (?);";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, subcategory);
            myStmt.execute();
        } finally {
            close(myConn, myStmt, null);
        }
    }

    public void updateCategory(String oldCategory, String newCategory, String login) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        log.info("At updateCategory():" + oldCategory + "/" + newCategory + "/" + login);
        try {
            myConn = dataSource.getConnection();
            String sql = "UPDATE " + login + "_categories SET category=? WHERE category=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, newCategory);
            myStmt.setString(2, oldCategory);
            log.info("UPDATING CATEGORY:" + myStmt);
            myStmt.execute();
        } finally {
            close(myConn, myStmt, null);
        }
    }

    public void updateSubcategory(String oldSubcategory, String newSubcategory, String login) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "UPDATE " + login + "_subcategories SET subcategory=? WHERE subcategory=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, newSubcategory);
            myStmt.setString(2, oldSubcategory);
            myStmt.execute();
        } finally {
            close(myConn, myStmt, null);
        }
    }

    public void deleteCategory(String category, String login) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "DELETE FROM " + login + "_categories WHERE category=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, category);
            myStmt.execute();
        } finally {
            close(myConn, myStmt, null);
        }
    }

    public void deleteSubcategory(String subcategory, String login) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "DELETE FROM " + login + "_subcategories WHERE subcategory=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, subcategory);
            myStmt.execute();
        } finally {
            close(myConn, myStmt, null);
        }
    }

    public void addActivity(Activity tempActivity, String login) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        log.info("START TIME IS: " +  new java.sql.Date(tempActivity.getStartTime().getTime()));
        try {
            myConn = dataSource.getConnection();
            String sql = "INSERT INTO " + login + "_activity (category, subcategory, start_time, finish_time," +
                    " description) VALUES (?, ?, ?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, tempActivity.getCategory());
            myStmt.setString(2, tempActivity.getSubcategory());
            myStmt.setTimestamp(3, new java.sql.Timestamp(tempActivity.getStartTime().getTime()));
            if(tempActivity.getFinishTime() != null) {
                myStmt.setTimestamp(4, new java.sql.Timestamp(tempActivity.getFinishTime().getTime()));
            } else {
                myStmt.setDate(4, null);
            }
            myStmt.setString(5, tempActivity.getDescription());
            myStmt.execute();
        } finally {
            close(myConn, myStmt, null);
        }
    }

    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try {
            if (myRs != null) {
                myRs.close();
            }
            if (myStmt != null) {
                myStmt.close();
            }
            if (myConn != null) {
                myConn.close();
            }
        } catch (Exception exc) {
            log.error("Error during closing connection" ,exc);
        }
    }
}
