import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Dictionary {

    private final Map<Integer, Set<String>> wordsGroupedByLength = new HashMap<>();
    private final Map<Integer, Set<String>> wordsGroupedByDistinctCharCount = new HashMap<>();

    public Dictionary(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("\t") || line.matches("[0-9]+"))
                    continue;

                String[] pair = line.split("/");

                Set<String> stringSet;
                int lenght = pair[0].length();

                if (wordsGroupedByLength.containsKey(lenght))
                    stringSet = wordsGroupedByLength.get(lenght);
                else
                    stringSet = new TreeSet<>();
                stringSet.add(pair[0]);
                wordsGroupedByLength.put(lenght, stringSet);

                Set<Character> charSet = new TreeSet<>();
                for (char element : pair[0].toLowerCase().toCharArray())
                    charSet.add(element);
                lenght = charSet.size();

                if (wordsGroupedByDistinctCharCount.containsKey(lenght))
                    stringSet = wordsGroupedByDistinctCharCount.get(lenght);
                else
                    stringSet = new TreeSet<>();
                stringSet.add(pair[0]);
                wordsGroupedByDistinctCharCount.put(lenght, stringSet);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR in constructor");
        }
    }

    public Set<String> getPermutations(String word) {
        Set<String> returnSet = new TreeSet<>();

        if (word.isEmpty() || wordsGroupedByLength.get(word.length()) == null)
            return returnSet;

        char[] charArrayOfGivenWord = word.toLowerCase().toCharArray();
        Arrays.sort(charArrayOfGivenWord);

        Set<String> wordSet = wordsGroupedByLength.get(word.length());
        for (String element : wordSet) {
            char[] charArrayOfWordSetElement = element.toLowerCase().toCharArray();
            Arrays.sort(charArrayOfWordSetElement);

            if (Arrays.equals(charArrayOfGivenWord, charArrayOfWordSetElement))
                returnSet.add(element);
        }
        return returnSet;
    }

    public Set<String> getWordsWithSameLetters(String word) {
        Set<String> returnSet = new TreeSet<>();

        if (word.isEmpty())
            return returnSet;

        Set<String> stringListOfGivenWord = new TreeSet<>(List.of(word.toLowerCase().split("")));

        Set<Character> charSet = new TreeSet<>();
        for (char element : word.toCharArray())
            charSet.add(element);

        if (wordsGroupedByDistinctCharCount.get(charSet.size()) == null)
            return returnSet;

        Set<String> wordSet = wordsGroupedByDistinctCharCount.get(charSet.size());
        for (String element : wordSet) {
            Set<String> stringListOfWordSetElement = new TreeSet<>(List.of(element.toLowerCase().split("")));
            if (stringListOfGivenWord.equals(stringListOfWordSetElement))
                returnSet.add(element);
        }
        return returnSet;
    }

}
