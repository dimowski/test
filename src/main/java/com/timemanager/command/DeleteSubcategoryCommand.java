package com.timemanager.command;

import com.timemanager.dao.DbUtil;
import com.timemanager.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteSubcategoryCommand implements Command {

    private DbUtil dbUtil;

    public DeleteSubcategoryCommand(DbUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String subcategory = request.getParameter("subcategoryName");
        User user = (User) request.getSession().getAttribute("USER");
        dbUtil.deleteSubcategory(subcategory, user.getId());
        List<String> subcategoryList = dbUtil.getSubcategoryList(user);
        request.getSession().setAttribute("SUBCATEGORY_LIST", subcategoryList);
        return "/manage.jsp";
    }
}
