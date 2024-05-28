package BackEnd.MazeSolver;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * The class of maze solver
 */
public class MazeSolver {
    /**
     * Class constructor
     */
    public MazeSolver(){

    }
    private LinkedList<TileLocation> paths = new LinkedList<TileLocation>();
    private LinkedList<String> pathNames = new LinkedList<String>();
    private int[][] maze;

    /**
     * The pathfinder methods for finding the optimal solution of a maze
     * @param input The maze stores in an 2 dimensional integer array
     * @return 2 dimentional integer array where each element is the tile of the solution
     */
    private int[][] FindMaze(int[][] input){
        this.maze = new int[input.length][input[0].length];
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++) {
                this.maze[x][y] = input[x][y];
            }
        }
        TileLocation startingPoint = null;
        TileLocation finishPoint = null;
        //starting point
        for(int y =0; y < maze.length ; y++){
            for(int x = 0; x< maze[y].length; x++){
                //start = 2
                if(maze [y][x] == 2){
                    startingPoint = new TileLocation (y,x);
                    paths.push(startingPoint);
                    // System.out.println("Found Start");
                }else if(maze[y][x] == 3){
                    finishPoint = new TileLocation (y,x);
                }
            }
        }
        if(startingPoint == null|| finishPoint == null){
            int [] [] nullArray = {};
            return nullArray;
        }



        while(true) {
            int y = paths.peek().y;
            int x = paths.peek().x;
            maze[y][x] = 1;

            if(isValid(y+1,x)){
                //down
                if (maze[y + 1][x] == 3) {
                    pathNames.push("down");
                    paths.push(new TileLocation(y+1,x));
                    //System.out.println("Move down,You Won");
                    break;
                } else if (maze[y + 1][x] == 0) {
                    // System.out.println("Moved down");
                    pathNames.push("down");
                    paths.push(new TileLocation(y + 1, x));
                    continue;
                }
            }

            if(isValid(y,x-1)){
                //left
                if (maze[y][x-1] == 3) {
                    pathNames.push("left");
                    paths.push(new TileLocation(y,x-1));
                    //System.out.println("Move left, You Won");
                    break;
                } else if (maze[y][x-1] == 0) {
                    //System.out.println("Moved left");
                    pathNames.push("left");
                    paths.push(new TileLocation(y, x-1));
                    continue;
                }
            }

            if(isValid(y-1,x)){
                //up
                if (maze[y - 1][x] == 3) {
                    pathNames.push("Up");
                    paths.push(new TileLocation(y-1,x));
                    //System.out.println("Move up, You Won");
                    break;
                } else if (maze[y - 1][x] == 0) {
                    // System.out.println("Moved up");
                    pathNames.push("up");
                    paths.push(new TileLocation(y - 1, x));
                    continue;
                }
            }

            if(isValid(y,x+1)){
                //right
                if (maze[y][x+1] == 3) {
                    pathNames.push("Right");
                    paths.push(new TileLocation(y,x+1));
                    //System.out.println("Move right, You Won");
                    break;
                } else if (maze[y][x+1] == 0) {
                    //System.out.println("Moved right");
                    pathNames.push("right");
                    paths.push(new TileLocation(y , x+1));
                    continue;
                }
            }


            paths.pop();
            pathNames.pop();
            if(paths.size()<0){
            }
        }

        for (int i = 0; i < pathNames.size(); i++) {
            //System.out.println(pathNames.get(pathNames.size()-1-i));
        }
        int[][] pathList =new int[paths.size()][2];
        for (int i = 0; i < paths.size(); i++) {
            pathList[i]=paths.get(paths.size()-1-i).getArrLocation();
            //System.out.println(paths.get(paths.size()-1-i).getLocation());
        }

        return pathList;


    }

    /**
     * The method for checking if the location of that path is valid in the maze
     * @param y row number
     * @param x column number
     * @return True if valid, else False
     */
    private boolean isValid(int x, int y){
        if(y<0 || y>= maze.length || x<0 || x>=maze[y].length){
            return false;
        }
        return true;
    }


    /**
     * This function returns the solution of a maze
     * @param unsolvedMaze The int array maze to be solved
     * @return The solved maze in a 2d array
     */
    public int [] [] getPath(int [] [] unsolvedMaze){
        int [][] solution;
        int[][] noSolution = {};
        try {
            solution = FindMaze(unsolvedMaze);


            if (solution.length == 0) {

                return noSolution;
            } else {
                for (int[] path : solution) {
                    System.out.println(Arrays.toString(path));
                }
                return solution;
            }

        }
        catch(Exception e) {
            return noSolution;
        }

    }

    /**
     * This function returns the percentage of the number of tiles solution path take
     * to the total number of tiles of the maze is a path
     * @param solve The solution too the maze
     * @param grid The maze inputted
     * @return The percentage of the solution covers
     */
    public double difficultyPercentage(int [] [] solve, int[][] grid){
        double totaltiles = 0;
        for (int x = 1; x<grid.length-1; x++) {
            for (int y = 1; y<grid[0].length-1; y++) {
                if (grid[x][y] == 0) {
                    totaltiles += 1;
                }
            }
        }
        double total = solve.length/2;
        return (total/totaltiles) * 100;
    }

    /**
     * This function returns the percentage of the number of tiles is a dead end
     * to the total number of tiles of the maze
     * @param grid the maze input
     * @return The percentage of dead ends
     */
    public double percentageDeadEnds(int [] [] grid){
        double totaltiles = 0;
        double deadends = 0;
        for (int x = 1; x<grid.length-1; x++) {
            for (int y = 1; y<grid[0].length-1; y++) {
                if (grid[x][y] == 0) {
                    totaltiles += 1;
                    int walls = 0;
                    if (grid[x+1][y] == 1) {walls++;}
                    if (grid[x-1][y] == 1) {walls++;}
                    if (grid[x][y+1] == 1) {walls++;}
                    if (grid[x][y-1] == 1) {walls++;}
                    if (walls >= 3) {deadends++;}
                }
            }
        }
        return (deadends/totaltiles)*100;
    }
}
