import java.util.*;
import java.util.function.Function;

public class Playlist<T extends Playable> {

    private List<T> playlistList = new ArrayList<>();

    public Map<Integer, Set<T>> getByRating() {
        Map<Integer, Set<T>> returnMap = new HashMap<>();
        for (T element : playlistList) {
            Set<T> elementSet = returnMap.containsKey(element.getRating()) ? returnMap.get(element.getRating()) : new HashSet<>();
            elementSet.add(element);
            returnMap.put(element.getRating(), elementSet);
        }
        return returnMap;
    }

    public double getAverageRating(){
        double averageRating = 0;

        for (T element : new HashSet<>(playlistList))
            averageRating += element.getRating();

        return averageRating / new HashSet<T>(playlistList).size();
    }

    public Set<T> getOrderedByTitle() {
        return new HashSet<>(playlistList);
    }

    public Set<T> getItemsByRating(int n) {
        Set<T> returnSet = new HashSet<>();

        for (T element : playlistList) {
            if (returnSet.size() >= n)
                break;
            returnSet.add(element);
        }
        return returnSet;
    }

    public <K> List<K> getSpecificPartOfItem(Function<T,K> function) {
        List<K> returnList = new ArrayList<>();

        for (T element : playlistList)
            returnList.add(function.apply(element));

        return returnList;
    }

    public List<String> getTitleOfItemUppercase() {
        List<String> returnList = new ArrayList<>();

        playlistList.forEach((element) -> returnList.add(element.getTitle().toUpperCase()));

        return returnList;
    }

    public int removeItemsPlayedTooOften(int played) {
        int removed = 0;
        List<T> toRemove = new ArrayList<>();

        for (T element : playlistList) {
            if (element.getPlayed() < played) {
                toRemove.add(element);
                removed++;
            }
        }
        playlistList.removeAll(toRemove);
        return removed;
    }

    public List<T> getList(){
        return playlistList;
    }

    public boolean addItem(T item) {
        return playlistList.add(item);
    }

}
