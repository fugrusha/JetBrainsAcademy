import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String text = scanner.nextLine();

        Pattern pattern = Pattern.compile("((?<=(password):?)\\s*\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            matcher.reset();
            while (matcher.find()) {
                System.out.println(matcher.group().trim());
            }
        } else {
            System.out.println("No passwords found.");
        }
    }
}