package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IReduceOperator;
import interfaces.IMapTransformation;

/**
 * Abstract test class for testing IListManipulator implementations.
 */
public abstract class AbstractListManipulatorTest {

    private static final int TIME_LIMIT = 100;

    private IListManipulator manipulator;

    private ListNode emptyList;

    private ListNode list1;
    private ListNode list2;
    private ListNode list3;
    private ListNode list4;
    private ListNode list5;
    private ListNode list6;
    private ListNode list7;
    private ListNode list8;
    private ListNode list9;
    private ListNode list10;
    private ListNode list11;
    private ListNode list12;
    private ListNode list13;
    private ListNode list14;
    private ListNode list15;
    private ListNode list16;
    private ListNode list17;
    private ListNode list18;

    private ListNode listOfLists;

    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int SEVEN = 7;
    private static final int EIGHT = 8;
    private static final int NINE = 9;
    private static final int TEN = 10;

    /**
     * The factory method that must be implemented by any concrete subclass of ListManipulatorTest in order to instantiate a particular implementation of IListManipulator.
     * @return the desired concrete implementation of IListManipulator
     */
    public abstract IListManipulator makeListManipulator();

    /**
     * Method used to set up common test objects prior to every test.
     */
    @BeforeEach
    public void setup() {
        manipulator = makeListManipulator();

        emptyList = null;

        list1 = new ListNode(FIVE); list1.next = list1; list1.previous = list1;
        list2 = new ListNode(FIVE); ListNode list2Node2 = new ListNode(THREE); list2.next = list2Node2; list2Node2.previous = list2; list2.previous = list2Node2; list2Node2.next = list2;
        list3 = new ListNode(FIVE); ListNode list3Node2 = new ListNode(THREE); ListNode list3Node3 = new ListNode(NINE);  list3.next = list3Node2;  list3Node2.previous = list3; list3Node3.previous = list3Node2; list3Node2.next = list3Node3; list3Node3.next = list3; list3.previous = list3Node3;
        list4 = new ListNode(FIVE); ListNode list4Node2 = new ListNode(THREE); ListNode list4Node3 = new ListNode(NINE);  list4.next = list4Node2;  list4Node2.previous = list4; list4Node3.previous = list4Node2; list4Node2.next = list4Node3; list4Node3.next = list4; list4.previous = list4Node3;
        list5 = new ListNode(ONE);   ListNode list5Node2 = new ListNode(TWO);  ListNode list5Node3 = new ListNode(THREE); list5.next = list5Node2; list5Node2.previous = list5; list5Node3.previous = list5Node2; list5Node2.next = list5Node3; list5Node3.next = list5; list5.previous = list5Node3;
        list6 = new ListNode(FIVE);  ListNode list6Node2 = new ListNode(FIVE); ListNode list6Node3 = new ListNode(NINE);  list6.next = list6Node2;  list6Node2.previous = list6; list6Node3.previous = list6Node2; list6Node2.next = list6Node3; list6Node3.next = list6; list6.previous = list6Node3;
        list7 = new ListNode(THREE); ListNode list7Node2 = new ListNode(TWO);  ListNode list7Node3 = new ListNode(ONE);   list7.next = list7Node2;  list7Node2.previous = list7; list7Node3.previous = list7Node2; list7Node2.next = list7Node3; list7Node3.next = list7; list7.previous = list7Node3;
        list8 = new ListNode(THREE); ListNode list8Node2 = new ListNode(NINE); list8.next = list8Node2; list8Node2.previous = list8; list8.previous = list8Node2; list8Node2.next = list8;
        list9 = new ListNode(NINE); list9.next = list9; list9.previous = list9;
        list10 = new ListNode(FIVE); list10.next = list10; list10.previous = list10;
        list11 = new ListNode(SIX); list11.next = list11; list11.previous = list11;
        list12 = new ListNode(SIX); ListNode list12Node2 = new ListNode(FOUR); list12.next = list12Node2; list12Node2.previous = list12; list12.previous = list12Node2; list12Node2.next = list12;
        list13 = new ListNode(SIX); ListNode list13Node2 = new ListNode(FOUR); ListNode list13Node3 = new ListNode(TEN); list13.next = list13Node2; list13Node2.previous = list13; list13Node3.previous = list13Node2; list13Node2.next = list13Node3; list13Node3.next = list13; list13.previous = list13Node3;
        list14 = new ListNode(THREE); list14.next = list14; list14.previous = list14;
        list15 = new ListNode(FOUR); ListNode list15Node2 = new ListNode(TEN); list15.next = list15Node2; list15Node2.previous = list15; list15.previous = list15Node2; list15Node2.next = list15;
        list16 = new ListNode(FIVE); ListNode list16Node2 = new ListNode(NINE); list16.next = list16Node2; list16Node2.previous = list16; list16.previous = list16Node2; list16Node2.next = list16;
        list17 = new ListNode(ONE); list17.next = list17; list17.previous = list17;
        list18 = new ListNode(THREE); ListNode list18Node2 = new ListNode(TWO); list18.next = list18Node2; list18Node2.previous = list18; list18.previous = list18Node2; list18Node2.next = list18;

        listOfLists = new ListNode(null); listOfLists.next = new ListNode(null); listOfLists.next.previous = listOfLists; listOfLists.next.next = listOfLists; listOfLists.previous = listOfLists.next;
    }


