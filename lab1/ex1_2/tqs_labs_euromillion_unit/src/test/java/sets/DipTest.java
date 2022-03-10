package sets;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import euromillions.Dip;

/**
 * @author ico0
 */
public class DipTest {

    private Dip instance;

    public DipTest() {
    }

    @BeforeEach
    public void setUp() {
        instance = new Dip(new int[]{10, 20, 30, 40, 50}, new int[]{1, 2});
    }

    @AfterEach
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testConstructorFromBadArrays() {
        // todo: instantiate a dip passing valid or invalid arrays
        // fail("constructor from bad arrays: test not implemented yet");
        Assertions.assertDoesNotThrow(()->(new Dip(new int[]{10, 20, 30, 40, 50}, new int[]{1, 2})));
        Assertions.assertThrows(IllegalArgumentException.class, ()->new Dip(new int[]{10, 20, 30, 40, 50,60}, new int[]{1, 2,3}));
    }

    @Test
    public void testFormat() {
        // note: correct the implementation of the format(), not the test...
        String result = instance.format();
        assertEquals("N[ 10 20 30 40 50] S[  1  2]", result, "format as string: formatted string not as expected. ");
    }

    @Test
    public void testStarRange(){
        for (Integer star :instance.getStarsColl()){
            assertTrue(star<=Dip.STAR_RANGE);
        }
        
    }

    @Test
    public void testNumberRange(){
        for (Integer number :instance.getNumbersColl()){
            assertTrue(number<=Dip.NUMBER_RANGE);
        }
        
    }


}