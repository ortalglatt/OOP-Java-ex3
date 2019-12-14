import oop.ex3.searchengine.*;
import org.junit.*;
import static org.junit.Assert.*;


public class BoopingSiteTest {
    private BoopingSite testSite1;
    private BoopingSite testSite2;
    private BoopingSite bigTestSite;
    private Hotel[] bigTestArr = HotelDataset.getHotels("hotels_dataset.txt");
    private Hotel[] testSite1Arr = HotelDataset.getHotels("hotels_tst1.txt");

    /**
     * Before any test, initialize the BoopingSite objects.
     */
    @Before
    public void initializeDatasets(){
        this.testSite1 = new BoopingSite("hotels_tst1.txt");
        this.testSite2 = new BoopingSite("hotels_tst2.txt");
        this.bigTestSite = new BoopingSite("hotels_dataset.txt");
    }

    /**
     * Tests if the given array is sorted by rating, if the rating is equal, it needs to be sorted
     * alphabetically by names.
     * @param hotelsArr hotels array to check.
     */
    private void RatingTest(Hotel[] hotelsArr){
        Hotel hotel1 = hotelsArr[0];
        int hotel1Rating = hotel1.getStarRating();
        for (int i = 1; i < hotelsArr.length; i++) {
            Hotel hotel2 = hotelsArr[i];
            int hotel2Rating = hotel2.getStarRating();
            if (hotel1Rating != hotel2Rating){
                assertTrue(hotel1Rating > hotel2Rating);
            } else assertTrue(hotel1.getPropertyName().compareTo(hotel2.getPropertyName()) <= 0);
            hotel1 = hotel2;
            hotel1Rating = hotel2Rating;
        }
    }

    /**
     * Tests if the given array is sorted by proximity from the given location, if the proximity is equal, it
     * needs to be sorted alphabetically by POI number.
     * @param hotelsArr hotels array to check.
     */
    private void proximityTest(Hotel[] hotelsArr, double latitude, double longitude){
        Hotel hotel1 = hotelsArr[0];
        double hotel1Proximity = this.getProximity(hotel1, latitude, longitude);
        for (int i = 1; i < hotelsArr.length; i++) {
            Hotel hotel2 = hotelsArr[i];
            double hotel2Proximity = this.getProximity(hotel2, latitude, longitude);
            if (hotel1Proximity != hotel2Proximity){
                assertTrue(hotel1Proximity < hotel2Proximity);
            } else assertTrue(hotel1.getNumPOI() >= hotel2.getNumPOI());
            hotel1 = hotel2;
            hotel1Proximity = hotel2Proximity;
        }
    }

    /**
     * @param hotel the Hotel object to check the proximity.
     * @param latitude the latitude of the location to check.
     * @param longitude the longitude of the location to check.
     * @return the proximity between the hotel to the given point.
     */
    private double getProximity(Hotel hotel, double latitude, double longitude){
        double latitudeDiff = hotel.getLatitude() - latitude;
        double longitudeDiff = hotel.getLongitude() - longitude;
        return Math.sqrt(Math.pow(latitudeDiff, 2) + Math.pow(longitudeDiff, 2));
    }

    /**
     * Check that all the hotels in the given array are in the given city.
     * @param hotelsArr hotels array to check.
     * @param city city that all the hotels need to be in.
     */
    private void cityTest(Hotel[] hotelsArr, String city){
        for (Hotel hotel: hotelsArr){
            assertEquals(city, hotel.getCity());
        }
    }

    /**
     * @param hotelsArr hotels array to check.
     * @param city city to count.
     * @return the number of hotels in the given array that they are in the given city.
     */
    private int cityCount(Hotel[] hotelsArr, String city){
        int counter = 0;
        for(Hotel hotel: hotelsArr){
            if (hotel.getCity().equals(city)) counter++;
        }
        return counter;
    }

    /**
     * Tests the initialization of the BoopingSite objects.
     */
    @Test
    public void initializeTest(){
        assertNotNull(bigTestSite);
        assertNotNull(testSite1);
        assertNotNull(testSite2);
        assertTrue(bigTestArr.length > 0);
        assertTrue(testSite1Arr.length > 0);
    }

    /**
     * Tests the city by rating sort on testSite1.
     */
    @Test
    public void cityByRatingTest1(){
        String city = "manali";
        Hotel[] sortedArr = testSite1.getHotelsInCityByRating(city);
        assertTrue(sortedArr.length > 0);
        this.RatingTest(sortedArr);
    }

    /**
     * Tests the city by rating sort on bigTestSite.
     */
    @Test
    public void cityByRatingTest2(){
        String city = "srinagar";
        Hotel[] sortedArr = bigTestSite.getHotelsInCityByRating(city);
        assertTrue(sortedArr.length > 0);
        this.cityTest(sortedArr, city);
        assertEquals(sortedArr.length, this.cityCount(this.bigTestArr, city));
        this.RatingTest(sortedArr);
    }

