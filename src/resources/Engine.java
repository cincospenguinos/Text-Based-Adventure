package resources;

import java.util.*;

/**
 * Engine
 *
 * A simple game engine made to be implemented as desired by whomever uses this collection of classes.
 *
 * Created by Andre LaFleur on 7/22/2015.
 *
 * What this class should do:
 * - Keep a running loop that plays the actual game itself
 * - Create some basic commands that every game will be expected to run (Anonymous methods/delegates?)
 * - Permit the user to be able to change what each command does.
 */
public class Engine {

    // The current room that the player is in
    private Room currentRoom;

    // HashMap that stores the rooms, mapping their names to their objects themselves
    private HashMap<String, Room> roomMap;

    // The collection of enemies that the player will have to face
    private List<Sentient> enemies;

    // A HashMap that maps what items are in what rooms
    private HashMap<Item, Room> itemsToRooms;

    // The input (System.in) that the user will use to provide commands.
    private Scanner input;

    // A player - simply keeps track of how much HP and the stuff you've got.
    private Sentient player;

    // The directory where the configuration files will be saved
    private String configDirectory;

    // True if player is allowed to save the game, creating a new adventure.tacfg file
    private boolean isSaveEnabled;

    public Engine(){
        roomMap = new HashMap<>();
        enemies = new LinkedList<>();
        itemsToRooms = new HashMap<>();

        player = null;
    }

    /*
     * Let's put the adventure generation methods here
     */

    /**
     * Adds the room with the name, description, and unique engine name and populates it.
     *
     * @param name - String name that the user will see
     * @param engineName - String name that will be used to identify this specific room
     * @param description - String description of the room itself.
     */
    public void addRoom(String name, String engineName,  String description){
        if(name == null || description == null)
            throw new RuntimeException("Cannot add a room without a name or description.");

        if(name.isEmpty())
            throw new RuntimeException("Cannot add a room with an empty String as the name.");

        Room r = new Room(name, engineName, description);
        roomMap.put(r.getEngineName(), r);

        if(currentRoom == null)
            currentRoom = r;
    }

    /**
     * Adds a connection between two rooms requested. These room names must exist already in the Engine, and they
     * must be the Engine room names, NOT the room names that will be given to the player.
     *
     * @param roomEngineNameA - Room to connect from
     * @param roomEngineNameB - Room to connect to
     * @param direction - Direction from room A's perspective
     * @param isTwoWay - true if the connection from room B to room A is possible.
     */
    public void addConnection(String roomEngineNameA, String roomEngineNameB, Direction direction, boolean isTwoWay){
        if(roomMap.containsKey(roomEngineNameA) && roomMap.containsKey(roomEngineNameB)){
            Room roomA = roomMap.get(roomEngineNameA);
            Room roomB = roomMap.get(roomEngineNameB);

            if(isTwoWay)
                roomA.addTwoWayConnection(direction, roomB);
            else
                roomA.addOneWayConnection(direction, roomB);

        } else {
            throw new RuntimeException("Both rooms must exist in the engine before adding a connection.");
        }
    }

    /**
     * Adds a connection between two rooms requested. These room names must exist already in the Engine, and they
     * must be the Engine room names, NOT the room names that will be shown to the player.
     *
     * @param roomEngineNameA - Room to connect from
     * @param roomEngineNameB - Room to connect to
     * @param direction - String direction from room A's perspective
     * @param isTwoWay - true if the connection from room B to room A is possible.
     */
    public void addConnection(String roomEngineNameA, String roomEngineNameB, String direction, boolean isTwoWay){
        if(roomMap.containsKey(roomEngineNameA) && roomMap.containsKey(roomEngineNameB)){
            Room roomA = roomMap.get(roomEngineNameA);
            Room roomB = roomMap.get(roomEngineNameB);

            Direction d = Direction.toDirection(direction);

            if(d == null)
                throw new RuntimeException("Must provide a valid direction string; provided \"" + direction + "\"");

            if(isTwoWay)
                roomA.addTwoWayConnection(d, roomB);
            else
                roomA.addOneWayConnection(d, roomB);

        } else {
            throw new RuntimeException("Both rooms must exist in the engine before adding a connection.");
        }
    }


