import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] range = scanner.nextLine().split("\\s+");
        int from = Integer.parseInt(range[0]);
        int to = Integer.parseInt(range[1]);
        int size = Integer.parseInt(scanner.nextLine());

        SortedMap<Integer, String> map = new TreeMap<>();

        for (int i = 0; i < size; i++) {
            String[] parts = scanner.nextLine().split("\\s+");
            map.put(Integer.parseInt(parts[0]), parts[1]);
        }

        SortedMap<Integer, String> subMap = map.subMap(from, to + 1);

        subMap.forEach((k, v) -> System.out.println(k + " " + v));
    }
}