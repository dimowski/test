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

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Command command = CommandFactory.getCommand(request);
        try {
            if (command == null) {
                request.getRequestDispatcher("main.jsp").forward(request, response);
                return;
            }
            String view = command.execute(request, response);
            log.debug("Target view: {}", view);
            request.getRequestDispatcher(view).forward(request, response);
        } catch (Exception e) {
            log.fatal("Executing command failed!", e);
            throw new ServletException("Executing command failed!", e);
        }
    }
}