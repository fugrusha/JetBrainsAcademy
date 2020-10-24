import java.util.*;
import java.util.regex.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String part = scanner.nextLine();
        String line = scanner.nextLine();

        Pattern pattern = Pattern.compile("\\B" + part + "\\B", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        
        boolean contains = matcher.find();
        
        System.out.println(contains ? "YES" : "NO");
    }
}
