package scripts.dmNature.Nodes.Ardougne;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;
import scripts.dmNature.Utils.Chest;
import scripts.dmNature.Utils.Location;
import scripts.dmNature.Utils.Node;

public class ThieveNature extends Node {
	Location inArd = Location.ardougneLocation;
	Location outSideHouse = Location.outSideChestHouse;
	Location insideHouse = Location.insideChestHouse;
	Location upperFloor = Location.insideUpperChestHouse;
	ABCUtil abc = new ABCUtil();
	public static Chest chest;

	@Override
	public boolean isNodeValid() {

		return SKILLS.THIEVING.getActualLevel() >= 28
				&& upperFloor.atLocation(Player.getPosition())
				&& SKILLS.THIEVING.getActualLevel() < 55;
	}

	@Override
	public void execute() {

		if (onChestPlane()) {
			final int OLDXP = Skills.getXP(SKILLS.THIEVING);
			final RSObject[] CHEST = Objects.find(15, 11736);
			RSObject[] CHESTCHECK = Objects.find(15, 11743);
			if (CHEST.length > 0) {
				if (Player.getAnimation() != 536 || CHESTCHECK.length <= 0)
					if (CHEST[0].isClickable()) {
						General.sleep(abc.DELAY_TRACKER.SWITCH_OBJECT.next());
				        abc.DELAY_TRACKER.SWITCH_OBJECT.reset();
				        
						if (Clicking.click(
								"Search for traps Chest", CHEST[0])) {
							Timing.waitCondition(new Condition() {

								@Override
								public boolean active() {
									return Skills.getXP(SKILLS.THIEVING) > OLDXP
											|| CHEST[0] == null;
								}
							}, General.random(2500, 3000));
							if (Skills.getXP(SKILLS.THIEVING) > OLDXP) {
							
								General.sleep(3000, 3500);
							}
							
							
						}
					}
			}
		}

		else if (!onChestPlane() && atDoor()) {
			final RSObject[] door = Objects.findNearest(10, chest.getDoorId());

			final RSObject[] stair = Objects
					.findNearest(10, chest.getStairUp());

			if (stair.length > 0) {
				if (stair[0].isClickable()) {
					Clicking.click("Climb-up Staircase", stair[0]);
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return onChestPlane();
						}
					}, General.random(1000, 2000));
				}
			}
		}

		else {
			walkToDoor();
		}

	}

	private boolean onChestPlane() {
		return Player.getPosition().getPlane() == 1;
	}

	private boolean atDoor() {
		return inArd.atLocation(Player.getPosition());
	}

	private boolean atInsideDoor() {
		return outSideHouse.atLocation(Player.getPosition());
	}

	private void walkToDoor() {
		if (WebWalking.walkTo(chest.getRstile())) {
			Timing.waitCondition(new Condition() {

				@Override
				public boolean active() {
					return atDoor() && Player.getAnimation() == -1;
				}
			}, General.random(3000, 6000));
		}
	}
}
