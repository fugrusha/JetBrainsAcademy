import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String codeWithComments = scanner.nextLine();

        String s = codeWithComments.replaceAll("/\\*(.|\\n)*?\\*/", "");
        String result = s.replaceAll("\\/\\/.*", "");
        
        System.out.println(result);
    }
}
