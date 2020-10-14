import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int size = sc.nextInt();
        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = sc.nextInt();
        }

        int len = 1;
        int maxLen = 1;

        for (int i = 1; i < size; i++) {
            if (arr[i] > arr[i - 1]) {
                len++;
            } else {
                len = 1;
            }
            if (len > maxLen) {
                maxLen = len;
            }
        }

        System.out.println(maxLen);
    }
}