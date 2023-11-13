public class Lager {

    private int bestand = 100;
    private int transaktionen = 0;
    private final int MAX = 20;

    public synchronized void liefere(int anzahl) {
        bestand += anzahl;
        transaktionen++;
        System.out.println(Thread.currentThread().getName() + " liefert " + anzahl + ", neuer Bestand: " + bestand);
    }

    public synchronized void verkaufe(int anzahl) {
        bestand -= anzahl;
        transaktionen++;
        System.out.println(Thread.currentThread().getName() + " kauft " + anzahl + ", neuer Bestand: " + bestand);
    }

    boolean istOffen() {
        if (transaktionen < MAX) {
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + ", es ist geschlossen!");
            return false;
        }
    }

}
