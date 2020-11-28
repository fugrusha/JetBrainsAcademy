import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

        String inputLine = scanner.nextLine();
        String phrase = scanner.nextLine();

        boolean result = true;
        Map<String, Integer> inputWords = getWordFrequencyMap(inputLine);

        for (String word : phrase.split(" ")) {
            if (inputWords.containsKey(word) && inputWords.get(word) > 0) {
                inputWords.put(word, inputWords.get(word) - 1);
            } else {
                result = false;
                break;
            }
        }

        System.out.println(result ? "You get money" : "You are busted");
    }

    private static Map<String, Integer> getWordFrequencyMap(String input) {
        String[] inputWords = input.split(" ");

        Map<String, Integer> map = new HashMap<>();
        for (String word : inputWords) {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }

        return map;
    }
}