package resources;

import resources.Item;
import resources.Sentient;

/**
 * Represents the corpse of some sentient. Sentient corpse is considered an item rather than a
 * sentient being (you kind of need to be alive to be considered sentient).
 */
public class Corpse extends Item {

    public Corpse(Sentient s){
        super(s);
    }
}
