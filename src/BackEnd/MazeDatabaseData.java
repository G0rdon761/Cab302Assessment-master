package BackEnd;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 * This version uses an mazeDatabaseSource and its methods to retrieve data
 * Retrieve from Practical prac06 and modified to suit the program
 *
 * @author Malcolm Corney
 * @version $Id: Exp $
 *
 */

public class MazeDatabaseData {

    DefaultListModel listModel;

    mazeDatabaseSource mazeData;

    /**
     * Class constructor
     */
    public MazeDatabaseData() {
        listModel = new DefaultListModel();

        mazeData = new JDBCMazeDatabaseSource();


        // add the retrieved data to the list model
        for (String maze : mazeData.getAllMaze()) {
            listModel.addElement(maze);
        }
    }

    /**
     * This function add the maze into the database
     * @param maze
     */
    public void add(BrokenWallMaze maze) {

        if (!listModel.contains(maze.getMazeName())) {
            listModel.addElement(maze.getMazeName());
            mazeData.insertMaze(maze);
        }
    }

    /**
     * This function remove the maze
     * @param key
     */
    public void remove(Object key) {

        // remove from both list and map
        listModel.removeElement(key);
        mazeData.deleteMaze((String) key);
    }

    /**
     * This function close the connection
     */
    public void persist() {
        mazeData.close();
    }

    /**
     * This function gets the maze class
     * @param key
     * @return
     */
    public Maze get(Object key) {
        return mazeData.getMazeDetails((String) key);
    }

    /**
     * Accessor for the list model.
     *
     * @return the listModel to display.
     */
    public ListModel getModel() {
        return listModel;
    }

    /**
     * @return the number of names in the Address Book.
     */
    public int getSize() { return mazeData.getSize();
    }
}


