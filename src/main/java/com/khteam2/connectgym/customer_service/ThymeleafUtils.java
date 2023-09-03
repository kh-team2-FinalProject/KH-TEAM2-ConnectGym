package com.khteam2.connectgym.customer_service;

import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class ThymeleafUtils {

    private static TemplateEngine templateEngine;

    public static void setTemplateEngine(TemplateEngine templateEngine){
        ThymeleafUtils.templateEngine = templateEngine;
    }

    public static String processFragment(String fragmentName, Model model){
        Context context = new Context();
        context.setVariables(model.asMap());
        return templateEngine.process(fragmentName, context);
    }

}
