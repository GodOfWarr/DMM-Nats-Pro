package scripts.dmNature.Nodes.Ship;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;

import scripts.dmNature.Utils.Location;
import scripts.dmNature.Utils.Node;

public class CrossShip extends Node {

	@Override
	public boolean isNodeValid() {
		// TODO Auto-generated method stub
		return isPlank();
	}

	@Override
	public void execute() {
		WalkPlank();

	}

	public boolean isPlank() {
		if (Player.getPosition().getPlane() == 1) {
			RSObject[] gankplank = Objects.find(20,"Gangplank");
			if (gankplank.length > 0) {
				return true;
			}
		}
		return false;
	}

	public void WalkPlank() {
		if (Player.getPosition().getPlane() == 1) {
			RSObject[] gankplank = Objects.find(20,"Gangplank");
			if (gankplank.length > 0) {
				if (!gankplank[0].isOnScreen())
					Camera.turnToTile(gankplank[0].getPosition());

				if (DynamicClicking.clickRSObject(gankplank[0], "Cross")) {
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return Player.getPosition().equals(
									Location.ardBoatLandTile);
						}
					}, General.random(3000, 4000));
				}
			}
		}
	}
}
