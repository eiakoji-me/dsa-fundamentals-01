/**
 * Sum Lists: You have two numbers represented by a linked list, where each node contains a single
 * digit. The digits are stored in reverse order, such that the 1 's digit is at the head of the list.
 * Write a function that adds the two numbers and returns the sum as a linked list.
 * EXAMPLE
 * Input: (7-> 1 -> 6) + (5 -> 9 -> 2).That is,617 + 295.
 * Output: 2 -> 1 -> 9. That is, 912.
 * FOLLOW UP
 * Suppose the digits are stored in forward order. Repeat the above problem.
 * Input: (6 -> 1 -> 7) + (2 -> 9 -> 5).That is,617 + 295.
 * Output: 9 -> 1 -> 2. That is, 912.
 */
import java.util.*;

/**
 * We do the following:
 * 1. We add 7 and 5 first, getting a result of 12. 2 becomes the first node in our linked list, and we "carry" the
 * 1 to the next sum.
 * List: 2 -> ?
 * 2. We then add 1 and 9, as well as the "carry;' getting a result of 11. 1 becomes the second element of our
 * linked list, and we carry the 1 to the next sum.
 * List: 2 -> 1 ->?
 * 3. Finally, we add 6, 2 and our"carrY:'to get 9. This becomes the final element of our linked list.
 * List: 2 -> 1 -> 9.
 */
class Solution {
    public static class LinkedListNode {
        public int data;
        public LinkedListNode next;

        public LinkedListNode(int data) {
            this.data = data;
            this.next = null;
        }

        public LinkedListNode(){
            this.next = null;
        }

        public void setNext(LinkedListNode value){
            this.next = value;
        }
    }

    public static LinkedListNode buildList(int[] values) {
        if (values == null || values.length == 0) return null;

        LinkedListNode head = new LinkedListNode(values[0]);
        LinkedListNode current = head;
        for (int i = 1; i < values.length; i++) {
            current.next = new LinkedListNode(values[i]);
            current = current.next;
        }
        return head;
    }

    public static void printList(LinkedListNode node) {
        LinkedListNode current = node;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) System.out.print("->");
            current = current.next;
        }
        System.out.println();
    }

    static LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2, int carry) {
        if(l1 == null && l2 == null && carry == 0) {
            return null;
        }

        LinkedListNode result = new LinkedListNode();
        int value = carry;
        if(l1 != null) value += l1.data;
        if( l2 != null) value += l2.data;

        result.data = value % 10; /* Second digit of number */
        /* Recurse */
        if (l1 != null || l2 != null) {
            LinkedListNode more = addLists(
                    l1 == null ? null : l1.next,
                    l2 == null ? null : l2.next,
                    value >= 10 ? 1 : 0
            );
            result.setNext(more);
        }
        return result;
    }

    /**
     * 1. One list may be shorter than the other, and we cannot handle this "on the flY:' For example, suppose we
     * were adding (1 -> 2 -> 3-> 4) and (5-> 6-> 7). We need to know that the 5 should be"matched"with the
     * 2, not the 1. We can accomplish this by comparing the lengths of the lists in the beginning and padding
     * the shorter list with zeros.
     * 2. In the first part, successive results were added to the tail (i.e., passed forward). This meant that the recursive
     * call would be passed the carry, and would return the result (which is then appended to the tail). In
     * this case, however, results are added to the head (i.e., passed backward). The recursive call must return
     * the result, as before, as well as the carry. This is not terribly challenging to implement, but it is more
     * cumbersome. We can solve this issue by creating a wrapper class called Partial Sum.
     */
    static class PartialSum {
        public LinkedListNode sum = null;
        public int carry = 0;
    }

    static int length(LinkedListNode node) {
        int len = 0;
        while (node != null) {
            len++;
            node = node.next;
        }
        return len;
    }

    static LinkedListNode padList(LinkedListNode node, int padding) {
        for (int i = 0; i < padding; i++) {
            LinkedListNode padded = new LinkedListNode(0);
            padded.next = node;
            node = padded;
        }
        return node;
    }

    static LinkedListNode insertBefore(LinkedListNode list, int data) {
        LinkedListNode node = new LinkedListNode(data);
        node.next = list;
        return node;
    }

    static LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2) {
        int len1 = length(l1);
        int len2 = length(l2);

        /** Pad the shorter list with zeros - see note (1) */
        if (len1 < len2) {
            l1 = padList(l1, len2 - len1);
        } else {
            l2 = padList(l2, len1 - len2);
        }

        /** Add lists */
        PartialSum sum = addListHelper(l1, l2);

        /**
         * If there was a carry left over, inser this at the front of the list. Otherwise,
         * just return the linked list.
         */
        if ( sum.carry == 0) {
            return sum.sum;
        } else {
            LinkedListNode result = insertBefore(sum.sum, sum.carry);
            return result;
        }
    }

    static PartialSum addListHelper(LinkedListNode l1, LinkedListNode l2) {
        if (l1 == null && l2 == null) {
            PartialSum sum = new PartialSum();
            return sum;
        }
        /** Add smaller digits recursively */
        PartialSum sum = addListHelper(l1.next, l2.next);
        /** Add carry to current data */
        int val = sum.carry + l1.data + l2.data;
        /** Insert sum of current digits */
        LinkedListNode fullResult = insertBefore(sum.sum, val % 10);

        /** Return sum so far, and the carry value */
        sum.sum = fullResult;
        sum.carry = val / 10;
        return sum;
    }

    public static void main(String[] args) {
        LinkedListNode list1 = buildList(new int[]{7, 1, 6}); // represents 617
        LinkedListNode list2 = buildList(new int[]{5, 9, 2}); // represents 295

        System.out.print("List 1: ");
        printList(list1);
        System.out.print("List 2: ");
        printList(list2);

        LinkedListNode sum = addLists(list1, list2, 0);
        System.out.print("Sum: ");
        printList(sum); // expected 2 -> 1 -> 9 (912)

        LinkedListNode list3 = buildList(new int[]{6, 1, 7}); // 617 in forward order
        LinkedListNode list4 = buildList(new int[]{2, 9, 5}); // 295 in forward order

        System.out.print("List 3: ");
        printList(list3);
        System.out.print("List 4: ");
        printList(list4);

        LinkedListNode sumForward = addLists(list3, list4); // uses wrapper and padding
        System.out.print("Sum (forward): ");
        printList(sumForward); // expected 9 -> 1 -> 2

    }
}