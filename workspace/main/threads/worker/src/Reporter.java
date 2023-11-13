import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Reporter extends Thread {

    public static void main(String[] args) {
        Reporter reporter = new Reporter();
        reporter.start();
    }

    public void run() {
        List<Worker> workerList = new ArrayList<>();
        Worker worker;

        for (int counter = 0; counter < 100; counter++) {
            worker = new Worker();
            worker.start();
            workerList.add(worker);
        }

        System.out.println("|St 1|St 2|St 3|Done|");

        Map<String, Integer> statusMap;

        int endcount = 0;
        while (endcount != workerList.size()) {
            statusMap = new LinkedHashMap<>();
            endcount = 0;

            for (int i = 1; i < StadiumEnum.values().length; i++) {
                statusMap.put(String.valueOf(StadiumEnum.values()[i]), 0);
            }

            for (Worker workerElement : workerList) {
                statusMap.put(workerElement.getStatus(), statusMap.get(workerElement.getStatus()) + 1);

                if (workerElement.getStatus().equals(StadiumEnum.values()[StadiumEnum.values().length-1].toString()))
                    endcount++;
            }

            System.out.print("|");
            for (int number : statusMap.values())
                System.out.print(String.format("%4d", number) + "|");
            System.out.println();

            trySleep(1000);
        }
    }

    public static void trySleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
