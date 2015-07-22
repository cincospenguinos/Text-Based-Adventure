package resources;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;

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

    // The directory where the configuration files will be saved
    private String configDirectory;

    // True if player is allowed to save the game, creating a new adventure.tacfg file
    private boolean isSaveEnabled;

    public Engine(){
        roomMap = new HashMap<>();
        enemies = new ArrayList<>();
        itemsToRooms = new HashMap<>();
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
     * @param roomNameA - Room to connect from
     * @param roomNameB - Room to connect to
     * @param direction - Direction from room A's perspective
     * @param isTwoWay - true if the connection from room B to room A is possible.
     */
    public void addConnection(String roomNameA, String roomNameB, Direction direction, boolean isTwoWay){
        if(roomMap.containsKey(roomNameA) && roomMap.containsKey(roomNameB)){
            Room roomA = roomMap.get(roomNameA);
            Room roomB = roomMap.get(roomNameB);

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
     * must be the Engine room names, NOT the room names that will be given to the player.
     *
     * @param roomNameA - Room to connect from
     * @param roomNameB - Room to connect to
     * @param direction - String direction from room A's perspective
     * @param isTwoWay - true if the connection from room B to room A is possible.
     */
    public void addConnection(String roomNameA, String roomNameB, String direction, boolean isTwoWay){
        if(roomMap.containsKey(roomNameA) && roomMap.containsKey(roomNameB)){
            Room roomA = roomMap.get(roomNameA);
            Room roomB = roomMap.get(roomNameB);

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
     * The game loop that runs the whole affair
     */
    public void playGame(){
        // First we will populate the rooms and stuff
        // TODO: Implement that^
        input = new Scanner(System.in);
        String command;

        // The never ending loop that handles everything
        while(true){
            System.out.println(currentRoom.getPublicName() + "\n");

            if(!currentRoom.isVisited()) {
                printDescription(); // Print out the description of the current room
                currentRoom.visit(); // Visit this room so that the description doesn't show up again.
            }
            System.out.print(">");

            command = input.nextLine();

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
                // TODO: Implement this
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
     * Prints the description of the current room.
     */
    private void printDescription(){
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

        // If the simple look command was run then show the description of the room and any items
        // left inside.
        if(!s.hasNext()) {
            System.out.println(currentRoom.getDescription() + "\n");

            for(Item i : currentRoom.getItems())
                System.out.println(i.getDescription() + "\n");
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
    private void take(String line){
        // TODO: Implement this
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
