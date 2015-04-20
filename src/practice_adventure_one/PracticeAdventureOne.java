package practice_adventure_one;

import resources.*;

import java.util.Scanner;
import java.util.HashMap;

/**
 * The first official prototype of the adventure game.
 */
public class PracticeAdventureOne {

    private Scanner input;

    private Sentient player;

    private Room currentRoom;

    /*
    Some quick notes:
    - All the item use handling, description output, etcetera, will be handled in this class, instead of
    by the other classes in resources. This will more easily facilitate
    - Basic outline: player kills enemy to get mystic orb, uses mystic orb at the small shrine to summon the enemy,
    player kills enemy to get a cleansed orb to set at the large shrine.
     */

    public PracticeAdventureOne(){
        input = new Scanner(System.in);

        player = new Sentient("Player", 10, 3, 3, 0.5, false);

        // All of the rooms that we will be populating
        Room apartment = new Room("Apartment", "You are in an apartment. The entire apartment is bare, with paint chipping off" +
                "the walls.");
        Room shrine = new Room("Shrine", "You are in front of a grand and majestic shrine, in the center of a shattered " +
                "apartment complex.");
        Room outside = new Room("Outside", "You are outside of the apartment.");
        Room bakery = new Room("Bakery", "You are inside of a bakery. All the tables and chairs have been moved out " +
                "of ");
        Room smallShrine = new Room("Shrine", "You are in front of a small shrine");

        // All the sentients and where they belong
        Sentient shadowWalker = new Sentient("Shadow Walker", 8, 3, 3, 0.5, true);
        bakery.addSentient(shadowWalker);

        Sentient dogKing = new Sentient("Dog", 25, 8, 5, 0.75, false);
        shrine.addSentient(dogKing);

        // All the items and where they belong in the various rooms
        Item mysticOrb = new Item("Mystic Orb", "A mystic orb, that seems to emit dark energy.");
        shadowWalker.addItem(mysticOrb);

        // Now connect all the rooms and set the current room
        apartment.addTwoWayConnection(Direction.SOUTH, shrine);
        apartment.addTwoWayConnection(Direction.NORTH, outside);
        outside.addTwoWayConnection(Direction.EAST, bakery);
        bakery.addTwoWayConnection(Direction.EAST, smallShrine);

        currentRoom = apartment;
    }

    public static void main(String[] args){
        new PracticeAdventureOne().play();
    }

    /**
     * The game engine that plays the actual game.
     */
    public void play(){
        while(true){
            // If the current room has been visited, don't show a description.
            System.out.println(currentRoom.getPublicName() + "\n");
            if(!currentRoom.isVisited()){
                look(currentRoom);
                currentRoom.visit();
            }

            // Get the command that needs to be parsed
            String command = input.nextLine().toLowerCase();

            if(command.equals("exit") || command.equals("quit"))
                break;
            else if(command.equals("help") || command.equals("?"))
                help();
            else if(command.contains("take") || command.contains("grab") || command.contains("get"))
                takeItem(command);
            else if(command.contains("talk"))
                talkTo(command);
        }

        input.close();
    }

    /**
     * Outputs to the player information about the room passed.
     *
     * @param room - Room to look through
     */
    private void look(Room room){
        // Print out the room's description
        System.out.println(room.getDescription() + "\n");

        // Indicate what directions the player can go to
        HashMap<Direction, Room> rooms = currentRoom.getRooms();

        for(Direction d : rooms.keySet())
            System.out.println("There is a path to the " + Direction.toString(d).toUpperCase() + ".");

        // Explain what items are in the room
        for(Item i : room.getItems()) {
            if(i.getItemName().startsWith("a") || i.getItemName().startsWith("e") || i.getItemName().startsWith("i")
                    || i.getItemName().startsWith("o") || i.getItemName().startsWith("u"))
                System.out.println("You see an " + i.getItemName().toLowerCase() + ".");
            else
                System.out.println("You see a " + i.getItemName().toLowerCase() + ".");
        }

        System.out.println();
    }

    /**
     * Displays the list of commands
     */
    private void help(){
        System.out.println("***** LIST OF COMMANDS *****");
        System.out.println("help/? - Displays this menu");
        System.out.println("go [direction] - Goes in that direction");
        System.out.println("take [item] - takes the item requested");
        System.out.println("drop [item] - drops the item from the inventory");
        System.out.println("look - shows what the current area looks like");
        System.out.println("look at [item] - describes the item in the current area");
        System.out.println("inventory - displays the player's inventory");
        System.out.println("score - displays your current score");
        System.out.println("use - uses the item requested");
        System.out.println("equip - equips the item requested");
    }

    /**
     * Takes the item requested.
     *
     * @param item - String name of item to take
     */
    private void takeItem(String item){
        item = item.replace("take ", "");
        item = item.replace("grab ", "");
        item = item.replace("get ", "");

        if(item.isEmpty()){
            System.out.println("Take what?");
            return;
        }

        if(currentRoom.hasItem(item)){
            player.addItem(currentRoom.takeItem(item));
            System.out.println("Taken.");
            return;
        }

        System.out.println("That item does not seem to be here.");
    }

    /**
     * Shows the dialog to talk to the sentient passed, or does nothing if there is no-one to talk
     * to.
     *
     * @param sentient  - Sentient to talk to
     */
    private void talkTo(String sentient){
        // Check to see if there is someone to talk to
        if(currentRoom.getSentients().size() == 0){
            System.out.println("There is no one to talk to.");
            return;
        }

        // Ensure we have nothing but the name
        sentient = sentient.replace("talk ", "");
        sentient = sentient.replace("to ", "");

        // If there is someone, check to see if it's the person requested
        if(currentRoom.hasSentient(sentient)){
            Sentient s = currentRoom.getSentient(sentient);

            // If it's the dog, the dialog is here.
            if(s.getName().toLowerCase().equals("dog")){
                System.out.println();
            }

            // If it's with the king, the dialog is here.
            else if(s.getName().toLowerCase().equals("king")){
                System.out.println();
            }
        }
    }
}
