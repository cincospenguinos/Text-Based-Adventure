package resources;

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
        // TODO: Implement this
    }

    /**
     * The game loop that runs the whole affair
     */
    public void playGame(){
        // First we will populate the rooms and stuff
        // TODO: Implement that
        input = new Scanner(System.in);
        String command;

        // The never ending loop that handles everything
        while(true){
            System.out.print(">");

            command = input.nextLine();

            // The command parser. Takes what command was given and attempts to figure out what the user desired
            if(command.toLowerCase().equals("exit") || command.toLowerCase().equals("quit")){
                System.out.println("Exiting the game.");
                input.close();
                System.exit(0);
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
     * Parses and runs the command "take" using the line given.
     *
     * @param line - String command that the user gave
     */
    private void take(String line){

    }

    /**
     * Parses and runs the command "drop" using the line given.
     *
     * @param line - String command that the user gave
     */
    private void drop(String line){

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
