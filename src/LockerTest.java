import org.junit.*;
import static org.junit.Assert.*;
import oop.ex3.spaceship.*;
import java.util.Map;


public class LockerTest {

    private Locker testLocker;
    private Locker bigLocker;
    private Item item0 = ItemFactory.createSingleItem("baseball bat");  // volume: 2
    private Item item1 = ItemFactory.createSingleItem("helmet, size 1");  // volume: 3
    private Item item2 = ItemFactory.createSingleItem("helmet, size 3");  // volume: 5
    private Item item3 = ItemFactory.createSingleItem("spores engine");  // volume: 10
    private Item item4 = ItemFactory.createSingleItem("football");  // volume: 4

    /**
     * Creates a new testLocker object with capacity of 20.
     */
    @Before
    public void createTestLocker(){testLocker = new Locker(20);}

    /**
     * Creates a new big test Locker object with capacity of 2000.
     */
    public void createBigLocker(){bigLocker = new Locker(2000);}

    /**
     * Tests the creation of a new Locker object.
     */
    @Test
    public void createLockerTest(){
        assertNotNull(testLocker);
        assertNotNull(testLocker.getInventory());
        assertTrue(testLocker.getInventory().isEmpty());
        assertEquals(20, testLocker.getCapacity());
        assertEquals(20, testLocker.getAvailableCapacity());
    }

    /**
     * Tests that the addItem returns an error code of -1 for any illegal input.
     */
    @Test
    public void addItemIllegalInputsTest(){
        assertEquals(-1, testLocker.addItem(item0, -3));
        assertEquals(-1, testLocker.addItem(item3, 5));
        assertEquals(-1, testLocker.addItem(item1, 7));
        assertEquals(-1, testLocker.addItem(item0, 20));
    }

    /**
     * Test the addItem method, makes sure it will return -1 if the locker is full and the user added an item.
     */
    @Test
    public void addItemTest1(){
        assertEquals(0, testLocker.addItem(item1, 2));
        assertEquals(2, testLocker.getItemCount(item1.getType()));
        assertEquals(0, testLocker.addItem(item3, 1));
        assertEquals(1, testLocker.getItemCount(item3.getType()));
        assertEquals(0, testLocker.addItem(item0, 2));
        assertEquals(2, testLocker.getItemCount(item0.getType()));
        // now the locker is full.
        assertEquals(-1, testLocker.addItem(item2, 1));
    }

    /**
     * Tests the addItem method, makes sure it will move some of the items to the long-term storage if needed
     * and return 1.
     */
    @Test
    public void addItemTest2(){
        assertEquals(0, testLocker.addItem(item3, 1));
        assertEquals(1, testLocker.addItem(item3, 1));
        assertEquals(0, testLocker.getItemCount(item3.getType()));

        assertEquals(0, testLocker.addItem(item4, 2));
        assertEquals(-1, testLocker.addItem(item2, 3));
        assertEquals(1, testLocker.addItem(item4, 1));
        assertEquals(1, testLocker.getItemCount(item4.getType()));
    }

    /**
     * Tests the addItem method, makes sure it returns -1 when their not enough place in the locker.
     */
    @Test
    public void addItemTest3(){
        assertEquals(0, testLocker.addItem(item2,0));
        assertEquals(0, testLocker.addItem(item1,3));
        assertEquals(0, testLocker.addItem(item3,1));
        assertEquals(-1, testLocker.addItem(item0,1));
        assertEquals(-1, testLocker.addItem(item4,1));
    }

    /**
     * Tests the addItem method, makes sure the contradictions check happens before the other checks.
     */
    @Test
    public void addItemTest4(){
        assertEquals(0, testLocker.addItem(item0, 1));
        assertEquals(-2, testLocker.addItem(item4, -5));
        testLocker.removeItem(item0, 1);
        assertEquals(1, testLocker.addItem(item4, 3));
        assertEquals(-2, testLocker.addItem(item0, -9));
    }

    /**
     * Tests the addItem method, makes sure the contradictions check works correctly, and if their is a
     * contradiction, it will return -2.
     */
    @Test
    public void addItemTest5(){
        assertEquals(0, testLocker.addItem(item0, 5));
        assertEquals(-2, testLocker.addItem(item4, 2));
        assertEquals(0, testLocker.getItemCount(item4.getType()));
        testLocker.removeItem(item0, 5);
        assertEquals(0, testLocker.addItem(item4, 2));
        assertEquals(-2, testLocker.addItem(item0, 2));
        assertEquals(0, testLocker.getItemCount(item0.getType()));
    }


    /**
     * Tests the addItem method, makes sure that when some of the items need to move to the long-term storage
     * nd the storage is full, it will return -1.
     */
    @Test
    public void addItemTest6(){
        this.createBigLocker();
        Locker.getStorage().resetInventory();
        assertEquals(0, bigLocker.addItem(item3, 100));
        assertEquals(1, bigLocker.addItem(item3, 40));
        assertEquals(40, bigLocker.getItemCount(item3.getType()));
        // now the long-term storage is full.
        assertEquals(-1, bigLocker.addItem(item3, 61));
        Locker.getStorage().resetInventory();
    }

