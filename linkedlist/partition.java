/**
 * Partition: Write code to partition a linked list around a value x, such that all nodes less
 * than x come before all nodes greater than or equal to x. If x is contained within the list the
 * values of x only need to be after the elements less than x (see below). The partition element
 * x can appear anywhere in the "right partition"; it does not need to appear between the left and
 * right partitions.
 * EXAMPLE
 * Input: 3 -> 5 -> 8 -> 5 -> 10 -> 2 -> 1 [partition= 5]
 * Output: 3 -> 1 -> 2 -> 10 -> 5 -> 5 -> 8
 */

import java.util.*;

/**
 * Rather than shifting and swapping elements, we can
 * actually create two different linked lists: one for elements less than x, and one for elements greater than or
 * equal to x.
 * We iterate through the linked list, inserting elements into our before list or our after list. Once we reach
 * the end of the linked list and have completed this splitting, we merge the two lists.
 * This approach is mostly "stable" in that elements stay in their original order, other than the necessary movement
 * around the partition. The code below implements this approach.
 */
class Solution {
    static class LinkedListNode {
        public int data;
        public LinkedListNode next;

        public LinkedListNode(int d){
            data = d;
            next = null;
        }
    }

    static LinkedListNode buildList(int[] values) {
        if (values.length == 0) return null;

        LinkedListNode head = new LinkedListNode(values[0]);
        LinkedListNode current = head;
        for (int i = 1; i < values.length; i++) {
            current.next = new LinkedListNode(values[i]);
            current = current.next;
        }
        return head;
    }

    static void printList(LinkedListNode node){
        LinkedListNode current = node;
        while(current != null){
            System.out.print(current.data);
            if(current.next != null) System.out.print("->");
            current = current.next;
        }
        System.out.println();
    }

    /* Pass in tghe head of the linked list and the value to partition around */
    static LinkedListNode partitionMethodI(LinkedListNode node, int x) {
        LinkedListNode beforeStart = null;
        LinkedListNode beforeEnd = null;
        LinkedListNode afterStart = null;
        LinkedListNode afterEnd = null;

        /* Partition list */
        while (node != null) {
            LinkedListNode next = node.next;
            node.next = null;
            if (node.data < x) {
                /* Insert node into end of before list */
                if (beforeStart == null) {
                    beforeStart = node;
                    beforeEnd = beforeStart;
                } else {
                    beforeEnd.next = node;
                    beforeEnd = node;
                }
            } else {
                /* Insert node into end of after list */
                if (afterStart == null) {
                    afterStart = node;
                    afterEnd = afterStart;
                } else {
                    afterEnd.next = node;
                    afterEnd = node;
                }
            }
            node = next;
        }

        if (beforeStart == null) {
            return afterStart;
        }

        /* Merge before list and after list */
        beforeEnd.next = afterStart;
        return beforeStart;
    }

    /**
     * If we don't care about making the elements of the list "stable" (which there's no obligation to, since the
     * interviewer hasn't specified that), then we can instead rearrange the elements by growing the list at the
     * head and tail.
     * In this approach, we start a"new" list (using the existing nodes). Elements bigger than the pivot element are
     * put at the tail and elements smaller are put at the head. Each time we insert an element, we update either
     * the head or tail.
     */
    static LinkedListNode partitionMethodII(LinkedListNode node, int x){
        LinkedListNode head = node;
        LinkedListNode tail = node;
        while(node != null) {
            LinkedListNode next = node.next;
            if(node.data < x) {
                /* Insert node at head. */
                node.next = head;
                head = node;
            } else {
                /* Insert node at tail. */
                tail.next = node;
                tail = node;
            }
            node = next;
        }
        tail.next = null;
        // The head has changed, so we need to return it to the user.
        return head;
    }

    public static void main(String[] args){
        LinkedListNode list = buildList(new int[]{3,5,8,5,10,2,1});
        System.out.println("Before: ");
        printList(list);
        System.out.println("After: ");
        //partitionMethodI(list, 5);
        //printList(list);
        var partitionedList = partitionMethodII(list, 5);
        printList(partitionedList);
    }
}