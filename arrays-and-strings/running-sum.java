/*
* Problem statement:
* Given a one-dimensional array of integers, create a new array that represents the running sum of the original array.
The running sum at position i in the new array is calculated as the sum of all the numbers in the original array from
* the 0th index up to the i-th index (inclusive). Formally, the resulting array should be computed as follows:
* result[i] = sum(nums[0] + nums[1] + ... + nums[i]) for each i from 0 to the length of the array minus one.
*
*/

import java.util.*;
import java.io.*;

class Solution {
    int[] runningSum(int[] nums) {
        if(nums.length < 2) return nums;
        //We can throw error if upper bound is exceeded

        int n = nums.length;
        for(int i=1; i < n; i++) nums[i] = nums[i-1]+nums[i];
        return nums;
    }

    public static void main(String[] args) {
        String filePath = "input-data/running-sum.input.txt";
        Solution solution = new Solution();

        int strMaxLength = 25;
        java.util.function.Function<String, String> truncate = s ->
                s.length() > strMaxLength ? "%s...".formatted(s.substring(0, strMaxLength-3)) : s;
        java.util.function.Function<String, int[]> parseCsv = csv ->
                Arrays.stream(csv.trim().split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] test = line.split("\\|");
                if (test.length != 3) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                String name = test[0].trim();
                int[] input = parseCsv.apply(test[1]);
                int[] expected = parseCsv.apply(test[2]);

                int[] actual = solution.runningSum(Arrays.copyOf(input, input.length));
                boolean passed = Arrays.equals(expected, actual);

                System.out.printf(
                        //"Test %-25s | Input: %-25s | Expected: %-25s | Actual: %-25s | %-30s%n",
                        "Test %-25s | Input: %-25s | Expected: %-25s | %-30s%n",
                        name,
                        truncate.apply(Arrays.toString(input)),
                        truncate.apply(Arrays.toString(expected)),
                        passed ? "PASS ✅" : "FAIL ❌"
                );
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}