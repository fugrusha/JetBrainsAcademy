import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
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

    public static void method2(LocalDateTime first, LocalDateTime second) {
        System.out.println(Math.abs(ChronoUnit.HOURS.between(first, second)));
    }

    public static void method3(LocalDateTime first, LocalDateTime second) {
        System.out.println(Duration.between(first, second).abs().toHours());
    }

    public static void method4(LocalDateTime first, LocalDateTime second) {
        System.out.println(first.until(second, ChronoUnit.HOURS));
    }
}