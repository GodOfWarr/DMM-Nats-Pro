package scripts.dmNature.Nodes.Lumbridge;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSNPC;

import scripts.dmNature.Utils.Conditions;
import scripts.dmNature.Utils.Location;
import scripts.dmNature.Utils.Node;
import Game.Movement.*;
public class WalkToPortSarim extends Node {
	Location walkToPort = Location.walkPortSarimLoc;
	Location portSarimLand = Location.portSarimLandLoc;

	@Override
	public boolean isNodeValid() {
		// TODO Auto-generated method stub
		return (SKILLS.HITPOINTS.getActualLevel() >= 25
				&& SKILLS.THIEVING.getActualLevel() >= 28
				&& Inventory.getCount("Coins") >= 60
				&& Location.LowerThieveLoc.atLocation(Player.getPosition()) && !checkSailors());
	}

	@Override
	public void execute() {
		WebWalking.walkTo(walkToPort.getArea().getRandomTile(), Conditions.isPlayerDead(), 15000);

	}
public boolean checkSailors(){
	RSNPC[] sailors = NPCs.find("Seaman Thresnor", "Seaman Tobias",
			"Seaman Lorris");

	return sailors.length > 0;

}
}
