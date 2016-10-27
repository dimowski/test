package com.timemanager.command;

import com.timemanager.dao.DbUtil;
import com.timemanager.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShowTimeCommand implements Command {

    private DbUtil dbUtil;
    private static final Logger log = LogManager.getLogger(ShowTimeCommand.class);

    public ShowTimeCommand(DbUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("USER");
//        List<Category> categoryList = dbUtil.getCategoryList(user);
//        List<Subcategory> subcategoryList = dbUtil.getSubcategoryList(user);
//        request.getSession().setAttribute("CATEGORY_LIST", categoryList);
//        request.getSession().setAttribute("SUBCATEGORY_LIST", subcategoryList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String targetDate = request.getParameter("value");
        Date date = null;
        log.debug("TARGET DATE = {}", targetDate);
        try {
            date = sdf.parse(targetDate);
        } catch (ParseException e) {
            log.error(e);
        }
        List<ActivityVM> activityList = dbUtil.getActivitiesByDate(date, user.getId());
        request.setAttribute("DAILY_ACTIVITY", activityList);
        return "/main.jsp";
    }
}
