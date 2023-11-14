import java.util.Objects;

abstract class Playable implements Comparable<Playable> {

    private final String title;
    private final int rating;
    private int played = 0;

    public Playable(String title, int rating) {
        this.title = title;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public int getPlayed() {
        return played;
    }

    public void play(){
        played++;
        playItem();
    }

    abstract void playItem();

    public int getRating() {
        return rating;
    }

    @Override
    public int compareTo(Playable o) {
        return this.compareTo(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playable playable = (Playable) o;
        return Objects.equals(title, playable.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

}
