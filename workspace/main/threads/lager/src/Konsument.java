import java.util.Random;

public class Konsument implements Runnable {

    private Lager lager;
    private Random random = new Random();

    public Konsument(Lager lager) {
        this.lager = lager;
    }

    @Override
    public void run() {
        while (lager.istOffen()) {
            try {
                lager.verkaufe(random.nextInt(10));
            } catch (Exception e) {}
        }
    }

}
