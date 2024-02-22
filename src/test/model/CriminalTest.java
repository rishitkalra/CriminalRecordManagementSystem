package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CriminalTest {
    private Criminal dumbledore;
    private Criminal nullCriminal;

    @BeforeEach
    void runBefore() {
        dumbledore = new Criminal(1, "Dumbledore", "03/21/1881", "Male");
        nullCriminal = new Criminal(0,null,null,null);
    }

    @Test
    void testConstructor() {
        assertEquals(1, dumbledore.getId());
        assertEquals("Dumbledore", dumbledore.getName());
        assertEquals("03/21/1881", dumbledore.getDob());
        assertEquals("Male", dumbledore.getGender());
        assertNull(dumbledore.getOffence());
        assertEquals(0, dumbledore.getReward());
    }

    @Test
    void testSetOffence() {
        dumbledore.setOffence("Burglary");
        assertEquals("Burglary", dumbledore.getOffence());
    }

    @Test
    void testSetOffenceMultiple() {
        dumbledore.setOffence("Burglary");
        dumbledore.setOffence("Trespassing");
        assertEquals("Burglary, Trespassing", dumbledore.getOffence());
    }

    @Test
    void testSetReward() {
        dumbledore.setReward(500);
        assertEquals(500, dumbledore.getReward());
    }

    @Test
    void testSetRewardNegative() {
        dumbledore.setReward(-500);
        assertEquals(-500, dumbledore.getReward());
    }

    @Test
    void testSetRewardMultiple() {
        dumbledore.setReward(500);
        dumbledore.setReward(1000);
        dumbledore.setReward(200);
        dumbledore.setReward(800);
        dumbledore.setReward(-500);
        assertEquals(2000, dumbledore.getReward());
    }

    @Test
    void testSetId() {
        nullCriminal.setId(3);
        assertEquals(3,nullCriminal.getId());
    }

    @Test
    void testSetDob() {
        nullCriminal.setDob("01/01/1001");
        assertEquals("01/01/1001", nullCriminal.getDob());
    }

    @Test
    void testSetGender() {
        nullCriminal.setGender("Female");
        assertEquals("Female",nullCriminal.getGender());
    }

    @Test
    void testSetName() {
        nullCriminal.setName("Voldemort");
        assertEquals("Voldemort",nullCriminal.getName());
    }
}