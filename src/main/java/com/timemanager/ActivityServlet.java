package com.timemanager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ActivityServlet extends HttpServlet{
    private DbUtil dbUtil;

    @Override
    public void init() throws ServletException {
        super.init();
        // Получение объекта DbUtil из MainServlet.java для доступа к базе данных
        dbUtil = (DbUtil) this.getServletConfig().getServletContext().getAttribute("dbUtil");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