    /**
     * Creates and adds an item given the parameters to the room requested. The room MUST exist within the Engine
     * before attempting to add an Item to that room.
     *
     * @param itemPublicName - String public name of the item itself
     * @param itemEngineName - String name that the game engine will identify as the item in question
     * @param itemDescription - String description of the item itself
     * @param roomEngineName - String engine name of the room that the item will be added to
     */
    public void addItem(String itemPublicName, String itemEngineName, String itemDescription, String roomEngineName, boolean canBeTaken){
        Room r = roomMap.get(roomEngineName);

        if(r == null)
            throw new RuntimeException("Cannot add an item to a non-existent room.");

        Item i = new Item(itemPublicName, itemEngineName, itemDescription, canBeTaken);

        r.addItem(i);

        // Temporarily commented out - I haven't decided if I need this data structure or not yet.
//        itemsToRooms.put(i, r);
    }

    /**
     * Adds a new player with the name passed to the new adventure.
     *
     * @param name - String name of the player
     * @param hp - How much the player will have with starting HP
     * @param defense - The defense value of the player
     * @param damage - How much damage the player does
     * @param toHit - How likely the player is able to be hit
     */
    public void addPlayer(String name, int hp, double attack, int defense, int damage, double toHit){
        player = new Sentient(name, "", hp, attack, defense, damage, toHit, false);
    }

    /**
     * Adds a Sentient to the room requested.
     *
     * @param name - String name of Sentient
     * @param description - String description of the Sentient
     * @param hp - int Hit points
     * @param attack - double probability of attack
     * @param defense - int defense value
     * @param damage - int damage able to deal
     * @param toHit - double probability of being hit
     * @param isHostile - true if the Sentient will attack on sight
     * @param roomEngineName - String name of the room to add this Sentient to.
     */
    public void addSentient(String name, String description, int hp, double attack, int defense, int damage, double toHit, boolean isHostile, String roomEngineName){
        Room r = roomMap.get(roomEngineName);

        if(r == null)
            throw new RuntimeException("Must add Sentient to an already existent room.");

        Sentient s = new Sentient(name, description, hp, attack, defense, damage, toHit, isHostile);
        r.addSentient(s);
    }

    /**
     * The game loop that runs the whole affair
     */
    public void playGame(){
        // Check to make sure that there is a player of some sort that is ready to be used
        if(player == null)
            throw new RuntimeException("Game requires a player to play.");

        // Now begin the actual gameplay
        input = new Scanner(System.in);

        // The never ending loop that handles everything
        while(true){
            System.out.println(currentRoom.getPublicName() + "\n");

            // First check to see if we are in combat:
            enemies = currentRoom.getHostileSentients();

            // If we are in combat, let the enemies fight the player!
            if(enemies.size() != 0){
                for(Sentient s : enemies){
                    System.out.println("You were attacked by " + s.getName() + ".");

                    if(s.attack(player)) {
                        System.out.println("You were hit.");
                        player.takeDamage(s.getDamage());
                    } else
                        System.out.println(s.getName() + " missed.");
                }

                printHealthStatus();

                if(player.isDead()){
                    System.out.println("GAME OVER");
                    return;
                }
            }

            // If the current room hasn't been visited, give a description of the room.
            if(!currentRoom.isVisited()) {
                printCurrentRoomDescription(); // Print out the description of the current room
                currentRoom.visit(); // Visit this room so that the description doesn't show up again.
            }

            System.out.print(">");

            String command = input.nextLine();

            // The command parser. Takes what command was given and attempts to figure out what the user desired
            if(command.toLowerCase().equals("exit") || command.toLowerCase().equals("quit")){
                System.out.println("Exiting the game.");
                input.close();
                return; // Just returns to whatever application called this method
            }

            else if(isCommandDirection(command)){
                go(command);
            }

            else if(command.startsWith("look")){
                look(command.replace("look ", ""));
            }

            else if(command.startsWith("take")){
                takeItem(command);
            }

            else if(command.startsWith("drop")){
                // TODO: Implement this
            }

            else if(command.startsWith("attack")){
                // TODO: Implement this
            }

            else if(command.startsWith("help")){
                // TODO: Implement this
            }

            else {
                System.out.println("I don't understand that.");
            }
        }
    }

