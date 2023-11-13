import java.util.Random;

public class Produzent implements Runnable {

    private Lager lager;
    private Random random = new Random();

    public Produzent(Lager lager) {
        this.lager = lager;
    }

    @Override
    public void run() {
        while (lager.istOffen()) {
            try {
                lager.liefere(random.nextInt(10));
            } catch (Exception e) {}
        }
    }

}
