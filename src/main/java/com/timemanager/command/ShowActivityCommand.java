package com.timemanager.command;

import com.timemanager.dao.DbUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowActivityCommand implements Command {

    private DbUtil dbUtil;

    public ShowActivityCommand(DbUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        return "/activity.jsp";
    }
}
