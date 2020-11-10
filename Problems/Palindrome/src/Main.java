import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        String reversedString = new StringBuilder(line).reverse().toString();

        System.out.println(line.equals(reversedString) ? "yes" : "no");
    }
}