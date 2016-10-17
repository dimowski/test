package com.timemanager.servlet;

import com.timemanager.dao.DbUtil;
import com.timemanager.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegistrationServlet extends HttpServlet {

    private DbUtil dbUtil;
    private static Logger log = LogManager.getLogger(RegistrationServlet.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        // Получение объекта com.timemanager.dao.DbUtil из com.timemanager.servlet.MainServlet.java для доступа к базе данных
        dbUtil = (DbUtil) getServletContext().getAttribute("dbUtil");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String md5Password = DigestUtils.md5Hex(request.getParameter("userPassword"));
        User user = new User(email, userName, md5Password);
        if (dbUtil.addUser(user)) {
            response.sendRedirect("login.html");
            log.info("User added successfully");
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
            PrintWriter out = response.getWriter();
            out.println("<div align=\"center\"><font color=red>User with this username already exists. Try another Login.</font></div>\n");
            rd.include(request, response);
            log.error("Registration failed");
        }
    }
}
