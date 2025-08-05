package com.example;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.yaml.snakeyaml.Yaml;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

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

public class CodeGenerator {

    private final Handlebars handlebars;

    public CodeGenerator() {
        TemplateLoader loader = new ClassPathTemplateLoader("/templates", ".hbs");
        this.handlebars = new Handlebars(loader);

        // Helpers standards
        this.handlebars.registerHelper("capitalize", StringHelpers.capitalize);

        // Helper de comparaison
        this.handlebars.registerHelper("eq", (Helper<Object>) (a, options) -> {
            Object b = options.param(0);
            return a != null && a.toString().equals(b != null ? b.toString() : null);
        });

        // Helpers relation
        this.handlebars.registerHelper("isOneToMany", (context, options) -> {
            if (context instanceof Map) {
                Object typeObj = ((Map<?, ?>) context).get("type");
                return typeObj instanceof String && ((String) typeObj).startsWith("List<");
            }
            return false;
        });

        this.handlebars.registerHelper("isManyToOne", (context, options) -> {
            if (context instanceof Map) {
                Object typeObj = ((Map<?, ?>) context).get("type");
                return typeObj instanceof String && !((String) typeObj).startsWith("List<");
            }
            return false;
        });

        // ✅ Helper pour générer les propriétés à ignorer dans @JsonIgnoreProperties
        this.handlebars.registerHelper("jsonIgnorePropsFor", (Helper<Object>) (entityNameObj, options) -> {
            String entityName = entityNameObj.toString();
            // Exemples personnalisés — ajoute ici tes règles
            switch (entityName) {
                case "Category":
                    return "\"products\"";
                case "User":
                    return "\"orders\"";
                case "Product":
                    return "\"category\"";
                case "Order":
                    return "\"user\", \"orderItems\"";
                default:
                    return "\"unknown\"";
            }
        });
    }

    public void generateCode(String yamlFilePath, String outputDir) throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(yamlFilePath)) {
            Map<String, Object> data = yaml.load(inputStream);

            String moduleName = (String) data.get("module");
            List<Map<String, Object>> entities = (List<Map<String, Object>>) data.get("entities");

            for (Map<String, Object> entity : entities) {
                String entityName = (String) entity.get("name");
                String entityNameLowercase = entityName.toLowerCase();
                List<Map<String, String>> fields = (List<Map<String, String>>) entity.get("fields");
                List<Map<String, Object>> relationships = (List<Map<String, Object>>) entity.get("relationships");

                Map<String, Object> context = new HashMap<>();
                context.put("module", moduleName);
                context.put("entityName", entityName);
                context.put("entityNameLowercase", entityNameLowercase);
                context.put("fields", fields);
                context.put("relationships", relationships);
                context.put("hasListRelationship", relationships != null &&
                        relationships.stream().anyMatch(rel -> ((String) rel.get("type")).startsWith("List<")));
                context.put("hasDate", hasType(fields, "Date"));

                generateJavaFile("EntityTemplate", outputDir, moduleName, entityName, "model", context);
                generateJavaFile("RepositoryTemplate", outputDir, moduleName, entityName, "repository", context);
                generateJavaFile("ServiceTemplate", outputDir, moduleName, entityName, "service", context);
                generateJavaFile("ControllerTemplate", outputDir, moduleName, entityName, "controller", context);
            }
        }
    }

    private boolean hasType(List<Map<String, String>> fields, String type) {
        if (fields == null) return false;
        return fields.stream().anyMatch(field -> type.equals(field.get("type")));
    }

    private void generateJavaFile(String templateName, String outputDir, String moduleName, String entityName,
                                  String subPackage, Map<String, Object> context) throws IOException {
        Template template = handlebars.compile(templateName);
        String outputContent = template.apply(context);

        String fileName = switch (subPackage) {
            case "repository" -> entityName + "Repository";
            case "service" -> entityName + "Service";
            case "controller" -> entityName + "Controller";
            default -> entityName;
        };

        String relativePath = String.format("com/example/modules/%s/%s/%s.java", moduleName, subPackage, fileName);
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

        String yamlPath = args[0];
        String outputDir = args[1];

        new CodeGenerator().generateCode(yamlPath, outputDir);
    }
}
