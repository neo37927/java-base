package data.structure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Administrator on 2017/6/17/017.
 */
public class MainLinkedList<AnyType> implements Iterable<AnyType> {

    private int theSize;
    private int modCount = 0;//记录链表的改变次数，同步至迭代器，做应该修改的检测
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;


    public MainLinkedList() {
        clear();
    }

    public void clear() {
        beginMarker = new Node<AnyType>(null, null, null);
        endMarker = new Node<AnyType>(null, beginMarker, null);
        beginMarker.next = endMarker;

        theSize = 0;
        modCount++;
    }

    public int size() {
        return theSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(AnyType x) {
        add(size(), x);
        return true;
    }

    public void add(int idx, AnyType x) {
        addBefore(getNode(idx), x);
    }

    public AnyType set(int idx, AnyType newVal) {
        Node<AnyType> p = getNode(idx);
        AnyType oldVal = p.data;
        p.data = newVal;
        return oldVal;
    }

    public AnyType remove(int idx) {
        return remove(getNode(idx));
    }

    private void addBefore(Node<AnyType> p, AnyType x) {
        Node<AnyType> newNode = new Node<AnyType>(x, p.prev, p);
        newNode.prev.next = newNode;
        p.prev = newNode;
        theSize++;
        modCount++;

    }

    private Node<AnyType> getNode(int idx) {
        Node<AnyType> p;

        if (idx < 0 || idx > size())
            throw new IndexOutOfBoundsException();

        if (idx < size() / 2) {
            p = beginMarker.next;
            for (int i = 0; i < idx; i++) {
                p = p.next;
            }
        } else {
            p = endMarker;
            for (int i = size(); i > idx; i--)
                p = p.prev;
        }

        return p;
    }

    private AnyType remove(Node<AnyType> p) {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;

        return p.data;
    }

    public Iterator<AnyType> iterator() {
        return new LinkedListIterator();
    }

    private class Node<AnyType> {
        private AnyType data;
        private Node<AnyType> prev;
        private Node<AnyType> next;

        private Node(AnyType data, Node<AnyType> prev, Node<AnyType> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private class LinkedListIterator implements Iterator<AnyType> {

        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        public boolean hasNext() {
            return current != endMarker;
        }

        public AnyType next() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (!hasNext())
                throw new NoSuchElementException();

            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }

        public void remove() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (!okToRemove)
                throw new IllegalStateException();

            MainLinkedList.this.remove(current.prev);
            okToRemove = false;
            expectedModCount++;
        }
    }
}
