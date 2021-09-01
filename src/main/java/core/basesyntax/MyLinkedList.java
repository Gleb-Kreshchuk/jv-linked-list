package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(tail, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = nodePosition(index);
        if (current == head) {
            Node<T> newNode = new Node<T>(null, value, current);
            current.prev = newNode;
            head = newNode;
        } else {
            Node<T> newNode = new Node<T>(current.prev, value, current);
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return nodePosition(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> current = nodePosition(index);
        T initial = current.element;
        current.element = value;
        return initial;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(nodePosition(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.element == object
                    || (current.element != null && current.element.equals(object))) {
                unlink(nodePosition(i));
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private T unlink(Node<T> node) {
        T removedValue = node.element;
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.element = null;
        size--;
        return removedValue;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throwException(index);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throwException(index);
        }
    }

    private void throwException(int index) {
        throw new IndexOutOfBoundsException("Index: " + index
                + " is out of LinkedList bounds.");
    }

    private Node<T> nodePosition(int index) {
        Node<T> position;
        if (index < size >> 1) {
            position = head;

            for (int i = 0; i < index; i++) {
                position = position.next;
            }

        } else {
            position = tail;
            for (int i = size - 1; i > index; i--) {
                position = position.prev;
            }
        }
        return position;
    }

    private class Node<T> {
        private Node<T> prev;
        private T element;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}

