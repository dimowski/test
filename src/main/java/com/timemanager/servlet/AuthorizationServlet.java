package com.timemanager.servlet;

import com.timemanager.dao.DbUtil;
import com.timemanager.model.User;
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
import java.util.List;

public class AuthorizationServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(AuthorizationServlet.class.getName());
    private DbUtil dbUtil;

    @Override
    public void init() throws ServletException {
        super.init();
        // Получение объекта DbUtil из MainServlet.java для доступа к базе данных
        dbUtil = (DbUtil) getServletContext().getAttribute("dbUtil");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Запрос параметров из формы авторизации
        String email = request.getParameter("email");
        String pwd = DigestUtils.md5Hex(request.getParameter("userPassword"));
        User user = new User(email, pwd);
        //Проверка валидности пользователя
        if (dbUtil.isAuthorized(user)) {
            log.info("{} authorized successfully.", user.getUsername());
            // Добавление cookie с ИД пользователя
            Cookie loginCookie = new Cookie("userID", Integer.toString(user.getId()));
            loginCookie.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(loginCookie);
            // Добавление объектов пользователя из БД в объект sission
            List<String> categoryList = dbUtil.getCategoryList(user);
            List<String> subcategoryList = dbUtil.getSubcategoryList(user);
            request.getSession().setAttribute("CATEGORY_LIST", categoryList);
            request.getSession().setAttribute("SUBCATEGORY_LIST", subcategoryList);
            request.getSession().setAttribute("USER", user);
            request.getRequestDispatcher("/main.jsp").forward(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = response.getWriter();
            out.println("<div align=\"center\"><font color=red>Login/Password incorrect. Try again</font></div>\n");
            rd.include(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String userId = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userID")) {
                    userId = cookie.getValue();
                }
            }
        }
        if (userId != null) {
            log.info("UserID is {}", userId);
            User user = dbUtil.getUserById(Integer.parseInt(userId));
            List<String> categoryList = dbUtil.getCategoryList(user);
            List<String> subcategoryList = dbUtil.getSubcategoryList(user);
            request.getSession().setAttribute("USER", user);
            log.info("User is {}", user.getEmail());
            request.getSession().setAttribute("CATEGORY_LIST", categoryList);
            request.getSession().setAttribute("SUBCATEGORY_LIST", subcategoryList);
            getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.html");
        }
    }
}