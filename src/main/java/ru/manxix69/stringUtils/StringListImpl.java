package ru.manxix69.stringUtils;

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
        String elementAdded = item;
        if (stringItems.length == size) {
            stringItems = Arrays.copyOf(stringItems, stringItems.length * 2 + 1);
        }
        stringItems[size] = elementAdded;
        size++;
        return stringItems[size - 1];
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
        if (index < 0 || index >= size() || stringItems.length == size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return stringItems[index] = item;
    }

    @Override
    public String remove(String item) {
        validateItem(item);
        String elementRemoved = null;
        int indexRemoved = -1;
        for (int i = 0; i < size(); i++) {
            if (stringItems[i].equals(item)) {
                elementRemoved = stringItems[i];
                stringItems[i] = null;
                indexRemoved = i;
                break;
            }
        }

        if (elementRemoved == null) {
            throw new ElementNotFoundException("Can not remove this element : because element not found!");
        }
        offsetAfterRemove(indexRemoved);
        return elementRemoved;
    }

    @Override
    public String remove(int index) {
        if (index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        String elementRemoved = stringItems[index];
        stringItems[index] = null;
        offsetAfterRemove(index);
        return elementRemoved;
    }

    @Override
    public boolean contains(String item) {
        validateItem(item);
        boolean containsElement = false;
        for (int i = 0; i < size(); i++) {
            if (stringItems[i].equals(item)) {
                containsElement = true;
                break;
            }
        }
        return containsElement;
    }

    @Override
    public int indexOf(String item) {
        validateItem(item);
        int index = -1;
        for (int i = 0; i < size(); i++) {
            if (stringItems[i].equals(item)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public int lastIndexOf(String item) {
        validateItem(item);
        int index = -1;
        for (int i = size() - 1; i >= 0; i--) {
            if (stringItems[i].equals(item)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public String get(int index) {
        if (index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return stringItems[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        validateStringList(otherList);
        boolean isEquals = false;

        if (size() == otherList.size()) {
            for (int i = 0; i < size(); i++) {
                if (!stringItems[i].equals(otherList.get(i))) {
                    isEquals = false;
                    break;
                } else {
                    isEquals = true;
                }
            }
        }
        return isEquals;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clear() {
        stringItems = new String[stringItems.length];
        size = 0;
    }

    @Override
    public String[] toArray() {
        return Arrays.copyOf(stringItems, stringItems.length);
    }


    private void offsetAfterRemove(int indexStart) {
        for (int i = indexStart; i < size() - 1; i++) {
            stringItems[i] = stringItems[i + 1];
        }
        stringItems[size()] = null;
        size--;
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

}
