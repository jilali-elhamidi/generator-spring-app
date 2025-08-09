package com.example;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CodeGenerator {

    private final Handlebars handlebars;

    public CodeGenerator() {
        TemplateLoader loader = new ClassPathTemplateLoader("/templates", ".hbs");
        this.handlebars = new Handlebars(loader);

        // Helper standard
        this.handlebars.registerHelper("capitalize", StringHelpers.capitalize);

        // Helper logiques
        this.handlebars.registerHelper("eq", (Helper<Object>) (a, options) -> {
            Object b = options.param(0);
            return (a == null && b == null) || (a != null && a.equals(b));
        });

        this.handlebars.registerHelper("and", (Helper<Object>) (context, options) -> {
            boolean result = isTruthy(context);
            if (result) {
                for (Object param : options.params) {
                    if (!isTruthy(param)) {
                        result = false;
                        break;
                    }
                }
            }
            if (result) {
                return options.fn(options.context);
            } else {
                return options.inverse();
            }
        });

        this.handlebars.registerHelper("or", (Helper<Object>) (context, options) -> {
            if (isTruthy(context)) {
                return options.fn(options.context);
            }
            for (Object param : options.params) {
                if (isTruthy(param)) {
                    return options.fn(options.context);
                }
            }
            return options.inverse();
        });
    }

    private static boolean isTruthy(Object value) {
        if (value == null) return false;
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof Number) return ((Number) value).doubleValue() != 0;
        if (value instanceof String) return !((String) value).isEmpty();
        if (value instanceof List) return !((List) value).isEmpty();
        if (value instanceof Map) return !((Map) value).isEmpty();
        return true;
    }

    public void generateCode(String yamlFilePath, String outputDir) throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(yamlFilePath)) {
            Map<String, Object> data = yaml.load(inputStream);

            String moduleName = (String) data.get("module");
            List<Map<String, Object>> entities = (List<Map<String, Object>>) data.get("entities");

            if (entities == null) {
                System.err.println("❌ Aucune entité définie dans le YAML");
                return;
            }

            for (Map<String, Object> entity : entities) {
                String entityName = (String) entity.get("name");
                String entityNameLowercase = entityName.toLowerCase();
                List<Map<String, Object>> fields = (List<Map<String, Object>>) entity.get("fields");
                List<Map<String, Object>> relationships = (List<Map<String, Object>>) entity.get("relationships");

                Map<String, Object> context = new HashMap<>();
                context.put("module", moduleName);
                context.put("entityName", entityName);
                context.put("entityNameLowercase", entityNameLowercase);
                context.put("fields", fields);
                context.put("relationships", relationships);
                context.put("hasDate", hasType(fields, "Date"));

                generateJavaFile("EntityTemplate", outputDir, moduleName, entityName, "model", context);
                generateJavaFile("RepositoryTemplate", outputDir, moduleName, entityName, "repository", context);
                generateJavaFile("ServiceTemplate", outputDir, moduleName, entityName, "service", context);
                generateJavaFile("ControllerTemplate", outputDir, moduleName, entityName, "controller", context);
            }
        }
    }

    private boolean hasType(List<Map<String, Object>> fields, String type) {
        if (fields == null) return false;
        return fields.stream().anyMatch(field -> type.equals(field.get("type")));
    }

    private void generateJavaFile(String templateName, String outputDir, String moduleName,
                                  String entityName, String subPackage, Map<String, Object> context) throws IOException {
        Template template = handlebars.compile(templateName);
        String outputContent = template.apply(context);

        String fileName = switch (subPackage) {
            case "repository" -> entityName + "Repository";
            case "service" -> entityName + "Service";
            case "controller" -> entityName + "Controller";
            default -> entityName;
        };

        String relativePath = String.format("com/example/modules/%s/%s/%s.java",
                moduleName, subPackage, fileName);
        Path outputPath = Paths.get(outputDir, relativePath);

        Files.createDirectories(outputPath.getParent());
        try (FileWriter writer = new FileWriter(outputPath.toFile())) {
            writer.write(outputContent);
            System.out.println("✅ Généré : " + outputPath);
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Utilisation : java -jar code-generator.jar <chemin-config-yaml> <répertoire-de-sortie>");
            return;
        }
        new CodeGenerator().generateCode(args[0], args[1]);
    }
}