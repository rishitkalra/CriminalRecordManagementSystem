package persistence;

import model.*;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            List<Criminal> cr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRecords.json");
        try {
            List<Criminal> cr = reader.read();
            assertEquals(0, cr.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

        @Test
        void testReaderGeneralWorkRoom() {
            JsonReader reader = new JsonReader("./data/testReaderGeneralRecords.json");
            try {
                List<Criminal> cr = reader.read();
                Criminal criminal1 = cr.get(0);
                Criminal criminal2 = cr.get(1);
                assertEquals(5, cr.size());
                checkCriminal(1, criminal1.getName(), cr.get(0));
                checkCriminal(2, criminal2.getName(), cr.get(1));
            } catch (IOException e) {
                fail("Couldn't read from file");
            }
        }
    }

