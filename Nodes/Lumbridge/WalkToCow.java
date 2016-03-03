package scripts.dmNature.Nodes.Lumbridge;

import org.tribot.api2007.Player;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.WebWalking;
import scripts.dmNature.Utils.Conditions;
import scripts.dmNature.Utils.Location;
import scripts.dmNature.Utils.Node;



public class WalkToCow extends Node{
Location walkToCowField = Location.walkCowField;

@Override
	public boolean isNodeValid() {
		return (SKILLS.HITPOINTS.getActualLevel() < 25 && SKILLS.THIEVING.getActualLevel() >= 28 && (!Location.cowField.atLocation(Player.getPosition())));
	}
	@Override
	public void execute() {
		WebWalking.walkTo(walkToCowField.getArea().getRandomTile(),Conditions.isPlayerDead(), 15000);
	}

	
	
}
