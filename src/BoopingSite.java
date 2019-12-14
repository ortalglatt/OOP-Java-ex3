import oop.ex3.searchengine.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class BoopingSite {
    private String filename;
    private Hotel[] hotelsArray;

    /**
     * Initialize a BoopingSite object.
     * @param name the dataset filename.
     */
    public BoopingSite(String name){
        this.filename = name;
        this.hotelsArray = HotelDataset.getHotels(this.filename);
    }

    /**
     * @param city the city we want to find the hotels in.
     * @return an Array of all the hotels in the given city sorted by their rating.
     *         Hotels that have the same rating will be sorted alphabetically by their name.
     */
    public Hotel[] getHotelsInCityByRating(String city){
        List<Hotel> hotelsToReturn = this.hotelsInCity(city);
        hotelsToReturn.sort(new RatingComparator());
        return hotelsToReturn.toArray(new Hotel[0]);
    }

    /**
     * @param latitude the latitude of the location.
     * @param longitude the longitude of the location.
     * @return an Array of all the hotels sorted by their proximity to the given location.
     *         Hotels that have the same proximity to the location will be sorted by the POI number.
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude){
        if (!legalInput(latitude, longitude)) return new Hotel[0];
        Hotel[] hotelsToReturn = this.hotelsArray;
        Arrays.sort(hotelsToReturn, new ProximityComparator(latitude, longitude));
        return hotelsToReturn;
    }

    /**
     * @param latitude the latitude of the location.
     * @param longitude the longitude of the location.
     * @return true if the given location is legal, false otherwise.
     */
    private boolean legalInput(double latitude, double longitude){
        return (latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180);
    }

    /**
     * @param city the city we want to sort the hotels in.
     * @param latitude the latitude of the location.
     * @param longitude the longitude of the location.
     * @return an Array of all the hotels in the given city sorted by their proximity to the given location.
     *         Hotels that have the same proximity to the location will be sorted by the POI number.
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude){
        if (!legalInput(latitude, longitude)) return new Hotel[0];
        List<Hotel> hotelsToReturn = this.hotelsInCity(city);
        hotelsToReturn.sort(new ProximityComparator(latitude, longitude));
        return hotelsToReturn.toArray(new Hotel[0]);
    }

    /**
     * @param city the city we want to find the hotels in.
     * @return a List of al the hotels in the given city.
     */
    private List<Hotel> hotelsInCity(String city){
        List<Hotel> cityHotels = new ArrayList<>();
        for (Hotel hotel: this.hotelsArray) {
            if (city.equals(hotel.getCity())) cityHotels.add(hotel);
        }
        return cityHotels;
    }
}
