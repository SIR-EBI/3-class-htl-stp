import javax.crypto.spec.PSource;
import java.awt.geom.QuadCurve2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Numbers {

    private static List<String> contentList = new ArrayList<>();

    public static List<Number> getContents(String filename) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(filename));
        List<Number> returnList = new ArrayList<>();

        if (content.length != 0 && !contentList.contains(Arrays.toString(content))) {
            throw new IllegalArgumentException("ERROR");
        }

        int index = 0;

        while (index < content.length) {
            if (content[index] == (byte) 0) {
                returnList.add(ByteBuffer.wrap(Arrays.copyOfRange(content, index+1, index+5)).getInt());
                index += 5;
            } else if (content[index] == (byte) 1) {
                returnList.add(ByteBuffer.wrap(Arrays.copyOfRange(content, index+1, index+9)).getDouble());
                index += 9;
            }
        }
        return returnList;
    }
    public static Map<String, Set<Number>> groupByType(List<? extends Number> numbers) {
        Map<String, Set<Number>> returnMap = new TreeMap<>();
        Set numberSet;
        String typ = "";
        for (Number element : numbers) {
            if (element.getClass() == Integer.class) {
                typ = "Integer";
            }
            else if (element.getClass() == Double.class) {
                typ = "Double";
            }
            numberSet = returnMap.get(typ);
            if (numberSet == null) {
                numberSet = new TreeSet();
            }
            numberSet.add(element);
            returnMap.put(typ, numberSet);
        }
        return returnMap;
    }

    public static void createFile(String filename, List<Number> numberList) throws IOException {
        File file = new File(filename);
        file.delete();
        file.createNewFile();
        for (Number element : numberList) {
            append(filename, element);
        }
    }

    public static void append(String filename, Number toAppend) throws IOException {
        try {
            byte[] content = Files.readAllBytes(Paths.get(filename));

            RandomAccessFile raf = new RandomAccessFile(filename, "rw");

            raf.seek(raf.length());
            if (toAppend.getClass() == Double.class) {
                raf.writeByte(1);
                raf.writeDouble((double) toAppend);
            }
            else if (toAppend.getClass() == Integer.class) {
                raf.writeByte(0);
                raf.writeInt((int) toAppend);
            }
            raf.close();

            contentList.remove(Arrays.toString(content));
            contentList.add(Arrays.toString(Files.readAllBytes(Paths.get(filename))));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error " + filename);
        }
    }

}
