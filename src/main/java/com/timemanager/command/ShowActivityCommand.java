package com.timemanager.command;

import com.timemanager.dao.DbUtil;
import com.timemanager.model.ActivityVM;
import com.timemanager.model.User;
import com.timemanager.servlet.AuthorizationServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowActivityCommand implements Command {

    private DbUtil dbUtil;

    private static final Logger log = LogManager.getLogger(ShowActivityCommand.class);


    public ShowActivityCommand(DbUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("USER");
        List<ActivityVM> activityList = dbUtil.getAllActivities(user);
        log.debug("Activities length = {}", activityList.size());
        request.setAttribute("ACTIVITY", activityList);
        return "/activity.jsp";
    }
}
