import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Tree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private TreeNode<T> root;

    private int size = 0;

    static class TreeNode<T> {
        TreeNode leftSon;
        TreeNode rightSon;
        TreeNode parent;
        T value;


        TreeNode(T value,
                 TreeNode parent,
                 TreeNode leftSon,
                 TreeNode rightSon){
            this.value = value;
            this.parent = parent;
            this.leftSon = leftSon;
            this.rightSon = rightSon;
        }
    }

    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) { return new SubSet<>(fromElement,toElement,this); }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) { return new HeadSet<>(toElement,this);}

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new TailSet<>(fromElement,this);
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        TreeNode current = root;
        while (current.leftSon != null) {
            current = current.leftSon;
        }
        return (T) current.value;
    }

    @Override
    public T last() {
        if(root == null) throw new NoSuchElementException();
        TreeNode current = root;
        while (current.rightSon != null){
            current = current.rightSon;
        }
        return (T) current.value;
    }


    public T getRootValue() { return (T) root.value;}


    public void find(T value){
        TreeNode<T> currentNode = root;


        while(currentNode.value.compareTo(value) != 0){
            if (currentNode.value.compareTo(value) < 0) {
                if (currentNode.rightSon == null) return;
                currentNode = currentNode.rightSon;
            }
            else {
                if (currentNode.leftSon == null) return;
                currentNode = currentNode.leftSon;
            }
        }

        splay(currentNode);
    }


    @Override
    public  Iterator<T> iterator() {
        return new TreeIterator();
    }

    public class TreeIterator implements Iterator<T> {

        private TreeNode<T> current = null;

        private List<TreeNode> listOfNodes;

        private TreeIterator() {
            listOfNodes = new ArrayList<>();
            fillListOfNodes(root);
        }

        private void fillListOfNodes(TreeNode current){
            listOfNodes.add(current);
            if (current.leftSon != null) fillListOfNodes(current.leftSon);
            if (current.rightSon != null) fillListOfNodes(current.rightSon);
        }

        private TreeNode findNext() {
            if (listOfNodes.isEmpty()) return null;

            TreeNode<T> result = listOfNodes.get(0);
            if (current == null){
                for (TreeNode<T> node: listOfNodes){
                    if (node.value.compareTo(result.value) < 0){ result = node; }
                }
            }
            else {
                for (TreeNode<T> node : listOfNodes) {
                    if (node.value.compareTo(result.value) < 0 && node.value.compareTo(current.value) > 0) {  result = node;  }
                }
            }

            return result;
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            listOfNodes.remove(current);
            return (T) current.value;
        }

        @Override
        public void remove() {
            Tree.this.remove(current);
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(T value){
        if (root == null) {
            root = new TreeNode(value, null,null,null);
            size ++;
        }
        else {
            TreeNode<T> currentNode = root;  // ячейка, в которой находимся в данный момент
            int cmp;


            while(true) {
                cmp = currentNode.value.compareTo(value);

                if (cmp == 0){
                    splay(currentNode);
                    return true;
                }
                if (cmp < 0) {
                    if (currentNode.rightSon != null) currentNode = currentNode.rightSon;
                    else {
                        currentNode.rightSon = new TreeNode(value, currentNode, null, null);
                        splay(currentNode.rightSon);
                        size ++;
                        return true;
                    }
                }

                if (cmp > 0) {
                    if (currentNode.leftSon != null) currentNode = currentNode.leftSon;
                    else {
                        currentNode.leftSon = new TreeNode(value, currentNode, null, null);
                        splay(currentNode.leftSon);
                        size ++;
                        return true;
                    }
                }
            }

        }
        return true;

    }


    public void remove(T value){
        find(value);                     // поднимает удаляемый эл-т

        TreeNode leftTreeRoot = root.leftSon;
        TreeNode rightTreeRoot = root.rightSon;

        if (leftTreeRoot != null) {
            leftTreeRoot = findMax(leftTreeRoot);                //ищем мах элемент левого дерева и поднимаем его вверх
            leftTreeRoot.rightSon = rightTreeRoot;
            if (rightTreeRoot != null) {
                rightTreeRoot.parent = leftTreeRoot;
            }

            root = leftTreeRoot;
            size --;
        }
        else{
            rightTreeRoot.parent = null;
            root = rightTreeRoot;
            size --;
        }


    }

    private TreeNode findMax(TreeNode root){
        TreeNode currentNode = root;

        while (currentNode.rightSon != null){
            currentNode = currentNode.rightSon;
        }

        splay(currentNode);
        return currentNode;
    }

    private void splay(TreeNode node){
        if (node.parent == null) return;

        TreeNode parent = node.parent;
        TreeNode gparent = parent.parent;

        if (gparent == null){
            if (parent.leftSon == node) {
                rightRotate(parent, node);
                return;
            }
            else{
                leftRotate(parent, node);
                return;
            }
        }
        else{
            if (gparent.leftSon == parent && parent.leftSon == node){     // left zig-zig
                leftZigZig(gparent, parent, node);
            }
            else if (gparent.rightSon == parent && parent.rightSon == node){    //right zig-zig
                rightZigZig(gparent, parent, node);
            }
            else if (gparent.leftSon == parent && parent.rightSon == node){      //right zig-zag
                rightZigZag(gparent, parent, node);
            }
            else if (gparent.rightSon == parent && parent.leftSon == node){   // left zig-zag
                leftZigZag(gparent, parent, node);
            }
        }

        splay(node); //может быть лучше сделать while ?
    }

    private void rightRotate(TreeNode parent, TreeNode node){
        TreeNode nodeRightSon = node.rightSon;

        node.rightSon = parent;
        parent.parent = node;
        parent.leftSon = nodeRightSon;
        if (nodeRightSon != null) nodeRightSon.parent = parent;
        node.parent = null;
        root = node;

    }

    private void leftRotate(TreeNode parent, TreeNode node){
        TreeNode nodeLeftSon = node.leftSon;

        node.leftSon = parent;
        node.parent = null;
        root = node;
        parent.parent = node;
        parent.rightSon = nodeLeftSon;
        if (nodeLeftSon != null) nodeLeftSon.parent = parent;
    }

    private void leftZigZig(TreeNode gparent, TreeNode parent, TreeNode node){

        //first rotate
        TreeNode parentRightSon = parent.rightSon;

        parent.rightSon = gparent;
        parent.parent = gparent.parent;
        setParent(gparent,parent);

        gparent.parent = parent;
        gparent.leftSon = parentRightSon;
        if (parentRightSon != null) parentRightSon.parent = gparent;

        //second rotate
        TreeNode nodeRightSon = node.rightSon;

        node.rightSon = parent;
        node.parent = parent.parent;
        setParent(parent,node);
        parent.parent = node;
        parent.leftSon = nodeRightSon;
        if (nodeRightSon != null) nodeRightSon.parent = parent;

        if (node.parent == null) root = node;
    }

    private void rightZigZig(TreeNode gparent, TreeNode parent, TreeNode node){

        //first rotate
        TreeNode parentLeftSon = parent.leftSon;

        parent.leftSon = gparent;
        parent.parent = gparent.parent;
        setParent(gparent,parent);

        gparent.parent = parent;
        gparent.rightSon = parentLeftSon;
        if (parentLeftSon != null) parentLeftSon.parent = gparent;

        //second rotate
        TreeNode nodeLeftSon = node.leftSon;

        node.leftSon = parent;
        node.parent = parent.parent;
        setParent(parent,node);

        parent.parent = node;
        parent.rightSon = nodeLeftSon;
        if (nodeLeftSon != null) nodeLeftSon.parent = parent;

        if (node.parent == null) root = node;
    }

    private void rightZigZag(TreeNode gparent, TreeNode parent, TreeNode node){

        // first rotate
        TreeNode nodeLeftSon = node.leftSon;

        gparent.leftSon = node;
        node.parent = gparent;
        node.leftSon = parent;
        parent.parent = node;
        parent.rightSon = nodeLeftSon;
        if (nodeLeftSon != null) nodeLeftSon.parent = parent;

        //second rotate
        TreeNode nodeRightSon = node.rightSon;

        node.rightSon = gparent;
        node.parent = gparent.parent;
        setParent(gparent,node);

        gparent.parent = node;
        gparent.leftSon = nodeRightSon;
        if (nodeRightSon != null) nodeRightSon.parent = gparent;

        if (node.parent == null) root = node;
    }

    private void leftZigZag(TreeNode gparent, TreeNode parent, TreeNode node){

        //first rotate
        TreeNode nodeRightSon = node.rightSon;

        gparent.rightSon = node;
        node.parent = gparent;
        node.rightSon = parent;
        parent.parent = node;
        parent.leftSon = nodeRightSon;
        if (nodeRightSon != null) nodeRightSon.parent = parent;

        //secont rotate
        TreeNode nodeLeftSon = node.leftSon;

        node.leftSon = gparent;
        node.parent = gparent.parent;
        setParent(gparent,node);

        gparent.parent = node;
        gparent.rightSon = nodeLeftSon;
        if (nodeLeftSon != null) nodeLeftSon.parent = gparent;

        if (node.parent == null) root = node;
    }

    private void setParent(TreeNode previousSon, TreeNode newSon){     //for ggParent
        if (newSon.parent != null){
            if (previousSon.parent.leftSon == previousSon) previousSon.parent.leftSon = newSon;
            else previousSon.parent.rightSon = newSon;
        }
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(TreeNode<T> node) {
        TreeNode<T> left = node.leftSon;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        TreeNode<T> right = node.rightSon;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    public boolean contains(T value){
        TreeNode<T> currentNode = root;

        while(currentNode.value.compareTo(value) != 0){
            if (currentNode.value.compareTo(value) < 0) {
                if (currentNode.rightSon == null) return false;
                currentNode = currentNode.rightSon;
            }
            else {
                if (currentNode.leftSon == null) return false;
                currentNode = currentNode.leftSon;
            }
        }

        return true;
    }

}