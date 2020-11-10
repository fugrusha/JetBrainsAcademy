import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String input = new Scanner(System.in).nextLine();

        String alphabet = "abcdefghijklmopqrstuvwxyz";

        System.out.println(alphabet.contains(input));
    }
}