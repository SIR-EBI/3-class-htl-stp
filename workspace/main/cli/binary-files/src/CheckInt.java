import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckInt {

    private static List<String> contentList = new ArrayList<>();

    public static void createFile(String filename, double... values) throws IOException {
        File file = new File(filename);
        file.delete();
        file.createNewFile();

        for (double element : values) {
            append(filename, element);
        }
    }
    public static boolean isValidFile(String filename) throws IOException {
        return contentList.contains(Arrays.toString(Files.readAllBytes(Paths.get(filename))));
    }
    public static String getFileInfo(String filename) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(filename));

        if (content.length == 0 || !isValidFile(filename)) {
            return "invalid";
        }

        StringBuilder builder = new StringBuilder(
                "Saved values: " +
                new BigInteger(Arrays.copyOfRange(content,0,4)).intValue() + "\n"
        );
        for (int counter = 0; counter < content.length-4; counter+= 8) {
            builder.append(String.format("%.2f ", ByteBuffer.wrap(Arrays.copyOfRange(content, counter + 4, counter + 4 + 8)).getDouble()));
        }
        return builder.toString().trim();
    }
    public static void append(String filename, double toAppend) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(filename));
        if (content.length != 0 && !isValidFile(filename)) {
            throw new IllegalArgumentException("ERROR " + filename);
        }

        RandomAccessFile raf = new RandomAccessFile(filename, "rw");

        raf.writeInt(new BigInteger(Arrays.copyOfRange(content,0,4)).intValue() + 1);
        raf.seek(raf.length());
        raf.writeDouble(toAppend);
        raf.close();

        contentList.remove(Arrays.toString(content));
        contentList.add(Arrays.toString(Files.readAllBytes(Paths.get(filename))));
    }

}
