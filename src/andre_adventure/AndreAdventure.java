package andre_adventure;

import java.util.Scanner;
import resources.*;

/**
 * AndreAdventure class - the first very simple adventure for Andre's text based adventure game.
 */
public class AndreAdventure {

    private Player player;
    private Room currentRoom;
    private Scanner input;

    public AndreAdventure() {
        // Create the player
        input = new Scanner(System.in);
        System.out.println("Please insert name (you will be able to change this later):");
        player = new Player(input.nextLine());
        player.addToScore(-1); // because one will be added when the game starts.

        // Now let's create all the rooms and add the necessary items.
        Room apartment = new Room("Apartment", "You are in an apartment. " +
                "Everything inside has been scraped out, with paint chipping off the walls. The floor has a thin " +
                "layer of carpet.");
        apartment.addItem(new Item("Machete", "A steel machete that says \"Made in Brazil\" on the blade."));

        Room outside = new Room("Outside", "You are outside. The moonlight reflects " +
                "off of the windows upon the building surrounding you.");

        Room shrine = new Room("Shrine", "You find yourself in front of a grand and majestic shrine. There seems to " +
                "be an inscription upon the base of it.");
        shrine.addItem(new Item("Inscription", "The inscription says " +
                "\"One iron loaf and one basin of water will summon the great ruler.\"", false));

        // TODO: Create more rooms and populate them.

        // Now let's add the connections:
        try{
            apartment.addConnection(Direction.NORTH, outside);
            outside.addConnection(Direction.SOUTH, apartment);

            apartment.addConnection(Direction.SOUTH, shrine);
            shrine.addConnection(Direction.NORTH, apartment);
        } catch(Exception e){
            System.err.println("A connection exists already!");
            System.exit(1);
        }

        // The player begins inside the apartment
        currentRoom = apartment;
    }

    public static void main(String[] args){
        new AndreAdventure().play();
    }

    /**
     * Plays the game.
     */
    public void play(){
        String command;

        while(true){
            // If the current room has been visited, don't show a description.
            System.out.println(currentRoom.getPublicName() + "\n");
            if(!currentRoom.isVisited()){
                currentRoom.look();
                player.addToScore(1); // one point for exploring a new room
                currentRoom.visit();
            }

            System.out.print("> ");
            command = input.nextLine().toLowerCase();

            if(command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("exit"))
                break;

            else if(command.equalsIgnoreCase("help") || command.equals("?"))
                showHelp();

            else if(isDirection(command))
                goDirection(command);

            else if(command.contains("take") || command.contains("get"))
                takeItem(command);

            else if(command.equals("look"))
                currentRoom.look();

            else if(command.contains("look at"))
                lookAtItem(command);

            else if(command.equals("inv"))
                player.showInventory();

            else if(command.equals("score"))
                System.out.println("Your score: " + player.getScore());

            else if(command.equals("help") || command.equals("?"))
                showHelp();
            else
                System.out.println("I don't understand that.");
        }

        input.close();
    }

    /**
     * Returns true if the string passed is a direction.
     * @param s - String that was provided by the user
     * @return true if s is a direction
     */
    private boolean isDirection(String s){
        return s.equalsIgnoreCase("n") || s.equalsIgnoreCase("ne") || s.equalsIgnoreCase("e") ||
                s.equalsIgnoreCase("se") || s.equalsIgnoreCase("s") || s.equalsIgnoreCase("sw") ||
                s.equalsIgnoreCase("w") || s.equalsIgnoreCase("nw") ||s.equalsIgnoreCase("north") ||
                s.equalsIgnoreCase("north east") || s.equalsIgnoreCase("east") || s.equalsIgnoreCase("south east") ||
                s.equalsIgnoreCase("south") || s.equalsIgnoreCase("south west") ||s.equalsIgnoreCase("west") ||
                s.equalsIgnoreCase("north west") || s.equalsIgnoreCase("up") || s.equalsIgnoreCase("down") ||
                s.toLowerCase().contains("go");
    }

    /**
     * Prints out a list of commands.
     */
    private void showHelp(){
        System.out.println("***** LIST OF COMMANDS *****");
        System.out.println("help/? - Displays this menu");
        System.out.println("go [direction] - Goes in that direction");
        System.out.println("take [item] - takes the item requested");
        System.out.println("drop [item] - drops the item from the inventory");
        System.out.println("look - shows what the current area looks like");
        System.out.println("look at [item] - describes the item in the current area");
        System.out.println("inv - displays the inventory");
        System.out.println("score - displays your current score");
    }

    /**
     * Goes in the direction requested, if it exists in the current room.
     *
     * @param direction to go
     */
    private void goDirection(String direction){
        Direction d = Direction.toDirection(direction);

        if(currentRoom.getConnection(d) != null)
            currentRoom = currentRoom.getConnection(d);
        else
            System.out.println("There is not a path that way that goes anywhere.");
    }

    /**
     * Gets the item or indicates to the player that the item requested is not here.
     *
     * @param item to take
     */
    private void takeItem(String item){
        // Make sure that we only have the item name.
        if(item.contains("get"))
            item = item.replace("get", "");
        if(item.contains("take"))
            item = item.replace("take", "");

        item = item.trim();

        // If the item exists, get it. Otherwise, let the player know
        Item i = currentRoom.takeItem(item);
        if((i != null)){
            player.addItem(i);
            System.out.println("Taken.");
        }
        else
            System.out.println("You cannot take that.");
    }

    /**
     * Shows the description of the item requested.
     *
     * @param item to look at
     */
    private void lookAtItem(String item){
        // If we have look at, then let's remove it
        if(item.contains("look at"))
            item = item.replace("look at", "");

        // Remove any white space
        item = item.trim();

        // Now look at the item.
        currentRoom.lookAt(item);
    }
}
