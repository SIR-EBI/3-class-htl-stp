import java.io.*;
import java.util.*;

public class Country implements Serializable {

    private String kurzzeichen, name, hauptstadt;
    private int flaeche, einwohnerzahl;

    public Country(String kurzzeichen, String name, String hauptstadt, int flaeche, int einwohnerzahl) {
        if (kurzzeichen.length() != 2 || flaeche <= 0 || einwohnerzahl < 0)
            throw new IllegalArgumentException("ERROR with constructor");

        this.kurzzeichen = kurzzeichen;
        this.name = name;
        this.hauptstadt = hauptstadt;
        this.flaeche = flaeche;
        this.einwohnerzahl = einwohnerzahl;
    }

    public static List<Country> readFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            List<Country> countryList = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] infoArray = line.split(";");

                    if (infoArray.length != 5)
                        throw new IllegalArgumentException("Info lenght != 5");

                    countryList.add(new Country(
                            infoArray[0],
                            infoArray[1],
                            infoArray[2],
                            Integer.parseInt(infoArray[3]),
                            Integer.parseInt(infoArray[4])
                    ));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return countryList;
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR with methode readFile");
        }
    }

    public static void saveCountryList (List<Country> countryList, String filename) {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filename))) {
            stream.writeObject(countryList);
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR with methode saveCountryList");
        }
    }

    public static List<Country> loadCountryList (String filename) {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Country>) stream.readObject();
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR with methode loadCountryList");
        }
    }

    @Override
    public String toString() {
        return "kurzzeichen=" + kurzzeichen +
                "; name=" + name +
                "; hauptstadt=" + hauptstadt +
                "; flaeche=" + flaeche +
                "; einwohnerzahl=" + einwohnerzahl;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return flaeche == country.flaeche && einwohnerzahl == country.einwohnerzahl && Objects.equals(kurzzeichen, country.kurzzeichen) && Objects.equals(name, country.name) && Objects.equals(hauptstadt, country.hauptstadt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kurzzeichen, name, hauptstadt, flaeche, einwohnerzahl);
    }

}
