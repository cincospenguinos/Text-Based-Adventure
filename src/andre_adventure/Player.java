package andre_adventure;

import resources.Sentient;
import resources.Item;

/**
 * Represents a Player in Andre's first Text Adventure.
 */
public class Player extends Sentient {

    private int score;

    public Player(String _name){
        instantiate(_name);

        toHit = 0.5;
        currentHitPoints = totalHitPoints = 10;

        score = 0;
    }

    public Item dropItem(String itemName) {
        if (inventory.containsKey(itemName.toLowerCase()))
            return inventory.remove(itemName);

        return null;
    }

    /**
     * Prints a message depending on the player's health.
     */
    public void checkHealth(){
        if(currentHitPoints == 10)
            System.out.println("You are feeling healthy.");
        else if(currentHitPoints >= 7)
            System.out.println("You feel a little scraped and cut.");
        else if(currentHitPoints >= 5)
            System.out.println("You are injured.");
        else if(currentHitPoints >= 2)
            System.out.println("You are seriously injured.");
        else if(currentHitPoints == 1)
            System.out.println("You are on the verge of death.");
        else
            System.err.println("This method should not be called when player has " + currentHitPoints + " hit points!");
    }

    public void setName(String _name){
        name = _name;
    }

    public void addToScore(int i){
        score += i;
    }

    public int getScore(){
        return score;
    }

    // Unused methods
    @Override
    public void talk() {}
}
