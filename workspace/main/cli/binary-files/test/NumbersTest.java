import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class NumbersTest extends FileBasedTest {

    @Test
    public void groupsValuesByType() {
        Set<Integer> ints = new TreeSet<>(Set.of(-2, 0, 3));
        Set<Double> doubles = new TreeSet<>(Set.of(1.41, 2.718281828459045, 3.141592653589793));
        Map<String, Set<? extends Number>> expected = Map.of("Integer", ints, "Double", doubles);

        List<Number> input = List.of(3, 3.141592653589793, 0, -2, 2.718281828459045, 1.41);
        Map<String, Set<Number>> result = Numbers.groupByType(input);

        assertEquals(expected, result);
    }

    @Test
    public void getsContentWithoutWritePermission() throws IOException {
        Numbers.createFile(TESTFILE_NAME, new ArrayList<>());
        setReadOnly();

        assertDoesNotThrow(() -> Numbers.getContents(TESTFILE_NAME));
    }

    @Test
    public void getsContent() throws IOException {
        List<Number> expected = List.of(3, 3.141592653589793, 0, -2, 2.718281828459045, 1.41);
        Numbers.createFile(TESTFILE_NAME, expected);

        List<Number> result = Numbers.getContents(TESTFILE_NAME);

        assertEquals(expected, result);
    }

    @Test
    public void cannotGetContentOfInvalidFile() throws IOException {
        Numbers.createFile(TESTFILE_NAME, List.of(2, 3));
        try (RandomAccessFile raf = new RandomAccessFile(TESTFILE_NAME, "rw")) {
            raf.seek(raf.length());
            raf.write(1);
        }

        assertThrows(IllegalArgumentException.class, () -> Numbers.getContents(TESTFILE_NAME));
    }
}