package data.structure;

import java.nio.BufferUnderflowException;

/**
 * Created by Administrator on 2017/6/17/017.
 */
public class MainBinarySearchTree<AnyType extends Comparable<? super AnyType>> {
    private class BinaryNode<AnyType> {
        private AnyType element;
        private BinaryNode<AnyType> left;
        private BinaryNode<AnyType> right;

        public BinaryNode(AnyType element) {
            this.element = element;
        }

        public BinaryNode(AnyType element, BinaryNode<AnyType> left, BinaryNode<AnyType> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    private BinaryNode<AnyType> root;

    public MainBinarySearchTree() {
        root = null;
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(AnyType x) {
        return contains(x, root);
    }

    public AnyType findMin() {
        if (isEmpty())
            throw new BufferUnderflowException();
        return findMin(root).element;
    }

    public AnyType findMax() {
        if (isEmpty())
            throw new BufferUnderflowException();
        return findMax(root).element;
    }

    public void insert(AnyType x) {
        root = insert(x, root);
    }

    public void remove(AnyType x) {
        root = remove(x, root);
    }

    public void printTree() {

    }

    private boolean contains(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return contains(x, t.left);
        else
            return compareResult <= 0 || contains(x, t.right);
    }

    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
        if (t == null)
            return null;
        else if (t.left == null)
            return t;
        return findMin(t.left);
    }

    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
        if (t != null)
            while (t.right != null)
                t = t.right;
        return t;
    }

    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return new BinaryNode<AnyType>(x, null, null);

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            t.left = insert(x, t.left);
        else if (compareResult > 0)
            t.right = insert(x, t.right);
        return t;

    }

    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return null;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            t.left = remove(x, t.left);
        else if (compareResult > 0)
            t.right = remove(x, t.right);
        else if (t.left != null && t.right != null) {
            t.element = findMin(t.right).element;
            t.right = remove(t.element,t.right);
        }else
            t = (t.left != null ) ?t.left : t.right;
        return t;
    }

    private void printTree(BinaryNode<AnyType> t) {

    }
}
