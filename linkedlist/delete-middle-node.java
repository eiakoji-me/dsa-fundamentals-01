/**
* Delete Middle Node: Implement an algorithm to delete a node in the middle (i.e., any node but
* the first and last node, not necessarily the exact middle) of a singly linked list, given only
*  access to that node.
* EXAMPLE
* lnput:the node c from the linked list a->b->c->d->e->f
* Result: nothing is returned, but the new linked list looks like a ->b->d- >e- >f
*/
import java.util.function.*;
/**
 * In this p roblem, you are not given access to the head of the linked list. You only have access to that node.
 * The solution is simply to copy the data from the next node over to the current node, and then to delete the
 * next node.
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

    public static void main(String[] args){
        Predicate<LinkedListNode> deleteNode = n -> {
            if(n == null || n.next == null) return false; // Failure
            LinkedListNode next = n.next;
            n.data = next.data;
            n.next = next.next;
            return true;
        };

        BiFunction<LinkedListNode, Integer, LinkedListNode> findNode = (head, value) -> {
            LinkedListNode current = head;
            while(current != null && current.data != value){
                current = current.next;
            }
            return current;
        };

        LinkedListNode list = buildList(new int[]{1,2,3,4,5,6,7,8,9});
        System.out.println("Before: ");
        printList(list);
        deleteNode.test(findNode.apply(list, 5));
        System.out.println("After: ");
        printList(list);
    }
}