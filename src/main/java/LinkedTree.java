import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * N-ary tree structure, Holds a pointer to a "current" node, which is used in most operations.
 * Identity based equals/hashcode
 *
 * @param <T> data type
 */
public class LinkedTree<T> {
    private static class Node<T> {
        T data;
        List<Node<T>> children;
        Node<T> parent;

        /**
         * Establishes new ROOT with no connections. Use addChild otherwise
         */
        Node(T data) {
            this.data = data;
            children = new ArrayList<>();
        }

        void setData(T data) {
            this.data = data;

        }

        void removeChild(int index) {
            children.remove(index);
        }

        void removeChild(Node<T> child) {
            children.remove(child);
        }

        /**
         * Adds new child to node.
         *
         * @param data value for new child to hold
         */
        void addChild(T data) {
            Node<T> node = new Node<>(data);
            children.add(node);
            node.parent = this;
        }

        /**
         * only for use during rerooting
         */
        void removeParent() {
            parent = null;
        }

        /**
         * Shallow copy
         */
        List<Node<T>> getChildren() {
            return children;
        }

        Node<T> getChild(int index) {
            if (index >= children.size()) {
                throw new IllegalArgumentException("Last Child is at index " + (children.size() - 1));
            }
            return children.get(index);
        }

        T getData() {
            return data;
        }

        Node<T> getParent() {
            return parent;
        }
    }

    private Node<T> root;
    private Node<T> current;

    public T getCurrent() {
        return current.getData();
    }

    /**
     * Establishes tree with data as root
     */
    public LinkedTree(T data) {
        root = new Node<>(data);
        current = root;
    }

    /**
     * Establishes the current node as the new root, and throws out all nodes located above.
     */
    public void reroot() {
        root = current;
        current.removeParent();
    }

    /**
     * Replaces the data of the current node, without affecting connections
     */
    public void replace(T data) {
        current.setData(data);
    }

    /**
     * @return children data in list
     */
    public List<T> getChildren() {
        return current.getChildren().stream().map(Node::getData).toList();
    }

    /**
     * Does not move pointer
     *
     * @param index index of child of current node
     * @return child data
     */
    public T getChild(int index) {
        if (hasChildren()) {
            return current.getChild(index).getData();
        }
        else{
            throw new IllegalStateException("There are no children to delve into");
        }
    }

    /**
     * Moves pointer to chosen child
     *
     * @param index index of child of current node
     */
    public void down(int index) {
        current = current.getChild(index);
    }

    public boolean hasChildren() {
        return !(current.getChildren().isEmpty());
    }

    /**
     * Add child to current node
     */
    public void addChild(T data) {
        current.addChild(data);
    }

    /**
     * Returns the pointer to its parent
     *
     * @return Parent data
     */
    public void up() {
        if (hasParent()) {
            current = current.getParent();
        } else {
            throw new IllegalStateException("The tree's pointer cannot move up from the root");
        }
    }

    public boolean hasParent() {
        return !(current == root);
    }

    /**
     * removes current node and moves pointer to parent
     */
    public void remove() {
        if (current.equals(root)) {
            throw new IllegalStateException("You cannot remove the root of a LinkedTree");
        }
        Node<T> temp = current;
        current = current.getParent();
        current.removeChild(temp);
    }

    /**
     * Returns the pointer to the root
     *
     * @return root data
     */
    public T reset() {
        current = root;
        return current.getData();
    }

}
