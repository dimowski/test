package com.timemanager.command;

import com.timemanager.dao.DbUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequest;

public class CommandFactory {

    private static final Logger log = LogManager.getLogger(CommandFactory.class);

    public static Command getCommand(ServletRequest request) {
        DbUtil dbUtil = (DbUtil) request.getServletContext().getAttribute("dbUtil");
        String theCommand = request.getParameter("command");
        log.debug("Command = {}", theCommand);
        if (theCommand == null) {
            theCommand = "list";
        }
        log.info("Command = {}", theCommand);
        switch (theCommand) {
            case "ADD_CATEGORY":
                return new AddCategoryCommand(dbUtil);
            case "ADD_SUBCATEGORY":
                return new AddSubcategoryCommand(dbUtil);
            case "UPDATE_CATEGORY":
                return new UpdateCategoryCommand(dbUtil);
            case "UPDATE_SUBCATEGORY":
                return new UpdateSubcategoryCommand(dbUtil);
            case "DELETE_CATEGORY":
                return new DeleteCategoryCommand(dbUtil);
            case "DELETE_SUBCATEGORY":
                return new DeleteSubcategoryCommand(dbUtil);
            default:
                return null;
        }
    }
}
