package ru.manxix69.stringUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.manxix69.exceptions.ElementNotFoundException;
import ru.manxix69.exceptions.NullParameterException;

@SpringBootTest
public class StringListTests {

    private StringList stringList;
    private String STRING_TEST1 = "TEST1";
    private String STRING_TEST2 = "TEST2";
    private String STRING_TEST3 = "TEST3";
    private String STRING_TEST4 = "TEST4";
    private String STRING_TEST5 = "TEST5";


    @BeforeEach
    public void setUp() {
        stringList = new StringListImpl(2);
    }

    @Test
    public void addToArray() {

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> stringList.add(-1, STRING_TEST1));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> stringList.add(1, STRING_TEST1));

        Assertions.assertEquals(stringList.size() , 0);
        stringList.add(0, STRING_TEST1);
        Assertions.assertEquals(stringList.size() , 1);
        stringList.add(1, STRING_TEST1);
        Assertions.assertEquals(stringList.size() , 2);
        stringList.add(STRING_TEST3);
        Assertions.assertEquals(stringList.size() , 3);
        stringList.add(1, STRING_TEST2);
        Assertions.assertEquals(stringList.size() , 4);

        Assertions.assertTrue(stringList.contains(STRING_TEST1));
        Assertions.assertTrue(stringList.contains(STRING_TEST3));

    }

    @Test
    public void removeFromArray() {

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> stringList.remove(-1));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> stringList.remove(1));
        Assertions.assertThrows(ElementNotFoundException.class, () -> stringList.remove(STRING_TEST1));

        stringList.add(0, STRING_TEST1);
        stringList.add(1, STRING_TEST1);
        stringList.add(STRING_TEST3);
        stringList.add(1, STRING_TEST2);

        stringList.remove(3);
        Assertions.assertEquals(stringList.size() , 3);

        Assertions.assertFalse(stringList.contains(STRING_TEST3));

        stringList.remove(1);
        Assertions.assertEquals(stringList.size() , 2);

        Assertions.assertFalse(stringList.contains(STRING_TEST2));

        stringList.add(STRING_TEST5);

        stringList.remove(STRING_TEST5);
        Assertions.assertEquals(stringList.size() , 2);

        Assertions.assertFalse(stringList.contains(STRING_TEST5));
    }

    @Test
    public void setToArray() {

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> stringList.set(-1, STRING_TEST1));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> stringList.set(1, STRING_TEST1));

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> stringList.get(10));

        stringList.add(0, STRING_TEST1);
        stringList.add(1, STRING_TEST2);
        stringList.add( STRING_TEST3);
        Assertions.assertThrows(NullParameterException.class, () -> stringList.set(0,null));

        Assertions.assertEquals(stringList.size() , 3);

        Assertions.assertTrue(stringList.contains(STRING_TEST1));
        Assertions.assertTrue(stringList.contains(STRING_TEST2));
        Assertions.assertTrue(stringList.contains(STRING_TEST3));

        stringList.set(1, STRING_TEST5);
        Assertions.assertEquals(STRING_TEST5 , stringList.get(1));
        Assertions.assertEquals(stringList.size() , 3);
        Assertions.assertTrue(stringList.contains(STRING_TEST1));
        Assertions.assertTrue(stringList.contains(STRING_TEST5));
        Assertions.assertTrue(stringList.contains(STRING_TEST3));
    }

    @Test
    public void getIndexesOfFromArray() {

        Assertions.assertEquals(stringList.indexOf(STRING_TEST1) , -1);
        Assertions.assertEquals(stringList.lastIndexOf(STRING_TEST1) , -1);

        stringList.add(0, STRING_TEST1);
        stringList.add(1, STRING_TEST2);
        stringList.add( STRING_TEST3);

        Assertions.assertEquals(0 , stringList.indexOf(STRING_TEST1));
        Assertions.assertEquals(1 , stringList.indexOf(STRING_TEST2));
        Assertions.assertEquals(2 , stringList.indexOf(STRING_TEST3));

        Assertions.assertEquals(0 , stringList.lastIndexOf(STRING_TEST1));
        Assertions.assertEquals(1 , stringList.lastIndexOf(STRING_TEST2));
        Assertions.assertEquals(2 , stringList.lastIndexOf(STRING_TEST3));

        Assertions.assertEquals(-1 , stringList.lastIndexOf(STRING_TEST5));

    }



    @Test
    public void isEqualsLists() {

        Assertions.assertThrows(NullParameterException.class, () -> stringList.equals(null));

        stringList.add(0, STRING_TEST1);
        stringList.add(1, STRING_TEST2);
        stringList.add( STRING_TEST3);

        StringList otherList = new StringListImpl(1);
        Assertions.assertFalse(stringList.equals(otherList));

        otherList = new StringListImpl(3);
        Assertions.assertFalse(stringList.equals(otherList));

        otherList.add(0, STRING_TEST2);
        Assertions.assertFalse(stringList.equals(otherList));
        otherList.add(1, STRING_TEST1);
        Assertions.assertFalse(stringList.equals(otherList));
        otherList.add( STRING_TEST3);
        Assertions.assertFalse(stringList.equals(otherList));

        otherList = new StringListImpl(3);

        otherList.add(0, STRING_TEST1);
        otherList.add(1, STRING_TEST2);
        otherList.add( STRING_TEST3);
        Assertions.assertTrue(stringList.equals(otherList));
    }


    @Test
    public void isEmptyList() {
        Assertions.assertTrue(stringList.isEmpty());
        stringList.add( STRING_TEST3);
        Assertions.assertFalse(stringList.isEmpty());
    }

    @Test
    public void clearList() {

        stringList.add( STRING_TEST3);
        Assertions.assertEquals(1, stringList.size());
        stringList.clear();

        Assertions.assertFalse(stringList.contains(STRING_TEST3));
        Assertions.assertEquals(0, stringList.size());
    }


    @Test
    public void createNewList() {

        stringList.add( STRING_TEST3);
        Assertions.assertEquals(1, stringList.size());
        String[] otherList = stringList.toArray();
        Assertions.assertEquals(2, otherList.length);

    }

}
