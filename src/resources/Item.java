package resources;

/**
 * Item class - represents an item of some sort in a command line text adventure game.
 */
public class Item {

    // The name of this item
    private String publicName;

    // The name that the game engine will identify with
    private String engineName;

    // The number of these there are
    private int quantity;

    // The description of the item
    private String description;

    // Indicates whether this item can be taken or not.
    private boolean canBeTaken;

    // TODO: Figure out looting corpses

    public Item(String _publicName, String _engineName, String _description){
        publicName = _publicName;
        engineName = _engineName;
        quantity = 1;
        description = _description;
        canBeTaken = true;
    }

    public Item(String _publicName, String _engineName, String _description, boolean _canBeTaken){
        publicName = _publicName;
        engineName = _engineName;
        description = _description;
        canBeTaken = _canBeTaken;
    }

    public Item(Sentient s){
        publicName = "Corpse of " + s.getName();
        description = "This is the corpse of " + s.getName();
        canBeTaken = false;
        quantity = 1;
    }

    /**
     * Prints description of the item.
     */
    public void look(){
        System.out.println(description);
    }

    public String toString(){
        return quantity + " " + publicName;
    }


    /**
     * Returns the name of this item.
     *
     * @return publicName - String name of this item.
     */
    public String getPublicName(){
        return publicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String _description){
        description = _description;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    /**
     * Returns the quantity of this item.
     *
     * @return quantity - number of this item.
     */
    public int getQuantity(){
        return quantity;
    }

    public boolean canBeTaken(){
        return canBeTaken;
    }
}
