/*
* Problem: Jack and Jill – Fruit Buckets Puzzle
Jack and Jill were passing through a jungle on their way when they encountered a monster.
* The monster agreed to let them pass only if they solved a puzzle. With no other choice,
* they accepted the challenge.
*
* You are given N empty buckets. Each bucket needs to be filled with a specific number of fruits. Your goal is to minimize the number of operations required to achieve the target configuration.
*
* Allowed Operations
* You can only use the following operations to add fruits:
* Double Operation:In a single operation, all buckets double the number of fruits they currently contain.
* Increment Operation:You can increment the number of fruits in a single bucket by 1.
* Each such increment is counted as a separate operation.
* Constraints:You cannot remove or decrease the number of fruits in any bucket.
* Input
* An integer n — the number of buckets.
* An integer array array[] — where array[i] represents the required number of fruits in the i-th bucket.
*
* Constraints:
* 1 <= array[i] <= 1000
* 1 <= i <= 1000
*
* Function Signature
*
* Example 1
*
* Input:
*
* array = [2, 3]
* Output:
* 4
* Explanation:
* Increment bucket 1 → [1, 0]
* Increment bucket 2 → [1, 1]
* Double → [2, 2]
* Increment bucket 2 → [2, 3]
*
* Example 2
* Input:
* array = [16, 16, 16]
* Output:
* 7
* Explanation:
* Increment all 3 buckets to 1 → [1, 1, 1] → 3 operations
* Double 4 times → [2, 2, 2] → [4, 4, 4] → [8, 8, 8] → [16, 16, 16] → 4 operationsTotal = 3 + 4 = 7 operations
*/

import java.util.*;

class JackAndJill {
    public static int playTheGame(int[] numbers){
        int incrementOps = 0;
        int maxDoubleOps = 0;

        for(int number : numbers){
            int countDoubleOps = 0;
            while(number > 0){
                // Min ops for even numbers is doubling
                if(number % 2 == 0){
                    countDoubleOps++;
                    number /= 2;
                } else { // odds can only be attained by incs
                    number--;
                    incrementOps++;
                }
            }
            maxDoubleOps = Math.max(maxDoubleOps, countDoubleOps);
        }

        return incrementOps + maxDoubleOps;
    }

    public static void main(String[] args){
        List<int[]> inputs = List.of(
                    new int[]{2,3},
                    new int[]{16,16,16},
                    new int[]{}
                );
        //inputs.stream().map(n -> playTheGame(n)).forEach(System.out::println);
        inputs.forEach(arr -> {
            System.out.printf("%s -> %d%n", Arrays.toString(arr),playTheGame(arr));
        });
    }
}
