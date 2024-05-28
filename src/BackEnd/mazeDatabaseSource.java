package BackEnd;

import java.util.List;

public interface mazeDatabaseSource {

    /**
     * This function is use for adding a maze to the database
     * @param maze The maze to be saved in the database
     */
    void insertMaze(Maze maze);

    /**
     * This function is use for updating the data of the maze in the database
     * @param maze The maze to be updated in the database
     */
    void updateMaze(Maze maze);

    /**
     * This function is use for getting the maze from the database
     * @param id The id of the maze
     * @return The maze of the id number in aa 2d int array
     */
    int[][] getMaze(String id);

    /**
     * This function is use for getting all the maze from the database
     * @return A List containing every maze in the database
     */
    List<String> getAllMaze();


    /**
     * This function is use for getting all the detail of maze from the database
     * @param id The id of the maze
     * @return All the details in Maze object
     */
    Maze getMazeDetails(String id);
    /**
     * This function is use for getting the author of the maze
     * @param id The id of the maze
     * @return The author's name of the maze
     */
    String getAuthor(String id);

    /**
     * This function is use for getting the date of the maze
     * @param id The id of the maze
     * @return The date of the maze created
     */
    String getCreateDate(String id);

    /**
     * This function is use for getting the last modified date of the maze
     * @param id The id of the maze
     * @return The latest date of the maze being modified
     */
    String getLastModifiedDate(String id);

    /**
     * This function is use for getting the name of the maze with the provided id
     * @param id The id of the maze
     * @return The name of the maze
     */
    String getMazeName(String id);

    /**
     * Gets the number of mazes in the maze database.
     *
     * @return size of maze database.
     */
    int getSize();

    /**
     * This function is use for deleting the maze with the provided id
     * @param id The id of the maze
     */
    void deleteMaze(String id);

    /**
     * This function is use for finalizes database.
     */
    void close();

}

