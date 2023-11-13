public class LagerApp {

    public static void main(String[] args) {
        Lager lager = new Lager();

        new Thread(new Konsument(lager)).start();
        new Thread(new Produzent(lager)).start();
    }

}
