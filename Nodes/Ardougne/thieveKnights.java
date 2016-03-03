package scripts.dmNature.Nodes.Ardougne;



import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.*;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.dmNature.Utils.Chest;
import scripts.dmNature.Utils.Location;
import scripts.dmNature.Utils.Node;

public class thieveKnights extends Node {
    Location menLocation = Location.knightLocation;
    ABCUtil abc = new ABCUtil();
    public boolean isNodeValid() {
        return (searchMen() && SKILLS.THIEVING.getActualLevel() >= 55 && !Location.karamja
                .atLocation(Player.getPosition()) && SKILLS.HITPOINTS.getCurrentLevel() > 15);
    }

    @Override
    public void execute() {
        pickpocketMen();
    }

    public boolean searchMen() {
        RSNPC[] men = NPCs.find(3108);
        return men.length > 0;

    }

    public void climbDownStairs() {
        final RSObject[] stair = Objects.findNearest(10,
                Chest.NATURE.getStairDown());

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

    private int percentHealth()
    {
        return (Skills.getCurrentLevel(SKILLS.HITPOINTS)/Skills.getActualLevel(SKILLS.HITPOINTS)) * 100;
    }

    public boolean canReach(RSNPC victim) {
        return PathFinding.canReach(Player.getPosition(), victim.getPosition(),
                false);
    }

    public void pickpocketMen() {


        if (onChestPlane()) {
            System.out.println("We're upstairs, climbing down.");
            climbDownStairs();
        }

        final int OLDXP = Skills.getXP(SKILLS.THIEVING);
        final RSNPC victim[] = NPCs.findNearest(3108);


        if (victim.length > 0) {
            if (victim[0].isOnScreen()) {
                if (!canReach(victim[0])) {
                    System.out.println("walking to victim");
                    Location.walkToTile(victim[0].getPosition());
                }

                if (Clicking.click("Pickpocket",victim[0])) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            return Skills.getXP(SKILLS.THIEVING) > OLDXP
                                    || !victim[0].isOnScreen();
                        }
                    }, General.random(1000, 1500));
                }
            } else {
                if (!victim[0].isOnScreen()) {
                    Camera.turnToTile(new RSTile(victim[0].getPosition().getX()
                            + General.random(-4, 5), victim[0].getPosition()
                            .getY() + General.random(-4, 3)));
                }
                if (!victim[0].isOnScreen()) {
                    Walking.walkTo(victim[0].getPosition());
                    Timing.waitCondition(new Condition() {

                        @Override
                        public boolean active() {
                            return victim[0].isOnScreen()
                                    || victim[0] == null
                                    || Player.getPosition().distanceTo(
                                    victim[0]) <= 4;
                        }
                    }, General.random(500, 1000));
                }
            }
        }
    }

}
