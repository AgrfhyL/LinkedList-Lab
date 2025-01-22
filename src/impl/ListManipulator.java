package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;

/**
 * This class represents the iterative implementation of the IListManipulator interface.
 *
 */
public class ListManipulator implements IListManipulator {

    @Override
    public int size(ListNode head) {
        if (head == null)
            return 0;

        ListNode current = head;
        int count = 0;
        while (current.next != head) {
            count++;
            current = current.next;
        }

        return count+1;
    }

    @Override
    public boolean isEmpty(ListNode head) {
        return head == null;
    }

    @Override
    public boolean contains(ListNode head, Object element) {
        if (head == null)
            return false;

        ListNode current = head;
        while (!current.element.equals(element)) { // if exit loop, definitely found
            if (current.next == head) { //if is at last element
                return false;
            }
            current = current.next;
        }
        return true;
    }

    @Override
    public int count(ListNode head, Object element) {
        if (head == null) return 0;
        int size = size(head);
        int count = 0;
        ListNode current = head;
        for (int i = 0; i < size; i++) {
            if (current.element.equals(element)) {
                count++;
            }
            current = current.next;
        }
        return count;
    }

    @Override
    public String convertToString(ListNode head) {
        String out = "";
        if (head == null) //if no element
            return out;
        else {
            out += head.element.toString();
            ListNode cur = head.next;
            //traverses the rest, previous 2 lines just make the loop condition easier
            while (cur != head) {
                out += "," + cur.element.toString();
                cur = cur.next;
            }
        }
        return out;
    }

