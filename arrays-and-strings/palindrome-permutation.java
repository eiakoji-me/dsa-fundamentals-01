/*
* Palindrome Permutation: Given a string, write a function to check if it is a permutation of
* a palindrome. A palindrome is a word or phrase that is the same forwards and backwards. A
* permutation is a rearrangement of letters. The palindrome does not need to be limited to just
* dictionary words.
* EXAMPLE
* Input: Tact Coa
* Output: True (permutations: "taco cat'; "atc o eta·; etc.)
*
*/


/*
* Insight:
*
* 1. Strings with even length (after removing all non-letter characters) must have
* all even counts of characters.
*
* 2. Strings of an odd length must have exactly one character with
* an odd count. Of course, an "even" string can't have an odd number of exactly one character,
* otherwise it wouldn't be an even-length string (an odd number+ many even numbers= an odd
* number).
*
* 3. Likewise, a string with odd length can't have all characters with even counts (sum of
* evens is even). It's therefore sufficient to say that, to be a permutation ot a palindrome, a string
* can have no more than one character that is odd. This will cover both the odd and the even cases.
*/
import java.io.*;
import java.util.*;
import java.util.function.*;

class Solution {
    /* Convert the given character to it's numberic equivalent  */
    Function<Character, Integer> charToInt = c -> {
        char lowerC = Character.toLowerCase(c);
        if(lowerC >= 'a' && lowerC <= 'z') return lowerC-'a';
        return -1;
    };

    /* Count how many times each character appears. */
    int[] buildCharFrequencyTable(String phrase){
        int[] table = new int[Character.getNumericValue('z') -
                Character.getNumericValue('a') + 1];
        for(char c : phrase.toCharArray()) {
            int x = charToInt.apply(c);
            if(x != -1){
                table[x]++;
            }
        }
        return table;
    }
    /*
    * Method I (Hash table): Use a hash table to count how many times each algorithm appears
    * and verify the the max number of odd counts is one
    * */
    boolean isPermutationOfPalindromeMethodI(String phrase) {
        /* Check that no more than one character has an odd count */
        Predicate<int[]> checkMaxOneOdd = table -> {
            boolean foundOdd = false;
            for(int count : table) {
                if(count % 2 == 1) { // Check for odd count
                    if (foundOdd) {
                        return false; // More than one odd count found
                    }
                    foundOdd = true;
                }
            }
            return true;
        };
        int[] table = buildCharFrequencyTable(phrase);
        return checkMaxOneOdd.test(table);
    }

    /*
    * Method II (Char count array): Instead of checking the number of odd counts at the end, we can check as we go along.
    * Then as soon as we get to the end, we have our answer
    */
    boolean isPermutationOfPalindromeMethodII( String phrase) {
        int countOdd = 0;
        int[] table = new int[Character.getNumericValue('z') -
                Character.getNumericValue('a') + 1];
        for(char c : phrase.toCharArray()) {
            int x = charToInt.apply(c);
            if(x != -1){
                table[x]++;
                if(table[x] % 2 == 1) {
                    countOdd++;
                } else {
                    countOdd--;
                }
            }
        }
        return countOdd <= 1;
    }

    /*
    * Method III: Bit vector
    */
    boolean isPermutationOfPalindromeMethodIII(String phrase) {
        /* Toggle the i-th bit */
        BiFunction<Integer, Integer, Integer> toggle = (bitVector,index) -> {
            if(index < 0) return bitVector;
            int mask = 1 << index;
            if((bitVector & mask) == 0) {
                bitVector |= mask;
            } else {
                bitVector &= ~mask;
            }
            return bitVector;
        };
        /* Create a bit vector for the string. For each letter with value i, toggle the i-th bit */
        Function<String, Integer> createBitVector = (s) -> {
            int bitVector = 0;
            for( char c: s.toCharArray()){
                int x = charToInt.apply(c);
                bitVector = toggle.apply(bitVector, x);
            }
            return bitVector;
        };
        /* Check that exactly one bit is set by substracting one from the integer and
        * ANDing it with the original integer
        */
        Predicate<Integer> checkExactlyOneBitSet = bitVector ->
                (bitVector & (bitVector - 1)) == 0;

        int bv = createBitVector.apply(phrase);
        return bv == 0 || checkExactlyOneBitSet.test(bv);
    }


    public static void main(String[] args) {
        Solution solution = new Solution();

        // Load test cases into memory first
        List<String[]> testCases = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("input-data/palindrome-permutation.inputs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    testCases.add(new String[] {
                            parts[0].trim(), // name
                            parts[1].trim(), // input
                            parts[2].trim()  // expected
                    });
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            return;
        }

        // Define all methods
        List<Function<String, Boolean>> methods = List.of(
                solution::isPermutationOfPalindromeMethodI,
                solution::isPermutationOfPalindromeMethodII,
                solution::isPermutationOfPalindromeMethodIII
        );
        String[] methodNames = { "Method I", "Method II", "Method III" };

        // Run tests for each method
        long[] methodTimes = new long[methods.size()];
        int[] passCounts = new int[methods.size()];
        int totalTests = testCases.size();

        for (int m = 0; m < methods.size(); m++) {
            Function<String, Boolean> method = methods.get(m);
            int pass = 0;
            long start = System.nanoTime();

            for (String[] test : testCases) {
                String name = test[0];
                String input = test[1];
                boolean expected = Boolean.parseBoolean(test[2]);
                boolean result = method.apply(input);
                if (result == expected) pass++;
            }

            long end = System.nanoTime();
            methodTimes[m] = end - start;
            passCounts[m] = pass;
        }

        // Print detailed test results using Method III (as primary display)
        System.out.println("Detailed Test Results (using Method III):\n");
        for (String[] test : testCases) {
            String name = test[0];
            String input = test[1];
            boolean expected = Boolean.parseBoolean(test[2]);
            boolean result = solution.isPermutationOfPalindromeMethodIII(input);
            boolean passed = (result == expected);
            System.out.printf("Test %-35s | Input: %-30s | Expected: %-5b | Actual: %-5b | %-35s%n",
                    name,
                    input.length() > 30 ? input.substring(0, 27) + "..." : input,
                    expected,
                    result,
                    passed ? "PASS ✅" : "FAIL ❌"
            );
        }

        // Print summary comparison table
        System.out.println("\n--- Performance Summary ---");
        System.out.printf("%-12s | %-15s | %-15s%n", "Method", "Pass Count", "Total Time (ms)");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < methods.size(); i++) {
            System.out.printf("%-12s | %-15d | %-15.3f%n", methodNames[i], passCounts[i], methodTimes[i] / 1_000_000.0);
        }

        System.out.println("\nAll methods processed " + totalTests + " tests.");
    }

}