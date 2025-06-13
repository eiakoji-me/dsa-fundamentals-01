/*
* Zero Matrix: Write an algorithm such that if an element in an MxN matrix is 0, its entire row and
* column are set to 0.
*/

import java.util.*;
import java.util.function.*;

/*
* Option I
* We use two arrays to keep track of all the rows with zeros and all
* the columns with zeros. We then nullify rows and columns based on the values in these arrays.
*
* Option II - TODO: study
* To make this somewhat more space efficient we could use a bit vector instead of a boolean array.
* It wouldstill be O(N) space.
*
*/
class Solution {
    // Pretty-print the matrix
    static Consumer<int[][]> printMatrix = (matrix) -> {
        for(int[] row : matrix) {
            for(int value : row) {
                System.out.printf("%d ", value);
            }
            System.out.printf("%n");
        }
        System.out.println("-------");
    };

    static void setZeros(int[][] matrix) {
        boolean[] row = new boolean[matrix.length];
        boolean[] column = new boolean[matrix[0].length];

        BiConsumer<int[][], Integer> nullifyRow = (mat, r) -> {
            for(int j=0; j < mat[0].length; j++) {
                mat[r][j] = 0;
            }
        };
        BiConsumer<int[][], Integer> nullifyColumn = (mat, col) -> {
            for (int i = 0; i < mat.length; i++) {
                mat[i][col] = 0;
            }
        };

        // Store the row and column index with value 0
        for(int i=0; i < matrix.length; i++){
            for(int j=0; j < matrix[0].length; j++){
                if(matrix[i][j] == 0){
                    row[i] = true;
                    column[j] = true;
                }
            }
        }

        // Nullify rows
        for (int i=0; i < row.length; i++){
            if(row[i]) nullifyRow.accept(matrix, i);
        }

        //Nullify columns
        for(int j=0; j < column.length; j++){
            if(column[j]) nullifyColumn.accept(matrix, j);
        }
    }

    public static void main(String[] args){
        int[][] matrix = {
                {1,2,3},
                {0,5,6},
                {7,8,9},
                {10,0,1},
        };

        System.out.println("Before: ");
        printMatrix.accept(matrix);
        setZeros(matrix);
        System.out.println("After: ");
        printMatrix.accept(matrix);
    }
}