public class Song extends Playable {

    private final String singer;

    public Song(String title, int rating, String singer) {
        super(title, rating);
        this.singer = singer;
    }

    public String getSinger() {
        return singer;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + getTitle() + '\'' +
                ", singer='" + singer + '\'' +
                ", played=" + getPlayed() +
                '}';
    }

    @Override
    void playItem() {
        System.out.println("playing song" + this);
    }

}
