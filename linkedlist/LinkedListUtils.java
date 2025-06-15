public class LinkedListUtils {

    public static class LinkedListNode {
        public int data;
        public LinkedListNode next;

        public LinkedListNode(int data) {
            this.data = data;
            this.next = null;
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
}
