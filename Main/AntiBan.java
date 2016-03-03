package scripts.dmNature.Main;

import org.tribot.api.General;
import org.tribot.api.types.generic.Filter;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.*;
import org.tribot.api2007.Login.STATE;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.script.Script;

public class AntiBan implements Runnable {

	@Override
	public void run() {
		while (Login.getLoginState().equals(STATE.INGAME)) {
			if (Player.getRSPlayer().isInCombat()) {

				}
			}
			
			General.sleep(150,500);
		}

	public boolean isCombatPlayer() {
		if (Players.getAll() == null) {
			return false;
		}
		RSPlayer[] players = Players.getAll(new Filter<RSPlayer>() {
			@Override
			public boolean accept(RSPlayer rsPlayer) {
				return !rsPlayer.getName().equals(
						Player.getRSPlayer().getName());
			}
		});
		RSCharacter[] ents = Combat.getAttackingEntities();
		for (RSCharacter npc : ents) {
			for (RSPlayer pker : players) {
				if (pker == npc) {
					return true;
				}
			}

		}
		return false;
	}
}
