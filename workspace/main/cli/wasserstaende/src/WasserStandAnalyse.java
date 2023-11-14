import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WasserStandAnalyse {

    private Map<LocalDateTime, Integer> levels = new TreeMap<>();

    public Map<LocalDateTime, Integer> readFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                String[] lineArray = line.split("\t");
                if (lineArray.length != 5) {
                    continue;
                }

                levels.put(parseDate(lineArray[0]), Integer.parseInt(lineArray[2]));
            } catch (Exception e) {}
        }
        reader.close();
        return levels;
    }

    public static LocalDateTime parseDate(String str) {
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public Map<LocalDateTime, Integer> highest(LocalDateTime from, LocalDateTime to) {
        Map<LocalDateTime, Integer> map = new TreeMap<>();
        int highest = 0;

        while (!from.isAfter(to)) {
            if (levels.get(from) > highest) {
                highest = levels.get(from);
            }

            map.put(from ,levels.get(from));
            from = from.plusMinutes(5);
        }

        Map<LocalDateTime, Integer> returnMap = new TreeMap<>();

        for (Map.Entry<LocalDateTime, Integer> entry : map.entrySet()) {
            if (entry.getValue() == highest) {
                returnMap.put(entry.getKey(), entry.getValue());
            }
        }
        return returnMap;
    }

    public double average(LocalDateTime from, LocalDateTime to) {
        if (levels.get(to) == null) {
            return 0;
        }

        double average = 0;
        int counter = 0;

        while (!from.isAfter(to)) {
            average += levels.get(from);
            counter++;
            from = from.plusMinutes(5);
        }
        return average / counter;
    }

}
