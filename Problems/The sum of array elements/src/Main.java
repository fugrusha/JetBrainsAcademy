import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int size = sc.nextInt();
        int sum = 0;

        for (int i = 0; i < size; i++) {
            sum += sc.nextInt();
        }

        System.out.println(sum);
    }
}