package BackEnd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.time.LocalDateTime ;

public class MazeTests {
    BrokenWallMaze example1;
    PictureMaze example2;

    @Test
    public void classExists() {
        example1 = new BrokenWallMaze("testmaze1", "Ivy", 10, 10);
        assertNotNull(example1);
    }
    @Test
    public void otherclassExists() {
        example2 = new PictureMaze("testmaze1", "Ivy", 10, 10);
        assertNotNull(example2);
    }
    @Test
    public void getmazetest() {
        example1 = new BrokenWallMaze("testmaze1", "Ivy", 10, 10);
        example1.drawMaze();
        int[][] output = example1.getMaze();
        assertNotNull(output);
    }
    @Test
    public void getmazetest2() {
        example2 = new PictureMaze("testmaze1", "Ivy", 10, 10);
        example2.drawMaze();
        int[][] output = example2.getMaze();
        assertNotNull(output);
    }
    @Test
    public void changestartcheck() {
        example1 = new BrokenWallMaze("testmaze1", "Ivy", 10, 10);
        example1.setStart(0,0);
        example1.drawMaze();
        int[][] output = example1.getMaze();
        assertEquals(output[0][1] , 2);
    }
    @Test
    public void changestartcheck2() {
        example2 = new PictureMaze("testmaze1", "Ivy", 10, 10);
        example2.setStart(0,0);
        example2.drawMaze();
        int[][] output = example2.getMaze();
        assertEquals(output[0][1] , 2);
    }

    @Test
    public void changeendcheck() {
        example1 = new BrokenWallMaze("testmaze1", "Ivy", 10, 10);
        example1.setStart(9,0);
        example1.drawMaze();
        example1.setEnd(0,1);
        int[][] output = example1.getMaze();
        assertEquals(output[0][1], 3);
    }

    @Test
    public void changeendcheck2() {
        example2 = new PictureMaze("testmaze1", "Ivy", 10, 10);
        example2.setStart(9, 0);
        example2.drawMaze();
        example2.setEnd(0, 0);
        int[][] output = example2.getMaze();
        assertEquals(output[0][0], 3);
    }
    @Test
    public void editcheck() {
        example1 = new BrokenWallMaze("testmaze1", "Ivy", 10, 10);
        example1.drawMaze();
        int[][] tester = example1.editMaze(0, 0, 3);
        assertEquals(tester[0][0], 3);
    }
    @Test
    public void editcheck2() {
        example2 = new PictureMaze("testmaze1", "Ivy", 10, 10);
        example2.drawMaze();
        int[][] tester = example2.editMaze(0, 0, 3);
        assertEquals(tester[0][0], 3);
    }
    @Test
    public void datechecker() {
        LocalDateTime createdTime = LocalDateTime .now().withNano(0).withSecond(0);
        example1 = new BrokenWallMaze("testmaze1", "Ivy", 10, 10);
        example1.settCreatedDate(String.valueOf(createdTime));
        assertEquals(String.valueOf(createdTime),String.valueOf(example1.getCreatedDate()));
    }
    @Test
    public void datechecker2() {
        LocalDateTime createdTime = LocalDateTime .now().withNano(0).withSecond(0);
        example2 = new PictureMaze("testmaze1", "Ivy", 10, 10);
        example2.settCreatedDate(String.valueOf(createdTime));
        assertEquals(String.valueOf(createdTime),String.valueOf(example2.getCreatedDate()));

    }

}
