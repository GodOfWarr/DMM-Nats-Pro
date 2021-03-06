package scripts.dmNature.Utils;

/**
 * 	This interface holds the actual listener methods that
 * 		scripts will implement.
 * 
 *	 @author Freddy
 *
 */
public interface FCInventoryListener 
{
	/**
	 * An item was added to our inventory.
	 * 
	 * @param id The item id of the new item
	 * @param count The amount of the new item
	 */
	void inventoryAdded(int id, int count);
	
	/**
	 * An item was removed from our inventory
	 * 
	 * @param id The item id of the old item
	 * @param count The amount of the old item
	 */
	void inventoryRemoved(int id, int count);

} //END FCInventoryListener