    @Override
    public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
        if (n >= size(head) || n < 0) {
            throw new InvalidIndexException();
        }
        ListNode current = head;
        for (int i = 0; i < n; i++) {
            current = current.next;
        }
        return current.element;
    }

    @Override
    public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
        int size = size(head);
        if (n >= size || n < 0) {
            throw new InvalidIndexException();
        }
        ListNode current = head.previous;
        for (int i = 0; i < n; i++) {
            current = current.previous;
        }
        return current.element;
    }

    @Override
    public boolean equals(ListNode head1, ListNode head2) {
        int size1 = size(head1);
        int size2 = size(head2);
        if (size1 != size2) {
            return false;
        } else {
            ListNode current1 = head1;
            ListNode current2 = head2;
            for (int i = 0; i < size1; i++) {
                if (!current1.element.equals(current2.element)) {
                    return false;
                }
                current1 = current1.next;
                current2 = current2.next;
            }
            return true;
        }
    }

    @Override
    public boolean containsDuplicates(ListNode head) {
        if (size(head) <= 1) {
            return false;
        }
        //basically going to implement a nested for loop traversal like with a 1d array
        //but since no direct access, must create 2 ListNodes to store the stationary arr[i] and moving arr[j]
        ListNode anchor = head;
        ListNode sweeper = head.next;
        for (int i = 0; i < size(head)-1; i++) {
            for (int j = i+1; j < size(head); j++) {
                if (anchor.element == sweeper.element) {
                    return true;
                }
                sweeper = sweeper.next;
            }
            anchor = anchor.next;
        }
        return false;
    }

    @Override
    public ListNode addHead(ListNode head, ListNode node) {
        node.previous = head.previous;
        head.previous.next = node;
        node.next = head;
        head.previous = node;
        return node;
    }

    @Override
    public ListNode append(ListNode head1, ListNode head2) {
        if (head2 == null)
            return head1;
        else if (head1 == null)
            return head2;

        head1.previous.next = head2;
        ListNode temp = head1.previous;
        head1.previous = head2.previous;
        head2.previous.next = head1;
        head2.previous = temp;

        return head1;
    }

    public ListNode insert(ListNode head, ListNode node, int n) throws InvalidIndexException {
        int size = size(head);
        if (n > size || n < 0) { // just in case want to insert at the very end (thus n == size),
            throw new InvalidIndexException();
        }
        if (head == null) {
            node.next = node;
            node.previous = node;
            return node;
        }
        ListNode current = head;
        for (int i = 0; i < n; i++) { // this loop condition will allow current to become the last element
            current = current.next;
        }
        //perform insertion
        node.next = current;
        node.previous = current.previous;
        current.previous.next = node;
        current.previous = node;

        if (n == 0) {
            head = node;
        }
        return head;
    }

    public ListNode delete(ListNode head, Object elem) {
        if (head == null) { //size = 0
            return null;
        } else if (head.next == head) { //size = 1
            if (head.element.equals(elem)) {
                return null;
            }
            return head;

        } else {
            ListNode current = head;
            do {
                if (current.element.equals(elem)) {
                    current.previous.next = current.next;
                    current.next.previous = current.previous;
                    if (head == current) {
                        head = current.next;
                    }
                    return head;
                }
                current = current.next;
            } while (current != head);

            return head;
        }
    }

    @Override
    public ListNode reverse(ListNode head) {
        // below is the chatgpt solution that i've made efforts to understand (and have)
        if (head == null || head.next == head) { // Empty list or single node
            return head;
        }

        ListNode current = head;
        ListNode newHead = head.previous; // The new head will be the previous tail
        // only executed cuz head only changed once (on the first swap)
        do {
            // Swap the next and previous pointers for the current node
            ListNode temp = current.next;
            current.next = current.previous;
            current.previous = temp;

            // Move to the next node in the original order
            current = temp;
        } while (current != head);

        // Return the new head
        return newHead;
    }

    @Override
    public ListNode split(ListNode head, ListNode node) throws InvalidListException {
        if (head == null) {
            throw new InvalidListException();
        } else if (head.next == head) {
            throw new InvalidListException();
        } else {
            //storing for easy access & prevent losing later
            ListNode tail = head.previous;
            ListNode current = head;
            do {
                if (current.element.equals(node.element)) {
                    if (current == head) {
                        throw new InvalidListException();
                    }
                    //break entire list's circular link –> make 2 individual circular links
                    current.previous.next = head;
                    head.previous = current.previous;
                    current.previous = tail;
                    tail.next = current;

                    //making the new list doubly & circularly linked
                    ListNode out = new ListNode(head);
                    ListNode two = new ListNode(current);
                    out.next = two;
                    two.previous = out;
                    two.next = out;
                    out.previous = two;

                    return out;
                }
                current = current.next;
            } while (current != head);
            return head;
        }
    }

    @Override
    public ListNode map(ListNode head, IMapTransformation transformation) {
        System.out.println(convertToString(head));
        if (head == null) {
            return null;
        } else if (head.next == head) {
            head.element = transformation.transform(head.element);
            System.out.println(convertToString(head));
            return head;
        } else {
            ListNode current = head;
            while (current.next != head) {
                current.element = transformation.transform(current.element);
                current = current.next;
            }
            current.element = transformation.transform(current.element);
            System.out.println(convertToString(head));
            return head;
        }
    }

    @Override
    public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
        if (head == null) {
            return initial;
        } else if (head.next == head) {
            return operator.operate(head.element, initial);
        } else {
            ListNode current = head;
            do { // makes sure we get out of head to begin with
                initial = operator.operate(current.element, initial);
                current = current.next;
            } while (current != head); //condition also makes sure the last element will be operated on
            return initial;
        }
    }

    @Override
    public ListNode filter(ListNode head, IFilterCondition condition) {
        if (head == null) {
            return null;
        } else if (head.next == head) {
            if (condition.isSatisfied(head.element)) {
                return head;
            } else {
                return null;
            }
        } else {
            ListNode current = head;
            do {
                if (!condition.isSatisfied(current.element)) {
                    System.out.println("anom found");
                    current.previous.next = current.next;
                    current.next.previous = current.previous;
                    System.out.println("removed");
                    if (current == head) {
                        head = current.previous;
                        System.out.println("head updated");
                    }
                }
                current = current.next;
                System.out.println(convertToString(current));
            } while (current != head);
            return head;
        }
    }


}
