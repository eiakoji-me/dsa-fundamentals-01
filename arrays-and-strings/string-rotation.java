/*
* String Rotation: Assume you have a method i 5Su b 5 tr ing which checks if one word is a substring
* of another. Given two strings, 51 and 52, write code to check if 52 is a rotation of 51 using only one
* call to i5Sub5tring (e.g., "waterbottle" is a rotation of" erbottlewat").
*/

import java.util.function.*;

/*
* If we imagine that s2 is a rotation of s1, then we can ask what the rotation point is. For example, if you
* rotate waterbottle after wat. you get erbottlewat. In a rotation, we cut s1 into two parts, x and y,
* and rearrange them to get s2.
* s1 = xy = waterbottle
* x = wat
* y = erbottle
* s2 = yx = erbottlewat
* So, we need to check if there's a way to split s1 into x and y such that xy = s1 and yx = s2. Regardless of
* where the division between x and y is, we can see thatyx will always be a substring of xyxy.That is, s2 will
* always be a substring of s1s1.
* And this is precisely how we solve the problem: simply do isSubstring(slsl, s2).
*/
class Solution {
    public static void main(String[] args){
        BiPredicate<String, String> isRotation = (s1, s2) -> {
            int len = s1.length();
            /*Check that s1 and s2 are equal length and not empty*/
            if(len == s2.length() && len > 0) {
                /* Concatenate s1 and s1 with new buffer*/
                String s1s1 = s1 + s1;
                return s1s1.indexOf(s2) > -1;
            }
            return false;
        };
        String s1 = "waterbottle", s2 = "erbottlewat";
        System.out.printf("isRotation: [%s, %s] -> %b %n", s1, s2, isRotation.test(s1, s2));
    }
}