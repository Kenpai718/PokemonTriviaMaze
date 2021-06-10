package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Maze;
import model.Pokemon;

class MazeTest {
        
        private static final int DEFAULT_ROWS = 5;
        
        private static final int DEFAULT_COLS = 5;
        private Maze myMaze;

        @BeforeEach
        void setUp() throws Exception {
                myMaze = Maze.getInstance();
        }

        @Test
        void testGetInstance() {
                assertNotNull(myMaze);
        }

        @Test
        void testIsWinCondition() {
                assertFalse(myMaze.hasWon());
                final int[] pos = new int[] {DEFAULT_ROWS - 1, DEFAULT_COLS - 1};
                myMaze.setWinCondition();
                myMaze.setPlayerLocation(pos);
                assertTrue(myMaze.hasWon());
        }

        @Test
        void testGetWinLocation() {
                fail("Not yet implemented");
        }

        @Test
        void testGetPlayerLocation() {
                fail("Not yet implemented");
        }

        @Test
        void testSetPlayerLocation() {
                fail("Not yet implemented");
        }

        @Test
        void testGetCurrRoom() {
                fail("Not yet implemented");
        }

        @Test
        void testGetAttemptRoom() {
                fail("Not yet implemented");
        }

        @Test
        void testGetAttemptedLocation() {
                fail("Not yet implemented");
        }

        @Test
        void testSetAttemptLocation() {
                fail("Not yet implemented");
        }

        @Test
        void testHasNotMoved() {
                fail("Not yet implemented");
        }

        @Test
        void testGetRoom() {
                fail("Not yet implemented");
        }

        @Test
        void testSetRoomInMatrix() {
                fail("Not yet implemented");
        }

        @Test
        void testGetPokemonList() {
                fail("Not yet implemented");
        }
        
        @Test
        void testGetPokemonListDuplicates() {
                final ArrayList<Pokemon> list = myMaze.getPokemonList();
                final HashSet<String> set = new HashSet<String>();
                for(final Pokemon pkmn : list) {
                        set.add(pkmn.getName());
                }
                
                assertEquals(set.size(), list.size(), "There are Duplicates in the Maze");
        }
        
        @Test
        void testGetPokemonListDuplicates10x10() {
                myMaze.setMatrixSize(10, 10);
                final ArrayList<Pokemon> list = myMaze.getPokemonList();
                final HashSet<String> set = new HashSet<String>();
                for(final Pokemon pkmn : list) {
                        set.add(pkmn.getName());
                }
                
                assertEquals(set.size(), list.size(), "There are Duplicates in the Maze");
        }

        @Test
        void testGetMatrix() {
                fail("Not yet implemented");
        }

        @Test
        void testSetMatrix() {
                fail("Not yet implemented");
        }

        @Test
        void testGetRows() {
                assertEquals(DEFAULT_ROWS, myMaze.getRows() );
        }

        @Test
        void testGetCols() {
                assertEquals(DEFAULT_COLS, myMaze.getCols());
        }

        @Test
        void testIsAtStart() {
                assertTrue(myMaze.isAtStart());
                final int[] pos = new int[] {2,3};
                myMaze.setPlayerLocation(pos);
                assertFalse(myMaze.isAtStart());
        }

        @Test
        void testSetMatrixSize() {
                fail("Not yet implemented");
        }

        @Test
        void testReset() {
                fail("Not yet implemented");
        }


}
