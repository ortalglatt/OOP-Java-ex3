import oop.ex3.spaceship.*;

public class Locker extends StorageUnit {
    private static LongTermStorage storage = new LongTermStorage();

    /**
     * Initialize a Locker object.
     * @param capacity the storage capacity of this locker.
     */
    public Locker(int capacity){
        this.capacity = capacity;
        this.availableCapacity = capacity;
    }

    /**
     * Adds n Items of the given type to the locker, if it's possible.
     * @param item Item object that the user want to add to this locker.
     * @param n the amount of this object the user want to add.
     * @return 0 if the n items added successfully, 1 if the n item were added successfully, but has caused
     * items to be moved to the storage, -2 if their wa s a contradiction between the given item and another
     * item in the locker, and -1 otherwise.
     */
    public int addItem(Item item, int n) {
        String itemType = item.getType();
        if (areTheirContradictions(itemType)) {
            System.out.println("Error: Your request cannot be completed at this time. Problem: the " +
                    "locker cannot contain items of type " + itemType + ", as it contains a " +
                    "contradicting item");
            return -2;
        }
        int result = super.addItem(item, n);
        if (result == 1) {
            if (0.5 * this.getCapacity() >= (n + this.getItemCount(itemType)) * item.getVolume()) {
                this.itemsInStorage.put(itemType, n + this.getItemCount(itemType));
                this.availableCapacity -= n * item.getVolume();
                return 0;
            } else return this.moveItemsToStorage(item, itemType, n);
        }
        if (result == -3){
            System.out.println("Error: Your request cannot be completed at this time. Problem: no room for " +
                    n + " items of type " + itemType);
            return -1;
        }
        return result;
    }

    /**
     * Checks how many items supposed to move to the long-tem storage.
     * If their is enough place in the storage, it will move them and add the rest to the locker.
     * @param item Item object that the user want to add to the locker.
     * @param itemType the given item type.
     * @param n the amount of this object the user want to add.
     * @return 1 If the add to the storage was successful, and -1 otherwise.
     */
    private int moveItemsToStorage(Item item, String itemType, int n){
        int itemAmount = n + this.getItemCount(itemType);
        while (itemAmount * item.getVolume() > 0.2 * this.getCapacity()) itemAmount--;
        // now itemAmount is the amount of items that can stay in the locker.
        int itemsToStorage = n + this.getItemCount(itemType) - itemAmount;

        if (Locker.storage.addItem(item, itemsToStorage) == 0){
            if (itemAmount > 0) this.itemsInStorage.put(itemType, itemAmount);
            else this.itemsInStorage.remove(itemType);

            this.availableCapacity += (itemsToStorage - n) * item.getVolume();
            System.out.println("Warning: Action successful, but has caused items to be moved to storage");
            return 1;
        } else {
            System.out.println("Error: Your request cannot be completed at this time. Problem: " +
                    "no room for " + itemsToStorage + " Items of type " + itemType +
                    " in the long-term storage.");
            return -1;
        }
    }

    /**
     * @param type the item type
     * @return true if their are contradictions in adding the given item.
     */
    private boolean areTheirContradictions(String type){
        return ((type.equals("baseball bat") && this.getItemCount("football") != 0) ||
                ((type.equals("football") && this.getItemCount("baseball bat") != 0)));
    }

    /**
     * Remove n Items of the given type from the locker, if it's possible.
     * @param item Item object that the user want to remove from this locker.
     * @param n the amount of this object the user want to remove.
     * @return 0 if the remove action was successful, -1 otherwise.
     */
    public int removeItem(Item item, int n){
        String itemType = item.getType();
        if (n < 0){
            System.out.println("Error: Your request cannot be completed at this time. Problem: cannot " +
                    "remove a negative number of items of type " + itemType);
            return -1;
        }
        if (this.getItemCount(itemType) >= n) {
            if (this.getItemCount(itemType) - n == 0) this.itemsInStorage.remove(itemType);
            else this.itemsInStorage.put(itemType, this.getItemCount(itemType) - n);
            this.availableCapacity += n * item.getVolume();
            return 0;
        }
        System.out.println("Error: Your request cannot be completed at this time. Problem: the locker " +
                "does not contain " + n + " items of type " + itemType);
        return -1;
    }

    /**
     * @return thr LongTermStorage object of the locker.
     */
    protected static LongTermStorage getStorage() {
        return Locker.storage;
    }

}
