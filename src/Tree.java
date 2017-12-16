import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Tree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private TreeNode<T> root;

    private int size = 0;

    static class TreeNode<T> {
        TreeNode<T> leftSon;
        TreeNode<T> rightSon;
        TreeNode<T> parent;
        T value;


        TreeNode(T value,
                 TreeNode<T> parent,
                 TreeNode<T> leftSon,
                 TreeNode<T> rightSon){
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
        TreeNode<T> current = root;
        while (current.leftSon != null) {
            current = current.leftSon;
        }
        return current.value;
    }

    @Override
    public T last() {
        if(root == null) throw new NoSuchElementException();
        TreeNode<T> current = root;
        while (current.rightSon != null){
            current = current.rightSon;
        }
        return current.value;
    }


    public T getRootValue() { return root.value;}


    public void find(T value){
        if (!contains(value)) throw new NoSuchElementException();

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


    @NotNull
    @Override
    public  Iterator<T> iterator() {
        return new TreeIterator();
    }

    public class TreeIterator implements Iterator<T> {

        private TreeNode<T> current = null;

        private TreeNode<T> findNext() {
            TreeNode<T> next = current;

            if (next == null){
               next = first();
               return next;
            }

           if (next.rightSon != null){
               next = next.rightSon;
               while (next.leftSon != null) next = next.leftSon;
               return next;
           }

           while (next.parent != null){
               if (next.parent.leftSon == next){
                   next = next.parent;
                   return next;
               }
               next = next.parent;
           }

           return null;

        }

        private TreeNode<T> first(){
            if (root == null) throw new NoSuchElementException();
            TreeNode<T> result = root;
            while (result.leftSon != null) {
                result = result.leftSon;
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
            return current.value;
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
            root = new TreeNode<T>(value, null,null,null);
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
                        currentNode.rightSon = new TreeNode<T>(value, currentNode, null, null);
                        splay(currentNode.rightSon);
                        size ++;
                        return true;
                    }
                }

                if (cmp > 0) {
                    if (currentNode.leftSon != null) currentNode = currentNode.leftSon;
                    else {
                        currentNode.leftSon = new TreeNode<T>(value, currentNode, null, null);
                        splay(currentNode.leftSon);
                        size ++;
                        return true;
                    }
                }
            }

        }
        return true;

    }

    @Override
    public boolean remove(Object o){
        @SuppressWarnings("unchecked")
        T value = (T) o;

        if (!contains(value)) return false;

        find(value);                     // поднимает удаляемый эл-т

        TreeNode<T> leftTreeRoot = root.leftSon;
        TreeNode<T> rightTreeRoot = root.rightSon;

        if (leftTreeRoot != null) {
            leftTreeRoot = findMax(leftTreeRoot);                //ищем мах элемент левого дерева и поднимаем его вверх
            leftTreeRoot.rightSon = rightTreeRoot;
            if (rightTreeRoot != null) {
                rightTreeRoot.parent = leftTreeRoot;
            }

            root = leftTreeRoot;
            size --;
            return true;
        }
        else{
            rightTreeRoot.parent = null;
            root = rightTreeRoot;
            size --;
            return true;
        }
    }

    private TreeNode<T> findMax(TreeNode<T> root){
        TreeNode<T> currentNode = root;

        while (currentNode.rightSon != null){
            currentNode = currentNode.rightSon;
        }

        splay(currentNode);
        return currentNode;
    }

    private void splay(TreeNode<T> node){
        if (node.parent == null) return;

        TreeNode<T> parent = node.parent;
        TreeNode<T> gparent = parent.parent;

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

    private void rightRotate(TreeNode<T> parent, TreeNode<T> node){
        TreeNode<T> nodeRightSon = node.rightSon;

        node.rightSon = parent;
        parent.parent = node;
        parent.leftSon = nodeRightSon;
        if (nodeRightSon != null) nodeRightSon.parent = parent;
        node.parent = null;
        root = node;

    }

    private void leftRotate(TreeNode<T> parent, TreeNode<T> node){
        TreeNode<T> nodeLeftSon = node.leftSon;

        node.leftSon = parent;
        node.parent = null;
        root = node;
        parent.parent = node;
        parent.rightSon = nodeLeftSon;
        if (nodeLeftSon != null) nodeLeftSon.parent = parent;
    }

    private void leftZigZig(TreeNode<T> gparent, TreeNode<T> parent, TreeNode<T> node){

        //first rotate
        TreeNode<T> parentRightSon = parent.rightSon;

        parent.rightSon = gparent;
        parent.parent = gparent.parent;
        setParent(gparent,parent);

        gparent.parent = parent;
        gparent.leftSon = parentRightSon;
        if (parentRightSon != null) parentRightSon.parent = gparent;

        //second rotate
        TreeNode<T> nodeRightSon = node.rightSon;

        node.rightSon = parent;
        node.parent = parent.parent;
        setParent(parent,node);
        parent.parent = node;
        parent.leftSon = nodeRightSon;
        if (nodeRightSon != null) nodeRightSon.parent = parent;

        if (node.parent == null) root = node;
    }

    private void rightZigZig(TreeNode<T> gparent, TreeNode<T> parent, TreeNode<T> node){

        //first rotate
        TreeNode<T> parentLeftSon = parent.leftSon;

        parent.leftSon = gparent;
        parent.parent = gparent.parent;
        setParent(gparent,parent);

        gparent.parent = parent;
        gparent.rightSon = parentLeftSon;
        if (parentLeftSon != null) parentLeftSon.parent = gparent;

        //second rotate
        TreeNode<T> nodeLeftSon = node.leftSon;

        node.leftSon = parent;
        node.parent = parent.parent;
        setParent(parent,node);

        parent.parent = node;
        parent.rightSon = nodeLeftSon;
        if (nodeLeftSon != null) nodeLeftSon.parent = parent;

        if (node.parent == null) root = node;
    }

    private void rightZigZag(TreeNode<T> gparent, TreeNode<T> parent, TreeNode<T> node){

        // first rotate
        TreeNode<T> nodeLeftSon = node.leftSon;

        gparent.leftSon = node;
        node.parent = gparent;
        node.leftSon = parent;
        parent.parent = node;
        parent.rightSon = nodeLeftSon;
        if (nodeLeftSon != null) nodeLeftSon.parent = parent;

        //second rotate
        TreeNode<T> nodeRightSon = node.rightSon;

        node.rightSon = gparent;
        node.parent = gparent.parent;
        setParent(gparent,node);

        gparent.parent = node;
        gparent.leftSon = nodeRightSon;
        if (nodeRightSon != null) nodeRightSon.parent = gparent;

        if (node.parent == null) root = node;
    }

    private void leftZigZag(TreeNode<T> gparent, TreeNode<T> parent, TreeNode<T> node){

        //first rotate
        TreeNode<T> nodeRightSon = node.rightSon;

        gparent.rightSon = node;
        node.parent = gparent;
        node.rightSon = parent;
        parent.parent = node;
        parent.leftSon = nodeRightSon;
        if (nodeRightSon != null) nodeRightSon.parent = parent;

        //secont rotate
        TreeNode<T> nodeLeftSon = node.leftSon;

        node.leftSon = gparent;
        node.parent = gparent.parent;
        setParent(gparent,node);

        gparent.parent = node;
        gparent.rightSon = nodeLeftSon;
        if (nodeLeftSon != null) nodeLeftSon.parent = gparent;

        if (node.parent == null) root = node;
    }

    private void setParent(TreeNode previousSon, TreeNode<T> newSon){     //for ggParent
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