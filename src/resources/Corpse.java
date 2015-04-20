package resources;

import java.util.Collection;

/**
 * Represents the corpse of some sentient. Sentient corpse is considered an item rather than a
 * sentient being (you kind of need to be alive to be considered sentient).
 */
public class Corpse extends Item {

    // The original sentient that was once this corpse
    private Sentient corpse;

    public Corpse(Sentient s){
        super(s);
        corpse = s;
    }

    /**
     * Returns the the inventory of the corpse.
     *
     * @return Collection of items that this corpse has.
     */
    public Collection<Item> lootCorpse(){ return corpse.getInventory(); }

    public Item getItem(String itemName) {
        if (corpse.hasItem(itemName))
            return corpse.dropItem(itemName); // dropItem() does what we would want "takeItem()" to do

        return null;
    }
}
