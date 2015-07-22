package resources;

import resources.Item;

/**
 * Represents a weapon in the game
 */
public class Weapon extends Item {

    // The bonus applied to the player's toHit value
    private double toHitBonus;

    // The bonus applied to the player's damage value
    private int damageBonus;

    public Weapon(String _publicName, String _engineName, String _description, double _toHitBonus, int _damageBonus){
        super(_publicName, _engineName, _description);

        toHitBonus = _toHitBonus;
        damageBonus = _damageBonus;
    }


    public double getToHitBonus(){
        return toHitBonus;
    }

    public int getDamageBonus(){
        return damageBonus;
    }
}
