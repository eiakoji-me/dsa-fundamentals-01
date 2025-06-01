/*
* String Compression: Implement a method to perform basic string compression using the counts
* of repeated characters. For example, the string aabcccccaaa would become a2blc5a3. If the
* "compressed" string would not become smaller than the original string, your method should return
* the original string. You can assume the string has only uppercase and lowercase letters (a - z).
*/

import java.io.*;

class Solution {
    String compressMethodI(String str){
        String compressedString = "";
        int countConsecutive = 0;
        for(int i=0; i < str.length(); i++){
            countConsecutive++;
            /* If next character is different from the current, append this char to result. */
            if(i + 1 >= str.length() || str.charAt(i) != str.charAt(i+1)) {
                compressedString += "" + str.charAt(i) + countConsecutive;
                countConsecutive = 0;
            }
        }
        return compressedString.length() < str.length() ? compressedString : str;
    }

    String compressMethodII(String str){
        StringBuilder compressed = new StringBuilder();
        int countConsecutive = 0;
        for(int i = 0; i < str.length(); i++){
            countConsecutive++;

            boolean isLast = (i == str.length() - 1);
            boolean isDifferent = !isLast && str.charAt(i) != str.charAt(i + 1);
            /* If next character is different from the current, append this char to result */
            if (isLast || isDifferent) {
                compressed.append(str.charAt(i));
                compressed.append(countConsecutive);
                countConsecutive = 0;
            }
        }
        return compressed.length() < str.length() ? compressed.toString() : str;
    }

    public static void main(String[] args){
        String filename = "input-data/string-compression.input.txt";
        Solution solution = new Solution();

        System.out.println("Running Method I...");
        int[] statsI = runTestSet(filename, solution::compressMethodI);
        System.out.println("\nRunning Method II...");
        int[] statsII = runTestSet(filename, solution::compressMethodII);

        System.out.printf("%n--- Summary ---\n");
        System.out.printf("Method I:  %d/%d passed (%.1f%%), Time: %d µs%n",
                statsI[1], statsI[0], 100.0 * statsI[1] / statsI[0], statsI[2] / 1000);
        System.out.printf("Method II: %d/%d passed (%.1f%%), Time: %d µs%n",
                statsII[1], statsII[0], 100.0 * statsII[1] / statsII[0], statsII[2] / 1000);
    }

    static int[] runTestSet(String filename, java.util.function.Function<String, String> compressFn) {
        int testCount = 0, passCount = 0;
        long start = System.nanoTime();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank() || line.startsWith("#")) continue;

                String[] parts = line.split("\\|");
                if (parts.length != 4) continue;

                String testName = parts[0].trim();
                String input = parts[1].trim();
                String expected = parts[2].trim();

                String result = compressFn.apply(input);
                boolean passed = result.equals(expected);
                if (passed) passCount++;
                testCount++;

                System.out.printf("Test %-30s | Input: '%-20s' | Expected: '%-15s' | Actual: '%-15s' | %s%n",
                        testName, input, expected, result, passed ? "✅ PASS" : "❌ FAIL");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        long end = System.nanoTime();
        return new int[]{testCount, passCount, (int)(end - start)};
    }
}