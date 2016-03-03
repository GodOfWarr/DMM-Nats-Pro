package scripts.dmNature.Nodes.Ardougne;

import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import scripts.dmNature.Utils.Conditions;
import scripts.dmNature.Utils.Location;
import scripts.dmNature.Utils.Node;

public class WalkToNatureRoom extends Node {

	@Override
	public boolean isNodeValid() {
		// TODO Auto-generated method stub
		return (Player.getPosition().equals(new RSTile(2683, 3271, 0))
				&& Skills.SKILLS.THIEVING.getActualLevel() < 55);
	}

	@Override
	public void execute() {
	
		WebWalking.walkTo(Location.outSideChestHouse.getArea().getRandomTile(), Conditions.isPlayerDead(), 500);
		
	}

}
