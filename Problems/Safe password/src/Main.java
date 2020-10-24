import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{12,}$";

        String password = scanner.nextLine();
        System.out.println(password.matches(regex) ? "YES" : "NO");
    }
}