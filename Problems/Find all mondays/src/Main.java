import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        int month = scanner.nextInt();

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, startDate.lengthOfMonth());

        LocalDate monday = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        while (monday.isBefore(endDate)) {
            System.out.println(monday);

            // Set up the next loop.
            monday = monday.plusWeeks(1);
        }
    }
}
