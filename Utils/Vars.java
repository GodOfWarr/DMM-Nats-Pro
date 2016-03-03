package scripts.dmNature.Utils;

import org.tribot.api2007.Inventory;

import scripts.dmNature.Main.NatureMain;

public class Vars
{
	private static Vars vars;

	public static Vars get()
	{
		return vars == null ? vars = new Vars() : vars;
	}
	
	public static void reset() 
	{
		vars = null;
	}
	
}