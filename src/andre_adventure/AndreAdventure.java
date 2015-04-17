package andre_adventure;

import java.util.Scanner;
import java.util.List;
import java.util.HashMap;
import resources.*;

/**
 * AndreAdventure class - the first very simple adventure for Andre's text based adventure game.
 */
public class AndreAdventure {

    private Player player;

    private Room currentRoom;

    private List<Sentient> enemies;

    private HashMap<Item, Room> itemsToRooms;

    private Scanner input;

    public AndreAdventure() {
        // Create the player
        player = new Player("Player");
        input = new Scanner(System.in);

        itemsToRooms = new HashMap<>();

        // First we'll make all the necessary items
        Weapon machete = new Weapon("Machete", "A steel machete that says \"Made in Brazil\" on the blade.", 0.25, 3);
        Item oldBaguette = new Item("Old Baguette", "The old baguette is cold and hard like iron. You better not eat it.");
        Item dogBowl = new Item("Dog Bowl", "A small clay bowl with small imprints of dog paws all around it.");

        // Now let's create all the rooms and add the necessary items.
        Room apartment = new Room("Apartment", "You are in an apartment. " +
                "Everything inside has been scraped out, with paint chipping off the walls. The floor has a thin " +
                "layer of carpet.");
        apartment.addItem(machete);

        Room outside = new Room("Outside", "You are outside. The moonlight reflects " +
                "off of the windows upon the building surrounding you.");
        outside.addSentient(new Sentient("Mean Bird", 5, 2, 2, 0.3, true)); // An enemy - a mean bird

        Room shrine = new Room("Shrine", "You find yourself in front of a grand and majestic shrine. There seems to " +
                "be an inscription upon the base of it.");
        shrine.addItem(new Item("Inscription", "The inscription says " +
                "\"One iron loaf and one basin of water will summon the great ruler.\"", false));

        Room bakery = new Room("Bakery", "You are inside of an old bakery. Chairs and tables are thrown all over the" +
                "ground, broken and forgotten.");
        bakery.addItem(oldBaguette);

        Room mazeA = new Room("Forest Maze", "You are in a forest, with twisty corners and trees surrounding you.");
        Room mazeB = new Room("Forest Maze", "You are in a forest, with twisty corners and trees surrounding you.");
        Room mazeC = new Room("Forest Maze", "You are in a forest, with twisty corners and trees surrounding you.");
        Room mazeD = new Room("Forest Maze", "You are in a forest, with twisty corners and trees surrounding you.");

        Room bowlLocation = new Room("Shrine", "You find a small stone shrine before you.");
        bowlLocation.addItem(new Item("Bowl Inscription", "The inscription says \"TAKE ME PLEASE\".", false));
        bowlLocation.addItem(dogBowl);

        // Now let's set the usable items up in the various rooms
        itemsToRooms.put(oldBaguette, shrine);
        itemsToRooms.put(dogBowl, shrine);

        // TODO: Create more rooms and populate them.

        // Now let's add the connections:
        try{
            apartment.addTwoWayConnection(Direction.SOUTH, shrine);
            apartment.addTwoWayConnection(Direction.NORTH, outside);

            outside.addTwoWayConnection(Direction.EAST, bakery);

            bakery.addTwoWayConnection(Direction.EAST, mazeA);

            mazeA.addOneWayConnection(Direction.SOUTH, mazeB);

            mazeB.addOneWayConnection(Direction.SOUTH, mazeB);
            mazeB.addOneWayConnection(Direction.NORTH, mazeD);
            mazeB.addOneWayConnection(Direction.WEST, mazeC);

            mazeC.addOneWayConnection(Direction.EAST, mazeA);
            mazeC.addOneWayConnection(Direction.SOUTH, mazeB);
            mazeC.addOneWayConnection(Direction.SOUTH_EAST, bowlLocation);

            mazeD.addOneWayConnection(Direction.NORTH_WEST, mazeA);

            bowlLocation.addOneWayConnection(Direction.NORTH, bakery);
        } catch(Exception e){
            System.err.println("A connection exists already!");
            System.exit(1);
        }

        // The player begins inside the apartment
        currentRoom = apartment;
        enemies = currentRoom.getHostileSentients();
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

            // Get the hostile enemies and apply attacks
            enemies = currentRoom.getHostileSentients();

            for(Sentient s : enemies) {
                System.out.println("You are attacked by " + s.getName() + ".");

                if(s.attack(player)){
                    System.out.println("You have been hit.");
                    player.checkHealth();
                }
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

            else if(command.contains("drop"))
                dropItem(command);

            else if(command.contains("use"))
                useItem(command);

            else if(command.equals("look"))
                currentRoom.look();

            else if(command.contains("look at"))
                lookAtItem(command);

            else if(command.equals("inv") || command.equals("inventory"))
                player.showInventory();

            else if(command.equals("score"))
                System.out.println("Your score: " + player.getScore());

            else if(command.contains("equip"))
                equipItem(command);

            else if(command.contains("attack"))
                attack(command);

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
        System.out.println("inventory - displays the player's inventory");
        System.out.println("score - displays your current score");
        System.out.println("use - uses the item requested");
        System.out.println("equip - equips the item requested");
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
     * Uses the item requested in the current room.
     *
     * @param item - Item to use on the current room
     */
    private void useItem(String item){
        item = item.replace("use ", "");
        item = item.trim();

        Item i = player.getItem(item);

        if(i == null) {
            System.out.println("You do not have that item.");
            return;
        }

        // If the item requested does have a connection to the current room, then we will do something.
        if(itemsToRooms.get(i) == currentRoom){
            // We don't have to worry what room it is because we are only allowing two items to be useable
            // in one room.
            if(i.getItemName().toLowerCase().equals("dog bowl")){
                // First, drop the item
                currentRoom.addItem(player.dropItem(item));
                System.out.println("You placed the dog bowl before the shrine.");
                checkEndGame();
                return;
            } else if (i.getItemName().toLowerCase().equals("old baguette")){
                currentRoom.addItem(player.dropItem(item));
                System.out.println("You placed the old baguette before the shrine.");
                checkEndGame();
                return;
            }
        }
            System.out.println("You cannot use that here.");
    }

    /**
     * Drop the item passed.
     *
     * @param item - Item to be dropped
     */
    private void dropItem(String item) {
        item = item.replace("drop ", "");
        item = item.trim();

        Item i = player.dropItem(item);

        if(i != null) {
            currentRoom.addItem(i);
            System.out.println("Dropped.");
        } else
            System.out.println("You don't have that item.");
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

    /**
     * Equips the weapon requested.
     *
     * @param weapon - String weapon name
     */
    private void equipItem(String weapon){
        weapon = weapon.replace("equip ", "");
        weapon = weapon.trim();

        player.equipWeapon(weapon);
    }

    /**
     * Manages attacking an enemy
     */
    private void attack(String enemy){
        if(enemies.size() == 0){
            System.out.println("There is nothing to attack!");
            return;
        }

        if(enemy.contains("attack"))
            enemy = enemy.replace("attack", "");

        enemy = enemy.trim();

        // If we have a target, go ahead and attack him
        Sentient toAttack = null;

        for(Sentient s : enemies)
            if(s.getName().toLowerCase().equals(enemy)) {
                toAttack = s;
                break;
            }

        // Otherwise attack the first one
        if(toAttack == null)
            toAttack = enemies.get(0);

        if(player.attack(toAttack)) {
            System.out.println("You hit the enemy!");
            if(toAttack.isDead()) {
                System.out.println("The enemy is dead.");
                player.addToScore(5); // Add points for killing an enemy.
            }
        }
        else
            System.out.println("You missed the enemy.");
    }

    private void checkEndGame(){
        // TODO: Implement this
        if(currentRoom.hasItem("dog bowl") && currentRoom.hasItem("old baguette")){
            System.out.println("You hear a low rumbling in the distance. It comes nearer and nearer, closer and closer.\n");
            System.out.print(">");
            input.nextLine();
            System.out.println("It comes nearer and nearer, louder and louder, until you can't bear it...\n");
            System.out.print(">");
            input.nextLine();
            System.out.println("When you think you can't take it anymore, a yellow lab appears, walking from behind the");
            System.out.println("large shrine. She walks up to you, sniffs the bread, picks it up in its mouth and walks");
            System.out.println("away.\n");
            System.out.print("> ");
            input.nextLine();
            System.out.println("Yes, that is the end of the game.");
            System.out.println("YOUR SCORE: " + player.getScore());
            input.nextLine();
            input.close();
            System.exit(0);
        }
    }

    private void exit(){
        input.close();
        System.exit(0);
    }
}
