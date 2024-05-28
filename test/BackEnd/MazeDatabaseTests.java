package BackEnd;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MazeDatabaseTests {
    JDBCMazeDatabaseSource testDatabase;
    BrokenWallMaze testMaze;

    // Test 0 building a new MazeDatabase
    @BeforeEach @Test
    public void testCreateDatabase(){
        testDatabase = new JDBCMazeDatabaseSource();
        testDatabase.deleteMaze("test1");
        testMaze = new BrokenWallMaze("test1","test", 10,10);
        testMaze.drawMaze();
        testDatabase.insertMaze(testMaze);
    }

    // Test 1 inserting a Maze into the database
    @Test
    public void testInsertMaze(){
        //testMaze = new BrokenWallMaze("test1","test", 10,10);
        //testMaze.drawMaze();
        testDatabase.insertMaze(testMaze);
    }

    // Test 2 updating Maze
    @Test
    public void testUpdateMaze(){
        //testMaze = new BrokenWallMaze("test1","test", 10,10);
        //testMaze.drawMaze();
        testDatabase.updateMaze(testMaze);
    }

    // Test 3 get Maze from database
    @Test
    public void testGetMaze(){
        //testMaze = new BrokenWallMaze("test1","test", 10,10);
        //testMaze.drawMaze();
        //testDatabase.insertMaze(testMaze);
        int[][] output = testDatabase.getMaze("test1");
        assertArrayEquals(testMaze.getMaze() , output);
        //System.out.println(output);

    }

    // Test 4 get a list of all the Mazes in the database
    @Test
    public void testGetAllMaze(){
        List<String> output = testDatabase.getAllMaze();
        assertNotNull(output);


    }

    // Test 5 get the author of the selected maze
    @Test
    public void testGetAuthor(){
        //testMaze = new BrokenWallMaze("test1","test", 10,10);
        //testMaze.drawMaze();
        String output = testDatabase.getAuthor(testMaze.getMazeName());
        assertEquals(testMaze.getAuthor() , output);
    }

    // Test 6
    @Test
    public void testGetCreateDate(){
        //testMaze = new BrokenWallMaze("test1","test", 10,10);
        //testMaze.drawMaze();
        String outputCreateDate =testDatabase.getCreateDate(testMaze.getMazeName());
        //System.out.println(String.valueOf(testMaze.getCreatedDate()).replace('T',''));
        //System.out.println(outputCreateDate);
        assertEquals( String.valueOf(testMaze.getCreatedDate()).replace('T',' ') , outputCreateDate);
    }

    // Test 7
    @Test
    public void testGetLastModifiedDate(){
        testMaze = new BrokenWallMaze("test1","test", 10,10);
        testMaze.drawMaze();
        String outputModifiedDate = testDatabase.getLastModifiedDate(testMaze.getMazeName());
        assertEquals(String.valueOf(testMaze.getLastModifiedDate()).replace('T',' ') , outputModifiedDate);

    }

    // Test 8
    @Test
    public void testGetMazeName(){
        testMaze = new BrokenWallMaze("test1","test", 10,10);
        testMaze.drawMaze();
        String OutputName = testDatabase.getMazeName(testMaze.getMazeName());
        assertEquals(testMaze.getMazeName() , OutputName);
    }

    // Test 9
    @Test
    public void testDeleteMaze(){
        testMaze = new BrokenWallMaze("test1","test", 10,10);
        testMaze.drawMaze();
        testDatabase.deleteMaze(testMaze.getMazeName());
        String OutputName = testDatabase.getMazeName(testMaze.getMazeName());
        assertEquals(null , OutputName);
    }

    @Test
    public void testMazeDetails(){
        testMaze = new BrokenWallMaze("test1","test", 10,10);
        testMaze.drawMaze();
        BrokenWallMaze outputMaze = (BrokenWallMaze) testDatabase.getMazeDetails("test1");
        assertEquals(testMaze.getMazeName() , outputMaze.getMazeName());
        assertEquals(testMaze.getAuthor() , outputMaze.getAuthor());

    }
}

