package br.hikarikun92.pdfgenerator;

import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@Service
public class HtmlGenerator {
    private final ITemplateEngine templateEngine;

    public HtmlGenerator(ITemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String generateHtmlFromTemplate(String templateName, Map<String, Object> variables) {
        final Context context = new Context(Locale.getDefault(), variables);
        return templateEngine.process(templateName, context);
    }
}
