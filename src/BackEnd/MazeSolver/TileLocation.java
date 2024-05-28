package BackEnd.MazeSolver;

/**
 * This class saves the location of a tile in the maze
 */
public class TileLocation {
    public int x;
    public int y;

    /**
     * Class constructor
     * @param y the row number
     * @param x the column number
     */
    public TileLocation(int y, int x){
        this.x = x;
        this.y = y;

    }

    /**
     * The getter for the location
     * @return The location in string form
     */
    public String getLocation(){
        return "["+y+", "+x+"]";
    }

    /**
     * The getter for the location
     * @return The location in array form
     */
    public int[] getArrLocation(){
        int y = this.y;
        int x = this.x;
        int[] arr = {y,x};
        return arr;
    }
}
