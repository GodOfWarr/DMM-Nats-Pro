package scripts.dmNature.Nodes.Lumbridge;

import java.lang.reflect.Method;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSNPC;

import scripts.dmNature.Utils.Conditions;
import scripts.dmNature.Utils.Node;
import scripts.dmNature.Utils.Location;

public class WalkToMen extends Node {
	Location menLocation = scripts.dmNature.Utils.Location.menLocation;

	@Override
	public boolean isNodeValid() {
		return (!searchMen() && SKILLS.HITPOINTS.getActualLevel() == 10 && SKILLS.THIEVING
				.getActualLevel() < 28)
				|| (!searchMen() && SKILLS.HITPOINTS.getActualLevel() >= 25
						&& SKILLS.THIEVING.getActualLevel() >= 28
						&& Inventory.getCount("Coins") < 60
						&& Location.wholeLumb.atLocation(Player.getPosition()));
	}

	@Override
	public void execute() {

		WebWalking.walkTo(Location.walkMenLocation
					.getArea().getRandomTile(), Conditions.isPlayerDead(),
					15000);
	}

	public boolean searchMen() {
		RSNPC[] men = NPCs.find("Man", "Woman");
		return men.length > 0;

	}

}