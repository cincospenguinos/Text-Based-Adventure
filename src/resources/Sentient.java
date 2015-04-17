package resources;

import java.util.HashMap;
import java.util.Collection;

/**
 * Representation of a Sentient in a text based adventure game.
 */
public class Sentient {

    // The name of this Sentient
    private String name;

    // The number of hit points this sentient has
    private int currentHitPoints;

    // Total number of hit points this Sentient can have.
    private int totalHitPoints;

    // The defense bonus of this sentient.
    private int defense;

    // Double value representing the chance of hitting this sentient.
    private double toHit;

    // The bonus applied toHit - often from holding a weapon
    private double toHitBonus;

    // The amount of guaranteed damage that this Sentient will do
    private int damage;

    // The bonus applied to damage - often from holding a weapon
    private int damageBonus;

    // True if this Sentient is dead - that is, when HP <= 0
    private boolean isDead;

    // True if this Sentient is hostile - that is, will attack
    private boolean isHostile;

    // The inventory of this Sentient
    private HashMap<String, Item> inventory;

    public Sentient(String _name, int hitPoints, int _defense, int _damage, double _toHit, boolean _isHostile){
        name = _name;
        currentHitPoints = hitPoints;
        totalHitPoints = hitPoints;
        defense = _defense;
        toHit = _toHit;
        damage = _damage;
        isHostile = _isHostile;
        isDead = false;

        inventory = new HashMap<>();
    }

    /**
     * Instantiates all the objects for this Sentient
     */
    protected void instantiate(String _name){
        inventory = new HashMap<>();
        name = _name;
    }

    /**
     * Adds the item passed into the inventory of this Sentient.
     *
     * @param i - Item to add
     */
    public void addItem(Item i){
        inventory.put(i.getItemName().toLowerCase(), i);
    }

    /**
     * Returns true if the Sentient has the item matching the name passed.
     *
     * @param itemName - String name of the item
     * @return true if the item is held by the Sentient
     */
    public boolean hasItem(String itemName){
        return inventory.keySet().contains(itemName);
    }

    /**
     * Returns whatever item is requested, or null if the item does not exist in the
     * inventory.
     * @param itemName - Name of the item to get
     * @return null or an Item object
     */
    public Item getItem(String itemName){
        if(!hasItem(itemName))
            return null;

        return inventory.get(itemName);
    }

    /**
     * Drops the item from this Sentient's inventory
     * @param itemName - name of item to drop
     * @return null or the item that is to be dropped
     */
    public Item dropItem(String itemName) {
        if (hasItem(itemName))
            return inventory.remove(itemName);

        return null;
    }

    /**
     * Shows the Sentient's inventory.
     */
    public void showInventory(){
        for(Item i : inventory.values())
            System.out.println(i.toString());
    }

    /**
     * Attacks the sentient passed.
     * @param s - The Sentient to attack
     * @return true if the attak hits.
     */
    public boolean attack(Sentient s){
        if(Math.random() < toHit) {
            s.takeDamage(damage + (int)(Math.random() / 3));
            return true;
        } else
            return false;
    }

    /**
     * Takes the amount of damage passed in, using defense in the
     * calculation.
     *
     * @param damage to be given
     */
    public void takeDamage(int damage){
        if(defense - damage < 0)
            currentHitPoints += (defense - damage);

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
     * Applies the bonus passed to the toHit score
     * @param bonus - The bonus to apply
     */
    public void applyToHitBonus(double bonus){
        toHitBonus = bonus;
        toHit += toHitBonus;
    }

    /**
     * Removes the to-hit bonus
     */
    public void removeToHitBonus(){
        toHit -= toHitBonus;
        toHitBonus = 0;
    }

    /**
     * Applies the bonus passed to the damage score
     *
     * @param bonus - Bonus to apply to damage
     */
    public void applyDamageBonus(int bonus){
        damageBonus = bonus;
        damage += damageBonus;
    }

    /**
     * Remove the bonus to damage.
     */
    public void removeDamageBonus(){
        damage -= damageBonus;
        damageBonus = 0;
    }

    /**
     * Returns true if this Sentient is dead.
     *
     * @return true if this sentient is dead.
     */
    public boolean isDead(){
        return isDead;
    }

    public boolean isHostile() { return isHostile; }

    public String getName(){
        return name;
    }

    public void setName(String _name){
        name = _name;
    }

    public int getCurrentHitPoints(){
        return currentHitPoints;
    }

    public Collection<Item> getInventory(){
        return inventory.values();
    }
}