    /**
     * Tests the city by rating sort on bigTestSite.
     */
    @Test
    public void cityByRatingTest3(){
        String city = "goa";
        Hotel[] sortedArr = bigTestSite.getHotelsInCityByRating(city);
        assertTrue(sortedArr.length > 0);
        this.cityTest(sortedArr, city);
        assertEquals(sortedArr.length, this.cityCount(this.bigTestArr, city));
        this.RatingTest(sortedArr);
    }

    /**
     * Tests that the city by rating sort returns an empty set when the city doesn't appear in the dataset.
     */
    @Test
    public void cityByRatingTestEmpty1(){
        String city = "kathmandu";
        Hotel[] sortedArr = bigTestSite.getHotelsInCityByRating(city);
        assertEquals(sortedArr.length, 0);
    }

    /**
     * Tests that the city by rating sort returns an empty array when the dataset is empty.
     */
    @Test
    public void cityByRatingTestEmpty2(){
        String city = "manali";
        Hotel[] sortedArr = testSite2.getHotelsInCityByRating(city);
        assertEquals(sortedArr.length, 0);
    }

    /**
     * Tests the proximity sort on testSite1.
     */
    @Test
    public void proximityTest1() {
        double latitude = -32.8;
        double longitude = 87.56;
        Hotel[] sortedArr = testSite1.getHotelsByProximity(latitude, longitude);
        this.proximityTest(sortedArr, latitude, longitude);
        assertEquals(sortedArr.length, testSite1Arr.length);
    }

    /**
     * Tests the proximity sort on testSite1.
     */
    @Test
    public void proximityTest2() {
        double latitude = 8;
        double longitude = -167;
        Hotel[] sortedArr = bigTestSite.getHotelsByProximity(latitude, longitude);
        this.proximityTest(sortedArr, latitude, longitude);
        assertEquals(sortedArr.length, bigTestArr.length);
    }

    /**
     * Tests the proximity sort on bigTestSite with the location (0,0).
     */
    @Test
    public void proximityTest3() {
        double latitude = 0;
        double longitude = 0;
        Hotel[] sortedArr = bigTestSite.getHotelsByProximity(latitude, longitude);
        this.proximityTest(sortedArr, latitude, longitude);
        assertEquals(sortedArr.length, bigTestArr.length);
    }

    /**
     * Tests that the proximity sort returns an empty set when the latitude is illegal.
     */
    @Test
    public void ProximityTestEmpty1(){
        Hotel[] sortedArr = bigTestSite.getHotelsByProximity(-90.2,144);
        assertEquals(sortedArr.length, 0);
    }

    /**
     * Tests that the proximity sort returns an empty set when the longitude is illegal.
     */
    @Test
    public void ProximityTestEmpty2(){
        Hotel[] sortedArr = bigTestSite.getHotelsByProximity(0,200);
        assertEquals(sortedArr.length, 0);
    }

    /**
     * Tests that the proximity sort returns an empty set when the dataset is empty.
     */
    @Test
    public void ProximityTestEmpty3(){
        Hotel[] sortedArr = testSite2.getHotelsByProximity(0,0);
        assertEquals(sortedArr.length, 0);
    }

    /**
     * Tests the city by proximity sort on testSite1.
     */
    @Test
    public void cityByProximityTest1(){
        String city = "manali";
        double latitude = 90;
        double longitude = 180;
        Hotel[] sortedArr = testSite1.getHotelsInCityByProximity(city, latitude, longitude);
        assertTrue(sortedArr.length > 0);
        this.cityTest(sortedArr, city);
        assertEquals(sortedArr.length, this.cityCount(this.bigTestArr, city));
        this.proximityTest(sortedArr, latitude, longitude);
    }

    /**
     * Tests the city by proximity sort on bigTestSite.
     */
    @Test
    public void cityByProximityTest2(){
        String city = "srinagar";
        double latitude = -10.983;
        double longitude = 127.07;
        Hotel[] sortedArr = bigTestSite.getHotelsInCityByProximity(city, latitude, longitude);
        assertTrue(sortedArr.length > 0);
        this.cityTest(sortedArr, city);
        assertEquals(sortedArr.length, this.cityCount(this.bigTestArr, city));
        this.proximityTest(sortedArr, latitude, longitude);
    }

    /**
     * Tests that the city by proximity sort returns an empty set when the city doesn't appear in the dataset.
     */
    @Test
    public void cityByProximityTestEmpty1(){
        String city = "jerusalem";
        Hotel[] sortedArr = bigTestSite.getHotelsInCityByProximity(city, 2, 3);
        assertEquals(sortedArr.length, 0);
    }

    /**
     * Tests that the city by proximity sort returns an empty set when the latitude and longitude are illegal.
     */
    @Test
    public void cityByProximityTestEmpty2(){
        String city = "goa";
        Hotel[] sortedArr = bigTestSite.getHotelsInCityByProximity(city, 98, -196);
        assertEquals(sortedArr.length, 0);
    }

    /**
     * Tests that the city by proximity sort returns an empty set when the dataset is empty
     */
    @Test
    public void cityByProximityTestEmpty3(){
        String city = "leh";
        Hotel[] sortedArr = testSite2.getHotelsInCityByProximity(city, 10, -98);
        assertEquals(sortedArr.length, 0);
    }

}
