package resources;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class representing a room for a text-only adventure game.
 *
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

    // The name that will be shown to the user
    private String publicName;

    // The name that the engine will use to keep track of the room
    private String engineName;

    // This is true if the room has been visited.
    private boolean visited;

    public Room(String _publicName, String _engineName, String _description){
        description = _description;
        engineName = _engineName;
        publicName = _publicName;

        items = new HashMap<>();
        sentients = new HashMap<>();
        connectedRooms = new HashMap<>();

        visited = false;
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
        items.put(i.getItemName().toLowerCase(), i);
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

    public boolean hasItem(String itemName){
        return items.containsKey(itemName);
    }

    public Item getItem(String itemName){
        if(items.containsKey(itemName))
            return items.get(itemName);

        return null;
    }

    public String getDescription() {
        return description;
    }

    public Collection<Item> getItems(){
        return items.values();
    }

    public Collection<Sentient> getSentients(){
        return sentients.values();
    }

    public boolean hasSentient(String sentientName){
        return sentients.containsKey(sentientName);
    }

    public HashMap<Direction, Room> getRooms(){
        return connectedRooms;
    }

    public String getEngineName(){
        return engineName;
    }
}