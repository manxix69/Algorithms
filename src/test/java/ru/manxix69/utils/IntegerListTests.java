package ru.manxix69.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.manxix69.exceptions.ElementNotFoundException;
import ru.manxix69.exceptions.NullParameterException;

import java.util.Arrays;

@SpringBootTest
public class IntegerListTests {
    private IntegerList integerList;
    private Integer STRING_TEST1 = 1;
    private Integer STRING_TEST2 = 2;
    private Integer STRING_TEST3 = 3;
    private Integer STRING_TEST4 = 4;
    private Integer STRING_TEST5 = 5;


    @BeforeEach
    public void setUp() {
        integerList = new IntegerListImpl(2);
    }

    @Test
    public void addToArray() {

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> integerList.add(-1, STRING_TEST1));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> integerList.add(1, STRING_TEST1));

        Assertions.assertEquals(integerList.size() , 0);
        integerList.add(0, STRING_TEST1);
        Assertions.assertEquals(integerList.size() , 1);
        integerList.add(1, STRING_TEST5);
        Assertions.assertEquals(integerList.size() , 2);
        integerList.add(STRING_TEST3);
        Assertions.assertTrue(integerList.contains(STRING_TEST3)); // !!!!!

        Assertions.assertEquals(integerList.size() , 3);

        integerList.add(1, STRING_TEST2);
        Assertions.assertTrue(integerList.contains(STRING_TEST3)); // !!!!!

        Assertions.assertEquals(integerList.size() , 4);

        integerList.add(3, STRING_TEST4);
        integerList.add(4, STRING_TEST4);
        integerList.add(3, STRING_TEST4);

        Assertions.assertTrue(integerList.contains(STRING_TEST1));
        Assertions.assertTrue(integerList.contains(STRING_TEST3));
        Assertions.assertTrue(integerList.contains(STRING_TEST4));

    }

    @Test
    public void removeFromArray() {

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> integerList.remove(-1));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> integerList.remove(1));
        Assertions.assertThrows(ElementNotFoundException.class, () -> integerList.remove(STRING_TEST1));

        integerList.add(0, STRING_TEST1);
        integerList.add(1, STRING_TEST1);
        integerList.add(STRING_TEST3);
        integerList.add(1, STRING_TEST2);
//        System.out.println(Arrays.toString(integerList.toArray()));
        integerList.remove(3);
//        System.out.println(Arrays.toString(integerList.toArray()));
        Assertions.assertEquals(integerList.size() , 3);

        Assertions.assertFalse(integerList.contains(STRING_TEST3));
//        System.out.println(Arrays.toString(integerList.toArray()));
        integerList.remove(1);
        Assertions.assertEquals(integerList.size() , 2);

