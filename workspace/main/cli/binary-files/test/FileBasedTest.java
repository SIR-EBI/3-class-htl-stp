import org.junit.jupiter.api.AfterEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;

public class FileBasedTest {

    public static final String TESTFILE_NAME = "test.dat";

    protected static void setReadOnly() throws IOException {
        Path path = Path.of(TESTFILE_NAME);
        if (isDosSystem(path))
            setDosReadOnly(path, true);
        else
            setPosixReadOnly(path);
    }

    private static boolean isDosSystem(Path path) throws IOException {
        return Files.getFileStore(path).supportsFileAttributeView(DosFileAttributeView.class);
    }

    private static void setPosixReadOnly(Path path) throws IOException {
        Files.getFileAttributeView(path, PosixFileAttributeView.class)
                .setPermissions(EnumSet.of(PosixFilePermission.OWNER_WRITE));
    }

    private static void setDosReadOnly(Path path, boolean readOnly) throws IOException {
        Files.getFileAttributeView(path, DosFileAttributeView.class)
                .setReadOnly(readOnly);
    }

    private void setWriteable(Path path) throws IOException {
        if (isDosSystem(path))
            setDosReadOnly(path, false);
        else
            setPosixWriteable(path);
    }

    private void setPosixWriteable(Path path) throws IOException {
        Files.getFileAttributeView(path, PosixFileAttributeView.class).setPermissions(
                EnumSet.of(PosixFilePermission.OTHERS_WRITE));
    }

    @AfterEach
    public void deleteTestFile() throws IOException {
        Path path = Paths.get(TESTFILE_NAME);
        if (!Files.exists(path))
            return;
        if (!Files.isWritable(path))
            setWriteable(path);
        Files.delete(path);
    }
}
