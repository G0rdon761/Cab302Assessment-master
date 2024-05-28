package BackEnd;

import static org.junit.jupiter.api.Assertions.*;

import BackEnd.MazeSolver.MazeSolver;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class MazeSolverTests {
    private MazeSolver solverTester;
    int[][] regularMaze={

            {1,2,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,1},
            {1,1,1,1,0,1,0,1},
            {1,0,0,0,0,1,1,1},
            {1,1,1,1,0,0,3,1},
            {1,1,1,1,1,1,1,1},
    };
    int [][] regularMazeSolution={
            {0, 1},
            {1, 1},
            {2, 1},
            {3, 1},
            {3, 2},
            {3, 3},
            {3, 4},
            {4, 4},
            {5, 4},
            {6, 4},
            {6, 5},
            {6, 6}
    };
    int[][] mazeWithoutStart={

            {1,1,1,1,1,1,1,1},
            {1,0,1,0,0,0,0,1},
            {1,0,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,1},
            {1,1,1,1,0,1,3,1},
            {1,0,0,0,0,1,1,1},
            {1,1,1,1,0,0,0,1},
            {1,1,1,1,1,1,1,1},
    };
    int [][] mazeWithoutStartSolution = {

    };
    int[][] mazeWithoutEnd={

            {1,2,1,1,1,1,1,1},
            {1,0,1,0,0,0,0,1},
            {1,0,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,1},
            {1,1,1,1,0,1,0,1},
            {1,0,0,0,0,1,1,1},
            {1,1,1,1,0,0,0,1},
            {1,1,1,1,1,1,1,1},
    };
    int [][] mazeWithoutEndSolution={

    };
    int[][] unsolveableMaze={

            {1,2,1,1,1,1,1,1},
            {1,1,1,0,0,0,0,1},
            {1,0,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,1},
            {1,1,1,1,0,1,0,1},
            {1,0,0,0,0,1,1,1},
            {1,1,1,1,0,0,3,1},
            {1,1,1,1,1,1,1,1},
    };
    int [][] unsolveableMazeSolution={

    };

    @BeforeEach @Test
    public void MazeSolver(){
        solverTester = new MazeSolver();
    }

    //test 1, able to solve a regular maze
    @Test
    public void solveSolveableMaze(){
        int[][] result = solverTester.getPath(regularMaze);
        assertTrue(Arrays.deepEquals(regularMazeSolution, result));
    }

    //test2, solve an unsolvable solution(empty list)
    @Test
    public void solveUnsolveableMaze(){
        int [][] result = solverTester.getPath(unsolveableMaze);
        for (int[] row: unsolveableMazeSolution
        ) {
        }
        assertArrayEquals(unsolveableMazeSolution, result);
    }

    //test3, solve a maze without start(empty list)
    @Test
    public void solveWithoutStartMaze(){
        int [][] result = solverTester.getPath(mazeWithoutStart);
        for (int[] row: result
        ) {
        }
        for (int[] row: mazeWithoutStartSolution
        ) {
        }
        assertTrue(Arrays.deepEquals(mazeWithoutStartSolution, result));
    }

    //test4, solve a maze without end(empty list)
    @Test
    public void solveWithoutEndMaze(){
        int [][] result = solverTester.getPath(mazeWithoutEnd);

    }


    @Test
    public void testgeneratedmaze() {
        BrokenWallMaze testmaze = new BrokenWallMaze("test","ivy",10,10);
        testmaze.drawMaze();
        int[][] result = solverTester.getPath(testmaze.getMaze());
    }
}
