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

public class PayPortFareTwo extends Node {

	@Override
	public boolean isNodeValid() {
		
		return (SKILLS.HITPOINTS.getActualLevel() >= 25
				&& SKILLS.THIEVING.getActualLevel() >= 28
				&& Inventory.getCount("Coins") >= 30 && Location.karamjaPort.atLocation(Player.getPosition()));
	}

	@Override
	public void execute() {
		walkToNewPort();

	}

	public void walkToNewPort() {
		if (NPCs.find("Customs officer").length > 0) {

			RSNPC[] sailor = NPCs.find("Customs officer");
			Camera.turnToTile(sailor[0]);
			if (sailor.length > 0 && sailor[0].isOnScreen()) {
				if (DynamicClicking.clickRSNPC(sailor[0], "Pay-Fare")) {
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return NPCChat.getClickContinueInterface() != null;
						}
					}, General.random(3000, 4000));

					if (NPCChat.getClickContinueInterface() != null
							&& NPCChat.getMessage().contains("I help you?")) {
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
							&& NPCChat.getOptions()[0]
									.contains("I journey on this ship")) {
						NPCChat.selectOption("Can I journey on this ship?",
								true);
					}
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return NPCChat.getClickContinueInterface() != null;
						}
					}, General.random(3000, 4000));
					if (NPCChat.getClickContinueInterface() != null
							&& NPCChat.getMessage().contains(
									"I journey on this ship?")) {
						NPCChat.clickContinue(true);
					}
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return NPCChat.getClickContinueInterface() != null;
						}
					}, General.random(3000, 4000));
					if (NPCChat.getClickContinueInterface() != null
							&& NPCChat.getMessage().contains(
									"to be searched before you")) {
						NPCChat.clickContinue(true);
					}
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return NPCChat.getSelectOptionInterface() != null;
						}
					}, General.random(3000, 4000));
					if (NPCChat.getSelectOptionInterface() != null
							&& NPCChat.getOptions().length > 1
							&& NPCChat.getOptions()[1]
									.contains("Search away, I have nothing to hide.")) {
						NPCChat.selectOption(
								"Search away, I have nothing to hide.", true);
					}
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return NPCChat.getSelectOptionInterface() != null;
						}
					}, General.random(3000, 4000));
					if (NPCChat.getClickContinueInterface() != null
							&& NPCChat.getMessage().contains(
									"Search away, I have nothing to hide.")) {
						NPCChat.clickContinue(true);
					}
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return NPCChat.getClickContinueInterface() != null;
						}
					}, General.random(3000, 4000));
					if (NPCChat.getClickContinueInterface() != null
							&& NPCChat.getMessage().contains(
									"Well you've got some odd stuff")) {
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
							&& NPCChat.getOptions()[0].contains("Ok.")) {
						NPCChat.selectOption("Ok.", true);
					}
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return NPCChat.getClickContinueInterface() != null;
						}
					}, General.random(3000, 4000));
					if (NPCChat.getClickContinueInterface() != null
							&& NPCChat.getMessage().contains("Ok.")) {
						if (NPCChat.clickContinue(true)) {
							Timing.waitCondition(new Condition() {

								@Override
								public boolean active() {
									return Player.getPosition().distanceTo(
											Location.ardBoatTile) == 0
											&& Inventory.getAll().length > 0;
								}
							}, General.random(7000, 8000));
						}
					}
				}
			}
		}
	}

}
