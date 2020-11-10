import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        char[] charArray = input.toCharArray();

        StringBuilder sb = new StringBuilder();
        char current = charArray[0];
        int count = 1;

        for (int i = 1; i < charArray.length; i++) {
            char next = charArray[i];
            if (current == next) {
                count++;
            } else {
                if (count < 2) {
                    sb.append(current).append(count);
                } else {
                    sb.append(current).append(count);
                    count = 1;
                }
            }
            current = next;
        }

        sb.append(current).append(count);

        System.out.println(sb.toString());
    }
}
