/*
* Write code to remove duplicates from an unsorted linked list.
* FOLLOW UP - How would you solve this problem if a temporary buffer is not allowed.
*/

import java.util.*;

/*
* In order to remove duplicates from a linked list, we need to be able to track duplicates. A simple hash table
* will work well here.
* In the below solution, we simply iterate through the linked list, adding each element to a hash table. When
* we discover a duplicate element, we remove the element and continue iterating. We can do this all in one
* pass since we are using a linked list.
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

    // Method I - buffer allowed
    static void deleteDups(LinkedListNode n) {
        HashSet<Integer> set = new HashSet<Integer>();
        LinkedListNode previous = null;

        while (n != null){
            if(set.contains(n.data)) {
                previous.next = n.next;
            } else {
                set.add(n.data);
                previous = n;
            }
            n = n.next;
        }
    }

    // Method II - No buffer, two pointer approach
    static void deleteDupsNoBuffer(LinkedListNode head) {
        LinkedListNode current = head;
        while (current != null) {
            LinkedListNode runner = current;
            while (runner.next != null) {
                if (runner.next.data == current.data) {
                    runner.next = runner.next.next; // remove duplicate
                } else {
                    runner = runner.next;
                }
            }
            current = current.next;
        }
    }

    public static void main(String[] args){
        LinkedListNode list = buildList(new int[]{1, 2, 2, 3});
        System.out.println("Before: ");
        printList(list);
        System.out.println();
        System.out.println("After: ");
        //deleteDups(list);
        deleteDupsNoBuffer(list);
        printList(list);
    }
}