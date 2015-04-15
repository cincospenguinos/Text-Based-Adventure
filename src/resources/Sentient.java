package resources;

import java.util.HashMap;

/**
 * Representation of a Sentient in a text based adventure game.
 */
public abstract class Sentient {

    // The name of this Sentient
    protected String name;

    // The number of hit points this sentient has
    protected int currentHitPoints;

    // Total number of hit points this Sentient can have.
    protected int totalHitPoints;

    // The defense bonus of this sentient.
    protected int defense;

    // Double value representing the chance of hitting this sentient.
    protected double toHit;

    // True if this Sentient is dead - that is, when HP <= 0
    protected boolean isDead;

    // The inventory of this Sentient
    protected HashMap<String, Item> inventory;

    /**
     * Instantiates all the objects for this Sentient
     */
    protected void instantiate(String _name){
        inventory = new HashMap<String, Item>();
        name = _name;
    }

    /**
     * Adds the item passed into the inventory of this Sentient.
     *
     * @param i - Item to add
     */
    public void addItem(Item i){
        inventory.put(i.toString().toLowerCase(), i);
    }

    /**
     * Shows the Sentient's inventory.
     */
    public void showInventory(){
        for(Item i : inventory.values())
            System.out.println(i.toString());
    }

    /**
     * Talks to this sentient.
     */
    public abstract void talk();

    /**
     * Takes the amount of damage passed in, using defense in the
     * calculation.
     *
     * @param damage to be given
     */
    public void takeDamage(int damage){
        if(defense - damage < 0)
            currentHitPoints -= (defense - damage);

        if(currentHitPoints <= 0)
            isDead = true;
    }

    /**
     * Heals this sentient according to the amount passed up to the total
     * number of hitpoints this Sentient can hold.
     *
     * @param amount - amount to heal
     */
    public void heal(int amount){
        currentHitPoints += amount;

        if(currentHitPoints > totalHitPoints)
            currentHitPoints = totalHitPoints;
    }

    /**
     * Returns true if this Sentient is dead.
     *
     * @return true if this sentient is dead.
     */
    public boolean isDead(){
        return isDead;
    }
}
