import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss");
    private final String description;
    private final LocalDateTime timestamp;

    public LogEntry(String description) {
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return timestamp.format(FORMATTER) + ": " + description;
    }

}
