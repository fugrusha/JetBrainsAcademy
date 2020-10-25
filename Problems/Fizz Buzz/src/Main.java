import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int start = scanner.nextInt();
        int finish = scanner.nextInt();

        for (int num = start; num <= finish; num++) {
            int divBy3 = num % 3;
            int divBy5 = num % 5;

            if (divBy3 == 0 && divBy5 == 0) {
                System.out.println("FizzBuzz");
            } else if (divBy3 == 0) {
                System.out.println("Fizz");
            } else if (divBy5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(num);
            }
        }
    }
}