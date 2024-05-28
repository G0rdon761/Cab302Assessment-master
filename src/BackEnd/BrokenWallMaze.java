package BackEnd;
import java.io.Serializable;
import java.util.Random;
import java.time.LocalDateTime ;

public class BrokenWallMaze extends Maze implements Serializable {

    private String name;
    private String author;
    private LocalDateTime  firstCreated;
    private LocalDateTime  lastEdited;
    private int height;
    private int width;
    private int startRow;
    private int startCol;
    private int[][] maze;
    private int endRow;
    private int endCol;
    private PictureObject icon;
    public BrokenWallMaze(){

    }

    /**
     * Class constructor which accepts maze name, maze author, maze height and maze width
     * @param name maze name
     * @param author maze author
     * @param height maze height
     * @param width maze width
     */
    public BrokenWallMaze(String name, String author, int height, int width) {
        super(name, author, height, width);
        this.height = height;
        this.width = width;
        this.name = name;
        this.author = author;
        firstCreated = LocalDateTime .now().withNano(0).withSecond(0); //sets the time of creation too when the maze is constructed
        this.lastEdited = LocalDateTime .now().withNano(0).withSecond(0);
        Random rand = new Random();
        endRow = height-1;
        endCol = height-1;
        int randwall = rand.nextInt(4);
        int randpos = rand.nextInt(height-1);
        int endrandpos = rand.nextInt(height-1);
        switch (randwall) { //chooses random edge of map to place start on
            case 1:
                this.startRow = 0;
                this.startCol = randpos;
                this.endRow = height-1;
                this.endCol = endrandpos;
                break;
            case 2:
                this.startRow = height-1;
                this.startCol = randpos;
                this.endRow = 0;
                this.endCol = endrandpos;
                break;
            case 3:
                this.startRow = randpos;
                this.startCol = 0;
                this.endRow = endrandpos;
                this.endCol = height-1;
                break;
            case 4:
                this.startRow = randpos;
                this.startCol = height-1;
                this.endRow = endrandpos;
                this.endCol = 0;
        }
    }

    /**
     * The method for finding the starting position of the maze
     * @return An integer array with the row and column value
     */
    public int[] getStartpos(){
        if(maze[startRow][startCol] == 1) {
            return new int[]{startRow, startCol};
        } else {
            if(maze[startRow+1][startCol] == 1){
                return new int[]{startRow+1, startCol};
            }
            if(maze[startRow-1][startCol] == 1){
                return new int[]{startRow-1, startCol};
            }
            if(maze[startRow][startCol+1] == 1){
                return new int[]{startRow, startCol+1};
            }
            if(maze[startRow][startCol-1] == 1){
                return new int[]{startRow, startCol-1};
            }
        }
        return new int[]{0,0};
    }
    @Override
    public int[][] editMaze(int row, int col, int type) {
        this.maze[row][col] = type;
        return this.maze;
    }

    @Override
    public void drawMaze() {
        MazeGenerator drawer = new MazeGenerator();
        this.maze = drawer.generateMaze(this, startRow, startCol,width,height, endRow, endCol);
    }


    @Override
    public void setStart(int startRow, int startCol) {
        try {
            for (int x = 0; x < this.maze.length; x++) {
                for (int y = 0; y < this.maze.length; y++) {
                    if (this.maze[x][y] == 2) {
                        this.maze[x][y] = 1;
                    }
                }
            }
            this.maze[startRow][startCol] = 2;
        } catch (Exception e) {
            this.startRow = startRow;
            this.startCol = startCol;
        }
    }

    @Override
    public void setEnd(int endRow, int endCol) {
        try {
            for (int x = 0; x < this.maze.length; x++) {
                for (int y = 0; y < this.maze.length; y++) {
                    if (this.maze[x][y] == 3) {
                        this.maze[x][y] = 1;
                    }
                }
            }
            this.maze[endRow][endCol] = 3;
        }
        catch(Exception e) {
            this.endRow = endRow;
            this.endCol = endCol;
        }
    }

    @Override
    public void setIcon(int colNum, int rolNum) {
    }


    @Override
    public int[][] getMaze() {
        return this.maze;
    }

    @Override
    public void setLastEdited() {
        LocalDateTime  lastEdited = LocalDateTime .now();
    }

    @Override
    public void setMaze(int[][] maze) {
        this.maze = maze;
    }
    public LocalDateTime  getfirstcreated() {
        return lastEdited;
    }




    @Override
    public String getMazeName() {
        return this.name;
    }

    /**
     * The setter method for maze name
     * @param name the maze name
     */
    public void setMazeName(String name) {
        this.name = name;
    }


    @Override
    public String getAuthor() {
        return author;
    }


    @Override
    public void setAuthor(String name) {
        this.author = name;
    }


    @Override
    public LocalDateTime  getCreatedDate() {
        return firstCreated.withNano(0).withSecond(0);
    }


    @Override
    public void settCreatedDate(String createDate) {
        this.firstCreated = LocalDateTime .parse(createDate).withNano(0).withSecond(0);
    }


    @Override
    public LocalDateTime  getLastModifiedDate() {
        return lastEdited;
    }

    /**
     * @param modifiedDate the last modified date to set
     */
    public void setLastModifiedDate(String modifiedDate) {
        this.lastEdited = LocalDateTime .parse(modifiedDate).withNano(0).withSecond(0);
    }
    @Override
    public void setIcon(PictureObject icon){this.icon = icon;}
    @Override
    public PictureObject getIcon(){
        return this.icon;
    }

}



