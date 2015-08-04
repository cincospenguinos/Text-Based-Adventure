package testing;

import static org.junit.Assert.*;

import org.junit.Assert;
import resources.*;

/**
 * Test class for Room class.
 *
 * Created by andrelafleur on 8/3/15.
 */
public class RoomTest {

    private Room room1;
    private Room room2;

    private Item item1;
    private Item item2;

    private Sentient sentient1;
    private Sentient sentient2;

    @org.junit.Before
    public void setUp() throws Exception {
        // Make a couple rooms
        room1 = new Room("A Room", "Room1", "This is a single room with no windows, doors, or other exit points, with the exception of a single portal.");
        room2 = new Room("A Room", "Room2", "This is a room with a single portal that leads to another room.");

        // Add a connection between rooms
        room1.addTwoWayConnection(Direction.WEST, room2);

        // Put some items together
        item1 = new Item("Item", "Item1", "A single little item.");
        item2 = new Item("Item", "Item2", "Another tiny little item.");

        // Create some Sentients
        sentient1 = new Sentient("Bird", "It's a bird.", false);
        sentient2 = new Sentient("Plane", "It's a plane.", true);

        // Add items to rooms
        room1.addItem(item1);

        // Let's add some extra items on the fly
        room2.addItem(new Item("MaChEtE", "Machete", "A lone machete, just chilling here."));
    }

    @org.junit.Test
    public void addItemTest1(){
        boolean flag = false;

        try {
            room1.addItem(new Item("Item", "Item3", "Another little item."));
        } catch(RuntimeException e){
            flag = true;
        }

        Assert.assertTrue(flag);
    }

    @org.junit.Test
    public void addItemTest2(){
        boolean flag = false;

        try {
            room1.addItem(new Item("iTeM", "Item3", "Another little item."));
        } catch(RuntimeException e){
            flag = true;
        }

        Assert.assertTrue(flag);
    }

    @org.junit.Test
    public void hasItemTest1(){
        Assert.assertFalse(room1.hasItem("machete"));
        Assert.assertTrue(room1.hasItem("Item"));
    }

    @org.junit.Test
    public void hasItemTest2(){
        Assert.assertFalse(room1.hasItem("Item1"));
        Assert.assertTrue(room1.hasItem("Item"));
    }

    @org.junit.Test
    public void hasItemTest3(){
        Assert.assertFalse(room1.hasItem("iTeM1"));
    }

    @org.junit.Test
    public void hasItemTest4(){
        Assert.assertTrue(room2.hasItem("machete"));
        Assert.assertTrue(room2.hasItem("MaChete"));
        Assert.assertTrue(room2.hasItem("MACHETE"));
        Assert.assertTrue(room2.hasItem("mAcHeTe"));
        Assert.assertFalse(room2.hasItem("A Machete"));
    }

    @org.junit.Test
    public void getItemTest1(){
        Item i = room1.getItem("Item");

        Assert.assertTrue(i != null);
        Assert.assertTrue(i.equals(item1));
        Assert.assertTrue(room1.getItem("Item1") == null);
    }

    @org.junit.Test
    public void getItemTest2(){
        Item i = room2.getItem("Item");

        Assert.assertTrue(i == null);

        i = room2.getItem("MacHeTe");

        Assert.assertTrue(i != null);
        Assert.assertTrue(i.getPublicName().equals("MaChEtE"));
    }

    @org.junit.Test
    public void takeItemTest1(){
        room2.addItem(item2);
        Item i = room2.takeItem("Item");

        Assert.assertTrue(i != null);
        Assert.assertTrue(i.equals(item2));
        Assert.assertFalse(room2.hasItem("IteM"));
    }
}