import org.junit.*;
import static org.junit.Assert.*;
import oop.ex3.spaceship.*;

import java.util.Map;


public class LongTermTest {

    private LongTermStorage testStorage;
    private Item item0 = ItemFactory.createSingleItem("baseball bat");  // volume: 2
    private Item item1 = ItemFactory.createSingleItem("helmet, size 1");  // volume: 3
    private Item item2 = ItemFactory.createSingleItem("helmet, size 3");  // volume: 5
    private Item item3 = ItemFactory.createSingleItem("spores engine");  // volume: 10
    private Item item4 = ItemFactory.createSingleItem("football");  // volume: 4

    /**
     * Creates a new testStorage object.
     */
    @Before
    public void createTestStorage(){testStorage = new LongTermStorage();}

    /**
     * Tests the creation of a new LongTermStorage object.
     */
    @Test
    public void createStorageTest(){
        assertNotNull(testStorage);
        assertNotNull(testStorage.getInventory());
        assertTrue(testStorage.getInventory().isEmpty());
        assertEquals(1000, testStorage.getCapacity());
        assertEquals(1000, testStorage.getAvailableCapacity());
    }

    /**
     * Tests that the addItem returns an error code of -1 for any illegal input.
     */
    @Test
    public void addItemIllegalInputTest(){
        assertEquals(-1, testStorage.addItem(item0, -4));
        assertEquals(-1, testStorage.addItem(item4, -1));
        assertEquals(-1, testStorage.addItem(item3, 101));
        assertEquals(-1, testStorage.addItem(item0, 503));

    }

    /**
     * Tests the addItem method, makes sure it adds every item and return -1 if their is not enough place for
     * the items.
     */
    @Test
    public void addItemTest1(){
        assertEquals(0, testStorage.addItem(item1, 40));
        assertEquals(40, testStorage.getItemCount(item1.getType()));
        assertEquals(0, testStorage.addItem(item2, 120));
        assertEquals(120, testStorage.getItemCount(item2.getType()));
        assertEquals(0, testStorage.addItem(item3, 27));
        assertEquals(27, testStorage.getItemCount(item3.getType()));
        assertEquals(-1, testStorage.addItem(item0, 6));
        assertEquals(0, testStorage.getItemCount(item0.getType()));
    }

    /**
     * Tests the addItem method, makes sure it adds more from the same item if needed.
     */
    @Test
    public void addItemTest2(){
        assertEquals(0, testStorage.addItem(item2, 30));
        assertEquals(30, testStorage.getItemCount(item2.getType()));
        assertEquals(0, testStorage.addItem(item2, 15));
        assertEquals(45, testStorage.getItemCount(item2.getType()));
        assertEquals(0, testStorage.addItem(item2, 50));
        assertEquals(95, testStorage.getItemCount(item2.getType()));
        assertEquals(-1, testStorage.addItem(item2, 110));
        assertEquals(95, testStorage.getItemCount(item2.getType()));
    }

    /**
     * Tests the addItem method, makes sure the inventory map changes correctly.
     */
    @Test
    public void addItemTest3(){
        assertEquals(0, testStorage.addItem(item2, 0));
        assertTrue(testStorage.getInventory().isEmpty());
        assertEquals(0, testStorage.addItem(item3, 1));
        assertEquals(0, testStorage.addItem(item4, 0));
        assertTrue(!testStorage.getInventory().containsKey(item4.getType()));
        assertTrue(testStorage.getInventory().containsKey(item3.getType()));
    }

    /**
     * Tests the resetInventory method.
     */
    @Test
    public void resetInventoryTest(){
        testStorage.addItem(item0, 10);
        testStorage.addItem(item1, 15);
        assertTrue(!testStorage.getInventory().isEmpty());
        assertEquals(935, testStorage.getAvailableCapacity());
        testStorage.resetInventory();
        assertNotNull(testStorage.getInventory());
        assertTrue(testStorage.getInventory().isEmpty());
        assertEquals(1000, testStorage.getAvailableCapacity());
        assertEquals( 0, testStorage.getItemCount(item0.getType()));
        assertEquals( 0, testStorage.getItemCount(item1.getType()));
        assertEquals( 0, testStorage.getItemCount(item2.getType()));
        assertEquals( 0, testStorage.getItemCount(item3.getType()));
        assertEquals( 0, testStorage.getItemCount(item4.getType()));
    }

    /**
     * Tests the storage's items count, makes sure the count is right when adding items to the storage.
     */
    @Test
    public void itemCountTest(){
        assertEquals(0, testStorage.getItemCount(item3.getType()));
        testStorage.addItem(item3, 80);
        assertEquals(80, testStorage.getItemCount(item3.getType()));
        testStorage.addItem(item3, 30);
        assertEquals(80, testStorage.getItemCount(item3.getType()));
        testStorage.resetInventory();
        assertEquals(0, testStorage.getItemCount(item3.getType()));
    }

    /**
     * Tests that the storage's inventory contains the correct information about the storage.
     */
    @Test
    public void inventoryTest() {
        testStorage.addItem(item3, 10);
        Map cur_inventory = testStorage.getInventory();
        assertTrue(cur_inventory.containsKey(item3.getType()));
        assertEquals(10, cur_inventory.get(item3.getType()));
        testStorage.addItem(item3, 7);
        cur_inventory = testStorage.getInventory();
        assertEquals(17, cur_inventory.get(item3.getType()));
        assertTrue(!cur_inventory.containsKey(item1.getType()));
    }

    /**
     * Tests that the storage capacity does not change when adding items.
     */
    @Test
    public void storageCapacityTest(){
        testStorage.addItem(item4, 1);
        assertEquals(1000, testStorage.getCapacity());
        testStorage.addItem(item3, 10);
        assertEquals(1000, testStorage.getCapacity());
    }

    /**
     * Tests the available capacity changes when adding items.
     */
    @Test
    public void availableCapacityTest1(){
        testStorage.addItem(item0, 5);
        assertEquals(990, testStorage.getAvailableCapacity());
        testStorage.addItem(item1, 23);
        assertEquals(921, testStorage.getAvailableCapacity());
        testStorage.addItem(item2, 68);
        assertEquals(581, testStorage.getAvailableCapacity());
    }

    /**
     * Tests the available capacity doesn't change when the item's adding wasn't successful.
     */
    @Test
    public void availableCapacityTest2(){
        testStorage.addItem(item3, 40);
        assertEquals(600, testStorage.getAvailableCapacity());
        testStorage.addItem(item3, 70);
        assertEquals(600, testStorage.getAvailableCapacity());
    }
}
