package com.timemanager.command;

import com.timemanager.dao.DbUtil;
import com.timemanager.model.Activity;
import com.timemanager.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

public class AddActivityCommand implements Command {

    private DbUtil dbUtil;
    private static final Logger log = LogManager.getLogger(AddActivityCommand.class);

    public AddActivityCommand(DbUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String  catId = request.getParameter("category");
        String  subcatId = request.getParameter("subcategory");
        String startTime = request.getParameter("startTime");
        String finishTime = request.getParameter("finishTime");
        String description = request.getParameter("desc");
        User user = (User) request.getSession().getAttribute("USER");
        String[] hourMin = startTime.split(":");
        Calendar startTimeCal = Calendar.getInstance();
        startTimeCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourMin[0]));
        startTimeCal.set(Calendar.MINUTE, Integer.parseInt(hourMin[1]));
        startTimeCal.set(Calendar.SECOND, 0);

        Calendar finishTimeCal = Calendar.getInstance();
        Activity tempActivity;
        if (!finishTime.equals("")) {
            hourMin = finishTime.split(":");
            finishTimeCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourMin[0]));
            finishTimeCal.set(Calendar.MINUTE, Integer.parseInt(hourMin[1]));
            finishTimeCal.set(Calendar.SECOND, 0);
            tempActivity = new Activity(Integer.parseInt(catId), Integer.parseInt(subcatId), startTimeCal.getTime(), finishTimeCal.getTime(), user, description);
        } else {
            tempActivity = new Activity(Integer.parseInt(catId), Integer.parseInt(subcatId), startTimeCal.getTime(), null, user, description);
        }
        dbUtil.addActivity(tempActivity);
        return "/main.jsp";
    }
}
