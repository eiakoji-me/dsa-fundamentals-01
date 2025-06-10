/*
* Rotate Matrix: Given an image represented by an NxN matrix, where each pixel in the image is 4
* bytes, write a method to rotate the image by 90 degrees. Can you do this in place?
*/

import java.io.*;
import java.util.function.*;


/*
* Solution:
*
* Approach A:
* Transpose the matrix: Flip it over its diagonal
*   matrix[i][j] ⇄ matrix[j][i]
*   Reverse each row: Mirror horizontally
* ------
* Approach B:
* Because we're rotating the matrix by 90 degrees, the easiest way to do this is to implement the rotation in
* layers. We perform a circular rotation on each layer, moving the top edge to the right edge, the right edge
* to the bottom edge, the bottom edge to the left edge, and the left edge to the top edge.
* for i=0 to n
*   temp = top[i]
*   top[i] = left[i]
*   left[i] = bottom[i]
*   right[i] = temp
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

    // Rotates the matrix 90 degrees clockwise
    static boolean rotateApproachA(int[][] matrix) {
        int n = matrix.length;
        if (n == 0 || matrix[0].length != n) return false; // Not a square matrix

        // Reverse a row in-place
        Consumer<int[]> reverseRow = row -> {
            int left = 0, right = row.length - 1;
            while (left < right){
                int temp = row[right];
                row[right] = row[left];
                row[left] = temp;
                left++;
                right--;
            }
        };

        // Step 1: Transpose the matrix
        for (int i = 0; i < n; i++) {
            for(int j = i+1; j < n; j++) {
                // Swap elements across the diagonal
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // step 2: Reverse each row
        for (int[] row : matrix){
            reverseRow.accept(row);
        }
        return true;
    }

    static boolean rotateApproachB(int[][] matrix) {
        // Check for non-square matrix
        if (matrix.length == 0 || matrix.length != matrix[0].length) return false;
        int n = matrix.length;

        // Rotate the matrix layer by layer, starting from the outermost layer
        for (int layer = 0; layer < n / 2; layer++) { // n/2 is the number of layers
            int first = layer;                // Top/left index of the layer
            int last = n - 1 - layer;         // Bottom/right index of the layer
            for (int i = first; i < last; i++) {
                int offset = i - first;       // Offset within the layer
                // Save the top element
                int top = matrix[first][i];
                // Move left -> top
                matrix[first][i] = matrix[last - offset][first];
                // Move bottom -> left
                matrix[last - offset][first] = matrix[last][last - offset];
                // Move right -> bottom
                matrix[last][last - offset] = matrix[i][last];
                // Assign saved top -> right
                matrix[i][last] = top;
            }
        }
        return true; // Successfully rotated in-place
    }


    public static void main(String[] args) {
        int[][] matrix = {
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9}
            };
        System.out.printf("Rotate with approach A%n");
        System.out.println("-------");
        System.out.println("Before: ");
        printMatrix.accept(matrix);
        long start = System.nanoTime();
        rotateApproachA(matrix);
        long end = System.nanoTime();
        System.out.println("After: ");
        printMatrix.accept(matrix);
        System.out.printf("Elapsed time: %.3f ms.%n", (end - start) / 1_000_000.0);
        System.out.println("-------");

        System.out.printf("Rotate with approach B%n");
        System.out.println("-------");
        System.out.println("Before: ");
        printMatrix.accept(matrix);
        start = System.nanoTime();
        rotateApproachB(matrix);
        end = System.nanoTime();
        System.out.println("After: ");
        printMatrix.accept(matrix);
        System.out.printf("Elapsed time: %.3f ms.%n", (end - start) / 1_000_000.0);
        System.out.println("-------");
    }
}