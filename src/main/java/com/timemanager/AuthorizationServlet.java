package com.timemanager;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class AuthorizationServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(AuthorizationServlet.class.getName());
    private DbUtil dbUtil;

    @Override
    public void init() throws ServletException {
        super.init();
        // Получение объекта DbUtil из MainServlet.java для доступа к базе данных
        dbUtil = (DbUtil) this.getServletConfig().getServletContext().getAttribute("dbUtil");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Запрос параметров из формы авторизации
        String login = request.getParameter("login");
        String pwd = DigestUtils.md5Hex(request.getParameter("userPassword"));
        try {
            //Проверка валидности пользователя
            if (dbUtil.isAuthorized(login, pwd)) {
                log.info(login + " authorized successfully.");
                // Добавление cookie с ИД пользователя
                Cookie loginCookie = new Cookie("userID", login);
                loginCookie.setMaxAge(60*60*24*365);
                response.addCookie(loginCookie);
                // Добавление объектов пользователя из БД в объект sission
                List<String> categoryList = dbUtil.getCategoryList(login);
                List<String> subcategoryList = dbUtil.getSubcategoryList(login);
                request.getSession().setAttribute("CATEGORY_LIST", categoryList);
                request.getSession().setAttribute("SUBCATEGORY_LIST", subcategoryList);
                request.getSession().setAttribute("LOGIN", login);
                response.sendRedirect("main.jsp");
            } else {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                    PrintWriter out = response.getWriter();
                    out.println("<div align=\"center\"><font color=red>Login/Password incorrect. Try again</font></div>\n");
                    rd.include(request, response);
            }
        } catch (SQLException e) {
            log.error("Unable to check user authorization", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String login = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userID")) {
                    login = cookie.getValue();
                }
            }
        }
        if (login == null)
            response.sendRedirect("login.html");
        else
        try {
            List<String> categoryList = dbUtil.getCategoryList(login);
            List<String> subcategoryList = dbUtil.getSubcategoryList(login);
            request.getSession().setAttribute("LOGIN", login);
            log.info("USER LOGIN IS " + login);
            request.getSession().setAttribute("CATEGORY_LIST", categoryList);
            request.getSession().setAttribute("SUBCATEGORY_LIST", subcategoryList);
            response.sendRedirect("main.jsp");
        } catch (SQLException e) {
            log.error("Unable to check user cookie....", e);
        }
    }
}