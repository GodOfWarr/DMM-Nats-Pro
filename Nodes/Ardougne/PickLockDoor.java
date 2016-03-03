package scripts.dmNature.Nodes.Ardougne;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.dmNature.Utils.Location;
import scripts.dmNature.Utils.Node;

public class PickLockDoor extends Node {

	@Override
	public boolean isNodeValid() {
		// TODO Auto-generated method stub
		return Location.outSideChestHouse.atLocation(Player.getPosition())
				&& SKILLS.THIEVING.getActualLevel() < 55;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		do {
			RSObject[] door = Objects.find(20, 11720);
			if (door.length > 0) {
				if (DynamicClicking.clickRSObject(door[0], "Pick-lock "
						+ "Door")) {
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return Player.getPosition().equals(
									new RSTile(2674, 3303, 0));
						}
					}, General.random(500, 1000));
				}
			}
		} while (!Player.getPosition().equals(new RSTile(2674, 3303, 0)));
		do {
			RSObject[] stairs = Objects.find(10, 16671);
			if (stairs.length > 0) {
				if (DynamicClicking.clickRSObject(stairs[0], "Climb-up "
						+ "Staircase")) {
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return Player.getPosition().getPlane() == 1;
						}
					}, General.random(500, 1000));
				}
			}
		} while (Player.getPosition().getPlane() != 1);
	}
}
