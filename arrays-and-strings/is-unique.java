import java.util.*;
import java.lang.*;

/*
 * Is Unique: implement an algorithm to determine if a string has all unique characters. What if
 * you can not use additional structures;
 */

class Solution {
    static boolean isUnique(String input){
        if(input.length() > 128) return false;
        boolean[] charSet = new boolean[128];
        for(int i=0; i < input.length(); i++){
            int val = input.charAt(i);
            if(charSet[val]) return false;
            charSet[val] = true;
        }
        return true;
    }

    public static void main(String[] args){
        List<String> inputs = List.of(
                "Write",
                "permutation",
                "string",
                "cannot");
        for(String input : inputs)
            System.out.printf("%s is unique: %b%n", input, isUnique(input));
    }
}
