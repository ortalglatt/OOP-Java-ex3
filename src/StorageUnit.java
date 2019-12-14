import java.util.HashMap;
import java.util.Map;
import oop.ex3.spaceship.*;


public abstract class StorageUnit {
    protected int capacity;
    protected int availableCapacity;
    protected Map<String, Integer> itemsInStorage;

    /**
     * Initialize a StorageUnit object.
     */
    public StorageUnit(){
        this.itemsInStorage = new HashMap<>();
    }

    /**
     * Checks if it's possible to add n Items of the given type to the storage-unit.
     * @param n the amount of this object the user want to add.
     * @return -1 if the input is illegal, 0 if n=0, 1 if their is room for the Items, and -3 if their is no
     * room.
     */
    public int addItem(Item item, int n){
        if (n < 0){
            System.out.println("Error: Your request cannot be completed at this time");
            return -1;
        }
        if (n==0) return 0;
        if (this.availableCapacity >= n * item.getVolume()) return 1;
        else return -3;
    }

    /**
     * @param type the type of the item.
     * @return the amount of times this item appears in the storage-unit.
     */
    public int getItemCount(String type){
        if (this.itemsInStorage.containsKey(type)) return this.itemsInStorage.get(type);
        return 0;
    }

    /**
     * @return a map of all the items in the storage-unit, and how many times each item appears in the locker.
     */
    public Map<String, Integer> getInventory(){return this.itemsInStorage;
    }

    /**
     * @return the total capacity of this locker.
     */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     * @return the available capacity of this locker.
     */
    public int getAvailableCapacity(){
        return this.availableCapacity;
    }

}
