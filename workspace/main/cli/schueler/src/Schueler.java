import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Schueler implements Serializable {

    private String name, vorname;
    private transient int kennung;
    private transient long sozialversicherungsnummer;

    public Schueler(String name, String vorname, int kennung, long sozialversicherungsnummer) {
        if (!testNummerKennunng(kennung, sozialversicherungsnummer))
            throw new IllegalArgumentException("ERROR with Constructor");
        this.name = name;
        this.vorname = vorname;
        this.kennung = kennung;
        this.sozialversicherungsnummer = sozialversicherungsnummer;
    }

    private static boolean testNummerKennunng(int kennung, long sozialversicherungsnummer) {
        if ((""+kennung).length() != 8 || (""+sozialversicherungsnummer).length() != 10)
            return false;

        int yearKennung = Integer.parseInt((""+kennung).substring(0,4));
        int yearNow = LocalDate.now().getYear();
        if (yearKennung < yearNow-8 || yearKennung > yearNow)
            return false;

        try {
            int[] intArray = Arrays.stream((""+sozialversicherungsnummer).split(""))
                    .map(String::trim).mapToInt(Integer::parseInt).toArray();

            if ((intArray[0]*3 + intArray[1]*7 + intArray[2]*9 + intArray[4]*5 + intArray[5]*8 +
                    intArray[6]*4 + intArray[7]*2 + intArray[8]*1 + intArray[9]*6) % 11 != intArray[3])
                return false;

            new SimpleDateFormat("ddMMyyyy").parse((""+sozialversicherungsnummer).substring(4,10));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String code(String text, int key) {
        text = text.toUpperCase();
        text = new StringBuilder(text).reverse().toString();
        char aktuell;
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < (text.length()); index++) {
            aktuell = text.charAt(index);
            while (aktuell + key > 90)
                aktuell -= 26;
            while (aktuell + key < 65)
                aktuell += 26;
            builder.append((char) (aktuell + key));
        }

        return builder.toString();
    }
    private static String decode(String text, int key) {
        return code(text, key);
    }

    private static String numberToText(long number) {
        String[] stringArray = {"null","eins","zwei","drei","vier","fuenf","sechs","sieben","acht","neun"};
        StringBuilder builder = new StringBuilder();

        for (String element : (""+number).split(""))
            builder.append(stringArray[Integer.parseInt(element)] + " ");
        return builder.toString().trim();
    }
    private static long textToNumber(String text) {
        String[] charArray = {"null","eins","zwei","drei","vier","fuenf","sechs","sieben","acht","neun"};
        List<Character> list = new ArrayList(Arrays.asList(charArray));
        StringBuilder builder = new StringBuilder();

        for (String element : text.split(" "))
            builder.append(list.indexOf(element));

        return Long.parseLong(builder.toString());
    }
    public static void saveSchueler(Schueler schueler, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
             DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(schueler);
            dos.writeUTF(code(numberToText(schueler.getKennung()), 4));
            dos.writeUTF(code(numberToText(schueler.getSozialversicherungsnummer()), 4));
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR with methode saveSchueler");
        }
    }
    public static Schueler loadSchueler (String filename) {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(filename));
            DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            String sozialversicherungsnummer = dis.readUTF();
            String kennung = dis.readUTF();

            Schueler schueler = (Schueler) stream.readObject();

            schueler.setSozialversicherungsnummer(textToNumber(code(sozialversicherungsnummer, 4)));
            schueler.setKennung(Integer.parseInt("" + textToNumber(code(kennung, 4))));

            return schueler;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("ERROR with methode loadSchueler");
        }
    }

    public static void main(String[] args) {
        Schueler schueler = new Schueler("Fabian", "Eberhardt", 20190614, 1237010180);

        saveSchueler(schueler, "main/cli/schueler/resources/test.txt");

        System.out.println(loadSchueler("main/cli/schueler/resources/test.txt"));
    }

    public int getKennung() {
        return kennung;
    }
    public void setKennung(int kennung) {
        this.kennung = kennung;
    }
    public long getSozialversicherungsnummer() {
        return sozialversicherungsnummer;
    }
    public void setSozialversicherungsnummer(long sozialversicherungsnummer) {
        this.sozialversicherungsnummer = sozialversicherungsnummer;
    }

}









