package ru.manxix69.utils;

import org.springframework.stereotype.Service;
import ru.manxix69.exceptions.ElementNotFoundException;
import ru.manxix69.exceptions.NullParameterException;

import java.util.Arrays;

@Service
public class IntegerListImpl implements IntegerList {

    private Integer[] integers;

    private int size = 0;

    public IntegerListImpl(int length) {
        this.integers = new Integer[length];
    }

    public IntegerListImpl() {
        this.integers = new Integer[10];
    }

    @Override
    public Integer add(Integer item) {
        validateItem(item);
        return addWithOutValidateItem(item);
    }

    private Integer addWithOutValidateItem(Integer item) {
        if (size() == integers.length) {
            integers = Arrays.copyOf(integers, size() * 2 + 1);
        }
        integers[size++] = item;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        validateIndex(index);
        if (index == size) {
            return add(item);
        }
        arrayCopy(index, index+1 , index);
        integers[index] = item;
        size++;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        validateIndex(index);
        validateItem(item);
        return integers[index] = item;
    }

    @Override
    public Integer remove(Integer item) {
        int removeIndex = indexOf(item);
        if (removeIndex == -1) {
            throw new ElementNotFoundException("Cannot remove item! Because item not found!");
        }
        return removeWithOutValidateIndex(removeIndex);
    }

    @Override
    public Integer remove(int index) {
        validateIndex(index);
        return removeWithOutValidateIndex(index);
    }

    private Integer removeWithOutValidateIndex(int index) {
        Integer integer = integers[index];
        if (size() - 1 != index) {
            arrayCopy(index + 1, index, index);
        }
        size--;
        return integer;
    }

    private void arrayCopy(int srcPos, int destPos, int index) {
        if (size == integers.length) {
            integers = Arrays.copyOf(integers, size() * 2 + 1);
        }
        System.arraycopy(integers, srcPos, integers, destPos, size() - index);
    }

    @Override
    public boolean contains(Integer item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(Integer item) {
        validateItem(item);
        for (int i = 0; i < size(); i++) {
            if (integers[i] != null && integers[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        validateItem(item);
        for (int i = size() - 1; i >= 0; i--) {
            if (integers[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        validateIndex(index);
        return integers[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        validateIntegerList(otherList);
        return Arrays.equals(this.toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public void clear() {
        integers = new Integer[10];
        size = 0;
    }

    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(integers, size());
    }


    private void validateItem(Integer item) {
        if (item == null) {
            throw new NullParameterException();
        }
    }

    private void validateIntegerList(IntegerList stringList) {
        if (stringList == null) {
            throw new NullParameterException();
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index > size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
