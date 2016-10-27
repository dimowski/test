package com.timemanager.command;

import com.timemanager.dao.DbUtil;
import com.timemanager.model.Category;
import com.timemanager.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteCategoryCommand implements Command {

    private DbUtil dbUtil;

    public DeleteCategoryCommand(DbUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String category = request.getParameter("categoryName");
        User user = (User) request.getSession().getAttribute("USER");
        dbUtil.deleteCategory(category, user.getId());
        List<Category> categoryList = dbUtil.getCategoryList(user);
        request.getSession().setAttribute("CATEGORY_LIST", categoryList);
        return "/manage.jsp";
    }
}