    /**
     * Prints out the current health status of the player.
     *
     * TODO: Consider making these values changeable for the creator to be able to decide when he wants his player to be worried about his/her health
     */
    private void printHealthStatus() {
        switch(player.getCurrentHitPoints()){
            case 7:
            case 6:
                System.out.println("You have a few scratches");
                break;
            case 5:
            case 4:
                System.out.println("You're bleeding a small amount.");
                break;
            case 3:
            case 2:
                System.out.println("You're bleeding a lot.");
                break;
            case 1:
                System.out.println("You are on the verge of death.");
                break;
            case 0:
                System.out.println("You have been killed.");
            default:
                System.out.println("You are feeling healthy.");
        }
    }

    /**
     * Prints the description of the current room.
     */
    private void printCurrentRoomDescription(){
        System.out.println(currentRoom.getDescription() + "\n");

        for(Item i : currentRoom.getItems())
            System.out.println(i.getDescription() + "\n");
    }

    /**
     * Returns true if the line passed is a direction
     * @param line - String command received from the user
     * @return true if the line is a "go" command
     */
    private boolean isCommandDirection(String line){
        if((line.length() == 1 && line.matches("[news]")) || (line.length() == 2 && line.matches("[ns][ew]")) || line.toLowerCase().contains("go")) {
            line = line.toLowerCase().replace("go", "");
            return true;
        }

        return false;
    }

    /**
     * Parses and runs the command "look" using the line given.
     *
     * @param line - String command that the user gave
     */
    private void look(String line){
        Scanner s = new Scanner(line);
        s.next();

        // If the simple look command was run then show the description of the room and any items
        // left inside.
        if(!s.hasNext()) {
            printCurrentRoomDescription();
        } else if(s.next().toLowerCase().equals("at")){
            String thing = s.next();

            // TODO: Include a check for inventory items here as well.
            if(thing == null || !currentRoom.hasItem(thing) || !currentRoom.hasSentient(thing)){
                System.out.println("Look at what?");
            } else if (currentRoom.hasItem(thing)){
                System.out.println(currentRoom.getItem(thing).getDescription());
            } else{
                System.out.println(currentRoom.getSentient(thing).getDescription());
            }
        }

        s.close();
    }

    /**
     * Makes the player go in the direction passed.
     *
     * @param line - String direction that the player must go
     */
    private void go(String line){
        Direction d = Direction.toDirection(line.trim().toLowerCase());

        if(d != null && currentRoom.getConnection(d) != null)
            currentRoom = currentRoom.getConnection(d);
        else
            System.out.println("It isn't possible to go that way.");
    }

    /**
     * Parses and runs the command "take" using the line given.
     *
     * @param line - String command that the user gave
     */
    private void takeItem(String line){
        line = line.replace("take", "");

        if(line.isEmpty()){
            System.out.println("Take what?");
        }

        // TODO: Finish implementing this - requires a Player object of some sort.
    }

    /**
     * Parses and runs the command "drop" using the line given.
     *
     * @param line - String command that the user gave
     */
    private void drop(String line){
        // TODO: Implement this
    }

    /**
     * Parses and runs the command "attack" using the line given.
     *
     * @param line - String command tha the user gave
     */
    private void attack(String line){
        // TODO: Implement this
    }

    /**
     * Saves the configuration of this engine at the directory saved above.
     */
    public void saveConfigurationFile(){
        // TODO: Implement this
    }

    /**
     * Deletes the configuration file completely. Returns the file to a default configuration.
     */
    public void deleteConfigurationFile(){
        // TODO: Implement this
    }
}
