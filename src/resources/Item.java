package resources;

import java.util.Collection;

/**
 * Item class - represents an item of some sort in a command line text adventure game.
 */
public class Item {

    // The name of this item
    private String itemName;

    // The number of these there are
    private int quantity;

    // The description of the item
    private String description;

    // Indicates whether this item can be taken or not.
    private boolean canBeTaken;

    // TODO: Figure out looting corpses

    public Item(String _itemName, String _description){
        itemName = _itemName;
        quantity = 1;
        description = _description;
        canBeTaken = true;
    }

    public Item(String _itemName, String _description, boolean _canBeTaken){
        itemName = _itemName;
        description = _description;
        canBeTaken = _canBeTaken;
    }

    public Item(Sentient s){
        itemName = s.getName() + " Corpse";
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

    /**
     * Use the item.
     *
     * TODO: Implement this method somehow
     */
    public void use(){

    }

    public String toString(){
        return quantity + " " + itemName;
    }


    /**
     * Returns the name of this item.
     *
     * @return itemName - String name of this item.
     */
    public String getItemName(){
        return itemName;
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
