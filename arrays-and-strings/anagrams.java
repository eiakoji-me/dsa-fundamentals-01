import java.util.*;
import java.lang.*;

class Source {
    public static boolean anagrams(String s1, String s2) {
        if(s1.length() != s2.length()) return false;
        int[] chars = new int[128];
        for(char ch : s1.toCharArray()){
            //int n = Character.toLowerCase(ch) - 'a';
            chars[ch]++;
        }
        System.out.printf("%s%n", Arrays.toString(chars));
        for(char ch : s2.toCharArray()){
            //int n = Character.toLowerCase(ch) - 'a';
            chars[ch]--;
            if(chars[ch] < 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(Source.anagrams("cats", "tocs"));
    }
}