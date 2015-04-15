package resources;

/**
 * Created by andrelafleur on 4/9/15.
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

        if(s.equals("n") || s.equals("north"))
            return NORTH;
        else if(s.equals("ne") || s.equals("north east"))
            return NORTH_EAST;
        else if(s.equals("e") || s.equals("east"))
            return EAST;
        else if(s.equals("se") || s.equals("south east"))
            return SOUTH_EAST;
        else if(s.equals("s") || s.equals("south"))
            return SOUTH;
        else if(s.equals("sw") || s.equals("south west"))
            return SOUTH_WEST;
        else if(s.equals("w") || s.equals("west"))
            return WEST;
        else if(s.equals("nw") || s.equals("north west"))
            return NORTH_WEST;
        else if(s.equals("up"))
            return UP;
        else if(s.equals("down"))
            return DOWN;

        return null;
    }
}
