import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        char[] charArray = input.toCharArray();

        int firstPartSum = 0;
        for (int i = 0; i < 3; i++) {
            char value = charArray[i];
            int num = Character.getNumericValue(value);
            firstPartSum += num;
        }

        int secondPartSum = 0;
        for (int i = 3; i < 6; i++) {
            char value = charArray[i];
            int num = Character.getNumericValue(value);
            secondPartSum += num;
        }

        System.out.println(firstPartSum == secondPartSum ? "Lucky" : "Regular");
    }
}