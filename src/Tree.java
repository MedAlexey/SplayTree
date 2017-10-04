import java.util.ArrayList;

public class Tree<T extends Comparable<T>>{

    private TreeNode root;

   private class TreeNode {
        TreeNode leftSon;
        TreeNode rightSon;
        TreeNode parent;
        T value;


        public TreeNode(T value,
                        TreeNode parent,
                        TreeNode leftSon,
                        TreeNode rightSon){
            this.value = value;
            this.parent = parent;
            this.leftSon = leftSon;
            this.rightSon = rightSon;
        }

   }


   public T getRootValue() { return root.value;}



    public ArrayList<TreeNode> split(T node){
       find(node);

       ArrayList<TreeNode> result = new ArrayList<>();
       result.add(root.leftSon);
       result.add(root.rightSon);

       return result;
    }


    public void find(T value){
        TreeNode currentNode = root;

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




    public void add( T value){
        if (root == null) root = new TreeNode(value, null,null,null);
        else {
            TreeNode currentNode = root;  // ячейка, в которой находимся в данный момент
            int cmp;


            while(true) {
                cmp = currentNode.value.compareTo(value);

                if (cmp == 0){
                    splay(currentNode);
                    return;
                }
                if (cmp < 0) {
                    if (currentNode.rightSon != null) currentNode = currentNode.rightSon;
                    else {
                        currentNode.rightSon = new TreeNode(value, currentNode, null, null);
                        splay(currentNode.rightSon);
                        return;
                    }
                }

                if (cmp > 0) {
                    if (currentNode.leftSon != null) currentNode = currentNode.leftSon;
                    else {
                        currentNode.leftSon = new TreeNode(value, currentNode, null, null);
                        splay(currentNode.leftSon);
                        return;
                    }
                }
            }

        }

    }



    public void remove(T value){
        find(value);                     // поднимает удаляемый эл-т

        TreeNode leftTreeRoot = root.leftSon;
        TreeNode rightTreeRoot = root.rightSon;

        if (leftTreeRoot != null) {
            leftTreeRoot.parent = null;
            findMax(leftTreeRoot);                //ищем мах элемент левого дерева и поднимаем его вверх
            if (rightTreeRoot != null) {
                rightTreeRoot.parent = leftTreeRoot;
                leftTreeRoot.rightSon = rightTreeRoot;
            }
            root = leftTreeRoot;
            return;
        }
        else{
            rightTreeRoot.parent = null;
            root = rightTreeRoot;
            return;
        }


    }

    private void findMax(TreeNode root){
        TreeNode currentNode = root;

        while (currentNode.rightSon != null){
            currentNode = currentNode.rightSon;
        }

        splay(currentNode);
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

        splay(node);
    }

    private void rightRotate(TreeNode parent, TreeNode son){
        TreeNode sonRightSon = son.rightSon;

        son.rightSon = parent;
        parent.parent = son;
        parent.leftSon = sonRightSon;
        son.parent = null;
        root = son;

    }

    private void leftRotate(TreeNode parent, TreeNode son){
        TreeNode sonLeftSon = son.leftSon;

        son.leftSon = parent;
        son.parent = null;
        root = son;
        parent.parent = son;
        parent.rightSon = sonLeftSon;
    }

    private void leftZigZig(TreeNode gparent, TreeNode parent, TreeNode node){

        //first rotate
        TreeNode parentRightSon = parent.rightSon;

        parent.rightSon = gparent;
        parent.parent = gparent.parent;
        setParent(gparent,parent);

        gparent.parent = parent;
        gparent.leftSon = parentRightSon;

        //second rotate
        TreeNode nodeRightSon = node.rightSon;

        node.rightSon = parent;
        node.parent = parent.parent;
        setParent(parent,node);
        parent.parent = node;
        parent.leftSon = nodeRightSon;

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

        //second rotate
        TreeNode nodeLeftSon = node.leftSon;

        node.leftSon = parent;
        node.parent = parent.parent;
        setParent(parent,node);

        parent.parent = node;
        parent.rightSon = nodeLeftSon;

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

        //second rotate
        TreeNode nodeRightSon = node.rightSon;

        node.rightSon = gparent;
        node.parent = gparent.parent;
        setParent(gparent,node);

        gparent.parent = node;
        gparent.leftSon = nodeRightSon;

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

        //secont rotate
        TreeNode nodeLeftSon = node.leftSon;

        node.leftSon = gparent;
        node.parent = gparent.parent;
        setParent(gparent,node);

        gparent.parent = node;
        gparent.rightSon = nodeLeftSon;

        if (node.parent == null) root = node;
    }

    private void setParent(TreeNode previousSon, TreeNode newSon){
        if (newSon.parent != null){
            if (previousSon.parent.leftSon == previousSon) previousSon.parent.leftSon = newSon;
            else previousSon.parent.rightSon = newSon;
        }
    }


}
