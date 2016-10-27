package com.timemanager.servlet;

import com.timemanager.command.Command;
import com.timemanager.command.CommandFactory;
import com.timemanager.dao.DbUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            if (command == null) {
                request.getRequestDispatcher("main.jsp").forward(request, response);
                return;
            }
            String view = command.execute(request, response);
            log.debug("Target view: {}", view);
//            if (!view.contains("main")) {
                request.getRequestDispatcher(view).forward(request, response);
//            } else {
//                response.sendRedirect(view);
//            }
        } catch (Exception e) {
            log.fatal("Executing command failed!", e);
            throw new ServletException("Executing command failed!", e);
        }
    }
}