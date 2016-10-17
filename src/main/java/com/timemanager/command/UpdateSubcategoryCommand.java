package com.timemanager.command;

import com.timemanager.dao.DbUtil;
import com.timemanager.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UpdateSubcategoryCommand implements Command {

    private DbUtil dbUtil;

    public UpdateSubcategoryCommand(DbUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String newSubcategory = request.getParameter("newSubcategory");
        String oldSubcategory = request.getParameter("subcategoryName");
        User user = (User) request.getSession().getAttribute("USER");
        dbUtil.updateSubcategory(oldSubcategory, newSubcategory, user.getId());
        List<String> subcategoryList = dbUtil.getSubcategoryList(user);
        request.getSession().setAttribute("SUBCATEGORY_LIST", subcategoryList);
        return "/manage.jsp";
    }
}
