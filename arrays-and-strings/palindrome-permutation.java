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
class Solution {

}