package com.timemanager;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class RegistrationServlet extends HttpServlet {
    private DbUtil dbUtil;

    @Override
    public void init() throws ServletException {
        super.init();
        // Получение объекта DbUtil из MainServlet.java для доступа к базе данных
        dbUtil = (DbUtil) this.getServletConfig().getServletContext().getAttribute("dbUtil");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String login = request.getParameter("login");
        String md5Password = DigestUtils.md5Hex(request.getParameter("userPassword"));
        try {
            if(dbUtil.createNewUser(userName, login, md5Password)) {
                response.sendRedirect("login.html");
                MainServlet.log.info("User added successfully");
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
                PrintWriter out = response.getWriter();
                out.println("<div align=\"center\"><font color=red>User with this Login already exists. Try another Login.</font></div>\n");
                rd.include(request, response);
                MainServlet.log.info("Registration failed");
            }
        } catch (SQLException e) {
            MainServlet.log.error("Error while creating new user", e);
        }
    }
}
