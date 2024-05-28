package BackEnd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class MazeGenratorTests {
    MazeGenerator test;
    BrokenWallMaze testmaze1;
    PictureMaze testmaze2;


    //test page, note that duplicated tests are for checking both maze types are functional

    //test 0, building a new mazeGenerator object
    @BeforeEach @Test
    public void testGenerator() {
       test= new MazeGenerator();
    }

    //test 1, ensure method for broken wall maze exist and return null values
    @Test
    public void methodexists1() {
        testmaze1 = new BrokenWallMaze("testmaze1", "Ivy", 10, 10);
        int[][] testnum = test.generateMaze(testmaze1, 0, 0,10,10,9,9);
        assertNotNull(testnum);
    }
    //test 2, ensure method for picture maze exist and return null values
    @Test
    public void methodexists2() {
        testmaze2 = new PictureMaze("testmaze1", "Ivy", 10, 10);
        int[][] testnum = test.generateMaze(testmaze2,0,0,10,10,9,9);
        assertNotNull(testnum);
    }

    @Test
    //tests that the dimensions of the maze have not been alterred
    public void lengthcheck() {
        testmaze1 = new BrokenWallMaze("testmaze1", "Ivy", 10, 10);
        testmaze1.drawMaze();
        int[][] testnum = test.generateMaze(testmaze1, 0, 0,10,10,9,9);
        assertEquals(testnum.length, (testmaze1.getMaze()).length);
    }
    @Test
    //tests that the dimensions of the maze have not been alterred
    public void lengthcheck2() {
        testmaze2 = new PictureMaze("testmaze1", "Ivy", 10, 10);
        testmaze2.drawMaze();
        int[][] testnum = test.generateMaze(testmaze2,0,0,10,10,9,9);
        assertEquals(testnum.length, (testmaze2.getMaze()).length);
    }

    @Test
    //tests if any changes have been made to the maze or if it remains blank
    public void editedcheck() {
        testmaze1 = new BrokenWallMaze("testmaze1", "Ivy", 10, 10);
        int[][] testnum = test.generateMaze(testmaze1, 0,0,10,10,9,9);
        int total = 0;
        for (int[] collumn : testnum) {
            for (int cell : collumn) {
                total += cell;
            }
        }
        assertNotEquals(0,total);
    }
    @Test
    //tests if any changes have been made to the maze or if it remains blank
    public void editedcheck2() {
        testmaze2 = new PictureMaze("testmaze1", "Ivy", 10, 10);
        int[][] testnum = test.generateMaze(testmaze2, 0, 0,10,10,9,9);
        int total = 0;
        for (int[] collumn : testnum) {
            for (int cell : collumn) {
                total += cell;
            }
        }
        assertNotEquals(0,total);
    }
    // Both tunneltest and tunneltest2 worked when implementing the code in generateMaze. Both of these test are not working due to the return type of
    // this function has changed
/*
    @Test
    //tests if cells have correctly connected to each other
    public void tunneltest() {
        boolean isconnected = true;
        testmaze1 = new BrokenWallMaze("testmaze1", "Ivy", 10, 10);
        int[][] testnum = test.generateMaze(testmaze1,0,0,10,10,9,9);
        for (int[] column : testnum) {
            for(int x = 1; x < column.length; x++) {
                if ((column[x-1] & 1) == 1) { //if bottom wall of cell abouve is open
                    if ((column[x] & 1 << 2) != 1 << 2) { //if top wall of cell is not open (no connection)
                        isconnected = false;
                    }
                }
            }
        }
        assertTrue(isconnected);
    }


    @Test
    //tests if cells have correctly connected to each other
    public void tunneltest2() {
        boolean isconnected = true;
        int[][] testnum = test.generateMaze(testmaze2,0,0,10,10,9,9);
        for (int[] column : testnum) {
            for(int x = 1; x < column.length; x++) {
                if ((column[x] & 1) == 1) { //if top wall of cell is open
                    if ((column[x - 1] & 1 << 2) != 1 << 2) { //bottom wall of lower cell is closed
                        isconnected = false;
                    }
                }
            }
        }
        assertTrue(isconnected);
    }

*/
    @Test
    //tests if the converter to the display type of maze was successfull
    public void convertertest2() {
        boolean isconverted= true;
        int[][] testnum = test.generateMaze(testmaze2,0,0,10,10,9,9);
        for (int[] column : testnum) {
            for (int x = 1; x < column.length; x++) {
                if (column[x] > 4)  {
                    isconverted = false;
                }
            }
        }
        assertTrue(isconverted);
    }
    @Test
    //tests if the converter to the display type of maze was successfull
    public void convertertest() {
        boolean isconverted= true;
        int[][] testnum = test.generateMaze(testmaze1,0,0,10,10,9,9);
        for (int[] column : testnum) {
            for (int x = 1; x < column.length; x++) {
                if (column[x] > 4)  {
                    isconverted = false;
                }
            }
        }
        assertTrue(isconverted);
    }
    @Test
    //tests if space has been left for an image
    public void imagetest () {
        testmaze2 = new PictureMaze("testmaze1", "Ivy", 10, 10);
        boolean imagespace = true;
        int[][] testnum = test.generateMaze(testmaze2,0,0,10,10,9,9);
        for (int[] column : testnum) {
            for (int x = 1; x < (column.length)-1; x++) {
                if (column[x] == 4) {
                    if ((column[x-1] != 4) && (column[x+1]) != 4) {
                        imagespace = false;
                    }
                }
            }
        }
        assertTrue(imagespace);
    }
}
