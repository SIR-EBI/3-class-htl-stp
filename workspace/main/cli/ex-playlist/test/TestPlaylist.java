import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlaylist {

    private Playlist<Song> songPlaylist = new Playlist<>();
    private Playlist<Movie> moviePlaylist = new Playlist<>();

    // our song "lib"
    private final Song song1_rating5 = new Song("Fortunate son", 5, "CCR");
    private final Song song2_rating4 = new Song("Come Tomorrow", 4, "Towns van Zandt");
    private final Song song3_rating5 = new Song("1979", 5, "Smashing Pumpkins");
    private final Song song4_rating3 = new Song("La femme d'argent", 3, "Air");

    // our movie "lib"
    private final Movie movie1 = new Movie("Terminator 2", 5, "James Cameron");
    private final Movie movie2 = new Movie("El Mariachi", 4, "Robert Rodriguez");
    private final Movie movie3 = new Movie("Pulp Fiction", 5, "Quentin Tarantino");

    @BeforeEach
    public void fillPlayList(){
        // songs
        songPlaylist.addItem(song1_rating5);
        songPlaylist.addItem(song2_rating4);
        songPlaylist.addItem(song3_rating5);
        songPlaylist.addItem(song4_rating3);
        songPlaylist.addItem(song1_rating5);

        // movies
        moviePlaylist.addItem(movie1);
        moviePlaylist.addItem(movie2);
        moviePlaylist.addItem(movie3);

    }

    @Test
    public void played_affects_all_references(){
        songPlaylist.getList().get(0).play();
        assertEquals(1,songPlaylist.getList().get(4).getPlayed());
    }

    @Test
    public void avg_rating_songs(){
        assertEquals(17/4d, songPlaylist.getAverageRating());
    }

    @Test
    public void avg_rating_movies(){
        assertEquals(14/3d, moviePlaylist.getAverageRating());
    }

    @Test
    public void sort_songs_rating(){
        HashMap<Integer, Set<Song>> expected = new HashMap<>();
        expected.put(5,Set.of(song1_rating5, song3_rating5));
        expected.put(4,Set.of(song2_rating4));
        expected.put(3,Set.of(song4_rating3));
        assertEquals(expected, songPlaylist.getByRating());
    }

    @Test
    public void sort_songs_by_title(){
        Set<Song> expected = new LinkedHashSet<>();
        expected.add(song3_rating5);
        expected.add(song2_rating4);
        expected.add(song1_rating5);
        expected.add(song4_rating3);
        assertEquals(expected, songPlaylist.getOrderedByTitle());
    }

    @Test
    public void sort_songs_by_rating_limit_3(){
        Set<Song> expected = new LinkedHashSet<>();
        expected.add(song3_rating5);
        expected.add(song1_rating5);
        expected.add(song2_rating4);
        assertEquals(expected, songPlaylist.getItemsByRating(3));
    }

    @Test
    public void remove_items_played_more_than_once(){
        song1_rating5.play();
        song1_rating5.play();
        song2_rating4.play();
        assertEquals(2,songPlaylist.removeItemsPlayedTooOften(1));
        assertEquals(3, songPlaylist.getList().size());
    }

    @Test
    public void get_uppercase_titles(){
        assertTrue(songPlaylist.getTitleOfItemUppercase().contains("COME TOMORROW"));
    }

    @Test
    public void get_singers(){
        assertTrue(songPlaylist.getSpecificPartOfItem(Song::getSinger).contains("CCR"));
    }

    @Test
    public void get_directors(){
        assertTrue(moviePlaylist.getSpecificPartOfItem(Movie::getDirector).contains("Quentin Tarantino"));
    }
}
