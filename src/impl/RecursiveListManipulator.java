package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;

/**
 * This class represents the recursive implementation of the IListManipulator interface.
 *
 */
public class RecursiveListManipulator implements IListManipulator {

    @Override
    public int size(ListNode head) {
        if (head == null) {
            return 0;
        } else {
            head.previous.next = null;
            int sizey = 1 + size(head.next);
            head.previous.next = head;
            return sizey;
        }
    }

    @Override
    public boolean isEmpty(ListNode head) {
        return head == null;
    }

    @Override
    public boolean contains(ListNode head, Object element) {
        if (head == null) {
            return false;
        } else if (!head.element.equals(element)) {
            head.previous.next = null;
            boolean out = contains(head.next, element);
            head.previous.next = head;
            return out;
        } else {
            return true;
        }
    }

    @Override
    public int count(ListNode head, Object element) {
        if (head == null) {
            return 0;
        } else if (!head.element.equals(element)) {
            head.previous.next = null;
            int count = count(head.next, element);
            head.previous.next = head;
            return count;
        } else {
            head.previous.next = null;
            int count = count(head.next, element);
            head.previous.next = head;
            return 1 + count;
        }
    }

    @Override
    public String convertToString(ListNode head) {
        if (head == null) {
            return "";
        } else {
            head.previous.next = null;
            String ele;
            ele = convertToString(head.next);
            head.previous.next = head;
            if (size(head) == 1)
                return head.element.toString() + ele;
            else
                return head.element.toString() + "," + ele;
        }
    }

    @Override
    public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
        if (n > size(head) || head == null) {
            throw new InvalidIndexException();
        } else if (n == 0) {
            return head.element;
        } else {
            return getFromFront(head.next, n-1);
        }
    }

    @Override
    public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
        if (head == null || n > size(head)) {
            throw new InvalidIndexException();
        } else if (n == 0) {
            return head.previous.element;
        } else {
            return getFromBack(head.previous, n-1);
        }
    }

    @Override
    public boolean equals(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) {
            return size(head1) == size(head2);
        } else if (!head1.element.equals(head2.element)) {
            return false;
        } else {
            head1.previous.next = null;
            head2.previous.next = null;
            boolean equals = equals(head1.next, head2.next);
            head1.previous.next = head1;
            head2.previous.next = head2;
            return equals;
        }
    }

    @Override
    public boolean containsDuplicates(ListNode head) {
        if (head == null) {
            return false;
        } else {
            int size = size(head);
            head.previous.next = null;
            boolean duplicate = thisAchorTraversedHasDuplicates(head, size) || containsDuplicates(head.next);
            //idk
            // should be something like return duplicate || containsDuplicates(head.next)
            // where each new call acts as a big iteration in a nested for-loop algorithm
            // but i don't know how i can do this without another iterative method
            head.previous.next = head;
            return duplicate;
        }
    }
    //below is a containsDuplicates helper method
    public boolean thisAchorTraversedHasDuplicates(ListNode head, int size) {
        if (head == null) {
            return false;
        } else {
            ListNode anchored = head;
            ListNode moving = head.next;
            for (int i = 0; i < size; i++) {
                if (moving == null) {
                    return false;
                }
                if (anchored.element.equals(moving.element)) {
                    return true;
                }
                moving = moving.next;
            }
            return false;
        }
    }

    // no need for iteration/recursion
    @Override
    public ListNode addHead(ListNode head, ListNode node) {
        node.previous = head.previous;
        head.previous.next = node;
        node.next = head;
        head.previous = node;
        return node;
    }


    // no need fore iteration/recursion
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

    @Override
    public ListNode insert(ListNode head, ListNode node, int n) throws InvalidIndexException {
        if (n < 0 || n > size(head)) {
            throw new InvalidIndexException();
        } else if (n == 0) {
            head.previous.next = node;
            node.previous = head.previous;
            head.previous = node;
            node.next = head;
            return node; //vs. return head, inserted node should be the new head
        }

        head.next = insert(head.next, node, n-1); // after this call, head –> head.next, head.next will be a separate list
        // with its new head: node. However, by storing this in head.next, we link it up
        head.previous.next = head;
        //because access to head.previous is outside of recursion, head.previous is still original, links the whole thing back together
        return head;
    }


    @Override
    public ListNode delete(ListNode head, Object elem) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            if (head.element.equals(elem)) {
                return null;
            } else {
                return head;
            }
        }
        if (head.next == head) {
            if (head.element.equals(elem))
                return null;
            else
                return head;
        }
        if (head.element.equals(elem)) {
            head.previous.next = head.next;
            head.next.previous = head.previous;
            return head.next;
        }
        head.previous.next = null;
        head.next = delete(head.next, elem);
        head.previous.next = head;
        return head;

    }


    @Override
    public ListNode reverse(ListNode head) {
        if (head == null || head.next == head)
            return head;

        ListNode temp = head.previous;
        head.previous = head.next;
        head.next = temp;

        if (head.next.next == head) {
            return reverse(head.next);
        } else {
            return head.previous;
        }
    }

    @Override
    public ListNode split(ListNode head, ListNode node) throws InvalidListException {
        return null;
    }

    @Override
    public ListNode map(ListNode head, IMapTransformation transformation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListNode filter(ListNode head, IFilterCondition condition) {
        // TODO Auto-generated method stub
        return null;
    }

}
