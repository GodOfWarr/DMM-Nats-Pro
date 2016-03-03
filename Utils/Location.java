package scripts.dmNature.Utils;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

public enum Location {

	knightLocation("KnightLocation", new RSArea(new RSTile(2679, 3288, 0),
			new RSTile(2647, 3328, 0))),
	menLocation("MenLocation", new RSArea(new RSTile(3215, 3243, 0),
			new RSTile(3234, 3234, 0))),
	walkMenLocation("WalkMenLocation",
			new RSArea(new RSTile(3225, 3236, 0), new RSTile(3220, 3241, 0))),
	lumbStartLocation(
			"Lumbridge-Spawn", new RSArea(new RSTile(3202, 3245, 0),
					new RSTile(3240, 3201, 0))), ardougneLocation(
			"Ardougne-ChestHouse", new RSArea(new RSTile(2679, 3297, 0),
					new RSTile(2669, 3305, 0))), outSideChestHouse(
			"Ardougne-OutsideChestHouse", new RSArea(new RSTile(2672, 3305, 0),
					new RSTile(2673, 3305, 0))), insideChestHouse(
			"Ardougne-InsideChestHouse", new RSArea(new RSTile(2673, 3303, 0),
					new RSTile(2675, 3299, 0))), insideUpperChestHouse(
			"Ardougne-InsideUpperChestHouse", new RSArea(new RSTile(2675, 3301,
					1), new RSTile(2671, 3299, 1))), walkCowField(
			"Walk-CowField", new RSArea(new RSTile(3256, 3268, 0), new RSTile(
					3260, 3264, 0))), cowField("CowField-Location", new RSArea(
			new RSTile(3265, 3255, 0), new RSTile(3240, 3299, 0))), walkPortSarimLoc(
			"PortSarim-Location", new RSArea(new RSTile(3027, 3232, 0),
					new RSTile(3028, 3220, 0))), portSarimLandLoc(
			"PortSarimLand-Location", new RSArea(new RSTile(3026, 3210, 0),
					new RSTile(3029, 3232, 0))) , karamja(
							"karamja-Location", new RSArea(new RSTile(2970, 3227, 0),
									new RSTile(2771, 3120, 0))), karamjaPort(
											"karamjaPort-Location", new RSArea(new RSTile(2773, 3219, 0),
													new RSTile(2766, 3227, 0))), karamjaFirstStep(
															"karamjaPort-Location", new RSArea(new RSTile(2907, 3173, 0),
																	new RSTile(2905, 3174, 0))), karamjaSecondStep(
																			"karamjaPort-Location", new RSArea(new RSTile(2816, 3182, 0),
																					new RSTile(2817, 3183, 0))), karamjaFirstStepCheck(
																							"karamjaFirststep-Location", new RSArea(new RSTile(2959, 3135, 0),
																									new RSTile(2913, 3180, 0))), karamjaSecondStepCheck(
																											"karamjaSecondstep-Location", new RSArea(new RSTile(2914, 3206, 0),
																													new RSTile(2826, 3147, 0))), karamjaThirdStepCheck(
																															"karamjaThreestep-Location", new RSArea(new RSTile(2818, 3180, 0),
																																	new RSTile(2771, 3227, 0))), karamjaThirdStep(
																																			"karamjaThreestepWalk-Location", new RSArea(new RSTile(2773, 3221, 0),
																																					new RSTile(2771, 3218, 0))), LowerThieveLoc(
																																							"LowerThieveLoc-Location", new RSArea(new RSTile(3266,3200,0),
																																									new RSTile(3047,3282,0))), wholeLumb(
																																											"WholeLumb-Location", new RSArea(new RSTile(3265,3296,0),
																																													new RSTile(3207,3196,0)));
	
	public final static RSTile karamjaBoatTile = new RSTile(2956, 3143, 1);
	public final static RSTile karamjaBoatLandTile = new RSTile(2956, 3146, 0);
	public final static RSTile ardBoatTile = new RSTile(2683, 3268, 1);
	public final static RSTile ardBoatLandTile = new RSTile(2683, 3271, 0);
	private String name;
	private RSArea rsarea;

	Location(String name, RSArea rsarea) {
		this.name = name;
		this.rsarea = rsarea;
	}

	public RSArea getArea() {
		return this.rsarea;
	}

	public String getName() {
		return this.name();
	}

	public boolean atLocation(RSTile tile) {
		for (RSTile til: this.rsarea.getAllTiles()) {
			if (til.distanceTo(tile) < 4){
				return true;
			}
		}
		return false;
		
	}

	public void setArea(RSArea area) {
		this.rsarea = area;
	}

	public static void walkToLocation(final Location location) {
		DPathNavigator dpath = new DPathNavigator();
		dpath.setDoorCacheTime(2000);
		if (dpath.traverse(location.getArea().getRandomTile())) {

			Timing.waitCondition(new Condition() {

				@Override
				public boolean active() {

					return (location.atLocation(Player.getPosition()) || Conditions.hasDied == true);
				}
			}, General.random(1000, 2000));
		}

	}
	
	public static void walkToTile(final RSTile tile) {
		DPathNavigator dpath = new DPathNavigator();
		dpath.setDoorCacheTime(2000);
		if (dpath.traverse(tile)) {

			Timing.waitCondition(new Condition() {

				@Override
				public boolean active() {

					return (tile.equals(Player.getPosition()) || Conditions.hasDied == true);
				}
			}, General.random(1000, 2000));
		}

	}

}