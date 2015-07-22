package debug_adventure;

import resources.*;

/**
 * SimpleAdventure
 *
 * A simple adventure that tests the core mechanics of the game. Intended to demo the game and to debug the game.
 *
 * Created by Andre LaFleur on 7/22/2015.
 */
public class SimpleAdventure {

    public static void main(String[] args){
        Engine e = new Engine();

        // Add rooms
        e.addRoom("Abandoned Apartment", "Apartment", "You are standing in an empty concrete room. A large window is open in front of you.");
        e.addRoom("Outside", "Outside1", "You are standing in an abandoned playground. Rust eats away at the fading yellow metal.");

        // Add connections between rooms
        e.addConnection("Apartment", "Outside1", Direction.NORTH, true);

        // Add items
        e.addItem("Machete", "Machete", "A dull, rusty machete.", "Apartment", true);

        // Add sentients
        e.addSentient("A Bird", "Bird1", 3, 0.05, 1, 2, 0.5, true, "Outside1");

        // Add the player
        e.addPlayer("Andre", 10, 0.25, 3, 4, 0.4);

        e.playGame();
    }
}
