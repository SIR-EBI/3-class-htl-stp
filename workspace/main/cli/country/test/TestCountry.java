import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCountry {
    @Test
    public void createCountry() {
        Country country = new Country("AU", "Australia", "Canberra", 7686850, 19731984);
        assertEquals(country.toString(), "kurzzeichen=AU; name=Australia; hauptstadt=Canberra; flaeche=7686850; einwohnerzahl=19731984");
    }
    @Test
    public void readCountries() {
        List<Country> countryList = Country.readFile("main/cli/country/resources/countries.csv");
        assertEquals(countryList.size(), 5);
    }
    @Test
    public void saveLoadCountries() throws IOException {
        List<Country> countryList = Country.readFile("main/cli/country/resources/countries.csv");

        File tempfFile = File.createTempFile("tmpFile", ".tmp");
        tempfFile.deleteOnExit();

        Country.saveCountryList(countryList, tempfFile.getAbsolutePath());
        List<Country> loadedCountryList = Country.loadCountryList(tempfFile.getAbsolutePath());

        assertEquals(loadedCountryList.size(), 5);
        assertTrue(countryList.equals(loadedCountryList));
    }
}
