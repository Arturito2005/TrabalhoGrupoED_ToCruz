package TestArrayList;

import ArrayList.ArrayUnordered;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestArrayUnordered {

    private ArrayUnordered<Integer> list;

    @BeforeEach
    public void setUp() {
        list = new ArrayUnordered<Integer>();
    }

    @Test
    public void testFindValidIndex() {
        list.addToRear(10);
        list.addToRear(20);
        list.addToRear(30);

        assertEquals(10, list.find(0));
        assertEquals(20, list.find(1));
        assertEquals(30, list.find(2));
    }

    @Test
    public void testFindInvalidIndex() {
        list.addToRear(10);
        list.addToRear(20);
        list.addToRear(30);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.find(3));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.find(-1));
    }

    @Test
    public void testFindEmptyList() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.find(0));
    }

    @Test
    public void testSizeAfterAddingElements() {
        list.addToRear(10);
        list.addToRear(20);
        list.addToRear(30);

        assertEquals(3, list.size());
    }

    @Test
    public void testFindAfterAddingElements() {
        list.addToRear(10);
        list.addToRear(20);
        list.addToRear(30);

        assertEquals(10, list.find(0));
        assertEquals(20, list.find(1));
        assertEquals(30, list.find(2));
    }
}
