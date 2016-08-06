package com.timemanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class MainServlet extends HttpServlet {

    @Resource(name = "jdbc/time_manager_db")
    private DataSource dataSource;

    private static final Logger log = LogManager.getLogger(MainServlet.class.getName());
    private DbUtil dbUtil;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            log.info("initializing Main Servlet");
            dbUtil = new DbUtil(dataSource);
            this.getServletConfig().getServletContext().setAttribute("dbUtil", dbUtil);
            log.debug("Main Servlet successful initialized");
        } catch (Exception ex) {
            log.error("Error while init MainServlet!", ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String theCommand = request.getParameter("command");
            log.info("At doGet(). Command is " + theCommand);
            switch (theCommand) {
                case "ADD_CATEGORY":
                    addCategory(request);
                    response.sendRedirect("manage.jsp");
                    break;
                case "ADD_SUBCATEGORY":
                    addSubcategory(request);
                    response.sendRedirect("manage.jsp");
                    break;
                case "UPDATE_CATEGORY":
                    updateCategory(request);
                    response.sendRedirect("manage.jsp");
                    break;
                case "UPDATE_SUBCATEGORY":
                    updateSubcategory(request);
                    response.sendRedirect("manage.jsp");
                    break;
                case "DELETE_CATEGORY":
                    deleteCategory(request);
                    response.sendRedirect("manage.jsp");
                    break;
                case "DELETE_SUBCATEGORY":
                    deleteSubcategory(request);
                    response.sendRedirect("manage.jsp");
                    break;
            }
        } catch (Exception exc) {
            log.error(exc);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String theCategory = request.getParameter("category");
        String theSubcategory = request.getParameter("subcategory");
        String startTime = request.getParameter("startTime");
        String finishTime = request.getParameter("finishTime");
        String description = request.getParameter("desc");
        String login = (String) request.getSession().getAttribute("LOGIN");
        String[] hourMin = startTime.split(":");
        Calendar startTimeCal = Calendar.getInstance();
        startTimeCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourMin[0]));
        startTimeCal.set(Calendar.MINUTE, Integer.parseInt(hourMin[1]));
        startTimeCal.set(Calendar.SECOND, 0);

        log.info("START TIME IS: " + startTimeCal.getTime());

        Calendar finishTimeCal = Calendar.getInstance();
        Activity tempActivity;
        if(!finishTime.equals("")) {
            hourMin = finishTime.split(":");
            finishTimeCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourMin[0]));
            finishTimeCal.set(Calendar.MINUTE, Integer.parseInt(hourMin[1]));
            finishTimeCal.set(Calendar.SECOND, 0);
            tempActivity = new Activity(theCategory, theSubcategory, startTimeCal.getTime(), finishTimeCal.getTime(), description);
        } else
            tempActivity = new Activity(theCategory, theSubcategory, startTimeCal.getTime(), null, description);
        try {
            dbUtil.addActivity(tempActivity, login);
        } catch (SQLException e) {
            log.error(e);
        }
        response.sendRedirect("main.jsp");
    }

    private void listCategoies(HttpServletRequest request) throws SQLException, ServletException, IOException {
        String login = (String) request.getSession().getAttribute("LOGIN");
        log.info("at listCategories(). login = " + login);
        List<String> categoryList = dbUtil.getCategoryList(login);
        request.getSession().setAttribute("CATEGORY_LIST", categoryList);
    }

    private void listSubcategoies(HttpServletRequest request) throws SQLException, ServletException, IOException {
        String login = (String) request.getSession().getAttribute("LOGIN");
        List<String> subcategoryList = dbUtil.getSubcategoryList(login);
        request.getSession().setAttribute("SUBCATEGORY_LIST", subcategoryList);
    }

    private void addCategory(HttpServletRequest request) throws SQLException, ServletException, IOException {
        String category = request.getParameter("category");
        String login = (String) request.getSession().getAttribute("LOGIN");
        dbUtil.addCategory(category, login);
        listCategoies(request);
    }

    private void addSubcategory(HttpServletRequest request) throws SQLException, ServletException, IOException {
        String subcategory = request.getParameter("subcategory");
        String login = (String) request.getSession().getAttribute("LOGIN");
        dbUtil.addSubcategory(subcategory, login);
        listSubcategoies(request);
    }

    private void updateCategory(HttpServletRequest request) throws ServletException, SQLException, IOException {
        String newCategory = request.getParameter("newCategory");
        String oldCategory = request.getParameter("categoryName");
        log.info("At updateCategory(). categories = " + oldCategory + "/" + newCategory);
        String login = (String) request.getSession().getAttribute("LOGIN");
        dbUtil.updateCategory(oldCategory, newCategory, login);
        listCategoies(request);
    }

    private void updateSubcategory(HttpServletRequest request) throws ServletException, SQLException, IOException {
        String newSubcategory = request.getParameter("newSubcategory");
        String oldSubcategory = request.getParameter("subcategoryName");
        String login = (String) request.getSession().getAttribute("LOGIN");
        dbUtil.updateSubcategory(oldSubcategory, newSubcategory, login);
        listSubcategoies(request);
    }

    private void deleteCategory(HttpServletRequest request) throws ServletException, SQLException, IOException {
        String category = request.getParameter("categoryName");
        String login = (String) request.getSession().getAttribute("LOGIN");
        dbUtil.deleteCategory(category, login);
        listCategoies(request);
    }

    private void deleteSubcategory(HttpServletRequest request) throws ServletException, SQLException, IOException {
        String subcategory = request.getParameter("subcategoryName");
        String login = (String) request.getSession().getAttribute("LOGIN");
        dbUtil.deleteSubcategory(subcategory, login);
        listSubcategoies(request);
    }
}
