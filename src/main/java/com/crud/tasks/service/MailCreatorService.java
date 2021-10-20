package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.scheduler.EmailScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbay_message", "Best regards.");
        context.setVariable("informational_message", "The most important message for you for today!");
        context.setVariable("company_mail", adminConfig.getCompanyMail());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildTrelloCardEmail2(String message) {

        List<String> message_details = new ArrayList<>();
        message_details.add("All tasks have been completed.");
        message_details.add("You have only one new task.");
        message_details.add("You have one month to complete this task.");
        message_details.add("Have some rest!");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit our website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbay_message", "Have a good day!");
        context.setVariable("informational_message", "You can't miss this mail!");
        context.setVariable("company_mail", adminConfig.getCompanyMail());
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("details", message_details);
        return templateEngine.process("mail/created-trello-card-mail-2", context);
    }

}
