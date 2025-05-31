/*
* Urlify: write a method to replace all spaces in a string with '%20'. You may assume that the string has sufficient space at the end
* to hold the additional characters, and that you are give the "true" length of the string. (use a character array so that operations
* can be performed in place.
*
* Example
*
* Input: "Mr John Smith     ", 13
* Output: "Mr%20John%20Smith"
*
*/

// Insight - edit the string from the end and working backwards
class Solution {
    static void replaceSpaces(char[] str, int trueLength){
        int spaceCount = 0, index;

        // Count the number of spaces in the "true" length
        for (int i = 0; i < trueLength; i++) {
            if (str[i] == ' ') {
                spaceCount++;
            }
        }

        // New index after replacements
        index = trueLength + spaceCount * 2;

        // Work backwards
        for(int i = trueLength - 1; i >= 0; i--) {
            if(str[i] == ' ') {
                str[index - 1] = '0';
                str[index - 2] = '2';
                str[index - 3] = '%';
                index = index - 3;
            } else {
                str[index - 1] = str[i];
                index--;
            }
        }
    }

    public static void main(String[] args){
        // Test input with sufficient buffer space
        char[] str = "Mr John Smith    ".toCharArray(); // 13 true chars, 4 extra spaces
        System.out.printf("Before: %s%n", String.valueOf(str));
        replaceSpaces(str, 13);
        System.out.printf("After: %s%n", String.valueOf(str));
    }
}