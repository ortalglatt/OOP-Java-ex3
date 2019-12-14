import oop.ex3.spaceship.*;
import java.util.HashMap;


public class LongTermStorage extends StorageUnit {

    /**
     * Initialize a LongTermStorage object.
     */
    public LongTermStorage() {
        this.capacity = 1000;
        this.availableCapacity = capacity;
    }

    /**
     * Adds n Items of the given type to the storage, if it's possible.
     * @param item Item object that the user want to add to this storage.
     * @param n the amount of this object the user want to add.
     * @return 0 if the n items added successfully and  -1 if their wasn't enough place for the items.
     */
    public int addItem(Item item, int n){
        int result = super.addItem(item, n);
        String itemType = item.getType();
        if (result == 1) {
            this.itemsInStorage.put(itemType, n + this.getItemCount(itemType));
            this.availableCapacity -= (n * item.getVolume());
            return 0;
        }
        if (result == -3) {
            System.out.println("Error: Your request cannot be completed at this time. Problem: no room for " +
                    n + " Items of type " + itemType);
            return -1;
        }
        return result;
    }

    /**
     * Resets the long-term storage’s inventory.
     */
    public void resetInventory(){
        this.availableCapacity = this.capacity;
        this.itemsInStorage = new HashMap<>();
    }

}
