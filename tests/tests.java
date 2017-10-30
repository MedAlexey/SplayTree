import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class tests {

    @Test
    public void add(){
        Tree tree = new Tree<Integer>();
        tree.add(7);
        Assert.assertEquals(1,tree.size());
        Assert.assertEquals(7,tree.getRootValue());

        tree.add(9);
        Assert.assertEquals(2,tree.size());
        Assert.assertEquals(9,tree.getRootValue());
        Assert.assertEquals(7,tree.first());
        Assert.assertEquals(9,tree.last());

        tree.add(8);
        Assert.assertEquals(3,tree.size());
        Assert.assertEquals(7,tree.first());
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

        tree.find(5);
        Assert.assertEquals(5,tree.getRootValue());

        tree.find(13);
        Assert.assertEquals(13,tree.getRootValue());

        tree.find(7);
        Assert.assertEquals(7, tree.getRootValue());
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

        tree.remove(15);
        Assert.assertEquals(14, tree.getRootValue());
        Assert.assertEquals(14, tree.last());
        Assert.assertEquals(13, tree.size());

        tree.remove(4);
        Assert.assertEquals(3, tree.getRootValue());
        Assert.assertEquals(1, tree.first());
        Assert.assertEquals(14, tree.last());

    }



}


