package dk.eamv.ferrari;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MyClassTest {
    @Test
    public void testAdd() {
        int result = MyClass.add(2, 3);
        assertEquals(5, result);
    }
}