import java.util.Scanner;

public class Main {

    public static double pow(double a, long n) {
        if (n == 0) {
            return 1;
        }

        double temp = pow(a, n / 2);

        if ( n % 2 == 1 ) {
            // Odd
            return a * temp * temp;
        } else {
            // Even
            return temp * temp;
        }
    }

    /* Do not change code below */
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final double a = Double.parseDouble(scanner.nextLine());
        final int n = Integer.parseInt(scanner.nextLine());
        System.out.println(pow(a, n));
    }
}