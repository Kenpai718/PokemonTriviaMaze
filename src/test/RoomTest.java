/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Room;

/**
 * @author ajdow
 *
 */
class RoomTest {

        private Room myRoom;

        /**
         * @throws java.lang.Exception
         */
        @BeforeEach
        void setUp() throws Exception {
                myRoom = new Room(0);
        }

        /**
         * Test method for {@link model.Room#Room(int)}.
         */
        @Test
        void testRoomInt() {
                assertNotNull(myRoom, "Room not initialized");
                assertEquals("1", myRoom.toString(), "Room not initialized - toString");
        }

//        /**
//         * Test method for {@link model.Room#Room(int, model.Pokemon)}.
//         */
//        @Test
//        void testRoomIntPokemon() {
//                fail("Not yet implemented"); // TODO
//        }

        /**
         * Test method for {@link model.Room#setEntry(java.lang.Boolean)}.
         */
        @Test
        void testSetEntry() {
                assertTrue(myRoom.canEnter(), "Initially true");
                myRoom.setEntry(false);
                assertFalse(myRoom.canEnter(), "Not set to false");
        }

        /**
         * Test method for {@link model.Room#setVisited(java.lang.Boolean)}.
         */
        @Test
        void testSetVisited() {
                assertFalse(myRoom.hasVisited(), "Initially false");
                myRoom.setVisited(true);
                assertTrue(myRoom.hasVisited(), "Not set to true");
        }

        /**
         * Test method for {@link model.Room#canEnter()}.
         */
        @Test
        void testCanEnter() {
                assertTrue(myRoom.canEnter(), "Initially true");
                myRoom.setEntry(false);
                assertFalse(myRoom.canEnter(), "Not set to false");
        }

        /**
         * Test method for {@link model.Room#setPlayer(java.lang.Boolean)}.
         */
        @Test
        void testSetPlayer() {
                
                assertFalse(myRoom.isPlayerHere(), "Initially false");
                myRoom.setPlayer(true);
                assertTrue(myRoom.isPlayerHere(), "Not set to true");
        }

        /**
         * Test method for {@link model.Room#isPlayerHere()}.
         */
        @Test
        void testIsPlayerHere() {
                assertFalse(myRoom.isPlayerHere(), "Initially false");
                myRoom.setPlayer(true);
                assertTrue(myRoom.isPlayerHere(), "Not set to true");
        }

        /**
         * Test method for {@link model.Room#hasVisited()}.
         */
        @Test
        void testHasVisited() {
                assertFalse(myRoom.hasVisited(), "Initially false");
                myRoom.setVisited(true);
                assertTrue(myRoom.hasVisited(), "Not set to true");
        }

        /**
         * Test method for {@link model.Room#getRoomName()}.
         */
        @Test
        void testGetRoomName() {
                assertEquals("1", myRoom.getRoomName());
        }

        /**
         * Test method for {@link model.Room#toString()}.
         */
        @Test
        void testToString() {
                assertEquals("1", myRoom.toString());
        }

}
