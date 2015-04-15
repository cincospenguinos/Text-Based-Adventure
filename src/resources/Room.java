package resources;

import com.sun.javafx.tools.ant.Application;

import java.util.HashMap;

/**
 * Abstract class representing a room for a text-only adventure game.
 */
public class Room {

    // Collection of items contained in the room
    private HashMap<String, Item> items;

    // Collection of sentient beings in the room
    private HashMap<String, Sentient> sentients;

    // The collection of connected rooms
    private HashMap<Direction, Room> connectedRooms;

    // The description of this room
    private String description;

    // The name that will be given to the user
    private String publicName;

    // This is true if the room has been visited.
    private boolean visited;

    public Room(String _publicName, String _description){
        description = _description;
        publicName = _publicName;

        items = new HashMap<>();
        sentients = new HashMap<>();
        connectedRooms = new HashMap<>();

        visited = false;
    }

    /**
     * Prints out the current look of the room.
     */
    public void look(){
        System.out.println(description + "\n");

        for(Direction d : connectedRooms.keySet())
            System.out.println(connectedRooms.get(d).getPublicName() + " is to the " + d.toString() + ". ");

        System.out.println("");

        for(Item i : items.values())
            i.look();
    }

    /**
     * Drops the item passed into this room.
     *
     * @param item to drop
     */
    public void dropItem(Item item){
        items.put(item.getItemName(), item);
    }

    /**
     * Initiates a conversation with that sentient, assuming the sentient is in
     * the room. Otherwise, this method indicates that the sentient does not
     * exist in the room.
     *
     * @param sentientName - name of the sentient to speak to
     */
    public void talkTo(String sentientName){
        if(sentients.containsKey(sentientName))
            sentients.get(sentientName).talk();
        else
            System.out.println("I don't know who or what that is.");
    }

    /**
     * Looks at the item matching the itemName passed.
     *
     * @param itemName - name of item to look at
     */
    public void lookAt(String itemName){
        if(items.containsKey(itemName))
            items.get(itemName).look();
        else
            System.out.println("That doesn't seem to exist here.");
    }

    /**
     * Sets this Room as visited.
     */
    public void visit(){
        visited = true;
    }

    /**
     * Adds the connection desired, unless the direction already exists in the collection. Otherwise,
     * this method throws a DirectionExistsException.
     *
     * @param d - Direction to connect this room to
     * @param r - Room to be connected to this one.
     * @throws DirectionExistsException
     */
    public void addConnection(Direction d, Room r) throws DirectionExistsException {
        if(connectedRooms.containsKey(d))
            throw new DirectionExistsException("Direction " + d +
                    " already exists in the collection of connections.");

        connectedRooms.put(d, r);
    }

    /**
     * This returns the Room matching the direction passed. If there is no room that is connected
     * via the direction requested, this method returns null.
     *
     * @param d - Direction that is requested
     * @return Room object or null
     */
    public Room getConnection(Direction d){
        if(!connectedRooms.containsKey(d))
            return null;

        return connectedRooms.get(d);
    }

    /**
     * Adds the item passed to the collection of items included.
     * @param i - Item to add
     */
    public void addItem(Item i){
        items.put(i.getItemName().toLowerCase().toLowerCase(), i);
    }

    /**
     * Removes the item requested from the room and returns it, or returns
     * null if the item is not in the room.
     *
     * @param itemName - a string name of item
     * @return Item - the item to take, or null
     */
    public Item takeItem(String itemName){
        if(items.containsKey(itemName)) {
            Item i = items.get(itemName);

            if(!i.canBeTaken())
                return null;

            return items.get(itemName);
        }

        return null;
    }

    /*
    Getters and Setters are all here:
     */

    public String getPublicName(){
        return publicName;
    }

    public boolean isVisited(){
        return visited;
    }

    /**
     * Class meant to help debugging.
     */
    private class DirectionExistsException extends Exception {

        private DirectionExistsException(String reason){
            super(reason);
        }
    }
}