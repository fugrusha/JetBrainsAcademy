import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int size = scanner.nextInt();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }

        int number = scanner.nextInt();
        boolean returnValue = false;

        for (int num : array) {
            if (num == number) {
                returnValue = true;
                break;
            }
        }

        System.out.println(returnValue);
    }
}