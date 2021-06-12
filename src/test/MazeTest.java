package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.InvalidMovementException;
import model.Maze;
import model.Pokemon;
import model.Room;

class MazeTest {

        private static final int DEFAULT_SIZE = 5;

        private static final int START = 0;

        private static final int[] STARTARR = new int[] { START, START };

        private static final int[] RANDOM_LOCAL = new int[] { 2, 3 };

        private static final int[] BAD_LOCAL = new int[] { -1, 5 };

        private Maze myMaze;

        @BeforeEach
        void setUp() throws Exception {
                myMaze = Maze.getInstance();
        }

        @Test
        void testGetInstance() {
                assertNotNull(myMaze);
                // Test that singleton pattern is working
                final Maze newMaze = Maze.getInstance();
                assertSame(myMaze, newMaze, "Singleton is not working correctly");
        }

        @Test
        void testHasLost() {
                final int finalRoom = DEFAULT_SIZE - 1;
                assertFalse(myMaze.hasLost());
                myMaze.getMatrix()[finalRoom][finalRoom].setEntry(false);
                myMaze.setLoseCondition();
                assertTrue(myMaze.hasLost());
                myMaze.reset();

        }

        @Test
        void testIsWinCondition() {
                assertFalse(myMaze.hasWon());
                final int[] pos = new int[] { DEFAULT_SIZE - 1, DEFAULT_SIZE - 1 };
                try {
                        myMaze.setPlayerLocation(pos);
                } catch (final InvalidMovementException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                myMaze.setWinCondition();
                assertTrue(myMaze.hasWon());
                myMaze.reset();
        }

        @Test
        void testGetWinLocation() {
                final int[] exp = new int[] { DEFAULT_SIZE - 1, DEFAULT_SIZE - 1 };
                assertEquals(exp[0], myMaze.getWinLocation()[0],
                                "Win location is incorrect");
        }

        @Test
        void testGetPlayerLocation() {
                int[] curr = myMaze.getPlayerLocation();
                for (int i = 0; i < RANDOM_LOCAL.length; i++) {
                        assertEquals(START, curr[i]);
                }
                try {
                        myMaze.setPlayerLocation(RANDOM_LOCAL);
                } catch (final InvalidMovementException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                curr = myMaze.getPlayerLocation();
                for (int i = 0; i < RANDOM_LOCAL.length; i++) {
                        assertEquals(RANDOM_LOCAL[i], curr[i]);
                }
                myMaze.reset();

        }

        @Test
        void testSetPlayerLocation() {
                try {
                        myMaze.setPlayerLocation(RANDOM_LOCAL);
                } catch (final InvalidMovementException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                assertArrayEquals(RANDOM_LOCAL, myMaze.getPlayerLocation(),
                                "Location was not changed correctly");
                myMaze.reset();
        }

        @Test
        void testSetPlayerLocationBad() {
                assertThrows(InvalidMovementException.class,
                                () -> myMaze.setPlayerLocation(BAD_LOCAL),
                                "Bad location did not throw exception");
        }

        @Test
        void testGetCurrRoom() {
                final int[] curr = myMaze.getPlayerLocation();
                final Room exp = myMaze.getCurrRoom();
                final Room[][] matrix = myMaze.getMatrix();
                final Room act = matrix[curr[0]][curr[1]];

                assertEquals(exp, act,
                                "Get current room did not return the correct room");
        }

        @Test
        void testGetAttemptRoom() {
                final int[] curr = myMaze.getAttemptedLocation();
                final Room exp = myMaze.getAttemptRoom();
                final Room[][] matrix = myMaze.getMatrix();
                final Room act = matrix[curr[0]][curr[1]];

                assertEquals(exp, act,
                                "Get atttempt room did not return the correct room");
        }

        @Test
        void testGetAttemptedLocation() {
                int[] curr = myMaze.getAttemptedLocation();
                for (int i = 0; i < RANDOM_LOCAL.length; i++) {
                        assertEquals(START, curr[i]);
                }
                try {
                        myMaze.setAttemptLocation(RANDOM_LOCAL);
                } catch (final InvalidMovementException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                curr = myMaze.getAttemptedLocation();
                for (int i = 0; i < RANDOM_LOCAL.length; i++) {
                        assertEquals(RANDOM_LOCAL[i], curr[i]);
                }
                myMaze.reset();
        }

        @Test
        void testSetAttemptLocation() {
                try {
                        myMaze.setAttemptLocation(RANDOM_LOCAL);
                } catch (final InvalidMovementException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                assertArrayEquals(RANDOM_LOCAL, myMaze.getAttemptedLocation(),
                                "Location was not changed correctly");
                myMaze.reset();
        }

        @Test
        void testSetAttemptLocationBad() {
                assertThrows(InvalidMovementException.class,
                                () -> myMaze.setAttemptLocation(BAD_LOCAL),
                                "Bad location did not throw exception");
        }

        @Test
        void testHasNotMoved() {
                assertTrue(myMaze.hasNotMoved());

                try {
                        myMaze.setAttemptLocation(RANDOM_LOCAL);
                } catch (final InvalidMovementException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                assertFalse(myMaze.hasNotMoved());
                myMaze.reset();

        }

        @Test
        void testGetRoom() {
                Room exp = null;
                try {
                        exp = myMaze.getRoom(RANDOM_LOCAL[0], RANDOM_LOCAL[1]);
                } catch (final Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                final Room[][] matrix = myMaze.getMatrix();
                final Room act = matrix[RANDOM_LOCAL[0]][RANDOM_LOCAL[1]];
                if (exp != null) {
                        assertEquals(exp, act,
                                        "Get atttempt room did not return the correct room");
                } else {
                        fail("Expected room never initialized");
                }
        }

        void testGetRoomBad() {
                assertThrows(Exception.class,
                                () -> myMaze.getRoom(BAD_LOCAL[0], BAD_LOCAL[1]),
                                "Bad location did not throw exception");
        }

        @Test
        void testSetRoomInMatrix() {
                final Room act = new Room(0);
                final Room[][] initMatrix = myMaze.getMatrix();
                myMaze.setRoomInMatrix(act, RANDOM_LOCAL[0], RANDOM_LOCAL[1]);
                assertEquals(act, initMatrix[RANDOM_LOCAL[0]][RANDOM_LOCAL[1]],
                                "Matrix was not changed");

        }

        @Test
        void testGetPokemonList() {
                final List<Room> roomList = Arrays.stream(myMaze.getMatrix()) // 'array'
                                                                              // is
                                                                              // two-dimensional
                                .flatMap(Arrays::stream).collect(Collectors.toList());
                final List<Pokemon> exp = new ArrayList<Pokemon>();
                for (final Room room : roomList) {
                        exp.add(room.getPokemon());
                }
                final ArrayList<Pokemon> actual = myMaze.getPokemonList();
                for (int i = 0; i < exp.size(); i++) {
                        assertEquals(exp.get(i).getName(), actual.get(i).getName(),
                                        "Get Pokemon List returned incorrectly");
                }
        }

        @Test
        void testGetPokemonListDuplicates() {
                final ArrayList<Pokemon> list = myMaze.getPokemonList();
                final HashSet<String> set = new HashSet<String>();
                for (final Pokemon pkmn : list) {
                        set.add(pkmn.getName());
                }

                assertEquals(set.size(), list.size(), "There are Duplicates in the Maze");
        }

        @Test
        void testGetPokemonListDuplicates10x10() {
                final int newSize = DEFAULT_SIZE + 5;
                myMaze.setMatrixSize(newSize, newSize);
                final ArrayList<Pokemon> list = myMaze.getPokemonList();
                final HashSet<String> set = new HashSet<String>();
                for (final Pokemon pkmn : list) {
                        set.add(pkmn.getName());
                }

                assertEquals(set.size(), list.size(), "There are Duplicates in the Maze");

        }

        @Test
        void testSetMatrix() {
                final Room[][] initMatrix = myMaze.getMatrix();
                myMaze.reset();
                myMaze.setMatrix(initMatrix);
                for (int i = 0; i < myMaze.getRows(); i++) {
                        for (int j = 0; j < myMaze.getCols(); j++) {
                                assertEquals(initMatrix[i][j], myMaze
                                                .getMatrix()[i][j], "Set matrix did"
                                                                + "did not set the matrix correctly");
                        }
                }

        }

        @Test
        void testGetRows() {
                assertEquals(DEFAULT_SIZE, myMaze.getRows());
        }

        @Test
        void testGetCols() {
                assertEquals(DEFAULT_SIZE, myMaze.getCols());
        }

        @Test
        void testIsAtStart() {
                assertTrue(myMaze.isAtStart());

                try {
                        myMaze.setAttemptLocation(RANDOM_LOCAL);
                        assertFalse(myMaze.isAtStart());
                        myMaze.setPlayerLocation(RANDOM_LOCAL);
                        assertFalse(myMaze.isAtStart());
                } catch (final InvalidMovementException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                myMaze.reset();

        }

        @Test
        void testSetMatrixSize() {
                myMaze.setMatrixSize(DEFAULT_SIZE - 1, DEFAULT_SIZE - 1);
                assertEquals(DEFAULT_SIZE - 1, myMaze.getRows());
                assertEquals(DEFAULT_SIZE - 1, myMaze.getCols());
                myMaze.setMatrixSize(DEFAULT_SIZE, DEFAULT_SIZE);
        }
        
        @Test
        void testReset() throws InvalidMovementException {
                final Room[][] initMatrix = myMaze.getMatrix();
                
                
                final List<Pokemon> initList = myMaze.getPokemonList();
                
                myMaze.setPlayerLocation(RANDOM_LOCAL);
                myMaze.setAttemptLocation(RANDOM_LOCAL);
                
                final int finalRoom = DEFAULT_SIZE - 1;
                assertFalse(myMaze.hasLost());
                myMaze.getMatrix()[finalRoom][finalRoom].setEntry(false);
                myMaze.setLoseCondition();
                assertTrue(myMaze.hasLost(), "Lose condition never set");
                
                final int[] pos = new int[] { DEFAULT_SIZE - 1, DEFAULT_SIZE - 1 };
                myMaze.setPlayerLocation(pos);
                myMaze.setWinCondition();
                assertTrue(myMaze.hasWon(), "Maze win condition never set");
                
                myMaze.reset();
                assertNotEquals(initMatrix, myMaze.getMatrix(), "Matrix was not reset");;
                assertEquals(START, myMaze.getPlayerLocation()[0]);
                assertEquals(START, myMaze.getAttemptedLocation()[0]);
                assertFalse(myMaze.hasLost(), "Lose condtion was not reset");
                assertFalse(myMaze.hasWon(), "Maze win condition was not reset");

        }
        

}
