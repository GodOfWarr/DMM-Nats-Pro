
package scripts.dmNature.Nodes.Ardougne;
 
 
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Players;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSPlayer;
import scripts.dmNature.Utils.Node;
 
public class EatFood extends Node {
 
    @Override
    public boolean isNodeValid() {
        return playerAttacking() || (SKILLS.HITPOINTS.getCurrentLevel() <= 15 && Inventory.getCount("Bread","Chocolate slice","Cake","Slice of Cake","2/3 Cake") >= 1);

    }
 
    @Override
    public void execute() {
        final int LOWHEALTH = Skills.getCurrentLevel(SKILLS.HITPOINTS);
        RSItem[] food = Inventory.find("Bread","Chocolate slice","Cake","Slice of Cake","2/3 Cake");
       
        if(food != null && food.length > 0)
        {
            if(food[0].click("Eat"))
            {
                Timing.waitCondition(new Condition()
                {
                    @Override
                    public boolean active() {
                        return Skills.getCurrentLevel(SKILLS.HITPOINTS) > LOWHEALTH;
                    }
                }, General.random(250, 750));
            }
        }
       
    }
   
    private int percentHealth()
    {
        return (Skills.getCurrentLevel(SKILLS.HITPOINTS)/Skills.getActualLevel(SKILLS.HITPOINTS)) * 100;
    }
   
    public boolean playerAttacking() {
        RSPlayer[] player = Players.getAll();
        for (RSPlayer p : player){
            if (p.isInteractingWithMe() && p.getAnimation() != -1) {
                System.out.println("WE'RE BEING ATTACKED, EATTTTTT");
                return true;
            }            
        } return false;
    }
 
}