    /**
     * Tests the size method in the IListManipulator implementation.
     */
    @Test
    public void size() {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(0, manipulator.size(null)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(ONE, manipulator.size(list1)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(TWO, manipulator.size(list2)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(THREE, manipulator.size(list3)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(THREE, manipulator.size(list6)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(TWO, manipulator.size(list2.next)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(THREE, manipulator.size(list3.previous)));
    }

    /**
     * Tests the isEmpty method in the IListManipulator implementation.
     */
    @Test
    public void isEmpty()
    {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.isEmpty(emptyList)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertFalse(manipulator.isEmpty(list1)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertFalse(manipulator.isEmpty(list2)));
    }


    /**
     * Tests the contains method in the IListManipulator implementation.
     */
    @Test
    public void contains() {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertFalse(manipulator.contains(emptyList, ONE)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.contains(list1, FIVE)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.contains(list2, FIVE)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.contains(list2, THREE)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.contains(list3, THREE)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.contains(list3, NINE)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.contains(list6, FIVE)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.contains(list6, NINE)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertFalse(manipulator.contains(list1, THREE)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertFalse(manipulator.contains(list6, SIX)));
    }

    /**
     * Tests the count method in the IListManipulator implementation.
     */
    @Test
    public void count() {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(0, manipulator.count(list3, ONE)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(1, manipulator.count(list3, FIVE)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(1, manipulator.count(list3, NINE)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(2, manipulator.count(list6, FIVE)));
    }

    /**
     * Tests the convertToString method in the IListManipulator implementation.
     */
    @Test
    public void convertToString() {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals("", manipulator.convertToString(emptyList)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(String.valueOf(FIVE), manipulator.convertToString(list1)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(FIVE + "," + THREE, manipulator.convertToString(list2)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(FIVE + "," + THREE + "," + NINE, manipulator.convertToString(list3)));
    }

    /**
     * Tests the getFromFront method in the IListManipulator implementation during normal operation.
     * @throws InvalidIndexException not expected to be thrown during this test.
     */
    @Test
    public void getCountingForwards() throws InvalidIndexException {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(FIVE, manipulator.getFromFront(list1, 0)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(FIVE, manipulator.getFromFront(list2, 0)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(THREE, manipulator.getFromFront(list2, 1)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(FIVE, manipulator.getFromFront(list3, 0)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(THREE, manipulator.getFromFront(list3, 1)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(NINE, manipulator.getFromFront(list3, 2)));
    }

    /**
     * Tests the getFromFront method in the IListManipulator implementation with an index that is too large.
     * @throws InvalidIndexException is expected to be thrown during this test.
     */
    @Test
    public void getCountingForwardsIndexTooLarge() throws InvalidIndexException {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertThrows(InvalidIndexException.class, () -> manipulator.getFromFront(list3, FOUR)));
    }

    /**
     * Tests the getFromFront method in the IListManipulator implementation with an empty (null) list and index 0.
     * @throws InvalidIndexException is expected to be thrown during this test.
     */
    @Test
    public void getCountingForwardsEmptyList() throws InvalidIndexException {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertThrows(InvalidIndexException.class, () -> manipulator.getFromFront(null, 0)));
    }

    /**
     * Tests the getFromBack method in the IListManipulator implementation during normal operation.
     * @throws InvalidIndexException not expected to be thrown during this test.
     */
    @Test
    public void getCountingBackwards() throws InvalidIndexException {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(FIVE, manipulator.getFromBack(list1, 0)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(THREE, manipulator.getFromBack(list2, 0)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(FIVE, manipulator.getFromBack(list2, 1)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(NINE, manipulator.getFromBack(list4, 0)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(THREE, manipulator.getFromBack(list4, 1)));
    }

    /**
     * Tests the getFromBack method in the IListManipulator implementation with an index that is too large.
     * @throws InvalidIndexException is expected to be thrown during this test.
     */
    @Test
    public void getCountingBackwardsIndexTooLarge() throws InvalidIndexException {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertThrows(InvalidIndexException.class, () -> manipulator.getFromBack(list4, FOUR)));
    }

    /**
     * Tests the getFromBack method in the IListManipulator implementation with an empty (null) list and index 0.
     * @throws InvalidIndexException is expected to be thrown during this test.
     */
    @Test
    public void getCountingBackwardsEmptyList() throws InvalidIndexException {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertThrows(InvalidIndexException.class, () -> manipulator.getFromBack(emptyList, 0)));
    }

    /**
     * Tests the equals method in the IListManipulator implementation.
     */
    @Test
    public void equals() {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(emptyList, emptyList)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertFalse(manipulator.equals(list1, emptyList)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertFalse(manipulator.equals(list1, list2)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertFalse(manipulator.equals(list2, list1)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list2, list2)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list3, list3)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list3, list4)));
    }

    /**
     * Tests the containsDuplicates method in the IListManipulator implementation.
     */
    @Test
    public void containsDuplicates() {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertFalse(manipulator.containsDuplicates(emptyList)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertFalse(manipulator.containsDuplicates(list1)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertFalse(manipulator.containsDuplicates(list2)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertFalse(manipulator.containsDuplicates(list3)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.containsDuplicates(list6)));
    }

    /**
     * Tests the addHead method in the IListManipulator implementation.
     */
    @Test
    public void addHead()
    {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list2, manipulator.addHead(list14, list1))));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list3, manipulator.addHead(list8, list10))));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list13, manipulator.addHead(list15, list11))));
    }


    /**
     * Tests the append method in the IListManipulator implementation.
     */
    @Test
    public void append() {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertNull(manipulator.append(emptyList, emptyList)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list1, manipulator.append(list1, emptyList))));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list1, manipulator.append(emptyList, list1))));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list3, manipulator.append(list1, list8))));
    }

    /**
     * Tests the append method in the IListManipulator implementation to append three lists together.
     */
    @Test
    public void appendThreeLists() {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list6, manipulator.append(manipulator.append(list1, list10), list9))));
    }

    /**
     * Tests the insert method in the IListManipulator implementation.
     */
    @Test
    public void insert()
    {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertThrows(InvalidIndexException.class, () -> manipulator.insert(list2, list1, 3)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list3, manipulator.insert(list16, list14, 1))));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list13, manipulator.insert(list15, list11, 0))));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list7, manipulator.insert(list18, list17, 2))));        
    }


    /**
     * Tests the delete method in the IListManipulator implementation.
     */
    @Test
    public void delete()
    {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertNull(manipulator.delete(list1, FIVE)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list12, manipulator.delete(list12, TWO)))); 
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list10, manipulator.delete(list2, THREE))));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list16, manipulator.delete(list6, FIVE))));
    }


    /**
     * Tests the reverse method in the IListManipulator implementation.
     */
    @Test
    public void reverse() {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertNull(manipulator.reverse(emptyList)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list1, manipulator.reverse(list1))));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list7, manipulator.reverse(list5))));
    }


    /**
     * Tests the split method in the IListManipulator implementation.
     * @throws InvalidIndexException during this test
     * @throws InvalidListException during this test
     */
    @Test
    public void split() throws InvalidIndexException, InvalidListException {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertThrows(InvalidListException.class, () -> manipulator.split(emptyList, emptyList)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertThrows(InvalidListException.class, () -> manipulator.split(list1, list1)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertThrows(InvalidListException.class, () -> manipulator.split(list2, list2)));

        listOfLists.element = list1; listOfLists.next.element = list14;
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(listOfLists, manipulator.split(list2, list2.next))));
    }

    /**
     * Tests the map method in the IListManipulator implementation.
     */
    @Test
    public void map() {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertNull(manipulator.map(emptyList, addOne)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list11, manipulator.map(list1, addOne))));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list12, manipulator.map(list2, addOne))));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertTrue(manipulator.equals(list13, manipulator.map(list3, addOne))));
    }

    /**
     * Tests the reduce method in the IListManipulator implementation.
     */
    @Test
    public void reduce() {
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(0, manipulator.reduce(emptyList, add, 0)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(FIVE, manipulator.reduce(list1, add, 0)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(FIVE + THREE, manipulator.reduce(list2, add, 0)));
        assertTimeoutPreemptively(Duration.ofMillis(TIME_LIMIT), () -> assertEquals(FIVE + THREE + NINE, manipulator.reduce(list3, add, 0)));
    }



    /**
     * Tests the filter method in the IListManipulator implementation.
     */
    @Test
    public void filter() {
        assertEquals(emptyList, manipulator.filter(emptyList, element -> (Integer) element > 0));
        assertEquals(emptyList, manipulator.filter(list1, greaterThanFive));

        assertTrue(manipulator.equals(list1, manipulator.filter(list1, lessOrEqualToFive)));
        assertTrue(manipulator.equals(list9, manipulator.filter(list3, greaterThanFive)));
    }


    /**
     * This ITransformation is to permit the map method to add a 1 to each element in the list.
     */
    private final IMapTransformation addOne = new IMapTransformation() {

        @Override
        public Object transform(Object element) {
            return (Integer) element + 1;
        }
    };


    /**
     * This IOperator is to permit the reduce method to perform addition when combining 2 elements.
     */
    private final IReduceOperator add = new IReduceOperator() {

        @Override
        public Object operate(Object element1, Object element2) {
            return (Integer) element1 + (Integer) element2;
        }
    };


    /**
     * This IFilterCondition is to permit the filter method to check whether an element satisfies the filter condition.
     */
    private final IFilterCondition greaterThanFive = new IFilterCondition() {

        @Override
        public boolean isSatisfied(Object element) {
            return (Integer) element > FIVE;
        }
    };


    /**
     * This IFilterCondition is to permit the filter method to check whether an element satisfies the filter condition.
     */
    private final IFilterCondition lessOrEqualToFive = new IFilterCondition() {

        @Override
        public boolean isSatisfied(Object element) {
            return (Integer) element <= FIVE;
        }
    };

}