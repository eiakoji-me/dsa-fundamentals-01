/*
* One Away: There are three types of edits that can be performed on strings: insert a character,
* remove a character, or replace a character. Given two strings, write a function to check if they are
* one edit (or zero edits) away.
*  EXAMPLE
* pale, ple -> true
* pales, pale -> true
* pale, bale -> true
* pale, bae -> false
*/
import java.io.*;
import java.util.*;
import java.util.function.*;

/*
* Insight - makes sense to think of the operations separately.
* Replacement operation - bale and pale
* Insertion  - apple, aple
* Removal - reversed insert i.e. aple, apple is insert apple, aple
*/
class Solution {
    static boolean oneEditAwayMethodI(String s, String t) {
        //Idea - Edit distance = number of difference. Distance must be less than 2 (0 or 1).
        BiPredicate<String, String> replacementPredicate = (a,b) -> {
            boolean foundDifference = false;
            for(int i = 0; i < a.length(); i++){
                if (a.charAt(i) != b.charAt(i)) {
                    if(foundDifference) return false;
                    foundDifference = true;
                }
            }
            return true;
        };
        //Idea - Check if you insert a character into a to make b.
        BiPredicate<String, String> insertionPredicate = (a,b) -> {
            int i = 0; // index in a
            int j = 0; // index in b
            while (j < b.length() && i < a.length()) { // iterate both strings to find at most one diference
                if (a.charAt(i) != b.charAt(j)) {
                    if (i != j) return false; // Already skipped once, can't skip again
                    j++; // Skip one character in b (i.e., attempt the insertion)
                } else {
                    i++;
                    j++;
                }
            }
            return true;
        };

        int difference = s.length() - t.length();
        if(difference == 0) return replacementPredicate.test(s,t);
        if(difference == 1) return insertionPredicate.test(t, s); // insert into s
        if(difference == -1) return insertionPredicate.test(s, t); // delete from s
        return false;
    }

    static boolean oneEditAwayMethodII(String s, String t) {
        /* Length checks */
        if(Math.abs(s.length() - t.length()) > 1){
            return false;
        }

        /* Get shorter and longer string. */
        String a = s.length() < t.length() ? s : t;
        String b = s.length() < t.length() ? t : s;

        int i = 0, j = 0;
        boolean foundDifference = false;
        while(i < a.length() && j < b.length()){
            if(a.charAt(i) != b.charAt(j)) {
                /* Ensure that this is the first difference found */
                if(foundDifference) return false;
                foundDifference = true;

                if(a.length() == b.length()) {
                    i++; // On replace move shorter pointer
                }
            } else {
                i++; // If matching, move shorter pointer
            }
            j++; // Always move pointer for longer string
        }
        return true;
    }

    public static void main(String[] args) {
        String filename = "input-data/one-edit-away.input.txt";
        int testCount = 0, passCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank() || line.startsWith("#")) continue;

                String[] parts = line.split("\\|");
                if (parts.length != 4) continue;

                String testName = parts[0].trim();
                String str1 = parts[1].trim();
                String str2 = parts[2].trim();
                boolean expected = Boolean.parseBoolean(parts[3].trim());

                boolean result = oneEditAwayMethodII(str1, str2);
                boolean passed = (result == expected);
                if (passed) passCount++;
                testCount++;

                System.out.printf("Test %-30s | Input: ('%-10s', '%-10s') | Expected: %-5b | Actual: %-5b | %-35s%n",
                        testName, str1, str2, expected, result,
                        passed ? "✅ PASS" : "❌ FAIL");
            }

            System.out.printf("%nSummary: %d/%d tests passed (%.1f%%)%n",
                    passCount, testCount, (100.0 * passCount / testCount));

        } catch (IOException e) {
            System.err.println("Error reading test file: " + e.getMessage());
        }
    }
}