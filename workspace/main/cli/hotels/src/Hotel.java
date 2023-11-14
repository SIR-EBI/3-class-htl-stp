import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class Hotel {
    private String name;
    private String location;
    private int size;
    private boolean smoking;
    private int rate;
    private LocalDate date;
    private String owner;

    public Hotel(byte[] data, Map<String, Short> columns) {

        List<String> hotelInfoList = new ArrayList<>();
        int index = 0;

        for (Short lenght : columns.values()) {
            hotelInfoList.add(new String(Arrays.copyOfRange(data, index, index+lenght)).trim());
            index += lenght;
        }
        String[] date = hotelInfoList.get(5).split("/");

        this.name = hotelInfoList.get(0);
        this.location = hotelInfoList.get(1);
        this.size = Integer.parseInt(hotelInfoList.get(2));
        this.smoking = hotelInfoList.get(3).equals("Y");
        this.rate = (int) Float.parseFloat(hotelInfoList.get(4).replace("$", "")) * 100;
        this.date = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        this.owner = hotelInfoList.get(6);
    }

    public Hotel(String name, String location, int size, boolean smoking, int rate, LocalDate date, String owner) {
        this.name = name;
        this.location = location;
        this.size = size;
        this.smoking = smoking;
        this.rate = rate;
        this.date = date;
        this.owner = owner;
    }
    public static int getStartingOffset(String filename) throws IOException {
        return ByteBuffer.wrap(Arrays.copyOfRange(Files.readAllBytes(Paths.get(filename)),4,8)).getInt();
    }

    public static Map<String, Short> readColumns(String filename) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(filename));
        Map<String,Short> returnMap = new LinkedHashMap<>();

        int index = 8; int n;

        for (int counter = 0; counter < 7; counter++) {
            n = ByteBuffer.wrap(Arrays.copyOfRange(content,index+2,index+4)).getShort();
            returnMap.put(
                    new String(Arrays.copyOfRange(content,index+4,index+n+4)),
                    ByteBuffer.wrap(Arrays.copyOfRange(content,index+n+4,index+n+6)).getShort());
            index += n+4;
        }
        return returnMap;
    }
    public static Set<Hotel> readHotels(String filename) throws  IOException {
        byte[] content = Files.readAllBytes(Paths.get(filename));
        Set<Hotel> hotelSet = new TreeSet<>( (o1, o2) -> {
            if (!o1.location.equals(o2.location)) {
                return o1.location.compareTo(o2.location);
            }
            return o1.name.compareTo(o2.name);
        });
        int index = getStartingOffset(filename)+2;

        if (ByteBuffer.wrap(Arrays.copyOfRange(content, index-2, index)).getShort() != 0) {
            throw new IllegalArgumentException("ERROR " + filename);
        }

        while (index <= content.length) {
            if (ByteBuffer.wrap(Arrays.copyOfRange(content, index-2, index)).getShort() == 0) {
                hotelSet.add(new Hotel(Arrays.copyOfRange(content, index, index+159), readColumns(filename)));
            }
            index += 159 + 2;
        }
        return hotelSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hotel hotel = (Hotel) o;
        return size == hotel.size &&
                smoking == hotel.smoking &&
                rate == hotel.rate &&
                Objects.equals(name, hotel.name) &&
                Objects.equals(location, hotel.location) &&
                Objects.equals(date, hotel.date) &&
                Objects.equals(owner, hotel.owner);
    }

    @Override
    public String toString() {
        return String.format("name=%s, location=%s, size=%s, smoking=%s, rate=%s, date=%s, owner=%s", name, location, size, smoking, rate, date, owner);
    }

}
