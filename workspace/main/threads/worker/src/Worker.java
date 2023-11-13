public class Worker extends Thread {

    private String state = String.valueOf(StadiumEnum.values()[0]);

    @Override
    public void run() {
        for (int index = 1; index < StadiumEnum.values().length; index++) {
            state = String.valueOf(StadiumEnum.values()[index]);
            trySleep(StadiumEnum.values()[index].getDuration());
        }
    }

    public static void trySleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStatus() {
        return state;
    }

}
