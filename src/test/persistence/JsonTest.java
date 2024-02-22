package persistence;

import model.Offence;
import model.Criminal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkCriminal(int id, String name, Criminal criminal) {
        assertEquals(name, criminal.getName());
        assertEquals(id, criminal.getId());
    }
}
