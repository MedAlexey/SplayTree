import org.junit.Assert;
import org.junit.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public class tests  {

    public static <T extends Throwable> void assertThrows(Class<T> expectedType, Runnable runnable){

        String expectedExeprion = expectedType.getTypeName();
        try {
            runnable.run();
        }catch (Throwable exeption){
            if (!exeption.toString().equals(expectedExeprion)) Assert.fail();
        }
    }

    @Test
    public void add(){
        Tree tree = new Tree<Integer>();
        tree.add(7);
        Assert.assertEquals(1,tree.size());
        Assert.assertEquals(7, tree.getRootValue());
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

        Runnable runable = () -> tree.find(100);
        assertThrows(NoSuchElementException.class, runable);
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
        Tree<Integer> tree = new Tree<>();
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

        SortedSet finalSubSet = subSet;
        Runnable runable = () -> finalSubSet.add(1);
        assertThrows(IllegalArgumentException.class, runable);

        SortedSet finalSubSet1 = subSet;
        runable = () -> finalSubSet1.remove(2);
        assertThrows(IllegalArgumentException.class, runable);


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
        Tree<Integer> tree = new Tree<>();
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

        SortedSet finalHeadSet1 = headSet;
        Runnable runable = () -> finalHeadSet1.remove(9);
        assertThrows(IllegalArgumentException.class, runable);

        SortedSet finalHeadSet = headSet;
        runable = () -> finalHeadSet.add(10);
        assertThrows(IllegalArgumentException.class, runable);

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
        Tree<Integer> tree = new Tree<>();
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

        SortedSet finalTailSet = tailSet;
        Runnable runable = () -> finalTailSet.remove(1);
        assertThrows(IllegalArgumentException.class, runable);

        SortedSet finalTailSet1 = tailSet;
        runable = () -> finalTailSet1.add(4);
        assertThrows(IllegalArgumentException.class, runable);

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
        Tree<Integer> tree = new Tree<>();
        tree.add(1);
        tree.add(2);
        tree.add(12);
        tree.add(17);
        tree.add(15);
        tree.add(11);
        tree.add(3);

        Iterator iterator = tree.iterator();
        iterator.hasNext();
        Assert.assertEquals(1, iterator.next());
        Assert.assertEquals(2, iterator.next());
        Assert.assertEquals(3, iterator.next());
        Assert.assertEquals(11, iterator.next());
        Assert.assertEquals(12, iterator.next());
        Assert.assertEquals(15, iterator.next());
        Assert.assertEquals(17, iterator.next());

        Runnable runable = () -> iterator.next();
        assertThrows(NoSuchElementException.class, runable);
    }

    @Test
    public void subSetSubSet(){
        Tree<Integer> tree = new Tree<>();
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

        Runnable runable = () -> subSetSubSet.remove(6);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> subSetSubSet.remove(4);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> subSetSubSet.remove(8);
        assertThrows(IllegalArgumentException.class, runable);

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

        runable = () -> subSetSubSet.add(3);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> subSetSubSet.add(10);
        assertThrows(IllegalArgumentException.class, runable);

        //Создание множеств, выходящих за пределы данного
        runable = () -> subSet.subSet(2,9);
        assertThrows(IndexOutOfBoundsException.class, runable);

        runable = () -> subSet.subSet(4, 12);
        assertThrows(IndexOutOfBoundsException.class, runable);
    }

    @Test
    public void subSetHeadSet(){
        Tree<Integer> tree = new Tree<>();
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

        Runnable runable = () -> headSet.remove(17);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> headSet.add(17);
        assertThrows(IllegalArgumentException.class, runable);
    }

    @Test
    public void subSetTailSet(){
        Tree<Integer> tree = new Tree<>();
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

        Runnable runable = () -> tailSet.add(4);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> tailSet.remove(20);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> subSet.tailSet(1);
        assertThrows(IndexOutOfBoundsException.class, runable);

        runable = () -> subSet.tailSet(17);
        assertThrows(IndexOutOfBoundsException.class, runable);
    }

    @Test
    public void subSetIterator(){
        Tree<Integer> tree = new Tree<>();
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

        Runnable runable = () -> iterator.next();
        assertThrows(NoSuchElementException.class, runable);
    }

    @Test
    public void headSetHeadSet(){
        Tree<Integer> tree = new Tree<>();
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

        Runnable runable = () -> headSetHeadSet.remove(11);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> headSetHeadSet.add(15);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> headSetHeadSet.remove(4);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> headSet.headSet(17);
        assertThrows(IndexOutOfBoundsException.class, runable);

    }

    @Test
    public void headSetSubSet(){
        Tree<Integer> tree = new Tree<>();
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

        Runnable runable = () -> subSet.remove(11);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> subSet.add(3);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> subSet.remove(7);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> headSet.subSet(2,13);
        assertThrows(IndexOutOfBoundsException.class, runable);
    }

    @Test
    public void headSetTailSet(){
        Tree<Integer> tree = new Tree<>();
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

        Runnable runable = () -> tailSet.remove(14);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> tailSet.add(1);
        assertThrows(IllegalArgumentException.class, runable);

    }

    @Test
    public void headSetIterator(){
        Tree<Integer> tree = new Tree<>();
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

        Runnable runable = () -> iterator.next();
        assertThrows(NoSuchElementException.class, runable);
    }

    @Test
    public void tailSetTailSet(){
        Tree<Integer> tree = new Tree<>();
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

        Runnable runable = () -> tailSetTailSet.add(5);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> tailSetTailSet.add(17);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> tailSetTailSet.remove(12);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> tailSet.tailSet(3);
        assertThrows(IndexOutOfBoundsException.class, runable);
    }

    @Test
    public void tailSetSubSet(){
        Tree<Integer> tree = new Tree<>();
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

        Runnable runable = () -> subSet.add(17);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> subSet.remove(2);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> subSet.remove(11);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> tailSet.subSet(4,11);
        assertThrows(IndexOutOfBoundsException.class, runable);
    }

    @Test
    public void tailSetHeadSet(){
        Tree<Integer> tree = new Tree<>();
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

        Runnable runable = () -> headSet.remove(2);
        assertThrows(IllegalArgumentException.class, runable);

        runable = () -> headSet.remove(2);
        assertThrows(IllegalArgumentException.class, runable);
    }

    @Test
    public void tailSetIterator()  {
        Tree<Integer> tree = new Tree<>();
        tree.add(1);
        tree.add(2);
        tree.add(12);
        tree.add(17);
        tree.add(15);
        tree.add(11);
        tree.add(3);

        SortedSet tailSet = tree.tailSet(11);
        Iterator iterator = tailSet.iterator();

        Assert.assertTrue(iterator.hasNext());            //find net переходит дальше
        Assert.assertEquals(11, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(12, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(15, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(17, iterator.next());
        Assert.assertFalse(iterator.hasNext());

        Runnable runable = iterator::next;
        assertThrows(NoSuchElementException.class, runable);
    }
}