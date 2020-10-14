import java.util.Scanner;

class Main {

    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int size = scanner.nextInt();
    int[] array = new int[size];

    for (int i = 0; i < size; i++) {
        array[i] = scanner.nextInt();
    }

    int n = scanner.nextInt();
    int m = scanner.nextInt();

    int indexN = -1;
    int indexM = -1;

    for (int i = 0; i < size; i++) {
        if (array[i] == n) {
        indexN = i;
        }
        if (array[i] == m) {
        indexM = i;
        }
    }

    int diff = indexM - indexN;
        if (diff == 1 || diff == -1) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
