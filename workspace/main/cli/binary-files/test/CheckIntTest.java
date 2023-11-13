import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


public class CheckIntTest extends FileBasedTest {

    @Test
    void createsFileAsSpecified() throws IOException {
        CheckInt.createFile(TESTFILE_NAME, 1.2, 3.141592, 4, 7.8, 2.683);
        byte[] expected = {0, 0, 0, 5, 63, -13, 51, 51, 51, 51, 51, 51, 64, 9, 33, -6, -4, -117, 0, 122, 64, 16, 0, 0,
                0, 0, 0, 0, 64, 31, 51, 51, 51, 51, 51, 51, 64, 5, 118, -56, -76, 57, 88, 16};

        byte[] result = Files.readAllBytes(Paths.get(TESTFILE_NAME));

        assertArrayEquals(expected, result);
    }

    @Test
    void overwritesExistingFile() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(TESTFILE_NAME, "rw")) {
            raf.seek(999);
            raf.writeByte(1);
        }
        int expected = Integer.BYTES + Double.BYTES;

        CheckInt.createFile(TESTFILE_NAME, 1);

        assertEquals(expected, Files.size(Path.of(TESTFILE_NAME)));
    }

    @Test
    void appendsToValidFile() throws IOException {
        CheckInt.createFile(TESTFILE_NAME, 2, 3);
        double toAppend = Math.E;

        CheckInt.append(TESTFILE_NAME, toAppend);

        try (DataInputStream dis = new DataInputStream(new FileInputStream(TESTFILE_NAME))) {
            assertEquals(3, dis.readInt());
            dis.skipNBytes(2 * Double.BYTES);
            assertEquals(toAppend, dis.readDouble(), 1e-15);
        }
    }

    @Test
    void cannotAppendToInvalidFile() throws IOException {
        CheckInt.createFile(TESTFILE_NAME, 2, 3);
        try (RandomAccessFile raf = new RandomAccessFile(TESTFILE_NAME, "rw")) {
            raf.writeInt(42);
        }

        String errorMsg = assertThrows(IllegalArgumentException.class,
                () -> CheckInt.append(TESTFILE_NAME, 0)).getMessage();
        assertTrue(errorMsg.contains(TESTFILE_NAME));
    }

    @Test
    public void getsFileInfoWithoutWritePermission() throws IOException {
        CheckInt.createFile(TESTFILE_NAME, 0);
        setReadOnly();

        assertDoesNotThrow(() -> CheckInt.getFileInfo(TESTFILE_NAME));
    }


    @Test
    public void getsFileInfoAsSpecified() throws IOException {
        CheckInt.createFile(TESTFILE_NAME, 1.2, 3.141592, 4, 7.8, 2.683);

        String result = CheckInt.getFileInfo(TESTFILE_NAME);
        String[] lines = result.split("\n");

        assertTrue(lines[0].contains("5"));
        assertEquals("1,20 3,14 4,00 7,80 2,68", lines[1]);
    }

    @Test
    public void getsFileInfoAsSpecifiedForSpecialValues() throws IOException {
        CheckInt.createFile(TESTFILE_NAME, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NaN);
        String expected = "Infinity -Infinity NaN";

        String result = CheckInt.getFileInfo(TESTFILE_NAME);
        String[] lines = result.split("\n");

        assertEquals(expected, lines[1]);
    }

    @Test
    public void cannotGetFileInfoForInvalidFile() throws IOException {
        CheckInt.createFile(TESTFILE_NAME, 2, 3);
        try (RandomAccessFile raf = new RandomAccessFile(TESTFILE_NAME, "rw")) {
            raf.writeInt(42);
        }

        String result = CheckInt.getFileInfo(TESTFILE_NAME);

        assertTrue(result.contains("invalid"));
    }

    @Test
    public void checksForValidFileWithoutWritePermission() throws IOException {
        CheckInt.createFile(TESTFILE_NAME, 0);
        setReadOnly();

        assertTrue(CheckInt.isValidFile(TESTFILE_NAME));
    }

    @Test
    public void checksForValidFile() throws IOException {
        CheckInt.createFile(TESTFILE_NAME, 1.2, 3.141592, 4, 7.8, 2.683);

        assertTrue(CheckInt.isValidFile(TESTFILE_NAME));
    }

    @Test
    public void checksFileContainingSpecialValues() throws IOException {
        CheckInt.createFile(TESTFILE_NAME, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NaN);

        assertTrue(CheckInt.isValidFile(TESTFILE_NAME));
    }

    @Test
    public void checkingDetectsWrongCount() throws IOException {
        CheckInt.createFile(TESTFILE_NAME, 2, 3);
        try (RandomAccessFile raf = new RandomAccessFile(TESTFILE_NAME, "rw")) {
            raf.writeInt(42);
        }

        assertFalse(CheckInt.isValidFile(TESTFILE_NAME));
    }

    @Test
    public void checkingDetectsWrongFileSize() throws IOException {
        CheckInt.createFile(TESTFILE_NAME, 2, 3);
        try (RandomAccessFile raf = new RandomAccessFile(TESTFILE_NAME, "rw")) {
            raf.seek(raf.length());
            raf.writeByte(0);
        }

        assertFalse(CheckInt.isValidFile(TESTFILE_NAME));
    }
}