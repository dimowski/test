package com.timemanager.command;

import com.timemanager.dao.DbUtil;
import com.timemanager.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UpdateCategoryCommand implements Command {

    private DbUtil dbUtil;

    public UpdateCategoryCommand(DbUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String newCategory = request.getParameter("newCategory");
        String oldCategory = request.getParameter("categoryName");
        User user = (User) request.getSession().getAttribute("USER");
        dbUtil.updateCategory(oldCategory, newCategory, user.getId());
        List<String> categoryList = dbUtil.getCategoryList(user);
        request.getSession().setAttribute("CATEGORY_LIST", categoryList);
        return "/manage.jsp";
    }
}
