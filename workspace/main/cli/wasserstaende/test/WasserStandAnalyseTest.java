import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created: 15.11.2021
 *
 * @author Werner Gitschthaler (Werner)
 */
public class WasserStandAnalyseTest {

    private static final String START_DATE = "15.06.2009 00:00";
    private static final String MID_DATE   = "15.06.2009 00:05";
    private static final String END_DATE   = "15.06.2009 00:10";

    private final LocalDateTime first   = WasserStandAnalyse.parseDate(START_DATE);
    private final LocalDateTime between = WasserStandAnalyse.parseDate(MID_DATE);
    private final LocalDateTime last    = WasserStandAnalyse.parseDate(END_DATE);

    private File testFile;

    @TempDir
    File tempDir;

    @BeforeEach
    public void prepareFile() throws IOException {
        testFile = new File(tempDir,"wasserstand.txt");

        List<String> header = Arrays.asList("Stationsname:	Deutsch Haslau",
                "Stationsnummer:	209007",
                "# Parameter:	Wasserstand [cm]",
                "# Messwerte",
                "# DeutschHaslau_W_5a",
                "TSTAMP	Wasserstand [cm]",
                "");
        Files.write(testFile.toPath(), header);
    }

   /* @AfterEach
    public void deleteFile(){
        if(!testFile.delete()){
            System.out.printf("tmp file not deleted %s%n", testFile.getAbsolutePath());
        }
    }*/

    private void writeTestFile(List<String> lines) throws IOException {
        Files.write(testFile.toPath(), lines, StandardOpenOption.APPEND);
    }

    @Test
    public void readAllEntries() throws IOException {
        List<String> lines = Arrays.asList(
                START_DATE + "\t15.06.2009 00:05\t250\tU\tungeprueft",
                MID_DATE   + "\t15.06.2009 00:10\t230\tU\tungeprueft");
        writeTestFile(lines);
        WasserStandAnalyse cut = new WasserStandAnalyse();
        Map<LocalDateTime, Integer> result = cut.readFromFile(testFile.getAbsolutePath());
        assertEquals(2,result.size());
    }

    @Test
    public void maximumIsFoundAndCounted() throws IOException {
        List<String> lines = Arrays.asList(
                START_DATE + "\t15.06.2009 00:05\t250\tU\tungeprueft",
                MID_DATE   + "\t15.06.2009 00:10\t230\tU\tungeprueft",
                END_DATE   + "\t15.06.2009 00:15\t250\tU\tungeprueft");

        writeTestFile(lines);
        WasserStandAnalyse cut = new WasserStandAnalyse();
        Map<LocalDateTime, Integer> result = cut.readFromFile(testFile.getAbsolutePath());

        assertEquals(3,result.size());
        assertEquals(2,cut.highest(first,last).size());
        assertEquals(250,cut.highest(first,last).get(last));
        assertEquals(1,cut.highest(between,last).size());
        assertEquals(250,cut.highest(between,last).get(last));
    }

    @Test
    public void averageIsCorrect() throws IOException {
        List<String> lines = Arrays.asList(
                START_DATE + "\t15.06.2009 00:05\t250\tU\tungeprueft",
                MID_DATE   + "\t15.06.2009 00:10\t230\tU\tungeprueft",
                END_DATE   + "\t15.06.2009 00:15\t250\tU\tungeprueft");

        writeTestFile(lines);
        WasserStandAnalyse cut = new WasserStandAnalyse();
        Map<LocalDateTime, Integer> result = cut.readFromFile(testFile.getAbsolutePath());
        assertEquals(3,result.size());
        assertEquals(730d/3d,cut.average(first,last));
        assertEquals(480d/2d,cut.average(first,between));
        assertEquals(480d/2d,cut.average(between,last));
    }

    @Test
    public void averageIsZeroIfNothingFound() throws IOException {
        List<String> lines = Arrays.asList(
                START_DATE + "\t15.06.2009 00:05\t250\tU\tungeprueft",
                MID_DATE   + "\t15.06.2009 00:10\t230\tU\tungeprueft",
                END_DATE   + "\t15.06.2009 00:15\t250\tU\tungeprueft");

        writeTestFile(lines);
        WasserStandAnalyse cut = new WasserStandAnalyse();
        Map<LocalDateTime, Integer> result = cut.readFromFile(testFile.getAbsolutePath());
        assertEquals(3,result.size());
        assertEquals(0d,cut.average(last.plusDays(1),last.plusDays(2)));
    }

    @Test
    public void illegalLinesAreSkipped() throws IOException {
        List<String> lines = Arrays.asList(
                "15.06.2009 00:00	15.06.2009 00:05	220	U	ungeprueft",
                "15.06.2009 00:00	220	U	ungeprueft",
                "----",
                "15.06.2009 00:00	15/06/2009 00:05	220	U	ungeprueft",
                "15/06/2009 00:05	15.06.2009 00:10	220	U	ungeprueft");

        writeTestFile(lines);
        WasserStandAnalyse cut = new WasserStandAnalyse();
        Map<LocalDateTime, Integer> result = cut.readFromFile(testFile.getAbsolutePath());
        assertEquals(1,result.size());
    }
}
