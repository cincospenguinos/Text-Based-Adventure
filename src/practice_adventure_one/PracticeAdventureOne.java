package practice_adventure_one;

import resources.*;

import java.util.Scanner;
import java.util.HashMap;

/**
 * The first official prototype of the adventure game.
 */
public class PracticeAdventureOne {

    private Scanner input;

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
//                player.addToScore(1); // one point for exploring a new room
                currentRoom.visit();
            }

            // Get the command that needs to be parsed
            String command = input.nextLine().toLowerCase();

            if(command.equals("exit") || command.equals("quit"))
                break;
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
}
