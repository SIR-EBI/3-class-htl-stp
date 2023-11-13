import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWatcher implements Runnable {

    public static void main(String[] args) {
        // Path directory = Path.of(args[0]);
        Path directory = Path.of("main/threads/file-watcher/resources/");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path path : stream) {
                new Thread(new FileWatcher(path, 1000)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private long timeStamp;
    private File file;
    private int intervalMs;

    public FileWatcher(Path path, int intervalMs) {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("ERROR path does not exists");
        }

        this.file = path.toFile();
        this.timeStamp = this.file.lastModified();
        this.intervalMs = intervalMs;
    }

    @Override
    public void run() {
        System.out.println(file + " watched");

        while (file.exists()) {
            if (file.lastModified() != timeStamp) {
                this.timeStamp = file.lastModified();
                logChange(this.file);
            }
            trySleep();
        }
        System.out.println("Can no longer find " + file.getName());
    }

    private void logChange(File file) {
        System.out.print(file + " changed");
    }

    private void trySleep() {
        try {
            Thread.sleep(intervalMs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
