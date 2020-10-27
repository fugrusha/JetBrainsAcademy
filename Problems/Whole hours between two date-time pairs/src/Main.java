import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime firstDateTime = LocalDateTime.parse(scanner.nextLine());
        LocalDateTime secondDateTime = LocalDateTime.parse(scanner.nextLine());

        long time1 = firstDateTime.toEpochSecond(ZoneOffset.UTC);
        long time2 = secondDateTime.toEpochSecond(ZoneOffset.UTC);

        long diff = Math.abs(time1 - time2);

        System.out.println(diff / 3600);
    }
}