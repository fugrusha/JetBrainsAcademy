package converter;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        if (input.startsWith("{")) {
            System.out.println(convertToXML(input));
        } else if (input.startsWith("<")) {
            System.out.println(convertToJSON(input));
        }
    }

    private static String convertToXML(String input) {
        input = input.substring(1, input.length() - 1); // remove brackets
        input = input.replaceAll("\"", "");

        String[] parts = input.split(":");

        parts[0] = parts[0].trim();
        parts[1] = parts[1].trim();

        if ("null".equals(parts[1])) {
            return "<" + parts[0] + "/>";
        } else {
            return "<" + parts[0] + ">" + parts[1] + "</" + parts[0] + ">";
        }
    }

    private static String convertToJSON(String input) {
        Pattern tagPattern = Pattern.compile("(<.*?>|<\\/.*?>)");
        Matcher matcher = tagPattern.matcher(input);

        String key = "";
        if (matcher.find()) {
            String group = matcher.group(1).replaceAll("/", "");
            key = group.substring(1, group.length() - 1);
        }

        String value = matcher.replaceAll("");

        if (value.trim().isEmpty()) {
             return "{\"" + key + "\": " + " null }";
        } else {
            return "{\"" + key + "\": \"" + value +"\"}";
        }
    }

}