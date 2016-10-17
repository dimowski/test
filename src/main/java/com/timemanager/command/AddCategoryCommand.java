package com.timemanager.command;

import com.timemanager.dao.DbUtil;
import com.timemanager.model.Category;
import com.timemanager.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCategoryCommand implements Command {

    private DbUtil dbUtil;

    public AddCategoryCommand(DbUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String categoryName = request.getParameter("category");
        User user = (User) request.getSession().getAttribute("USER");
        Category category = new Category(categoryName, user);
        dbUtil.addCategory(category);
        request.getSession().setAttribute("CATEGORY_LIST", dbUtil.getCategoryList(user));
        return "/manage.jsp";
    }
}
