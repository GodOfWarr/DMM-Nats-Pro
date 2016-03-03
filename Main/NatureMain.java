package scripts.dmNature.Main;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.*;
import org.tribot.api2007.Login.STATE;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.Trading.WINDOW_STATE;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.ThreadSettings;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.Painting;
import scripts.dmNature.Nodes.Ardougne.*;
import scripts.dmNature.Nodes.Karamja.WalkStepOne;
import scripts.dmNature.Nodes.Karamja.WalkStepThree;
import scripts.dmNature.Nodes.Karamja.WalkStepTwo;
import scripts.dmNature.Nodes.Lumbridge.*;
import scripts.dmNature.Nodes.Ship.CrossShip;
import scripts.dmNature.Nodes.Ship.PayPortFare;
import scripts.dmNature.Nodes.Ship.PayPortFareTwo;
import scripts.dmNature.Utils.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

@ScriptManifest(authors = { "Jabr" }, category = "Deadman", name = "God of Nat Chest" +
		"")
public class NatureMain extends Script implements MessageListening07, Painting,
		FCPaintable, FCInventoryListener {
	public ArrayList<Node> nodes = new ArrayList<Node>();
	private final Thread anti = new Thread(new AntiBan());
	public static boolean loggedout = false;
	private String status = "";
	public static boolean working = true;
	public static boolean trading = false;
	public String playerTradeName = "ENTER NAME OF MULE TO TRADE NATURES TO";
	public ABCUtil abc = new ABCUtil();
	final FCPaint PAINT = new FCPaint(this, Color.BLACK );
	public  int naturesMade = 0;
	private final FCInventoryObserver 	INV_OBSERVER = new FCInventoryObserver();
	@Override
	public void run() {
		Mouse.setSpeed(150 + (int) (Math.random() * ((300 - 250) + 1)));
		initialize();
		General.useAntiBanCompliance(true);
		INV_OBSERVER.addListener(this);


		ThreadSettings.get().setClickingAPIUseDynamic(true);
		while (true) {


			for (final Node n : nodes) {
				if (Conditions.hasDied == true) {
					Conditions.hasDied = false;
				}

				if(SKILLS.THIEVING.getActualLevel() >= 55 && Player.getPosition().getPlane() == 1){
					climbDownStairs();
				}

				if (Login.getLoginState() == STATE.LOGINSCREEN) {
					stopScript();
				}
				if (trading == false) {
					if (n.isNodeValid()) {
						status = n.getClass().getSimpleName();
						System.out.println(status);

						n.execute();
						if (Player.getAnimation() == 424) {
						} else {
							sleep(175, 265);
						}
					} else {
						abc.performRotateCamera();
						abc.performXPCheck(SKILLS.THIEVING);
						abc.performLeaveGame();
						abc.performFriendsCheck();
						abc.performEquipmentCheck();
						abc.performTimedActions(SKILLS.THIEVING);

					}
				}
				sleep(25);
			}

		}

	}

	private void initialize() {

		{

			if (Login.getLoginState() == STATE.INGAME) {

			}

			nodes.add(new ProtectSkills());
			nodes.add(new WalkToMen());
			nodes.add(new ThieveMen());
			nodes.add(new GetGold());
			nodes.add(new WalkToPortSarim());
			nodes.add(new PayPortFare());
			nodes.add(new WalkStepOne());
			nodes.add(new WalkStepTwo());
			nodes.add(new WalkStepThree());
			nodes.add(new CrossShip());
			nodes.add(new PayPortFareTwo());
			nodes.add(new WalkToNatureRoom());
			nodes.add(new PickLockDoor());
			nodes.add(new ThieveNature());
			nodes.add(new WalkToCow());
			nodes.add(new AttackCows());
			nodes.add(new WalkToMen());
			nodes.add(new ThieveStall());
			nodes.add(new EatFood());
			// nodes.add(new thieveKnights()); !!! NOTE !!! Thieving knights works, but is very buggy. Uncomment this line to use it.

		}
	}

	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch(IOException e) {
			return null;
		}
	}
	private final Image img = getImage("http://i.imgur.com/EO9YTfI.png");
	private static final long startTime = System.currentTimeMillis();


	@Override
	public String[] getPaintInfo() {
		return new String[] {"" + naturesMade,
				"" + PAINT.getPerHour(naturesMade),"" + PAINT.getTimeRan()};
	}

	@Override
	public void onPaint(Graphics g) {
		Graphics2D gg = (Graphics2D)g;
		gg.drawImage(img, 0, 300, null);
		PAINT.paint(g);

	}

	@Override
	public void personalMessageReceived(String arg0, String arg1) {
		sleep(3000, 10000);
		if (arg1.contains("im here bro")) {
			trading = true;
			playerTradeName = arg0;
			System.out.println("RECIEVED PM FROM MULE");
			if (Location.insideUpperChestHouse.atLocation(Player.getPosition())) {
				System.out
						.println("Correct location -- attempting to meet mule.");
				climbDownStairs();
				sleep(1000, 2000);
				openDoor();

			}
		}

	}

	public void climbDownStairs() {
		final RSObject[] stair = Objects.findNearest(10,
				"Staircase");
		System.out.println("Finding stairs");
		if (stair.length > 0) {
			do {
				if (stair[0].isClickable()) {
					System.out.println("Found staircase!");
					Clicking.click("Climb-down Staircase", stair[0]);
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return !onChestPlane();
						}
					}, General.random(6000, 7000));
				}
			} while (onChestPlane());
		}
	}

	private boolean onChestPlane() {
		return Player.getPosition().getPlane() == 1;
	}

	private void openDoor() {

		RSObject[] door = Objects.find(20, 11720);
		if (door.length > 0) {
			do {
				if (DynamicClicking.clickRSObject(door[0], "Open " + "Door")) {
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return Player.getPosition().equals(
									new RSTile(2674, 3304, 0));
						}
					}, General.random(4000, 5000));
				} else {
					Walking.walkTo(new RSTile(2674, 3302, 0));
					openDoor();

				}
			} while (!Player.getPosition().equals(new RSTile(2674, 3304, 0)));
		}

	}

	@Override
	public void playerMessageReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void serverMessageReceived(String arg0) {
		if (arg0.equals("Oh dear, you are dead!")) {
			Conditions.hasDied = true;
		}
	}

	@Override
	public void tradeRequestReceived(String arg0) {
		sleep(2000, 3500);
		if (arg0.equals(playerTradeName)) {
			RSPlayer playerToTrade = getPlayerByName(playerTradeName);
			if (playerToTrade != null) {
				System.out.println("Trading with: " + playerTradeName);
				if (DynamicClicking.clickRSNPC(playerToTrade, "Trade with "
						+ playerTradeName)) {
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return Trading.getWindowState() != null
									&& Trading.getWindowState().equals(
											WINDOW_STATE.FIRST_WINDOW);
						}
					}, General.random(2000, 3000));
				}
				if (Trading.getWindowState() != null
						&& Trading.getWindowState().equals(
								WINDOW_STATE.FIRST_WINDOW)) {
					Trading.offer(Inventory.getCount("Nature rune"),
							"Nature rune");
					do {
						General.sleep(1500, 3000);
						Trading.accept();
						System.out.println("Accepting trade.");
					} while (Trading.getWindowState().equals(
							WINDOW_STATE.FIRST_WINDOW));
				} else {
					trading = false;
				}

				if (Trading.getWindowState() != null
						&& Trading.getWindowState().equals(
								WINDOW_STATE.SECOND_WINDOW)) {
					do {
						General.sleep(1500, 3000);
						Trading.accept();
					} while (Trading.getWindowState() != null
							&& Trading.getWindowState().equals(
									WINDOW_STATE.SECOND_WINDOW));
				}
			}

		}
		if (Inventory.getCount("Nature rune") == 0) {
			System.out.println("Trade finished! Continuing...");

		}
		trading = false;
		sleep(600, 1500);
	}

	public RSPlayer getPlayerByName(String targetPlayerName) {
		RSPlayer[] allPlayers = Players.getAll();

		for (RSPlayer currentPlayer : allPlayers) {
			if (currentPlayer != null && currentPlayer.getName() != null
					&& currentPlayer.getName().matches(targetPlayerName)) {
				// Found him
				return currentPlayer;
			}
		}

		return null;
	}

	public boolean playerAttacking() {
		RSPlayer[] player = Players.getAll();
		for (RSPlayer p : player) {
			if (p.isInteractingWithMe() && p.getAnimation() != -1) {
				return true;
			}
		}
		return false;
	}


	@Override
	public void clanMessageReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void duelRequestReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void inventoryAdded(int id, int count) {
		// TODO Auto-generated method stub
		if (id == 561) {
			naturesMade += count;
		
		}
	}

	@Override
	public void inventoryRemoved(int id, int count) {
		// TODO Auto-generated method stub

	}

}