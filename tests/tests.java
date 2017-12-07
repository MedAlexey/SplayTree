import com.sun.deploy.association.AssociationException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

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

        SortedSet subSet = tree.subSet(7,14);
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
        subSet = tree.subSet(7,9);
        Assert.assertEquals(7, subSet.first());
        Assert.assertEquals(8, subSet.last());
        Assert.assertEquals(2, subSet.size());

        subSet = tree.subSet(2,8);
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

        SortedSet headSet = tree.headSet(7);
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
        headSet = tree.headSet(5);
        Assert.assertEquals(1,headSet.first());
        Assert.assertEquals(4, headSet.last());
        Assert.assertEquals(4, headSet.size());

        headSet = tree.headSet(15);
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

        SortedSet tailSet =  tree.tailSet(6);
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
        tailSet = tree.tailSet(12);
        Assert.assertEquals(12, tailSet.first());
        Assert.assertEquals(16, tailSet.last());
        Assert.assertEquals(4, tailSet.size());

        tailSet = tree.tailSet(9);
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

    @Test
    public void subSetSubSet(){
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

        SortedSet subSet = tree.subSet(3,10);
        SortedSet subSetSubSet = subSet.subSet(5,8);


        subSetSubSet.remove(6);
        Assert.assertFalse(tree.contains(6));
        Assert.assertEquals(14, tree.size());
        Assert.assertEquals(2,subSetSubSet.size());
        Assert.assertTrue(tree.checkInvariant());

        subSetSubSet.remove(5);
        Assert.assertFalse(tree.contains(5));
        Assert.assertEquals(13, tree.size());
        Assert.assertEquals(1, subSetSubSet.size());
        Assert.assertTrue(tree.checkInvariant());

        try{
            subSetSubSet.remove(6);
        }catch (IllegalArgumentException e){
            System.out.println("SubSetSubSet, remove(6) : IllegalArgumentException");
        }

        try{
            subSetSubSet.remove(4);
        }catch (IllegalArgumentException e){
            System.out.println("SubSetSubSet, remove(4) : IllegalArgumentException");
        }

        try{
            subSetSubSet.remove(8);
        }catch (IllegalArgumentException e){
            System.out.println("SubSetSubSet, remove(8) : IllegalArgumentException");
        }

        subSetSubSet.add(6);
        Assert.assertTrue(tree.contains(6));
        Assert.assertEquals(14, tree.size());
        Assert.assertEquals(2, subSetSubSet.size());
        Assert.assertTrue(tree.checkInvariant());

        subSetSubSet.add(5);
        Assert.assertEquals(15, tree.size());
        Assert.assertEquals(3, subSetSubSet.size());
        Assert.assertTrue(tree.contains(5));
        Assert.assertTrue(tree.checkInvariant());

        try{
            subSetSubSet.add(3);
        }catch (IllegalArgumentException e){
            System.out.println("SubSetSubSet, add(3) : IllegalArgumentException");
        }

        try{
            subSetSubSet.add(10);
        }catch (IllegalArgumentException e){
            System.out.println("SubSetSubSet, add(10) : IllegalArgumentException");
        }

        //Создание множеств, выходящих за пределы данного
        try{
            subSetSubSet = subSet.subSet(2,9);
        }catch(IndexOutOfBoundsException e) {
            System.out.println("SubSetSubSet, create(2,9) : IndexOutOfBoundsException");
        }

        try{
            subSet = subSet.subSet(4,12);
        }catch(IndexOutOfBoundsException e){
            System.out.println("SubSetSubSet, create(4, 12) : IndexOutOfBoundsException");
        }
    }

    @Test
    public void subSetHeadSet(){
        Tree tree = new Tree<Integer>();
        tree.add(1);
        tree.add(2);
        tree.add(12);
        tree.add(17);
        tree.add(15);
        tree.add(11);
        tree.add(3);

        SortedSet subSet = tree.subSet(2, 15);
        SortedSet headSet = subSet.headSet(12);

        headSet.add(5);
        Assert.assertTrue(tree.contains(5));
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(4, headSet.size());

        headSet.remove(2);
        Assert.assertEquals(3, headSet.first());
        Assert.assertFalse(tree.contains(2));
        Assert.assertTrue(tree.checkInvariant());

        try{
            headSet.remove(17);
        }catch (IllegalArgumentException e){
            System.out.println("SubSetHeadSet, remove(17) : IllegalArgumentException");
        }

        try{
            headSet.add(0);
        }catch (IllegalArgumentException e){
            System.out.println("SubSetHeadSet, add(0) : IllegalArgumentException");
        }
    }

    @Test
    public void subSetTailSet(){
        Tree tree = new Tree<Integer>();
        tree.add(1);
        tree.add(2);
        tree.add(12);
        tree.add(17);
        tree.add(15);
        tree.add(11);
        tree.add(3);

        SortedSet subSet = tree.subSet(3, 17);
        SortedSet tailSet = subSet.tailSet(12);

        tailSet.remove(12);
        Assert.assertEquals(15, tailSet.first());
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertFalse(tree.contains(12));

        tailSet.add(16);
        Assert.assertTrue(tree.contains(16));
        Assert.assertTrue(tree.checkInvariant());
    }


    @Test
    public void subSetIterator(){
        Tree tree = new Tree<Integer>();
        tree.add(1);
        tree.add(2);
        tree.add(12);
        tree.add(17);
        tree.add(15);
        tree.add(11);
        tree.add(3);

        SortedSet subSet = tree.subSet(1,11);
        Iterator iterator = subSet.iterator();

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(1, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(2, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(3, iterator.next());
        Assert.assertFalse(iterator.hasNext());

        try{
            iterator.next();
        }catch (NoSuchElementException e){
            System.out.println("subSetIterator test: NoSuchElementException");
        }
    }

    @Test
    public void headSetHeadSet(){
        Tree tree = new Tree<Integer>();
        tree.add(1);
        tree.add(2);
        tree.add(12);
        tree.add(17);
        tree.add(15);
        tree.add(11);
        tree.add(3);

        SortedSet headSet = tree.headSet(15);

        SortedSet headSetHeadSet = headSet.headSet(11);

        headSetHeadSet.remove(3);
        Assert.assertFalse(tree.contains(3));
        Assert.assertEquals(6, tree.size());
        Assert.assertEquals(2, headSetHeadSet.size());
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(1, headSetHeadSet.first());
        Assert.assertEquals(2, headSetHeadSet.last());

        headSetHeadSet.add(5);
        Assert.assertTrue(tree.contains(5));
        Assert.assertEquals(7, tree.size());
        Assert.assertEquals(3, headSetHeadSet.size());
        Assert.assertTrue(tree.checkInvariant());

        try{
            headSetHeadSet.remove(11);
        }catch (IllegalArgumentException e){
            System.out.println("HeadSetHeadSet, remove(11) : IllegalArgumentException");
        }

        try{
            headSetHeadSet.add(15);
        }catch (IllegalArgumentException e){
            System.out.println("HeadSetHeadSet, add(15) : IllegalArgumentException");
        }

        try{
            headSetHeadSet.remove(4);
        }catch (IllegalArgumentException e){
            System.out.println("HeadSetHeadSet, remove(4) : IllegalArgumentException");
        }

        try{
            headSetHeadSet = headSet.headSet(17);
        }catch(IndexOutOfBoundsException e){
            System.out.println("HeadSetHeadSet, create(17) : IndexOutOfBoundsException");
        }

    }

    @Test
    public void headSetSubSet(){
        Tree tree = new Tree<Integer>();
        tree.add(13);
        tree.add(7);
        tree.add(4);
        tree.add(9);
        tree.add(11);
        tree.add(2);
        tree.add(14);

        SortedSet headSet = tree.headSet(13);
        SortedSet subSet = headSet.subSet(4,11);

        subSet.remove(7);
        Assert.assertFalse(tree.contains(7));
        Assert.assertEquals(6, tree.size());
        Assert.assertTrue(tree.checkInvariant());

        subSet.add(10);
        Assert.assertTrue(tree.contains(10));
        Assert.assertEquals(7, tree.size());
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(10, subSet.last());
        Assert.assertEquals(4, subSet.first());

        try{
            subSet.remove(11);
        }catch (IllegalArgumentException e){
            System.out.println("HeadSetSubSet, remove(11) : IllegalArgumentException");
        }

        try{
            subSet.add(3);
        }catch (IllegalArgumentException e){
            System.out.println("HeadSetSubSet, add(3) : IllegalArgumentException");
        }

        try{
            subSet.remove(7);
        }catch (IllegalArgumentException e){
            System.out.println("HeadSetSubSet, remove(7) : IllegalArgumentException");
        }

        try{
            headSet.subSet(2,13);
        }catch (IndexOutOfBoundsException e){
            System.out.println("HeadSetSubSet, create(2, 13) : IndexOutOfBoundsException");
        }
    }

    @Test
    public void headSetTailSet(){
        Tree tree = new Tree<Integer>();
        tree.add(13);
        tree.add(7);
        tree.add(4);
        tree.add(9);
        tree.add(11);
        tree.add(2);
        tree.add(14);

        SortedSet headSet = tree.headSet(11);
        SortedSet tailSet = headSet.tailSet(4);

        tailSet.remove(7);
        Assert.assertFalse(tree.contains(7));
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(6, tree.size());
        Assert.assertEquals(3, headSet.size());

        tailSet.add(6);
        Assert.assertTrue(tree.contains(6));
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(7, tree.size());

        try{
            tailSet.remove(14);
        }catch (IllegalArgumentException e){
            System.out.println("HeadSetTailSet, remove(14) : IllegalArgumentException");
        }

        try{
            tailSet.add(1);
        }catch (IllegalArgumentException e){
            System.out.println("HeadSetTailSet, add(1) : IllegalArgumentException");
        }

    }

    @Test
    public void headSetIterator(){
        Tree tree = new Tree<Integer>();
        tree.add(1);
        tree.add(2);
        tree.add(12);
        tree.add(17);
        tree.add(15);
        tree.add(11);
        tree.add(3);

        SortedSet headSet = tree.headSet(12);
        Iterator iterator = headSet.iterator();

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(1, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(2, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(3, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(11, iterator.next());
        Assert.assertFalse(iterator.hasNext());

        try{
            iterator.next();
        }catch (NoSuchElementException e){
            System.out.println("headSetIterator test: NoSuchElementException");
        }
    }

    @Test
    public void tailSetTailSet(){
        Tree tree = new Tree<Integer>();
        tree.add(13);
        tree.add(7);
        tree.add(4);
        tree.add(9);
        tree.add(11);
        tree.add(2);
        tree.add(14);

        SortedSet tailSet = tree.tailSet(7);

        SortedSet tailSetTailSet = tailSet.tailSet(11);


        tailSetTailSet.remove(14);
        Assert.assertEquals(6, tree.size());
        Assert.assertFalse(tree.contains(14));
        Assert.assertEquals(13, tailSetTailSet.last());
        Assert.assertTrue(tree.checkInvariant());

        tailSetTailSet.add(17);
        Assert.assertTrue(tree.contains(17));
        Assert.assertEquals(7, tree.size());
        Assert.assertEquals(17, tailSetTailSet.last());
        Assert.assertTrue(tree.checkInvariant());

        try{
            tailSetTailSet.add(5);
        }catch (IllegalArgumentException e){
            System.out.println("TailSetTailSet, add(5) : IllegalArgumentException");
        }

        try{
            tailSetTailSet.remove(10);
        }catch (IllegalArgumentException e){
            System.out.println("TailSetTailSet, remove(10) : IllegalArgumentException");
        }

        try {
            tailSetTailSet.remove(12);
        }catch (IllegalArgumentException e){
            System.out.println("TailSetTailSet, remove(12) : IllegalArgumentException");
        }


        try{
            tailSetTailSet = tailSet.tailSet(3);
        }catch (IndexOutOfBoundsException e){
            System.out.println("TailSetTailSet, create(3) : IndexOutOfBoundsException");
        }
    }

    @Test
    public void tailSetSubSet(){
        Tree tree = new Tree<Integer>();
        tree.add(13);
        tree.add(7);
        tree.add(4);
        tree.add(9);
        tree.add(11);
        tree.add(2);
        tree.add(14);

        SortedSet tailSet = tree.tailSet(9);
        SortedSet subSet = tailSet.subSet(9, 13);

        subSet.remove(11);
        Assert.assertFalse(tree.contains(11));
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(1, subSet.size());

        subSet.add(12);
        Assert.assertTrue(tree.contains(12));
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(2, subSet.size());

        try{
            subSet.add(17);
        }catch (IllegalArgumentException e){
            System.out.println("TailSetSubSet, add(17) : IllegalArgumentException");
        }

        try{
            subSet.remove(2);
        }catch(IllegalArgumentException e){
            System.out.println("TailSetSubSet, remove(2) : IllegalArgumentException");
        }

        try{
            subSet.remove(11);
        }catch (IllegalArgumentException e){
            System.out.println("TailSetSubSet, remove(11) : IllegalArgumentException");
        }

        try{
            subSet = tailSet.subSet(4, 11);
        }catch (IndexOutOfBoundsException e){
            System.out.println("TailSetSubSet, create(4, 11) : IndexOutOfBoundsException");
        }

    }

    @Test
    public void tailSetHeadSet(){
        Tree tree = new Tree<Integer>();
        tree.add(13);
        tree.add(7);
        tree.add(4);
        tree.add(9);
        tree.add(11);
        tree.add(2);
        tree.add(14);  // 2 4 7 9 11 13 14

        SortedSet tailSet = tree.tailSet(7);
        SortedSet headSet = tailSet.headSet(13);

        headSet.add(12);
        Assert.assertTrue(tree.contains(12));
        Assert.assertTrue(tree.checkInvariant());
        Assert.assertEquals(12, headSet.last());

        headSet.remove(9);
        Assert.assertFalse(tree.contains(9));
        Assert.assertTrue(tree.checkInvariant());

        try{
            headSet.remove(2);
        }catch (IllegalArgumentException e){
            System.out.println("TailSetHeadSet, remove(2) : IllegalArgumentException");
        }

        try{
            headSet.add(15);
        }catch (IllegalArgumentException e){
            System.out.println("TailSetTailSet, add(15) : IllegalArgumentException");
        }
    }

    @Test
    public void tailSetIterator(){
        Tree tree = new Tree<Integer>();
        tree.add(1);
        tree.add(2);
        tree.add(12);
        tree.add(17);
        tree.add(15);
        tree.add(11);
        tree.add(3);

        SortedSet tailSet = tree.tailSet(11);
        Iterator iterator = tailSet.iterator();

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(11, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(12, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(15, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(17, iterator.next());
        Assert.assertFalse(iterator.hasNext());

        try{
            iterator.next();
        }catch (NoSuchElementException e){
            System.out.println("tailSetIterator test: NoSuchElementException");
        }
    }
}