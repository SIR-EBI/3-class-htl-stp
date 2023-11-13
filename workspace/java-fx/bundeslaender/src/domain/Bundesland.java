package domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Bundesland {

    private String wappenName;
    private String bundesland;
    private StringProperty hauptstadt = new SimpleStringProperty();
    private String landeshauptmann;
    private Map<String, Integer> sitzverteilung;
    private int einwohner;
    private double flaeche;

    public Bundesland(String[] infoArray) {
        if (infoArray.length != 13 || !infoArray[0].contains(".png") || infoArray[1].isEmpty()) {
            throw new IllegalArgumentException("ERROR");
        }

        for (int index = 4; index < 9; index++) {
            if (!infoArray[index].matches("[0-9]+") && !infoArray[index].isEmpty()) {
                throw new IllegalArgumentException("ERROR");
            }
        }
        if (!infoArray[10].matches("[0-9]+") && !infoArray[10].isEmpty()) {
            throw new IllegalArgumentException("ERROR");
        }

        this.wappenName = infoArray[0];
        this.bundesland = infoArray[1];
        this.hauptstadt.set(infoArray[2]);
        this.landeshauptmann = infoArray[3];
        this.sitzverteilung = new TreeMap<>();
        this.einwohner = Integer.parseInt(infoArray[11]);
        this.flaeche = Double.parseDouble(infoArray[12]);

        String[] nameArray = new String[] {"ÖVP","SPÖ","FPÖ","Grüne","NEOS"};
        for (int index = 4; index < 9; index++) {
            if (!infoArray[index].isEmpty()) {
                sitzverteilung.put(nameArray[index-4], Integer.parseInt(infoArray[index]));
            }
        }
        if (!infoArray[9].isEmpty()) {
            String[] split = infoArray[9].split(" ");
            if (Integer.parseInt(split[1]) < 0) {
                throw new IllegalArgumentException("ERROR");
            }
            sitzverteilung.put(split[0], Integer.parseInt(split[1]));
        }
        if (!infoArray[10].isEmpty()) {
            sitzverteilung.put("Wilde", Integer.parseInt(infoArray[10]));
        }
    }


    public static List<Bundesland> readFile(InputStream stream) {
        List<Bundesland> returnList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    returnList.add(new Bundesland(line.split(",")));
                } catch (Exception e) { continue; }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR | Class: Bundesland | Methode: readFile(InputStream stream)");
        }
        return returnList;
    }

    public String getWappenName() {
        return wappenName;
    }
    public String getName() {
        return bundesland;
    }
    public String getHauptstadt() {
        return hauptstadt.getValue();
    }
    public String getLandeshauptmann() {
        return landeshauptmann;
    }
    public Map<String, Integer> getSitze() {
        return sitzverteilung;
    }
    public int getEinwohner() {
        return einwohner;
    }
    public double getFlaeche() {
        return flaeche;
    }
    public void setHauptstadt(String hauptstadt) {
        this.hauptstadt.set(hauptstadt);
    }

}
