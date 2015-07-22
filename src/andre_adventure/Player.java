package andre_adventure;

import resources.Sentient;
import resources.Weapon;

/**
 * Represents a Player in Andre's first Text Adventure.
 */
public class Player extends Sentient {

    // The current score of the player
    private int score;

    // The current equipped weapon
    private Weapon equippedWeapon;

    // TODO: Figure out what to do about weapons

    public Player(String _name){
        super(_name, "It's you, silly!", 10, 3, 3, 0.5, false); // The description was added here to avoid compiler errors
        equippedWeapon = null;

        score = 0;
    }

    /**
     * Unequipes the weapon currently held by the Player.
     */
    public void unequipWeapon(){
        equippedWeapon = null;
        removeDamageBonus();
        removeToHitBonus();
    }

    /**
     * Prints a message depending on the player's health.
     */
    public void checkHealth(){
        int currentHP = getCurrentHitPoints();
        if(currentHP == 10)
            System.out.println("You are feeling healthy.");
        else if(currentHP >= 7)
            System.out.println("You feel a little scraped and cut.");
        else if(currentHP >= 5)
            System.out.println("You are injured.");
        else if(currentHP >= 2)
            System.out.println("You are seriously injured.");
        else if(currentHP == 1)
            System.out.println("You are on the verge of death.");
        else
            System.err.println("This method should not be called when player has " + currentHP + " hit points!");
    }

    public void addToScore(int i){
        score += i;
    }

    public int getScore(){
        return score;
    }
}
