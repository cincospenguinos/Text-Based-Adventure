package resources;

import java.util.Collection;

/**
 * Represents the corpse of some sentient. Sentient corpse is considered an item rather than a
 * sentient being (you kind of need to be alive to be considered sentient).
 */
public class Corpse extends Item {

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
    public Collection<Item> lootCorpse(){
        return corpse.getInventory();
    }
}
