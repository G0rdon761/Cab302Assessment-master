package BackEnd;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCMazeDatabaseSource implements mazeDatabaseSource {
    private static final String create_Table =
            "CREATE TABLE IF NOT EXISTS maze ("
                + "idx INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE," // from https://stackoverflow.com/a/41028314
                + "mazeId VARCHAR(30),"
                + "mazeName VARCHAR(30),"
                + "maze BLOB(512),"
                + "author VARCHAR(30),"
                + "createDate VARCHAR(50),"
                + "lastModifiedDate VARCHAR(50),"
                + "mazeImages BLOB(512),"
                + "mazeType VARCHAR(20));";
    private static final String insert_Maze =
            "INSERT INTO maze  (mazeId, mazeName, maze, author, createDate, LastModifiedDate, mazeImages, mazeType)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String get_MazeId = "SELECT mazeId FROM maze";
    private static final String get_MazeName = "SELECT mazeName FROM maze WHERE mazeId=?";
    private static final String get_Maze = "SELECT * FROM maze WHERE mazeId=?";
    private static final String get_all_Maze = "SELECT * FROM maze";
    private static final String get_Author = "SELECT author FROM maze WHERE mazeId=?";
    private static final String get_Create_Date = "SELECT createDate FROM maze WHERE mazeId=?";
    private static final String get_Last_Modified_Date = "SELECT lastModifiedDate FROM maze WHERE mazeId=?";
    private static final String delete_Maze = "DELETE FROM maze WHERE mazeName=?";
    private static final String Count_Rows = "SELECT COUNT(*) FROM maze";
    private static final String update_Rows = "UPDATE maze SET maze = ?, LastModifiedDate = ?, mazeImages = ? WHERE mazeId = ?";
    private List<Maze> mazeDatabase = new ArrayList<Maze>();
    private Connection connection;
    private PreparedStatement insertMaze;
    private PreparedStatement getMazeList;
    private PreparedStatement getAuthor;
    private PreparedStatement getCreateDate;
    private PreparedStatement getLastModifiedDate;
    private PreparedStatement getMazeName;
    private PreparedStatement deleteMaze;
    private PreparedStatement updateMaze;
    private PreparedStatement getAllMaze;
    private PreparedStatement rowCount;


    /**
     * Class constructor
     */
    public JDBCMazeDatabaseSource() {
        connection = JDBCConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(create_Table);
            insertMaze = connection.prepareStatement(insert_Maze);
            getMazeList = connection.prepareStatement(get_Maze);
            getMazeName = connection.prepareStatement(get_MazeName);
            getCreateDate = connection.prepareStatement(get_Create_Date);
            getLastModifiedDate = connection.prepareStatement(get_Last_Modified_Date);
            deleteMaze = connection.prepareStatement(delete_Maze);
            rowCount = connection.prepareStatement(Count_Rows);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * This function is use for adding a maze to the database
     * @param maze The maze to be saved in the database
     */
    public void insertMaze(Maze maze){
        String nameofmaze = maze.getMazeName();
        if (getAuthor(nameofmaze) == null) {
            try {
                int[][] matrix = maze.getMaze();
                Matrixobject matrixobj = new Matrixobject(matrix);
                byte[] bytestream = matrixToBytes(matrixobj);
                //            "INSERT INTO maze  (mazeId, mazeName, maze, author, createDate, LastModifiedDate)" +
                //            " VALUES (?, ?, ?, ?, ?, ?);";
                byte[] mazebytes = mazeToBytes(maze);
                insertMaze.setString(1, maze.getMazeName());
                insertMaze.setString(2, maze.getMazeName());
                //       insertMaze.setBinaryStream(3, bias);
                insertMaze.setBytes(3, bytestream);
                insertMaze.setString(4, maze.getAuthor());
                insertMaze.setString(5, String.valueOf(maze.getCreatedDate()).replace('T',' '));
                insertMaze.setString(6, String.valueOf(maze.getLastModifiedDate()).replace('T',' '));
                try {
                    insertMaze.setBytes(7, mazebytes);
                }
                catch(Exception e) {
                    insertMaze.setBytes(7,null);
                }
                try { //tests if maze to upload is a brokenwallmaze or picturemaze
                    BrokenWallMaze typetest = (BrokenWallMaze) maze;
                    insertMaze.setString(8,null);
                }
                catch(Exception e) {
                    insertMaze.setString(8,"notnull");
                }
                insertMaze.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            updateMaze(maze);
        }
    }


    /**
     * This function is use for updating the data of the maze in the database
     * @param maze The maze to be updated in the database
     */
    public void updateMaze(Maze maze){
        int[][] mazearray = maze.getMaze();
        try {
            Matrixobject matrixobj = new Matrixobject(mazearray);
            byte[] bytestream = matrixToBytes(matrixobj);
            byte[] mazebytes = mazeToBytes(maze);

            updateMaze = connection.prepareStatement(update_Rows);
            updateMaze.setBytes(1,bytestream);
            updateMaze.setString(2, String.valueOf(maze.getLastModifiedDate()).replace('T',' '));
            updateMaze.setBytes(3,mazebytes);
            updateMaze.setString(4,maze.getMazeName());
            updateMaze.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function is use for getting the maze object from the database
     * @param id The id of the maze
     * @return The maze object
     */
    public Maze GetMazeObject(String id) {
        ResultSet rs;
        byte[] bytestream = null;

        try {
            getMazeList = connection.prepareStatement(get_Maze);

            getMazeList.setString(1, id);
            rs = getMazeList.executeQuery();
            while (rs.next()) {
                bytestream = rs.getBytes("mazeImages");
            }
            ByteArrayInputStream baip = new ByteArrayInputStream(bytestream);
            ObjectInputStream ois = new ObjectInputStream(baip);
            Maze output = (Maze) ois.readObject();
        }
        catch(SQLException e) {

        }
        catch(Exception e) {

        }
            return null;
        }

    /**
     * This function is use for getting the maze from the database
     * @param id The id of the maze
     * @return The maze of the id number in aa 2d int array
     */
    public int[][] getMaze(String id) {
        ResultSet rs = null;
        int[][] intoutput = null;
        byte[] bytestream = null;
        try {
            getMazeList = connection.prepareStatement(get_Maze);

            getMazeList.setString(1, id);
            rs = getMazeList.executeQuery();
            while (rs.next()) {
                bytestream = rs.getBytes("maze");
            }
            ByteArrayInputStream baip = new ByteArrayInputStream(bytestream);
            ObjectInputStream ois = new ObjectInputStream(baip);
            Matrixobject output = (Matrixobject) ois.readObject();
            intoutput = output.getmatrix();
            //return intoutput;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        catch (Exception e) {

        }
        return intoutput;
    }

    /**
     * This function is use for getting all the maze from the database
     * @return A List containing every maze in the database
     */
    public List<String> getAllMaze(){
        List<String> maze = new ArrayList<String>();
        ResultSet rs = null;

        try {
            getMazeList = connection.prepareStatement(get_all_Maze);
            rs = getMazeList.executeQuery();
            while (rs.next()) {
                //maze.add(rs.getString("mazeId"));

                String name = (rs.getString("mazeId"));
                String author = (rs.getString("author"));
                String createdate = (rs.getString("createDate"));
                String lastmodified = (rs.getString("LastModifiedDate"));
                maze.add(name + ", by " + author + ", created " + createdate + ", last modified " + lastmodified);


            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return maze;
    }

    /**
     * This furnction is use for confirm byte stream to object
     * @param byteArr object in byte stream form
     * @return Object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static Object getObject(byte[] byteArr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArr);
        ObjectInput in = new ObjectInputStream(bis);
        return in.readObject();
    }

    /**
     * This function is use for getting all the detail of maze from the database
     * @param id The id of the maze
     * @return All the details in Maze object
     */
    public Maze getMazeDetails(String id) {
        ResultSet rs = null;

        try {
            getMazeName = connection.prepareStatement(get_Maze);
            getMazeName.setString(1, String.valueOf(id));
            rs = getMazeName.executeQuery();
            while(rs.next()) {
                if (rs.getBytes("mazeImages") == null) {
                    BrokenWallMaze maze = new BrokenWallMaze();
                    maze.setMazeName(rs.getString("mazeId"));
                    maze.setAuthor(rs.getString("author"));
                    maze.settCreatedDate(rs.getString("createDate"));
                    maze.setLastModifiedDate(rs.getString("LastModifiedDate"));
                    int[][] output = getMaze(id);
                    maze.setMaze(output);
                    return maze;
                }
                else {
                    /*PictureMaze maze = new PictureMaze();
                    maze.setMazeName(rs.getString("mazeId"));

                    maze.setAuthor(rs.getString("author"));
                    maze.settCreatedDate(rs.getString("createDate"));
                    maze.setLastModifiedDate(rs.getString("LastModifiedDate"));*/
                    byte [] mazeByte = rs.getBytes("mazeImages");
                    int[][] output = getMaze(id);
                    //maze.setMaze(output);
                    if(rs.getString("mazeType") == null){
                        BrokenWallMaze mazes = (BrokenWallMaze) getObject(mazeByte);
                        return mazes;
                    }
                    else {
                        PictureMaze mazes = (PictureMaze) getObject(mazeByte);
                        return mazes;
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch(Exception e){

        }
        return null;
    }

    /**
     * This function is use for getting the author of the maze
     * @param id The id of the maze
     * @return The author's name of the maze
     */
    public String getAuthor(String id){
        String author = null;
        ResultSet rs = null;
        try {
            getAuthor = connection.prepareStatement(get_Author);
            getAuthor.setString(1, id);
            rs = getAuthor.executeQuery();
            while(rs.next()) {
                author = rs.getString("author");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return author;

    }

    /**
     * This function is use for getting the date of the maze
     * @param id The id of the maze
     * @return The date of the maze created
     */
    public String getCreateDate(String id){
        String createDate = null;
        ResultSet rs = null;
        try {
            getCreateDate.setString(1, id);
            rs = getCreateDate.executeQuery();
            while(rs.next()) {
                createDate = rs.getString("createDate");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return createDate;
    }

    /**
     * This function is use for getting the last modified date of the maze
     * @param id The id of the maze
     * @return The latest date of the maze being modified
     */
    public String getLastModifiedDate(String id) {
        String lastModifiedDate = null;
        ResultSet rs = null;
        try {
            getLastModifiedDate.setString(1, id);
            rs = getLastModifiedDate.executeQuery();
            while(rs.next()) {
                lastModifiedDate = rs.getString("lastModifiedDate");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lastModifiedDate;
    }

    /**
     * This function is use for getting the name of the maze with the provided id
     * @param id The id of the maze
     * @return The name of the maze
     */
    public String getMazeName(String id){
        String mazeName = null;
        ResultSet rs = null;
        try {
            getMazeName.setString(1, id);
            rs = getMazeName.executeQuery();
            while(rs.next()) {
                mazeName = rs.getString("mazeName");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return mazeName;
    }


    /**
     * Gets the number of mazes in the maze database.
     *
     * @return size of maze database.
     */
    public int getSize() {
        ResultSet rs = null;
        int rows = 0;


        try {
            rs = rowCount.executeQuery();
            rs.next();
            rows = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        return rows;
    }


    /**
     * This function is use for deleting the maze with the provided id
     * @param id The id of the maze
     */
    public void deleteMaze(String id){
        try {
            deleteMaze.setString(1, id);
            deleteMaze.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This function is for converting matrix to bytes
     * @param matrix the maze in matrix format
     * @return maze in byte format
     * @throws Exception
     */
    public byte[] matrixToBytes(Matrixobject matrix) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(matrix);
        byte[] matrixasbytes = baos.toByteArray();
        return matrixasbytes;
    }

    /**
     * This function is for converting maze to bytes
     * @param maze the maze object
     * @return maze in byte format
     * @throws Exception
     */
    public byte[] mazeToBytes(Maze maze) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(maze);
        byte[] matrixasbytes = baos.toByteArray();
        return matrixasbytes;
    }

    /**
     * This function is for closing the database
     */
    public void close() {

        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}


