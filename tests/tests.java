import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class tests {

    @Test
    public void add(){
        Tree tree = new Tree<Integer>();
        tree.add(7);
        Assert.assertEquals(1,tree.size());
        Assert.assertEquals(7,tree.getRootValue());
        Assert.assertTrue(tree.checkInvariant());

        tree.add(9);
        Assert.assertEquals(2,tree.size());
        Assert.assertEquals(9,tree.getRootValue());
        Assert.assertEquals(7,tree.first());
        Assert.assertEquals(9,tree.last());
        Assert.assertTrue(tree.checkInvariant());

        tree.add(8);
        Assert.assertEquals(3,tree.size());
        Assert.assertEquals(7,tree.first());
        Assert.assertTrue(tree.checkInvariant());
    }

    @Test
    public void find(){
        Tree tree = new Tree<Integer>();
        tree.add(11);
        tree.add(5);
        tree.add(4);
        tree.add(8);
        tree.add(1);
        tree.add(14);
        tree.add(7);
        tree.add(12);
        tree.add(6);
        tree.add(3);
        tree.add(10);
        tree.add(13);
        tree.add(15);
        tree.add(2);
        tree.add(9);

        tree.find(12);
        Assert.assertEquals(12, tree.getRootValue());
        Assert.assertTrue(tree.checkInvariant());

        tree.find(5);
        Assert.assertEquals(5,tree.getRootValue());
        Assert.assertTrue(tree.checkInvariant());

        tree.find(13);
        Assert.assertEquals(13,tree.getRootValue());
        Assert.assertTrue(tree.checkInvariant());

        tree.find(7);
        Assert.assertEquals(7, tree.getRootValue());
        Assert.assertTrue(tree.checkInvariant());
    }

    @Test
    public void remove(){
        Tree tree = new Tree<Integer>();
        tree.add(11);
        tree.add(5);
        tree.add(4);
        tree.add(8);
        tree.add(1);
        tree.add(14);
        tree.add(7);
        tree.add(12);
        tree.add(6);
        tree.add(3);
        tree.add(10);
        tree.add(13);
        tree.add(15);
        tree.add(2);
        tree.add(9);

        tree.remove(9);
        Assert.assertEquals(8,tree.getRootValue());
        Assert.assertEquals(15, tree.last());
        Assert.assertEquals(1, tree.first());
        Assert.assertEquals(14, tree.size());
        Assert.assertTrue(tree.checkInvariant());

        tree.remove(15);
        Assert.assertEquals(14, tree.getRootValue());
        Assert.assertEquals(14, tree.last());
        Assert.assertEquals(13, tree.size());
        Assert.assertTrue(tree.checkInvariant());

        tree.remove(4);
        Assert.assertEquals(3, tree.getRootValue());
        Assert.assertEquals(1, tree.first());
        Assert.assertEquals(14, tree.last());
        Assert.assertTrue(tree.checkInvariant());
    }

    @Test
    public void subSet(){
        Tree tree = new Tree<Integer>();
        tree.add(11);
        tree.add(5);
        tree.add(4);
        tree.add(8);
        tree.add(1);
        tree.add(14);
        tree.add(7);
        tree.add(12);
        tree.add(6);
        tree.add(3);
        tree.add(10);
        tree.add(13);
        tree.add(15);
        tree.add(2);
        tree.add(9);

        SubSet subSet = (SubSet) tree.subSet(7,14);
        Assert.assertEquals(7,subSet.size());
        Assert.assertEquals(7, subSet.first());
        Assert.assertEquals(13, subSet.last());

        subSet.remove(10);
        Assert.assertFalse(tree.contains(10));
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(6, subSet.size());

        subSet.add(10);
        Assert.assertTrue(tree.contains(10));
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(7,subSet.size());

        try{
            subSet.add(1);
        }catch (IllegalArgumentException e){System.out.println("SubSet test, add(1) : IllegalArgumentException");}

        try{
            subSet.remove(2);
        }catch (IllegalArgumentException e){System.out.println("SubSet test, remove(2) : IllegalArgumentException");}


        //First and last test
        subSet = (SubSet) tree.subSet(7,9);
        Assert.assertEquals(7, subSet.first());
        Assert.assertEquals(8, subSet.last());
        Assert.assertEquals(2, subSet.size());

        subSet = (SubSet) tree.subSet(2,8);
        Assert.assertEquals(2, subSet.first());
        Assert.assertEquals(7, subSet.last());
        Assert.assertEquals(6,subSet.size());
    }

    @Test
    public void headSet(){
        Tree tree = new Tree<Integer>();
        tree.add(11);
        tree.add(5);
        tree.add(4);
        tree.add(8);
        tree.add(1);
        tree.add(14);
        tree.add(7);
        tree.add(12);
        tree.add(6);
        tree.add(3);
        tree.add(10);
        tree.add(13);
        tree.add(15);
        tree.add(2);
        tree.add(9);

        HeadSet headSet = (HeadSet) tree.headSet(7);
        Assert.assertEquals(6,headSet.size());
        Assert.assertEquals(1, headSet.first());
        Assert.assertEquals(6, headSet.last());

        headSet.add(6);
        Assert.assertTrue(tree.contains(6));
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(6,headSet.size());

        headSet.remove(5);
        Assert.assertTrue(tree.contains(6));
        Assert.assertFalse(tree.contains(5));
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(5, headSet.size());

        try {
            headSet.remove(9);
        }catch(IllegalArgumentException e){System.out.println("HeadSet test, remove(9) : IllegalArgumentException");}

        try{
            headSet.add(10);
        }catch (IllegalArgumentException e){System.out.println("HeadSet test, add(10) : IllegalArgumentException");}

        //First and last test
        headSet = (HeadSet) tree.headSet(5);
        Assert.assertEquals(1,headSet.first());
        Assert.assertEquals(4, headSet.last());
        Assert.assertEquals(4, headSet.size());

        headSet = (HeadSet) tree.headSet(15);
        Assert.assertEquals(1,headSet.first());
        Assert.assertEquals(14,headSet.last());
        Assert.assertEquals(13, headSet.size());  //не 14 т.к. удаляли 5ку
    }

    @Test
    public void tailSet(){
        Tree tree = new Tree<Integer>();
        tree.add(11);
        tree.add(5);
        tree.add(4);
        tree.add(8);
        tree.add(1);
        tree.add(14);
        tree.add(7);
        tree.add(12);
        tree.add(6);
        tree.add(3);
        tree.add(10);
        tree.add(13);
        tree.add(15);
        tree.add(2);
        tree.add(9);

        TailSet tailSet = (TailSet) tree.tailSet(6);
        Assert.assertEquals(10, tailSet.size());
        Assert.assertEquals(6, tailSet.first());
        Assert.assertEquals(15, tailSet.last());

        tailSet.add(16);
        Assert.assertTrue(tree.contains(16));
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(11, tailSet.size());

        tailSet.remove(14);
        Assert.assertTrue(tree.contains(15));
        Assert.assertTrue(tree.contains(16));
        Assert.assertFalse(tree.contains(14));
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(10, tailSet.size());

        try{
            tailSet.remove(1);
        }catch (IllegalArgumentException e){System.out.println("TailSet test, remove(1) : IllegalArgumentException");}

        try{
            tailSet.add(4);
        }catch(IllegalArgumentException e){System.out.println("TailSet test, add(4) : IllegalArgumentException");}

        //First and last test
        tailSet = (TailSet) tree.tailSet(12);
        Assert.assertEquals(12, tailSet.first());
        Assert.assertEquals(16, tailSet.last());
        Assert.assertEquals(4, tailSet.size());

        tailSet = (TailSet) tree.tailSet(9);
        Assert.assertEquals(9, tailSet.first());
        Assert.assertEquals(16, tailSet.last());
        Assert.assertEquals(7, tailSet.size());
    }

    @Test
    public void iteratorTest(){
        Tree tree = new Tree<Integer>();
        tree.add(1);
        tree.add(2);
        tree.add(12);
        tree.add(17);
        tree.add(15);
        tree.add(11);
        tree.add(3);

        Iterator iterator = tree.iterator();
        Assert.assertEquals(1, iterator.next());
        Assert.assertEquals(2, iterator.next());
        Assert.assertEquals(3, iterator.next());
        Assert.assertEquals(11, iterator.next());
        Assert.assertEquals(12, iterator.next());
        Assert.assertEquals(15, iterator.next());
        Assert.assertEquals(17, iterator.next());
        try{
            iterator.next();
        }catch (NoSuchElementException e) {System.out.println("Iterator test: NoSuchElementException");}

    }

}


