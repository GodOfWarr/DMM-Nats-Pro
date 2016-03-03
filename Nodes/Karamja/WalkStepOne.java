package scripts.dmNature.Nodes.Karamja;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.Skills.SKILLS;

import scripts.dmNature.Utils.Location;
import scripts.dmNature.Utils.Node;

public class WalkStepOne extends Node {

	@Override
	public boolean isNodeValid() {
		// TODO Auto-generated method stub
		return (SKILLS.HITPOINTS.getActualLevel() >= 25
				&& SKILLS.THIEVING.getActualLevel() >= 28
				&& Inventory.getCount("Coins") >= 30 && (Location.karamjaFirstStepCheck.atLocation(Player.getPosition()) ||Location.karamjaBoatLandTile.equals(Player.getPosition()) ));
	}

	@Override
	public void execute() {
		Walking.setWalkingTimeout(General.random(15000,20000));
		WebWalking.walkTo(Location.karamjaFirstStep.getArea()
				.getRandomTile());	

	}
}
