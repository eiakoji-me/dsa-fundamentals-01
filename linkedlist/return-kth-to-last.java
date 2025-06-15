/**
* Return Kth to Last: Implement an algorithm to find the kth to last element of a singly linked list.
*/
import java.util.*;
/**
* We will approach this problem both recursively and non-recursively. Remember that recursive solutions are
* often cleaner but less optimal. For example, in this problem, the recursive implementation is about half the
* length of the iterative solution but also takes 0( n) space, where n is the number of elements in the linked
* list.
* Note that for this solution, we have defined k such that passing in k = 1 would return the last element, k
* 2 would return to the second to last element, and so on. It is equally acceptable to define k such that k
* = 0 would return the last element.
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

    static class Index {int value = 0;}

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

    /**
    * This algorithm recurses through the linked list. When it hits the end, the method passes back a counter set
    * to 0. Each parent call adds 1 to this counter. When the counter equals k, we know we have reached the kth
    * to last element of the linked list.
    *
    * Alternatives: Use C++ pass by reference.
    *
    */
    static int printKthToLast(LinkedListNode head, int k){
        if(head == null){
            return 0;
        }
        int index = printKthToLast(head.next, k) + 1;
        if(index == k) {
            System.out.printf("%d-th to last node is %d%n", k, head.data);
        }
        return index;
    }
    /* Use wrapper class to encapsulate value and index */
    static LinkedListNode kthToLastRecursive(LinkedListNode head, int k, Index idx){
        if(head == null) return null;
        LinkedListNode node = kthToLastRecursive(head.next, k, idx);
        idx.value++;
        if(idx.value == k) return head;
        return node;
    }

    /*
    * A more optimal, but less straightforward, solution is to implement this iteratively. We can use two pointers,
    * p1 and p2. We place them k nodes apart in the linked list by putting p2 at the beginning and moving p1 k nodes
    * into the list. Then, when we move them at the same pace, pl will hit the end of the linked list after
    * LENGTH - k steps. At that point, p2 will be LENGTH - k nodes into the list, or k nodes from the end.
    */
    LinkedListNode kthToLastIterative(LinkedListNode head, int k){
        LinkedListNode p1 = head;
        LinkedListNode p2 = head;

        /* Move p1 k nodes into the list. */
        for(int i=0; i < k; i++) {
            if(p1 == null) return null; // Out of bounds
            p1 = p1.next;
        }

        /* Move them at same pace. When p1 hits the ends p2 will be at the right element*/
        while (p1 != null){
            p1 = p1.next;
            p2 = p2.next;
        }
        return p2;
    }

    public static void main(String[] args) {
        int[] array = new int[10];
        for(int i=0;i<10;i++) array[i] = new Random().nextInt(100);
        LinkedListNode list = buildList(array);
        printList(list);
        printKthToLast(list, 5);

        Index idx = new Index();
        LinkedListNode result = kthToLastRecursive(list, 5, idx);
        System.out.println("Recursive result node: " + (result != null ? result.data : "null"));

        LinkedListNode iterResult = new Solution().kthToLastIterative(list, 5);
        System.out.println("Iterative result node: " + (iterResult != null ? iterResult.data : "null"));

    }
}