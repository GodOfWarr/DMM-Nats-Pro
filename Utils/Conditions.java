package scripts.dmNature.Utils;

import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;

import scripts.dmNature.Main.NatureMain;

public class Conditions {
	public static boolean hasDied = false;
	public static Condition isPlayerDead() {
		Condition conditons = new Condition() {
			@Override
			public boolean active() {
				return Conditions.hasDied == true;
			}

		};
		return conditons;
	}
}
