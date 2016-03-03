package scripts.dmNature.Nodes.Ship;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;

import scripts.dmNature.Utils.Location;
import scripts.dmNature.Utils.Node;

public class PayPortFare extends Node {
	Location portSarimLand = Location.portSarimLandLoc;

	@Override
	public boolean isNodeValid() {
		// TODO Auto-generated method stub
		return (SKILLS.HITPOINTS.getActualLevel() >= 25
				&& SKILLS.THIEVING.getActualLevel() >= 28
				&& Inventory.getCount("Coins") >= 60 && portSarimLand
					.atLocation(Player.getPosition()));
	}

	@Override
	public void execute() {
		payFare();

	}

	public void payFare() {
		RSNPC[] sailors = NPCs.find("Seaman Thresnor", "Seaman Tobias",
				"Seaman Lorris");

		if (sailors.length > 0) {
			Camera.turnToTile(sailors[0]);
			if (DynamicClicking.clickRSNPC(sailors[0], "Pay-fare")) {
				Timing.waitCondition(new Condition() {

					@Override
					public boolean active() {
						return NPCChat.getClickContinueInterface() != null;
					}
				}, General.random(3000, 4000));

				if (NPCChat.getClickContinueInterface() != null
						&& NPCChat.getMessage().contains(
								"will cost you 30 coins")) {
					NPCChat.clickContinue(true);
				}
				Timing.waitCondition(new Condition() {

					@Override
					public boolean active() {
						return NPCChat.getSelectOptionInterface() != null;
					}
				}, General.random(3000, 4000));
				if (NPCChat.getSelectOptionInterface() != null
						&& NPCChat.getOptions().length > 0
						&& NPCChat.getOptions()[0].contains("Yes please.")) {
					NPCChat.selectOption("Yes please.", true);
				}
				Timing.waitCondition(new Condition() {

					@Override
					public boolean active() {
						return (NPCChat.getClickContinueInterface() != null && NPCChat
								.getMessage().contains("Yes please"));
					}
				}, General.random(3000, 4000));
				if (NPCChat.getClickContinueInterface() != null
						&& NPCChat.getMessage().contains("Yes please")) {
					if (NPCChat.clickContinue(true)) {
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Player.getPosition().distanceTo(
										Location.karamjaBoatTile) == 0;
							}
						}, General.random(7000, 8000));
					}
				}
			}
		}

		

	}

}
