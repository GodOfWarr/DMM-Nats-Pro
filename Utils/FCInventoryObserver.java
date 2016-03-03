package scripts.dmNature.Utils;

import java.util.HashMap;
import java.util.HashSet;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

/**
 * 	This class will observe a player's inventory, and notify the inventory
 * 		listener of any changes
 * 
 * 	@author Freddy
 *
 */
public class FCInventoryObserver extends Thread 
{
	
	private final int CYCLE_TIME = 400; //How long our run() method will sleep for every cycle
	
	//The listeners that have to be notified - No duplicates.
	private HashSet<FCInventoryListener> listeners = new HashSet<FCInventoryListener>();
	
	//The inventory maps, so we can compare and see if the inventory changed
	private HashMap<Integer, Integer> oldInventory = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> newInventory;

	/**
	 * 	The public constructor
	 * 
	 * 	START FCInventoryObserver()
	 * 		set old inventory
	 * 		start running
	 * 	END FCInventoryObserver()
	 */
	public FCInventoryObserver()
	{
		//set old inventory
		fillInventoryMap(oldInventory);
		
		//start running
		start();
		
	} //END FCInventoryObserver()
	
	/**
	 * 	run() - This method will handle the processing and main logic of the class.
	 * 
	 *	START run()
	 *		handle exceptions
	 *			WHILE(infinite loop)
	 *				fill new inventory map
	 *				FOR(each index in new inventory)
	 *					IF(old inventory does not contain item in new inventory)
	 *						notify listeners of addition
	 *					END IF
	 *				END FOR
	 *				set last inventory to new inventory
	 *				sleep for CYCLE_TIME
	 *			END WHILE
	 *		END handle exceptions
	 *	END run()
	 */
	public void run()
	{
		//handle exceptions
		try
		{
			//WHILE(infinite loop)
			while(true)
			{
				newInventory = new HashMap<Integer, Integer>();
				
				//fill new inventory map
				fillInventoryMap(newInventory);
				
				//FOR(each index in new inventory)
				for(int i : newInventory.keySet())
				{
					//IF(old inventory does not contain item in new inventory)
					if(!oldInventory.containsKey(i))
					{
						//notify listeners of addition
						notifyListenersOfAddition(i, newInventory.get(i));
						
					}
					else //(old inventory does contain item in new inventory)
					{					
						//IF(the items have been added)
						if(newInventory.get(i) > oldInventory.get(i))
						{
							
							//notify listeners of addition
							notifyListenersOfAddition(i, newInventory.get(i) - oldInventory.get(i));
							
						} //END IF
					}
					
				} //END FOR
				
				for(int x : oldInventory.keySet())
				{
					if(!newInventory.containsKey(x))
					{
						notifyListenersOfRemoval(x, oldInventory.get(x));

					}
					else
					{
						if(oldInventory.get(x) > newInventory.get(x))
						{
							notifyListenersOfRemoval(x, oldInventory.get(x) - newInventory.get(x));
							
						}
					}
				}
			
				//set last inventory to new inventory
				oldInventory = newInventory;
				
				//sleep for CYCLE_TIME
				sleep(CYCLE_TIME);
				
			} //END WHILE(infinite loop)
		}
		catch(Exception e)
		{
			e.printStackTrace();
		} //END handle exceptions
		
	} //END RUN()
	
	/**
	 * 	fillInventoryMap(HashMap<Integer, Integer> map) - This will fill the designated
	 * 		hash map with the player's current inventory
	 * 
	 * 	START fillInventoryMap(HashMap<Integer, Integer> map)
	 * 		clear map
	 * 		FOR(each item i in inventory)
	 * 			add item to map
	 * 		END FOR
	 * 	END fillInventoryMap(HashMap<Integer, Integer> map)
	 * 
	 * 	@param map The map to fill
	 */
	public void fillInventoryMap(HashMap<Integer, Integer> map)
	{
		//clear map
		map.clear();
		
		//FOR(each item i in inventory)
		for(RSItem i : Inventory.getAll())
		{
			//add item to map
			map.put(i.getID(), Inventory.getCount(i.getID()));
			
		} //END FOR
		
	} //END fillInventoryMap(HashMap<Integer, Integer> map)
	
	/**
	 * 
	 * @param id
	 * @param amt
	 */
	public void notifyListenersOfAddition(int id, int amt)
	{
		for(FCInventoryListener l : listeners)
		{
			l.inventoryAdded(id, amt);
		}
	}
	
	/**
	 * 
	 * @param id
	 * @param amt
	 */
	public void notifyListenersOfRemoval(int id, int amt)
	{
		for(FCInventoryListener l : listeners)
		{
			l.inventoryRemoved(id, amt);
		}
	}
	
	/**
	 * 	Adds an inventory listener to this observer. We have a set of listeners
	 * 		just in case we have multiple classes listening.
	 * 
	 * 	START addListener(FCInventoryListener listener)
	 * 		IF(listener is not null)
	 * 			add listener to set
	 * 		END IF
	 * 	END addListener(FCInventoryListener listener)
	 * 
	 * @param listener The FCInventoryListener to add to the set
	 */
	public void addListener(FCInventoryListener listener)
	{
		//IF(listener is not null)
		if(listener != null)
		{
			//add listener to set
			listeners.add(listener);
			
		} //END IF
		
	} //END addListener(FCInventoryListener listener)
	
} //END FCInventoryObservers
