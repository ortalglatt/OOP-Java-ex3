import oop.ex3.searchengine.Hotel;
import java.util.Comparator;


public class ProximityComparator implements Comparator<Hotel> {
    private double latitudeToCompare;
    private double longitudeToCompare;

    /**
     * Initialize a ProximityComparator object.
     * @param latitude the latitude of the location.
     * @param longitude the longitude of the location.
     */
    public ProximityComparator(double latitude, double longitude){
        this.latitudeToCompare = latitude;
        this.longitudeToCompare = longitude;
    }

    /**
     * Checks which hotel needs to come before the other, by checking their proximity from the given location.
     * If the proximity is equal, it will check the number of POI their are in the hotels area.
     * @param hotel1 first Hotel object to compare.
     * @param hotel2 second Hotel object to compare.
     * @return the comparision result.
     */
    @Override
    public int compare(Hotel hotel1, Hotel hotel2) {
        Double hotel1Proximity = this.getProximity(hotel1);
        Double hotel2Proximity = this.getProximity(hotel2);
        int result = hotel1Proximity.compareTo(hotel2Proximity);
        if (result != 0) return result;

        return this.comparePOI(hotel1, hotel2);
    }

    /**
     * @param hotel the Hotel object to check the proximity.
     * @return the proximity between the hotel to the given point.
     */
    private double getProximity(Hotel hotel){
        double latitudeDiff = hotel.getLatitude() - this.latitudeToCompare;
        double longitudeDiff = hotel.getLongitude() - this.longitudeToCompare;
        return Math.sqrt(Math.pow(latitudeDiff, 2) + Math.pow(longitudeDiff, 2));
    }

    /**
     *
     * Checks which hotel needs to come before the other, by checking the number of POI their are in the
     * hotels area.
     * @param hotel1 first Hotel object to compare.
     * @param hotel2 second Hotel object to compare.
     * @return the POI number comparision result.
     */
    private int comparePOI(Hotel hotel1, Hotel hotel2){
        Integer hotel1POI = hotel1.getNumPOI();
        Integer hotel2POI = hotel2.getNumPOI();
        return hotel2POI.compareTo(hotel1POI);
    }

}