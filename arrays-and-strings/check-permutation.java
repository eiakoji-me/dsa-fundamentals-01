import java.util.*;
import java.util.function.Function;

/*
 * Check permutation: given two strings, write a method to decide if one is a permutation of the other
 */

class Solution {
    static boolean methodI(String s, String t){
        Function<String, String> fn = str -> {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            return new String(chars);
        };
        return fn.apply(s).equals(fn.apply(t));
    }

    static boolean methodII(String s, String t){
        if(s.length() != t.length()) return false;

        int[] chars = new int[128];
        for(int i = 0; i < s.length(); i++){
            chars[s.charAt(i)]++;
        }

        for(int i = 0; i < t.length(); i++){
            int c = t.charAt(i);
            chars[c]--;
            if(chars[c] < 0) return false;
        }
        return true;
    }
    
    public static void main(String[] args){
        String s = "good", t = "dogo";
        System.out.printf("M1: %s is permutation of %s -> %b.%n", s, t, methodI(s,t));
        System.out.printf("M2: %s is permutation of %s -> %b.%n", s, t, methodII(s,t));
    }
}