    /**
     * Tests the addItem method, makes sure that when some of the items need to move to the long-term storage
     * nd the storage is full, it will return -1.
     */
    @Test
    public void addItemTest7(){
        this.createBigLocker();
        Locker.getStorage().resetInventory();
        assertEquals(1, bigLocker.addItem(item2, 250));
        assertEquals(80, bigLocker.getItemCount(item2.getType()));
        // now the long-term storage is full.
        assertEquals(-1, bigLocker.addItem(item2, 150));
        Locker.getStorage().resetInventory();
    }


    /**
     * Tests that the removeItem will return an error code of -1 for any illegal input.
     */
    @Test
    public void removeItemIllegalInputsTest() {
        assertEquals(-1, testLocker.removeItem(item1, -2));
        assertEquals(-1, testLocker.removeItem(item2, 3));
        assertEquals(-1, testLocker.removeItem(item4, 8));
    }

    /**
     * Tests the removeItem method, makes sure it will return -1 if the user will try to remove more items
     * then their are in the locker.
     */
    @Test
    public void removeItemTest1() {
        assertEquals(0, testLocker.removeItem(item4, 0));
        testLocker.addItem(item1, 2);
        testLocker.addItem(item0, 4);
        assertEquals(-1, testLocker.removeItem(item1, 3));
        assertEquals(-1, testLocker.removeItem(item0, 10));
    }

    /**
     * Tests the removeItem method, makes sure it will remove the items when the input is fine.
     */
    @Test
    public void removeItemTest2(){
        testLocker.addItem(item1, 2);
        testLocker.addItem(item0, 4);
        assertEquals(0, testLocker.removeItem(item0, 3));
        assertEquals(1, testLocker.getItemCount(item0.getType()));
        assertEquals(0, testLocker.removeItem(item0, 0));
        assertEquals(1, testLocker.getItemCount(item0.getType()));
        assertEquals(0, testLocker.removeItem(item1, 2));
        assertEquals(0, testLocker.getItemCount(item1.getType()));
    }

    /**
     * Tests the locker's items count, makes sure the count is right when adding items to the locker.
     */
    @Test
    public void itemCountTest1() {
        testLocker.addItem(item4, 2);
        testLocker.addItem(item3, 1);
        assertEquals(2, testLocker.getItemCount(item4.getType()));
        assertEquals(1, testLocker.getItemCount(item3.getType()));
        assertEquals(0, testLocker.getItemCount(item0.getType()));
        assertEquals(0, testLocker.getItemCount(item1.getType()));
    }

    /**
     * Tests the locker's items count, makes sure the count is right when removing items from the locker.
     */
    @Test
    public void itemCountTest2(){
        testLocker.addItem(item0, 2);
        assertEquals(2, testLocker.getItemCount(item0.getType()));
        testLocker.addItem(item0, 1);
        assertEquals(3, testLocker.getItemCount(item0.getType()));
        testLocker.addItem(item0, 3);
        assertEquals(2, testLocker.getItemCount(item0.getType()));
        testLocker.removeItem(item0, 1);
        assertEquals(1, testLocker.getItemCount(item0.getType()));
    }

    /**
     * Tests that the locker's inventory contains the correct information about the locker.
     */
    @Test
    public void inventoryTest(){
        testLocker.addItem(item1, 2);
        Map cur_inventory = testLocker.getInventory();
        assertTrue(cur_inventory.containsKey(item1.getType()));
        assertEquals(2, cur_inventory.get(item1.getType()));
        testLocker.addItem(item1, 1);
        cur_inventory = testLocker.getInventory();
        assertEquals(3, cur_inventory.get(item1.getType()));
        testLocker.removeItem(item1, 3);
        cur_inventory = testLocker.getInventory();
        assertTrue(!cur_inventory.containsKey(item1.getType()));
    }

    /**
     * Tests that the locker capacity does not change when adding or removing items.
     */
    @Test
    public void lockerCapacityTest(){
        testLocker.addItem(item0, 3);
        assertEquals(20, testLocker.getCapacity());
        testLocker.removeItem(item0, 2);
        assertEquals(20, testLocker.getCapacity());
    }

    /**
     * Tests the available capacity changes during the use of the other methods.
     */
    @Test
    public void availableCapacityTest1(){
        testLocker.addItem(item0, 4);
        assertEquals(12, testLocker.getAvailableCapacity());
        testLocker.addItem(item3, 1);
        assertEquals(2, testLocker.getAvailableCapacity());
        testLocker.addItem(item0, 1);
        assertEquals(0, testLocker.getAvailableCapacity());
        testLocker.removeItem(item0, 2);
        assertEquals(4, testLocker.getAvailableCapacity());
    }

    /**
     *Tests the available capacity, makes sure it will be right when some of the items that were already on
     * the locker needs to be moved to the long-term storage.
     */
    @Test
    public void availableCapacityTest2(){
        testLocker.addItem(item2, 3);
        assertEquals(20, testLocker.getAvailableCapacity());
        testLocker.addItem(item1, 5);
        assertEquals(17, testLocker.getAvailableCapacity());
        testLocker.addItem(item0, 6);
        assertEquals(13, testLocker.getAvailableCapacity());
    }

    /**
     *Tests the available capacity, makes sure it will be right when some of the items that were already on
     * the locker needs to be moved to the long-term storage.
     */
    @Test
    public void availableCapacityTest3(){
        testLocker.addItem(item0, 2);
        assertEquals(16, testLocker.getAvailableCapacity());
        testLocker.addItem(item0, 5);
        assertEquals(16, testLocker.getAvailableCapacity());
    }

}
