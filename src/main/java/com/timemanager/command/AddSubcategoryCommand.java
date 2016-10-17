package com.timemanager.command;

import com.timemanager.dao.DbUtil;
import com.timemanager.model.Subcategory;
import com.timemanager.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddSubcategoryCommand implements Command {

    private DbUtil dbUtil;

    public AddSubcategoryCommand(DbUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String subcategoryName = request.getParameter("subcategory");
        User user = (User) request.getSession().getAttribute("USER");
        Subcategory subcategory = new Subcategory(subcategoryName, user);
        dbUtil.addSubcategory(subcategory);
        request.getSession().setAttribute("SUBCATEGORY_LIST", dbUtil.getSubcategoryList(user));
        return "/manage.jsp";
    }
}
