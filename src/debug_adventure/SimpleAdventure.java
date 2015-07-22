package debug_adventure;

import resources.*;

/**
 * SimpleAdventure
 *
 * A simple adventure that tests the core mechanics of the game. Intended to demo the game and to debug the game.
 *
 * Created by tsvetok on 7/22/2015.
 */
public class SimpleAdventure {

    public static void main(String[] args){
        Engine e = new Engine();
        e.addRoom("Abandoned Apartment", "Apartment", "You are standing in an empty concrete room. A large window is open in front of you.");
        e.addRoom("Outside", "Outside1", "You are standing in an abandoned playground. Rust eats away at the fading yellow metal.");
        e.addConnection("Apartment", "Outside1", Direction.NORTH, true);
        e.playGame();
    }

}
