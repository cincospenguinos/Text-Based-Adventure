package resources;

/**
 * An enumeration representing the directions a player may go.
 */
public enum Direction {
    NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST, UP, DOWN;

    public static String toString(Direction d){
        switch(d){
            case NORTH:
                return "NORTH";
            case NORTH_EAST:
                return "NORTH EAST";
            case EAST:
                return "EAST";
            case SOUTH_EAST:
                return "SOUTH EAST";
            case SOUTH:
                return "SOUTH";
            case SOUTH_WEST:
                return "SOUTH WEST";
            case WEST:
                return "WEST";
            case NORTH_WEST:
                return "";
            case UP:
                return "UP";
            case DOWN:
                return "DOWN";
            default:
                return "";
        }
    }

    /**
     * Returns the direction that corresponds to the direction passed. If
     * the string passed doesn't match a direction, then this method returns null.
     *
     * @param s - the direction
     * @return Direction or null
     */
    public static Direction toDirection(String s){
        if(s.contains("go"))
            s = s.replace("go", "");

        switch (s) {
            case "n":
            case "north":
                return NORTH;
            case "ne":
            case "north east":
                return NORTH_EAST;
            case "e":
            case "east":
                return EAST;
            case "se":
            case "south east":
                return SOUTH_EAST;
            case "s":
            case "south":
                return SOUTH;
            case "sw":
            case "south west":
                return SOUTH_WEST;
            case "w":
            case "west":
                return WEST;
            case "nw":
            case "north west":
                return NORTH_WEST;
            case "up":
                return UP;
            case "down":
                return DOWN;
        }

        return null;
    }

    /**
     * Returns the opposite direction of the direction passed into this method.
     *
     * @param d - Direction to get the opposite of
     * @return Opposite direction of the direction passed.
     */
    public static Direction oppositeDirection(Direction d){
        switch(d){
            case NORTH:
                return SOUTH;
            case NORTH_EAST:
                return SOUTH_WEST;
            case EAST:
                return WEST;
            case SOUTH_EAST:
                return NORTH_WEST;
            case SOUTH:
                return NORTH;
            case SOUTH_WEST:
                return NORTH_EAST;
            case WEST:
                return EAST;
            case NORTH_WEST:
                return SOUTH_EAST;
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            default:
                return null;
        }
    }
}
