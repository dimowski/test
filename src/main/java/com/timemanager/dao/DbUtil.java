package com.timemanager.dao;

import com.timemanager.model.Activity;
import com.timemanager.model.Category;
import com.timemanager.model.Subcategory;
import com.timemanager.model.User;
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

    public boolean addUser(User user) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        boolean isOk = false;
        try {
            myConn = dataSource.getConnection();
            String sql = "INSERT INTO service_user (`email`, `username`, `pwd`) VALUES (?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, user.getEmail());
            myStmt.setString(2, user.getUsername());
            myStmt.setString(3, user.getPwd());
            myStmt.execute();
            isOk = true;
        } catch (SQLException e) {
            log.error(e);
        } finally {
            close(myConn, myStmt, null);
        }
        return isOk;
    }

    public User getUserById(int userId) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "SELECT * FROM service_user WHERE user_id=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, userId);
            myRs = myStmt.executeQuery();
            myRs.next();
            return new User(userId, myRs.getString("email"), myRs.getString("username"), myRs.getString("pwd"));
        } catch (SQLException e) {
            log.error(e);
            return null;
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public boolean isAuthorized(User user) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "SELECT * FROM service_user WHERE email=? AND pwd=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, user.getEmail());
            myStmt.setString(2, user.getPwd());
            myRs = myStmt.executeQuery();
            while (myRs.next()) {
                if (myRs.getString("email").equals(user.getEmail()) && myRs.getString("pwd").equals(user.getPwd()))
                    user.setId(myRs.getInt("user_id"));
                user.setUsername(myRs.getString("username"));
                return true;
            }
        } catch (SQLException e) {
            log.error(e);
        } finally {
            close(myConn, myStmt, myRs);
        }
        return false;
    }

    public List<String> getCategoryList(User user) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        List<String> categoriesList = new ArrayList<>();
        try {
            myConn = dataSource.getConnection();
            String sql = "SELECT name FROM category WHERE user_id=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, user.getId());
            myRs = myStmt.executeQuery();
            while (myRs.next()) {
                categoriesList.add(myRs.getString("name"));
            }
        } catch (SQLException exc) {
            log.error("Error while connecting to DB!", exc);
        } finally {
            close(myConn, myStmt, myRs);
        }
//        if (categoriesList.size() == 0)
//            return null;
//        else
        return categoriesList;
    }

    public List<String> getSubcategoryList(User user) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        List<String> subcategoriesList = new ArrayList<>();
        try {
            myConn = dataSource.getConnection();
            String sql = "SELECT name FROM subcategory WHERE user_id=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, user.getId());
            myRs = myStmt.executeQuery();
            while (myRs.next()) {
                subcategoriesList.add(myRs.getString("name"));
            }
        } catch (SQLException e) {
            log.error(e);
        } finally {
            close(myConn, myStmt, myRs);
        }
//        if (subcategoriesList.size() == 0)
//            return null;
//        else
        return subcategoriesList;
    }

    public void addCategory(Category category) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "INSERT INTO category (name, user_id) VALUES (?, ?)";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, category.getCategoryName());
            myStmt.setInt(2, category.getUser().getId());
            myStmt.executeUpdate();
        } catch (SQLException e) {
        } finally {
            close(myConn, myStmt, null);
        }
    }

    public void addSubcategory(Subcategory subcategory) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "INSERT INTO subcategory (name, user_id) VALUES (?, ?);";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, subcategory.getSubcategoryName());
            myStmt.setInt(2, subcategory.getUser().getId());
            myStmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        } finally {
            close(myConn, myStmt, null);
        }
    }

    public void updateCategory(String oldCategory, String newCategory, int userId) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "UPDATE category SET name=? WHERE name=? AND user_id=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, newCategory);
            myStmt.setString(2, oldCategory);
            myStmt.setInt(3, userId);
            log.info("UPDATING CATEGORY:" + myStmt);
            myStmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        } finally {
            close(myConn, myStmt, null);
        }
    }

    public void updateSubcategory(String oldSubcategory, String newSubcategory, int userId) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "UPDATE subcategory SET name=? WHERE name=? AND user_id=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, newSubcategory);
            myStmt.setString(2, oldSubcategory);
            myStmt.setInt(3, userId);
            myStmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        } finally {
            close(myConn, myStmt, null);
        }
    }

    public void deleteCategory(String category, int userId) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "DELETE FROM category WHERE name=? AND user_id=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, category);
            myStmt.setInt(2, userId);
            myStmt.execute();
        } catch (SQLException e) {
            log.error(e);
        } finally {
            close(myConn, myStmt, null);
        }
    }

    public void deleteSubcategory(String subcategory, int userId) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            myConn = dataSource.getConnection();
            String sql = "DELETE FROM subcategory WHERE name=? AND user_id=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, subcategory);
            myStmt.setInt(2, userId);
            myStmt.execute();
        } catch (SQLException e) {
            log.error(e);
        } finally {
            close(myConn, myStmt, null);
        }
    }

    public void addActivity(Activity tempActivity, String login) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        log.info("START TIME IS: " + new java.sql.Date(tempActivity.getStartTime().getTime()));
        try {
            myConn = dataSource.getConnection();
            String sql = "INSERT INTO " + login + "_activity (category, subcategory, start_time, finish_time," +
                    " description) VALUES (?, ?, ?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, tempActivity.getCategory());
            myStmt.setString(2, tempActivity.getSubcategory());
            myStmt.setTimestamp(3, new java.sql.Timestamp(tempActivity.getStartTime().getTime()));
            if (tempActivity.getFinishTime() != null) {
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
            log.error("Error during closing connection", exc);
        }
    }
}
