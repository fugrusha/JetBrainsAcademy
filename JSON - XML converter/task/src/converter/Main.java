package converter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Path path = Paths.get("test.txt");
        String input = readFileToString(path);

        if (input.startsWith("{")) {
            System.out.println(convertToXML(input));
        } else if (input.startsWith("<")) {
            System.out.println(convertToJSON(input));
        }
    }

    private static String readFileToString(Path path) {
        try {
            return Files.readString(path).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static String convertToXML(String input) {
        input = input.substring(1, input.length() - 1); // remove brackets
        input = input.replaceAll("\"", "");
        input = input.replaceAll("\\s{2,}", "");

        // text before ":{"
        Matcher keyMather = Pattern.compile("\\w+\\s*(?=:\\s*\\{)").matcher(input);
        // groups of text after "@"
        Matcher attributeMather = Pattern.compile("(?<=@)\\w+\\s*:\\s*\\w+").matcher(input);
        // group of text after "#"
        Matcher anchorMatcher = Pattern.compile("(?<=#)(.*)(?=})").matcher(input);

        String key = "";
        String value = "";
        String anchorValue = "";
        Map<String, String> attributes = new HashMap<>();
        if (keyMather.find()) {
            key = keyMather.group().trim();

            while (attributeMather.find()) {
                String group = attributeMather.group();
                String[] strings = group.split(":");
                attributes.put(strings[0].trim(), strings[1].trim());
            }

            if (anchorMatcher.find()) {
                anchorValue = anchorMatcher.group().split(":")[1].trim();
            }
        } else {
            String[] parts = input.split(":");
            key = parts[0].trim();
            value = parts[1].trim();
        }

        StringBuilder sb = new StringBuilder();

        if (attributes.isEmpty()) {
            if ("null".equals(value)) {
                sb.append("<").append(key).append("/>");
            } else {
                sb.append("<").append(key).append(">")
                        .append(value)
                        .append("</").append(key).append(">");
            }
        } else {
            sb.append("<").append(key).append(" ");

            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                sb.append(entry.getKey())
                        .append(" = \"")
                        .append(entry.getValue())
                        .append("\" ");
            }

            if ("null".equals(anchorValue)) {
                sb.append("/>");
            } else {
                sb.append(">").append(anchorValue).append("</").append(key).append(">");
            }
        }

        return sb.toString();
    }

    private static String convertToJSON(String input) {
        Matcher tagMatcher = Pattern.compile("(?<=<)\\w+").matcher(input);
        Matcher attrMatcher = Pattern.compile("\\s\\w+\\s*=\\s*\"\\w+").matcher(input);

        String key = "";
        if (tagMatcher.find()) {
            key = tagMatcher.group();
        }

        // text between > and <
        Matcher valueMatcher = Pattern.compile("(?<=>)(.*)(?=<)").matcher(input);

        String value = "";
        if (valueMatcher.find()) {
            value = valueMatcher.group();
        } else {
            value = "null";
        }

        Map<String, String> attributes = new HashMap<>();
        while (attrMatcher.find()) {
            String group = attrMatcher.group().replaceAll("\"", "");
            String[] pairs = group.split("=");
            attributes.put(pairs[0].trim(), pairs[1].trim());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\"").append(key).append("\": ");

        if (attributes.isEmpty()) {
            appendValue(value, sb);
        } else {
            sb.append("{");
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                sb.append("\"@").append(entry.getKey()).append("\" : \"")
                        .append(entry.getValue()).append("\",");
            }

            sb.append("\"#").append(key).append("\" : ");
            appendValue(value, sb).append("}");
        }

        return sb.toString();
    }

    private static StringBuilder appendValue(String value, StringBuilder sb) {
        if ("null".equals(value)) {
            return sb.append(value).append(" }");
        } else {
            return sb.append("\"").append(value).append("\" }");
        }
    }
}