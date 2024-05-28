package BackEnd;
import java.io.Serializable;

public class Matrixobject implements Serializable{
    private int[][] matrix;


    public Matrixobject(int[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * Get the matrix object
     * @return serializable 2D matrix
     */
    public int[][] getmatrix() {
        return matrix;
    }
}
