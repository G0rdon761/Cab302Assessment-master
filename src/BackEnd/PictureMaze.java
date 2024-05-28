package BackEnd;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;
import java.time.LocalDateTime ;

public class PictureMaze extends Maze implements Serializable {
    private PictureObject startImg;
    private PictureObject endImg;
    private String name;
    private String author;
    private PictureObject icon;
    private int endRow;
    private int endCol;
    private int height;
    private int width;
    private int startRow;
    private int startCol;
    private int[][] maze;
    private LocalDateTime firstCreated;
    private LocalDateTime  lastEdited;
    /**
     * Class constructor accepts name, author, height, width of the maze
     * @param name name of the maze
     * @param author author of the maze
     * @param height height of the maze
     * @param width width of the maze
     */
    public PictureMaze(String name, String author, int height, int width) {
        super(name, author, height, width);
        this.height = height;
        this.width = width;
        this.name = name;
        this.author = author;
        this.firstCreated = LocalDateTime .now().withNano(0).withSecond(0);
        this.lastEdited = LocalDateTime .now();
        Random rand = new Random();
        this.startRow = rand.nextInt(width);
        this.startCol = rand.nextInt(height);
        this.endRow = rand.nextInt(width);
        this.endCol = rand.nextInt(height);

    }

    /**
     * Class constructor accepts name, author, height, width and images of the maze
     * @param name name of the maze
     * @param author author of the maze
     * @param height height of the maze
     * @param width width of the maze
     * @param icon icon of the maze
     * @param startPic starting point image of the maze
     * @param endPic finishing point image of the maze
     */
    public PictureMaze(String name, String author, int height, int width, PictureObject icon, PictureObject startPic, PictureObject endPic) {
        super(name, author, height, width);
        this.height = height;
        this.width = width;
        this.name = name;
        this.author = author;
        LocalDateTime  firstCreated = LocalDateTime .now().withNano(0).withSecond(0);
        this.lastEdited = LocalDateTime .now().withNano(0).withSecond(0);
        Random rand = new Random();
        this.startRow = rand.nextInt(width);
        this.startCol = rand.nextInt(height);
        this.endRow = rand.nextInt(width);
        this.endCol = rand.nextInt(height);

    }

    /**
     * Class constructor
     */
    public PictureMaze() {

    }

    /**
     * This function gets the starting position of the maze
     * @return the starting position of the maze
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
    /**
     * This function set the image indicating the starting point of the maze
     * @param directory The file path of the image to be used
     */
    public void setStartImg(String directory){

    }
    /**
     * This function set the image indicating the finishing point of the maze
     * @param directory The file path of the image to be used
     */
    public void setFinishImg(String directory){

    }

    @Override
    public int[][] editMaze(int row, int col, int type) {
        this.maze[row][col] = type;
        return this.maze;
    }

    @Override
    public void drawMaze() {
        MazeGenerator drawer = new MazeGenerator();
        this.maze = drawer.generateMaze(this, startRow, startCol,width,height,endRow,endCol);
    }

    @Override
    public void setStart(int startRow, int startCol) {
        try {
            for (int x = 0; x < this.maze.length; x++) {
                for (int y = 0; y < this.maze.length; y++) {
                    if (this.maze[x][y] == 2) {
                        this.maze[x][y] = 0;
                    }
                }
            }
            this.maze[startRow][startCol] = 2;
            if(this.startImg!=null){
                startImg.setRow(startRow);
                startImg.setColumn(startCol);
            }
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
                        this.maze[x][y] = 0;
                    }
                }
            }
            this.maze[endRow][endCol] = 3;
            if(this.endImg!=null){
                endImg.setRow(endRow);
                endImg.setColumn(endCol);
            }
        }
        catch(Exception e) {
            this.endRow = endRow;
            this.endCol = endCol;
        }
    }

    @Override
    public void setIcon(int colNum, int rolNum) {}

    @Override
    public int[][] getMaze() {
        return this.maze;
    }

    @Override
    public void setMaze(int[][] maze) {
        this.maze=maze;
    }

    /**
     * This is the getter method for first created date
     * @return the date time value
     */
    public LocalDateTime  getfirstcreated() {
        return lastEdited;
    }
    /**
     * @return the maze name
     */
    @Override
    public String getMazeName() {
        return this.name;
    }

    @Override
    public void setLastEdited() {

    }

    /**
     * This is the setter method for maze name
     * @param name the maze name to set
     */
    public void setMazeName(String name) {
        this.name = name;
    }


    @Override
    public String getAuthor() {
        return author;
    }


    @Override
    public LocalDateTime  getLastModifiedDate() {
        return lastEdited;
    }

    @Override
    public LocalDateTime  getCreatedDate() {return firstCreated;}
    /**
     * The setter method for the last modified date
     * @param ModifiedDate the last modified date to set
     */
    public void setLastModifiedDate(String ModifiedDate) {
        this.lastEdited = LocalDateTime .parse(ModifiedDate).withNano(0).withSecond(0);
    }


    @Override
    public void setAuthor(String author) { this.author = author;
    }

    /**
     * The setter method for created date
     * @param createDate the created date the maze
     */
    public void settCreatedDate(String createDate) {

    }

    @Override
    public void setIcon(PictureObject image){
        this.icon=image;
    }
    /**
     * This function set the image
     * @param image image object
     */
    public void setImage (PictureObject image) {
        switch(image.getName()){
            case "Icon":
                this.icon = image;
                break;
            case "Start":
                this.startImg = image;
                startImg.setColumn(this.startRow);
                startImg.setRow(this.startCol);
                break;
            case "End":
                this.endImg = image;
                endImg.setColumn(this.endCol);
                endImg.setRow(this.endRow);
        }


    }
    @Override
    public PictureObject getIcon(){
        return this.icon;
    }

    /**
     * This function get the start image
     * @return the start image object
     */
    public PictureObject getStartImg(){
        return  this.startImg;
    }

    /**
     * This function get the finish image
     * @return the end image object
     */
    public PictureObject getEndImg(){
        return  this.endImg;
    }
}
