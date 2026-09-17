import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//barebones functional test
class LinkedTreeTest {
    LinkedTree<Integer> intTree= new LinkedTree<>(0);

    @BeforeEach
    void setup(){
        intTree.addChild(101);
        intTree.addChild(102);
        intTree.addChild(103);
        intTree.addChild(104);
        intTree.down(0);
        intTree.addChild(201);
        intTree.addChild(202);
        intTree.down(1);
        intTree.addChild(301);
        intTree.reset();
        intTree.down(2);
        intTree.addChild(203);
        intTree.addChild(204);
        intTree.reset();
    }



    @Test
    void getCurrent() {
        assertEquals(0,intTree.getCurrent());
        intTree.down(2);
        assertEquals(103,intTree.getCurrent());
        intTree.down(1);
        assertEquals(204,intTree.getCurrent());
        intTree.up();
        assertEquals(103,intTree.getCurrent());
    }

    @Test
    void reroot() {
        intTree.down(0);
        intTree.reroot();
        assertThrows(IllegalStateException.class,()->intTree.up());
    }

    @Test
    void replace() {
        intTree.down(2);
        intTree.replace(12345);
        intTree.up();
        intTree.down(2);
        assertEquals(12345,intTree.getCurrent());
        intTree.down(0);
        intTree.up();
        assertEquals(12345,intTree.getCurrent());
    }

    @Test
    void getChildren() {
        intTree.down(0);
        intTree.down(0);
        List<Integer> children = new ArrayList<>();
        children.add(intTree.getCurrent());
        intTree.up();
        intTree.down(1);
        children.add(intTree.getCurrent());
        intTree.up();
        assertEquals(children,intTree.getChildren());
    }
    @Test
    void remove() {
        intTree.down(2);
        intTree.remove();
        intTree.down(2);
        assertFalse(intTree.hasChildren());
        intTree.reset();
        assertThrows(IllegalStateException.class,()->intTree.remove());
    }
}