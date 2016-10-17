package com.timemanager.servlet;

import com.timemanager.dao.DbUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ServletListener implements ServletContextListener {

    private static final Logger log = LogManager.getLogger(ServletListener.class);

    @Resource(name = "jdbc/time_manager")
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DbUtil dbUtil = new DbUtil(dataSource);
        servletContextEvent.getServletContext().setAttribute("dbUtil", dbUtil);
        log.info("Servlet properties loaded.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}