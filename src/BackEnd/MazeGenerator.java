package BackEnd;
import java.util.*;
public class MazeGenerator {
    public int recursion = 0;
    /**
     * This function generates the maze with a broken wall indciating the start and end of the maze
     * @param maze The object of the maze to be generated
     * @param startx the row of the starting location
     * @param starty the column of the starting location
     * @param width the width of the maze
     * @param height the height of the maze
     * @return The generated maze
     */
    public int [] [] generateMaze(BrokenWallMaze maze, int startx, int starty, int width, int height, int endRow, int endCol){
        int[][] output = new int[width][height];
        output = carvecells(startx,starty,output,0,0);
        output = translateCells(output, startx, starty,endRow,endCol);
        return output;
    }

    /**
     * This function generates the maze with images indicating the start and finish point of the maze
     * @param maze The object of the maze to be generated
     * @param startx the row of the starting location
     * @param starty the column of the starting location
     * @param width the width of the maze
     * @param height the height of the maze
     * @return The generated maze
     */
    public int [] [] generateMaze(PictureMaze maze, int startx, int starty, int width, int height, int endRow, int endCol){
        int[][] output = new int[width][height];
        output = carvecells(startx,starty,output,0,0);
        output = translateCells(output, startx, starty,endRow,endCol);
        return output;
    }

    /**
     * recursive maze cell carver, makes initial maze
     * @param curX the currently being edited position x
     * @param curY the currently being edited position
     * @param grid the maze to be edited
     * @param prevX the x position of current cell in the previous recursion
     * @param prevY the y position of current cell in the previous recursion
     * @return updated maze upon finnish
     */
    public int[][] carvecells(int curX, int curY, int[][]grid, int prevX, int prevY) {
        //these arrays provide the reprisentation of a wall being removed from a cell, they are addedd to the current cell value using the | function
        final int[] cellWalls = {1,2,4,8};
        final int[] opCellWalls = {4,8,1,2};
        //these arrays translate the direction (north, east, south and west) into values that can be addedd to the x and y co-ordinates of the current cell to obtain the cell being moved to, eg: move north from cell 5,5 = cell 5,6
        final int[] xDirections = {0,1,0,-1};
        final int[] yDirections = {1,0,-1,0};

        //randomises direction list
        int[] directions = {0,1,2,3};
        Random rand = new Random();
        for (int i = 0; i < directions.length; i++) {
            int index = rand.nextInt(directions.length);
            int temp = directions[index];
            directions[index] = directions[i];
            directions[i] = temp;
        }
        //the carve program loops through every direction, if it successfully carves a new path it then recursively calls itself as a function, if not it returns to the previous itteration and carves in a different direction
        for (int direction : directions) {
            int newX = curX + xDirections[direction];
            int newY = curY + yDirections[direction];
            //now that the new position has been calculated, we must check if it is in bounds
            if ((newY < grid.length && newY >= 0) && (newX < grid[0].length && newX >= 0) && (grid[newX][newY] == 0)) {
                //walls are removed from current and next cell

                grid[curX][curY] |= cellWalls[direction];
                grid[newX][newY] |= opCellWalls[direction];

                recursion += 1;
                //function called again, moves on from current position to next pos
                grid = carvecells(newX, newY, grid, curX, curY);

            }
        }
        return grid;

    }
    /**
     * converts maze from initial algorithmically generated maze too one which can be displayed and solved
     * @param grid the maze to translate
     * @param startx the start position of the maze x co-ordinate
     * @param starty the start position of the maze y co-ordinate
     * @return the completed maze ready for display
     */
    public int[][] translateCells(int[][] grid, int startx, int starty, int endRow, int endCol) {
        int translatedlen = (grid.length * 2) + 1;
        //cells must be upsized to be properly displayed
        int[][] output = new int[translatedlen][translatedlen];
        for (int x = 0;  x < grid.length ; x++) {
            for (int y = 0; y < grid.length; y++) {
                //for every cell in the origional grid
                int nx = (x * 2) + 1;
                int ny = (y * 2) + 1;
                //each old grid cell becomes a 3x3 maze, the corners are always filled walls
                output[nx - 1][ny - 1] = 1;
                output[nx + 1][ny - 1] = 1;
                output[nx - 1][ny + 1] = 1;
                output[nx + 1][ny + 1] = 1;
                //read generated grid to determine which walls are closesd with bit masking
                if ((grid[x][y] & 1) == 0) { //checks if old cell had wall closed
                    output[nx][ny + 1] = 1; //closes corresponding direction in translated cell
                }
                if ((grid[x][y] & 4) == 0) {
                    output[nx][ny - 1] = 1;
                }
                if ((grid[x][y] & 2) == 0) {
                    output[nx + 1][ny] = 1;
                }
                if ((grid[x][y] & 8) == 0) {
                    output[nx - 1][ny] = 1;
                }
            }
        }
        output = setstartend(startx, starty, output, grid, 2);
        output = setstartend(endRow, endCol, output, grid, 3);

        return output;
    }

    /**
     * this function places new starting location, either on a wall if possible or anywhere else if not
     * @param startx column position of the starting point
     * @param starty row position of the starting point
     * @param output
     * @param grid
     * @param type
     * @return
     */
    public int[][] setstartend(int startx, int starty, int[][] output, int[][] grid, int type) {
            if (startx == 0) { //checks if co-ordinates of start are on the edge of the grid
            output[0][(starty * 2) + 1] = type; //places display position accordingly
        }
        else if (startx == grid.length-1) {
            output[output.length-1][(starty*2)+1] = type;
        }
        else if (starty == 0) {
            output[(startx*2)+1][0] = type;
        }
        else if (starty == grid.length-1) {
            output[(startx*2)+1][output.length-1] = type;
        }
        else {
            output[(startx*2)+1][(starty*2)+1] = type;
        }
        return output;
    }
}



