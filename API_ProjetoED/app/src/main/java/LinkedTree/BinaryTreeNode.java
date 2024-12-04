package LinkedTree;

public class BinaryTreeNode<T> {

    protected T element;

    protected BinaryTreeNode<T> left, right;

    public BinaryTreeNode() {
        this.element = null;
        this.left = null;
        this.right = null;
    }

    public BinaryTreeNode(T obj) {
        this.element = obj;
        this.left = null;
        this.right = null;
    }

    public int numChildren() {
        int numChildren = 0;

        if (this.left != null) {
            numChildren = 1 + this.left.numChildren();
        }

        if (this.right != null) {
            numChildren = numChildren + 1 + this.right.numChildren();
        }

        return numChildren;
    }
}
