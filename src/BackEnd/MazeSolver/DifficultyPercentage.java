package BackEnd.MazeSolver;

/**
 * This class calculates the Solution percentage and dead end percentage of the maze
 */
public class DifficultyPercentage extends MazeSolver{
    /**
     * Class constructor
     */
    public DifficultyPercentage(){

    }
    /**
     * This function gets the total number of paths
     * @param maze 2D array maze
     * @return  total number
     */
    public int getNumberofPath(int[][] maze){
        int pathCounter=0;
        for (int[] row : maze) {
            for(int column:row){
                if(column == 0){
                    pathCounter++;
                }
            }
        }
        return pathCounter;
    }

    /**
     * This function gets the number of solution tiles
     * @param maze The maze stores in an 2 dimensional integer array
     * @return The number of tiles with the minimum value of 0
     */
    public int getNumberofSolutionTiles(int[][] maze){
        int solutionCounter;
        int[][] solution = getPath(maze);
        solutionCounter= solution.length;
        return solutionCounter;
    }
    /**
     * This function gets the number of path tiles which are dead ends
     * @param maze The maze stores in an 2 dimensional integer array
     * @return The number of tiles with the minimum value of 0
     */
    public int getDeadEndNumber(int[][] maze){
        int deadEndCounter = 0;
        for(int rows=0; rows < maze.length;rows++){
            for(int columns=0;columns<maze[rows].length;columns++){
                if(maze[rows][columns] == 0 ){
                    int wallCounter = 0;
                    //Check whether North is a wall
                    if(maze[rows-1][columns]==1){
                        wallCounter++;
                    }
                    //Check whether East is a wall
                    if(maze[rows][columns+1]==1){
                        wallCounter++;
                    }
                    //Check whether South is a wall
                    if(maze[rows+1][columns]==1){
                        wallCounter++;
                    }
                    //Check whether West is a wall
                    if(maze[rows][columns-1]==1){
                        wallCounter++;
                    }
                    if(wallCounter >=3){
                        deadEndCounter++;
                    }
                }
            }
        }
        return deadEndCounter;

    }

    /**
     * This function calculate the percentage of number of tiles the solution takes to the number of path tiles
     * @param maze The maze stores in an 2 dimensional integer array
     * @return Percentage of the solution to path in the range of 0 to 100
     */
    public double getSolutionPercentage(int[][] maze){
        double numberOfPath = getNumberofPath(maze);
        double numberOfSolutionPath = getNumberofSolutionTiles(maze);
        return numberOfSolutionPath/numberOfPath * 100;
    }
    /**
     * This function calculate the percentage of number of tiles the dead end takes to the number of path tiles
     * @param maze The maze stores in an 2 dimensional integer array
     * @return Percentage of dead end to path in the range of 0 to 100
     */
    public double getDeadEndPercentage(int[][] maze){
        double numberOfPath = getNumberofPath(maze);
        double numberOfDeadEnd = getDeadEndNumber(maze);
        return numberOfDeadEnd/numberOfPath * 100;
    }
}
