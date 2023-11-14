public class Movie extends Playable {

    private final String director;

    public Movie(String title, int rating, String director) {
        super(title, rating);
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

    @Override
    public void playItem() {
        System.out.println("playing movie" + this);
    }

}
