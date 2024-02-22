package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class JasonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            List<Criminal> cr = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRecords.json");
            writer.open();
            writer.write(cr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRecords.json");
            cr = reader.read();
            assertEquals(0, cr.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            List<Criminal> cr = new ArrayList<>();
            Criminal c1 = new Criminal(1,"a","01/01/0101","male");
            Criminal c2 = new Criminal(2,"b","02/02/1002","female");
            cr.add(c1);
            cr.add(c2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRecords.json");
            writer.open();
            writer.write(cr);
            writer.close();

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
