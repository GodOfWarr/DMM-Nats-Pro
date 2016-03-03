package scripts.dmNature.Nodes.Ardougne;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.*;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.dmNature.Utils.Chest;
import scripts.dmNature.Utils.Location;
import scripts.dmNature.Utils.Node;
import scripts.dmNature.Utils.Stall;

public class ThieveStall extends Node {

	@Override
	public boolean isNodeValid() {
		return (
				SKILLS.THIEVING.getActualLevel() >= 55
				&& SKILLS.HITPOINTS.getActualLevel() >= 25
				&& Inventory.getCount("Bread") == 0
				&& Inventory.getCount("Chocolate slice") == 0
				&& Inventory.getCount("Cake") == 0 && !Inventory.isFull()
				&& Inventory.getCount("2/3 Cake") == 0
				&& Inventory.getCount("Slice of Cake") == 0);
	}

	@Override
	public void execute() {
		do {
		if (inSpot() && !Inventory.isFull()) {

			if (Player.getRSPlayer().isInCombat()) {
				runFromGuard();
				getInPosition();

			} else {

				final int OLDXP = Skills.getXP(SKILLS.THIEVING);
				final RSObject[] sStall = Objects.findNearest(2,
						stall.getName());

				if (sStall.length > 0) {

					if (Camera.getCameraAngle() < 40)
						Camera.setCameraAngle(General.random(50, 70));

					if (DynamicClicking.clickRSObject(sStall[0], "Steal-from "
							+ stall.getName())) {
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Skills.getXP(SKILLS.THIEVING) > OLDXP;
							}
						}, General.random(500, 1000));

						General.sleep((stall.getRespawn() - 1000),
								stall.getRespawn());
					} // click
				} // if

			}
		}

		else if (inLoc() && !inSpot()) {
			getInPosition();
		}

		if (!inLoc() && !inSpot()) {

			walkToLoc();

		}
		General.sleep(250);
		} while (Inventory.getCount("Cake") <= 3);
		
		if (Inventory.getCount("Cake") >= 3) {
			System.out.println("Got food! Continuing...");

		}

	}

	public static Stall stall = Stall.CAKE;

	private boolean inLoc() {
		return stall.getArea().contains(Player.getPosition());
	}

	private boolean inSpot() {
		return Player.getPosition().equals(stall.getTile());
	}

	private void walkToLoc() {
		if (WebWalking.walkTo(stall.getTile())) {
			Timing.waitCondition(new Condition() {

				@Override
				public boolean active() {
					General.sleep(200);
					return inLoc();
				}
			}, General.random(3000, 6000));
		}
	}

	private void runFromGuard() {
		if (WebWalking.walkTo(new RSTile(2670, 3310, 0))) {
			Timing.waitCondition(new Condition() {

				@Override
				public boolean active() {

					return false;
				}
			}, General.random(1000, 1200));
		}
	}

	private void getInPosition() {
		ABCUtil abc = null;
		abc = new ABCUtil();
		if (Player.getPosition().distanceTo(stall.getTile()) >= abc.INT_TRACKER.WALK_USING_SCREEN
				.next()) {
			DynamicClicking.clickRSTile(stall.getTile(), "Walk here");
			abc.INT_TRACKER.WALK_USING_SCREEN.reset();
		} else {
			Walking.walkTo(stall.getTile());
		}

		Timing.waitCondition(new Condition() {

			@Override
			public boolean active() {
				General.sleep(200);
				return inSpot() && !Player.isMoving();
			}
		}, General.random(500, 750));
	}

	public void climbDownStairs() {
		final RSObject[] stair = Objects.findNearest(10,
				Chest.NATURE.getStairDown());

		if (stair.length > 0) {
			if (stair[0].isClickable()) {
				System.out.println("Found staircase!");
				Clicking.click("Climb-down Staircase", stair[0]);
				Timing.waitCondition(new Condition() {

					@Override
					public boolean active() {
						return !onChestPlane();
					}
				}, General.random(6000, 10000));
			}
		}
	}

	private boolean onChestPlane() {
		return Player.getPosition().getPlane() == 1;
	}

	private void openDoor() {
		RSObject[] door = Objects.find(20, 11720);
		if (door.length > 0) {
			System.out.println("Found a door...");
			System.out.println(door[0]);
			if (DynamicClicking.clickRSObject(door[0], "Open " + "Door")) {
				System.out.println("Found door!");
				Timing.waitCondition(new Condition() {

					@Override
					public boolean active() {
						Location inHouse = Location.insideChestHouse;
						return inHouse.atLocation(Player.getPosition());
					}
				}, General.random(4000, 5000));
			}
		}

	}

}