//        System.out.println(Arrays.toString(integerList.toArray()));

        Assertions.assertTrue(integerList.contains(STRING_TEST2));
        Assertions.assertTrue(integerList.contains(STRING_TEST1));

        integerList.add(STRING_TEST5);

        integerList.remove(STRING_TEST5);
        Assertions.assertEquals(integerList.size() , 2);

        Assertions.assertFalse(integerList.contains(STRING_TEST5));
    }

    @Test
    public void setToArray() {

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> integerList.set(-1, STRING_TEST1));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> integerList.set(1, STRING_TEST1));

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> integerList.get(10));

        integerList.add(0, STRING_TEST1);
        integerList.add(1, STRING_TEST2);
        integerList.add( STRING_TEST3);
        Assertions.assertThrows(NullParameterException.class, () -> integerList.set(0,null));

        Assertions.assertEquals(integerList.size() , 3);

        Assertions.assertTrue(integerList.contains(STRING_TEST1));
        Assertions.assertTrue(integerList.contains(STRING_TEST2));
        Assertions.assertTrue(integerList.contains(STRING_TEST3));

        integerList.set(1, STRING_TEST5);
        Assertions.assertEquals(STRING_TEST5 , integerList.get(1));
        Assertions.assertEquals(integerList.size() , 3);
        Assertions.assertTrue(integerList.contains(STRING_TEST1));
        Assertions.assertTrue(integerList.contains(STRING_TEST5));
        Assertions.assertTrue(integerList.contains(STRING_TEST3));
    }

    @Test
    public void getIndexesOfFromArray() {

        Assertions.assertEquals(integerList.indexOf(STRING_TEST1) , -1);
        Assertions.assertEquals(integerList.lastIndexOf(STRING_TEST1) , -1);

        integerList.add(0, STRING_TEST1);
        integerList.add(1, STRING_TEST2);
        integerList.add( STRING_TEST3);

        Assertions.assertEquals(0 , integerList.indexOf(STRING_TEST1));
        Assertions.assertEquals(1 , integerList.indexOf(STRING_TEST2));
        Assertions.assertEquals(2 , integerList.indexOf(STRING_TEST3));

        Assertions.assertEquals(0 , integerList.lastIndexOf(STRING_TEST1));
        Assertions.assertEquals(1 , integerList.lastIndexOf(STRING_TEST2));
        Assertions.assertEquals(2 , integerList.lastIndexOf(STRING_TEST3));

        Assertions.assertEquals(-1 , integerList.lastIndexOf(STRING_TEST5));

    }



    @Test
    public void isEqualsLists() {

        Assertions.assertThrows(NullParameterException.class, () -> integerList.equals(null));

        integerList.add(0, STRING_TEST1);
        integerList.add(1, STRING_TEST2);
        integerList.add( STRING_TEST3);

        IntegerList otherList = new IntegerListImpl(1);
        Assertions.assertFalse(integerList.equals(otherList));

        otherList = new IntegerListImpl(3);
        Assertions.assertFalse(integerList.equals(otherList));

        otherList.add(0, STRING_TEST2);
        Assertions.assertFalse(integerList.equals(otherList));
        otherList.add(1, STRING_TEST1);
        Assertions.assertFalse(integerList.equals(otherList));
        otherList.add( STRING_TEST3);
        Assertions.assertFalse(integerList.equals(otherList));

        otherList = new IntegerListImpl(3);

        otherList.add(0, STRING_TEST1);
        otherList.add(1, STRING_TEST2);
        otherList.add( STRING_TEST3);
        Assertions.assertTrue(integerList.equals(otherList));
    }


    @Test
    public void isEmptyList() {
        Assertions.assertTrue(integerList.isEmpty());
        integerList.add( STRING_TEST3);
        Assertions.assertFalse(integerList.isEmpty());
    }

    @Test
    public void clearList() {

        integerList.add( STRING_TEST3);
        Assertions.assertEquals(1, integerList.size());
        integerList.clear();

        Assertions.assertFalse(integerList.contains(STRING_TEST3));
        Assertions.assertEquals(0, integerList.size());
    }


    @Test
    public void createNewList() {

        integerList.add( STRING_TEST3);
        Assertions.assertEquals(1, integerList.size());
        Integer[] otherList = integerList.toArray();
        Assertions.assertEquals(1, otherList.length);

    }

    @Test
    public void sortBubble() {

        for (int i = 0; i < 100_000; i++) {
            integerList.add((int) (Math.random() * 1_000.0));
        }

//        long startSortBubble = System.currentTimeMillis();
//        integerList.sortBubble(Arrays.copyOf(integerList.toArray(), integerList.size()));
//        System.out.println("time sortBubble millis = " + (System.currentTimeMillis() - startSortBubble));

        long startSortSelection = System.currentTimeMillis();
        integerList.sortSelection(Arrays.copyOf(integerList.toArray(), integerList.size()));
        System.out.println("time sortSelection millis = " + (System.currentTimeMillis() - startSortSelection));

        long startSortInsertion = System.currentTimeMillis();
        integerList.sortInsertion(Arrays.copyOf(integerList.toArray(), integerList.size()));
        System.out.println("time sortInsertion millis = " + (System.currentTimeMillis() - startSortInsertion));

    }

}
