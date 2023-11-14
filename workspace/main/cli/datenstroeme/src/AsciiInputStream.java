import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AsciiInputStream extends FileInputStream {

    public AsciiInputStream(String name) throws FileNotFoundException {
        super(name);
    }

    public String readLine() throws IOException {
        StringBuilder builder = new StringBuilder();

        int number;
        while ((number = read()) != -1 && number != "\n".getBytes()[0]) {
            builder.append((char)number);
        }

        return builder.isEmpty() && number == -1 ? null : builder.toString().trim();
    }

}
