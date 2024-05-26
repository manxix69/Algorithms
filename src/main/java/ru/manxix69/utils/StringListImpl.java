package ru.manxix69.utils;

import org.springframework.stereotype.Service;
import ru.manxix69.exceptions.ElementNotFoundException;
import ru.manxix69.exceptions.NullParameterException;

import java.util.Arrays;

@Service
public class StringListImpl implements StringList {

    private String[] stringItems;
    private int size;

    public StringListImpl(int lengthArray) {
        this.stringItems = new String[lengthArray];
        this.size = 0;
    }

    public StringListImpl() {
        this.stringItems = new String[10];
        this.size = 0;
    }

    @Override
    public String add(String item) {
        validateItem(item);

        if (stringItems.length == size) {
            stringItems = Arrays.copyOf(stringItems, stringItems.length * 2 + 1);
        }
        stringItems[size++] = item;
        return item;
    }


    @Override
    public String add(int index, String item) {
        validateItem(item);

        String elementAdded = null;
        if (index < 0 || index > size() || stringItems.length == size()) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (size() == index) {
            elementAdded = add(item);
        } else {
            for (int i = size(); i > index; i--) {
                stringItems[i] = stringItems[i - 1];
            }
            stringItems[index] = item;
            elementAdded = stringItems[index];
            size++;
        }

        return elementAdded;
    }

    @Override
    public String set(int index, String item) {
        validateItem(item);
        validateIndex(index);
        return stringItems[index] = item;
    }

    @Override
    public String remove(String item) {
        int indexRemoved = indexOf(item);

        if (indexRemoved == -1) {
            throw new ElementNotFoundException("Can not remove this element : because element not found!");
        }
        offsetAfterRemove(indexRemoved);
        return item;
    }

    @Override
    public String remove(int index) {
        validateIndex(index);
        String elementRemoved = stringItems[index];
        stringItems[index] = null;
        offsetAfterRemove(index);
        return elementRemoved;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(String item) {
        validateItem(item);
        for (int i = 0; i < size(); i++) {
            if (stringItems[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        validateItem(item);
        for (int i = size() - 1; i >= 0; i--) {
            if (stringItems[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
        validateIndex(index);
        return stringItems[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        validateStringList(otherList);
        return Arrays.equals(this.toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        stringItems = new String[stringItems.length];
        size = 0;
    }

    @Override
    public String[] toArray() {
        return Arrays.copyOf(stringItems, size());
    }


    private void offsetAfterRemove(int indexStart) {
        for (int i = indexStart; i < size() - 1; i++) {
            stringItems[i] = stringItems[i + 1];
        }
        stringItems[size--] = null;
    }

    private void validateItem(String item) {
        if (item == null || item.isEmpty()) {
            throw new NullParameterException();
        }
    }

    private void validateStringList(StringList stringList) {
        if (stringList == null) {
            throw new NullParameterException();
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

}
