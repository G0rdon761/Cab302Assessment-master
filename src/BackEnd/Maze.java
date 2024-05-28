package BackEnd;

import java.time.LocalDateTime ;

public abstract class Maze {
    private String name;
    private String author;
    private int height;
    private int width;
    private String firstCreated;
    private String lastEdited;
    private final int maxHeight = 100;
    private final int maxWidth = 100;
    private int startRow;
    private int startCol;
    private int finishRow;
    private int finishCol;
    private String logo;
    private int [] [] maze;
    public int Id;

    public Maze(){
    }

    /**
     * This is the constructor for Maze
     *
     * @param name the name of the Maze
     * @param author the author of the Maze
     * @param height the height of the Maze
     * @param width the width of the Maze
     */
    public Maze(String name, String author, int height, int width){
        this.name = name;
        this.author = author;
        this.height = height;
        this.width = width;



    }



    /**
     * This function is use for editing the maze
     * @param col the column
     * @param row the row value of the cell being edited
     * @param type the value the chosen cell is being edited too
     * @return edited maze
     */
    public abstract int[][] editMaze(int row, int col, int type);

    /**
     * This function is use for generating the maze
     */
    public abstract void drawMaze();

    /**
     * This function is use for setting the starting tile of the maze
     * @param startRow The row number where the starting point is located
     * @param startCol The column number where the starting point is located
     */
    public abstract void setStart(int startRow, int startCol);

    /**
     * This function is use for setting the ending tile of the maze
     * @param endRow The row number where the destination is located
     * @param endCol The column number where the destination is located
     */
    public abstract void setEnd(int endRow, int endCol);

    /**
     * This is the setter method for icon
     *
     * @param colNum The column number where the icon is placed
     * @param rolNum The row number where the icon is placed
     */
    public void setIcon(int colNum, int rolNum) {}

    /**
     * This is the getter method for maze
     * @return Return an int [] [] Array which holds the maze
     */
    public abstract int [] [] getMaze();


    /**
     * This is the setter method for maze
     * @param maze The generated maze
     */
    public abstract void setMaze(int [] [] maze);

    /**
     * This is the getter method for last modified date
     * @return Last modified date
     */
    public abstract LocalDateTime  getLastModifiedDate();

    /**
     * This is the getter method for maze created date
     * @return Maze created date
     */
    public abstract LocalDateTime  getCreatedDate();
    public abstract void settCreatedDate(String createdDate);
    /**
     * This is the getter method for getting the author
     * @return name of the Author
     */
    public abstract String getAuthor();

    /**
     * This is the setter method for setting the author
     * @param author name of the author
     */
    public abstract void setAuthor(String author);
    /**
     * THis is the getter method for icon
     * @return Icon image
     */
    public abstract PictureObject getIcon();

    /**
     * This is the setter method for icon
     * @param icon
     */
    public abstract void setIcon(PictureObject icon);
    /**
     * This is the getter function for the maze name
     * @return Maze name
     */
    public abstract String getMazeName();

    /**
     * This is the setter method for last edit date
     */
    public abstract void setLastEdited();
}

