import java.util.Comparator;
import oop.ex3.searchengine.*;


public class RatingComparator implements Comparator<Hotel> {

    /**
     * Checks which hotel needs to come before the other, by checking their ratings.
     * If the rating is equal, it will check their names.
     * @param hotel1 first Hotel object to compare.
     * @param hotel2 second Hotel object to compare.
     * @return the comparision result.
     */
    @Override
    public int compare(Hotel hotel1, Hotel hotel2) {
        Integer hotel1Rating = hotel1.getStarRating();
        Integer hotel2Rating = hotel2.getStarRating();
        int result = hotel2Rating.compareTo(hotel1Rating);
        if (result != 0) return result;
        return this.compareNames(hotel1, hotel2);
    }

    /**
     * Checks which hotel needs to come before the other, by checking their names (alphabetically).
     * @param hotel1 first Hotel object to compare.
     * @param hotel2 second Hotel object to compare.
     * @return the names comparision result.
     */
    private int compareNames(Hotel hotel1, Hotel hotel2){
        String hotel1Name =  hotel1.getPropertyName();
        String hotel2Name = hotel2.getPropertyName();
        return hotel1Name.compareTo(hotel2Name);
    }

}
