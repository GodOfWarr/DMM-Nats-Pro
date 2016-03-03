package scripts.dmNature.Nodes.Lumbridge;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills.SKILLS;

import scripts.dmNature.Utils.Location;
import scripts.dmNature.Utils.Node;

public class GetGold extends Node {
ThieveMen thieve = new ThieveMen();
	@Override
	public boolean isNodeValid() {
	
		 return (thieve.searchMen() && SKILLS.HITPOINTS.getActualLevel() >= 25 && SKILLS.THIEVING
				.getActualLevel() >= 28 && Inventory.getCount("Coins") < 60 && Location.wholeLumb.atLocation(Player.getPosition()));
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		thieve.execute();
	}

}
