package BackEnd.MazeSolver;


import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifficultyPercentageTest {
    private DifficultyPercentage percentageTester;
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
    int pathTileNumber = 21;
    int solutionTileNumber = 12;
    int deadEndNumber = 3;

    int[][] noSolutionMaze={

            {1,2,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,1},
            {1,1,1,1,0,1,0,1},
            {1,0,0,0,0,1,1,1},
            {1,1,1,1,0,1,3,1},
            {1,1,1,1,1,1,1,1},
    };
    int noSolutionpathTileNumber = 21;
    int noSolutionsolutionTileNumber = 0;

    int[][] freeRoamMaze={

            {1,2,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,1},
            {1,0,0,0,0,1,0,1},
            {1,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,3,1},
    };
    int freeRoampathTileNumber = 36;
    int freeRoamDeadEndNumber = 0;
    @BeforeEach @Test
    public void DifficultyPercentage(){
        percentageTester = new DifficultyPercentage();
    }
    //test 1, get the number of Path tiles
    @Test
    public void NumberOfPathTiles(){
        int actualResult = percentageTester.getNumberofPath(regularMaze);
        assertEquals(pathTileNumber, actualResult);
    }

    //test 2, get the number of tiles the Solution has
    @Test
    public void NumberOfSolutionTiles(){
        int actualResult = percentageTester.getNumberofSolutionTiles(regularMaze);
        assertEquals(solutionTileNumber,actualResult);
    }
    //test 3, get the number of dead end
    @Test
    public void DeadEnd(){
        int actualResult = percentageTester.getDeadEndNumber(regularMaze);
        assertEquals(deadEndNumber, actualResult);
    }
    //test 4, get the percentage of solution tile number to path tile number
    @Test
    public void SolutionPercentage(){
        double solutionNumberDouble = solutionTileNumber;
        double pathTileNumberDouble = pathTileNumber;
        double expectedResult = solutionNumberDouble/pathTileNumberDouble*100;
        double actualResult = percentageTester.getSolutionPercentage(regularMaze);
        assertEquals(expectedResult,actualResult);
    }
    //test 4.1, get the percentage of solution tile number to path tile number for no solution maze
    @Test
    public void WithoutSolutionPercentage(){
        double solutionNumberDouble =  noSolutionsolutionTileNumber;
        double pathTileNumberDouble = noSolutionpathTileNumber;
        double expectedResult = solutionNumberDouble/pathTileNumberDouble*100;
        double actualResult = percentageTester.getSolutionPercentage(noSolutionMaze);
        assertEquals(expectedResult,actualResult);
    }
    @Test
    //test 5, get the percentage of solution tile number to path tile number
    public void DeadEndPercentage(){
        double deadEndNumberDouble = deadEndNumber;
        double pathTileNumberDouble = pathTileNumber;
        double expectedResult = deadEndNumberDouble/pathTileNumberDouble*100;
        double actualResult = percentageTester.getDeadEndPercentage(regularMaze);
        assertEquals(expectedResult,actualResult);
    }
    @Test
    //test 5, get the percentage of solution tile number to path tile number
    public void FreeRoamDeadEndPercentage(){
        double deadEndNumberDouble = freeRoamDeadEndNumber;
        double pathTileNumberDouble = freeRoampathTileNumber;
        double expectedResult = deadEndNumberDouble/pathTileNumberDouble*100;
        double actualResult = percentageTester.getDeadEndPercentage(freeRoamMaze);
        assertEquals(expectedResult,actualResult);
    }

}
