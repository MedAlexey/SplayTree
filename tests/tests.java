import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class tests {

    private Tree tree = new Tree();

    @Before
    public void before() {
        tree.add(3);
        tree.add(2);
        tree.add(7);
        tree.add(5);
        tree.add(4);
    }


    @Test
    public void findTest(){
        tree.find(2);
        Assert.assertEquals(2, tree.getRootValue());
        tree.find(7);
        Assert.assertEquals(7,tree.getRootValue());
    }

    @Test
    public void removeTest(){
        tree.remove(4);
        Assert.assertEquals(3, tree.getRootValue());

    }
}


