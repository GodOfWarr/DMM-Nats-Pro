package scripts.dmNature.Nodes.Lumbridge;

import Game.Antiban.Antiban;
import Game.Combat.Combat;
import Game.Painting.PaintHelper;
import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.*;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.dmNature.Utils.Location;
import scripts.dmNature.Utils.Node;


public class AttackCows extends Node {
	private Camera camera = new Camera();
	ABCUtil abc = new ABCUtil();

	@Override
	public boolean isNodeValid() {
		// TODO Auto-generated method stub
		return SKILLS.HITPOINTS.getActualLevel() != 25
				&& SKILLS.THIEVING.getActualLevel() >= 28
				&& Location.cowField.atLocation(Player.getPosition());
	}

	@Override
	public void execute() {
		if (!Combat.isUnderAttack() && SKILLS.HITPOINTS.getCurrentLevel() != 0) {

			PaintHelper.statusText = "Searching for chickens";

			final RSNPC npcs[] = Antiban.orderOfAttack(NPCs.findNearest("Cow"));

			if (npcs == null || npcs.length == 0) {
				// Nothing to attack, idling
				Antiban.doIdleActions();
				return;
			}

			// NPCs to attack are found.
			Antiban.doActivateRun();
			Antiban.waitDelay(true);

			if (Combat.attackNPCs(npcs, true)) {

				if (!Combat.isUnderAttack()) {

					Antiban.doIdleActions();
				}
				Antiban.setIdle(false);
			}
		} else {
			
		}
	}

	private RSNPC getNearestNPC(String id1) {
		RSNPC[] npcPool = NPCs.findNearest(id1);
		RSNPC npcToClick = null;
		if (npcPool.length > 0) {
			if (!(npcPool[0].isInCombat())) {
				npcToClick = npcPool[0];
			}

			if (npcPool.length > 1 && abc.BOOL_TRACKER.USE_CLOSEST.next()) {
				if (npcPool[1].getPosition().distanceToDouble(npcPool[0]) < 5.0) {
					if (!(npcPool[1].isInCombat())) {
						npcToClick = npcPool[1];
					}
				}
			}
			abc.BOOL_TRACKER.USE_CLOSEST.reset();
			return npcToClick;
		}
		return null;
	}

	private boolean canReachCow(RSNPC npc) {
		return PathFinding.canReach(npc.getPosition(), false);
	}

	public boolean attackMonster() {
		RSNPC monster = getNearestNPC("Cow");

		if (monster != null && monster.isValid() && !monster.isInCombat()) {
			RSModel model = monster.getModel();
			General.sleep(abc.DELAY_TRACKER.NEW_OBJECT.next());
			abc.DELAY_TRACKER.NEW_OBJECT.reset();
			if (model != null && Clicking.click("Attack", model)) {
				if (!monster.isOnScreen()) {
					Camera.turnToTile(new RSTile(monster.getPosition().getX()
							+ General.random(-4, 5), monster.getPosition()
							.getY() + General.random(-4, 3)));
				}
				if (!canReachCow(monster)) {
					WebWalking.walkTo(monster.getPosition());

				}

				General.sleep(200, 350);

				// Check if we are in combat
				long t = System.currentTimeMillis();

			}

			abc.BOOL_TRACKER.HOVER_NEXT.next();
			abc.BOOL_TRACKER.HOVER_NEXT.reset();

		}

		return false;
	}

}
