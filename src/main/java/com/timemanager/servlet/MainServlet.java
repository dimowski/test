package com.timemanager.servlet;

import com.timemanager.command.Command;
import com.timemanager.command.CommandFactory;
import com.timemanager.dao.DbUtil;
import com.timemanager.model.Activity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

public class MainServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(MainServlet.class.getName());
    private DbUtil dbUtil;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            log.info("initializing Main Servlet");
            dbUtil = (DbUtil) getServletContext().getAttribute("dbUtil");
            log.debug("Main Servlet successful initialized");
        } catch (Exception ex) {
            log.error("Error while init MainServlet!", ex);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandFactory.getCommand(request);
        try {
            String view = command.execute(request, response);
            log.debug("Target view: {}", view);
            if (!view.contains("main")) {
                request.getRequestDispatcher(view).forward(request, response);
            } else {
                response.sendRedirect(view);
            }
        } catch (Exception e) {
            log.fatal("Executing command failed!", e);
            throw new ServletException("Executing command failed!", e);
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

        Calendar finishTimeCal = Calendar.getInstance();
        Activity tempActivity;
        if (!finishTime.equals("")) {
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
}
