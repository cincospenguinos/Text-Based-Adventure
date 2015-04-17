package resources;

import java.util.List;
import java.util.ArrayList;
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
            System.out.println("There is a " + i.getItemName() + ".");
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
        // TODO: Figure out this method
    }

    /**
     * Adds the sentient passed to this room.
     * @param s - Sentient to add
     */
    public void addSentient(Sentient s){
        sentients.put(s.getName().toLowerCase(), s);
    }

    /**
     * Returns the sentient matching the name requested. This method
     * returns null if the Sentient requested does not exist.
     *
     * @param sentientName - name of the sentient
     * @return Sentient matchting the name or null.
     */
    public Sentient getSentient(String sentientName){
        if(sentients.containsKey(sentientName))
            return sentients.get(sentientName.toLowerCase());

        return null;
    }

    /**
     * Returns a list of hostile sentients that will attack the player.
     *
     * @return List of Sentient objects that are hostile to the player.
     */
    public List<Sentient> getHostileSentients(){
        ArrayList<Sentient> list = new ArrayList<>();

        // First get all the hostile sentients
        for(Sentient s : sentients.values()) {
            if(s.isDead()){ // If a sentient is dead, then drop him from the list.
                Corpse c = new Corpse(s);
                items.put(c.getItemName(), c);
                sentients.remove(s.getName().toLowerCase());
            } else if (s.isHostile())
                list.add(s);
        }

        return list;
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
     * Adds a connection between this room and the room passed, but also creates
     * a connection back from that room to this room.
     *
     * @param d - Direction to the new room
     * @param r - Room to be connected
     */
    public void addTwoWayConnection(Direction d, Room r){
        addOneWayConnection(d, r); // Add a connection from this room to r
        r.addOneWayConnection(Direction.oppositeDirection(d), this); // Add a connection from r to this room
    }

    /**
     * Adds a single connection from this room to the room passed.
     *
     * @param d - Direciton of the new room
     * @param r - Room that will be connected
     */
    public void addOneWayConnection(Direction d, Room r){
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
        // If the item exists, we will run a couple of checks
        if(items.containsKey(itemName)) {
            Item i = items.get(itemName);

            // If the item can't be taken, return null
            if(!i.canBeTaken())
                return null;

            // Otherwise, remove the item and return it.
            items.remove(itemName);
            return i;
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