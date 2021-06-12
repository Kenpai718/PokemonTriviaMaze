/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.MissingPokemonException;
import model.Pokedex;
import model.Pokemon;
import model.QuestionAnswer;

/**
 * @author ajdow
 *
 */
class QuestionAnswerTest {
        
        /*
         * 
         */
        private static final int TESTS = 50;
        
        /*
         * 
         */
        private Pokemon myPoke;
        
        /*
         * 
         */
        private Pokedex myPokedex;
        
        /*
         * 
         */
        private QuestionAnswer myQA;
        
        /*
         * 
         */
        private QuestionAnswer myQAP;

        private QuestionAnswer myQAPQ;

        private ArrayList<String> myTest;

        /**
         * @throws java.lang.Exception
         */
        @BeforeEach
        void setUp() throws Exception {
                myPokedex = Pokedex.getInstance();
                myPoke = myPokedex.findPokemon(1);
                myQA = new QuestionAnswer();
                myQAP = new QuestionAnswer(myPoke);
                myTest = new ArrayList<String>();
                myTest.add("Bob");
                myTest.add(myPoke.getName());
                myTest.add("Smith");
                myTest.add("John");
                myQAPQ = new QuestionAnswer(myPoke, myTest);
        }

        /**
         * Test method for {@link model.QuestionAnswer#QuestionAnswer()}.
         */
        @Test
        void testQuestionAnswer() {
                assertNotNull(myQA, "QA not initialized");
        }

        /**
         * Test method for {@link model.QuestionAnswer#QuestionAnswer(model.Pokemon)}.
         */
        @Test
        void testQuestionAnswerPokemon() {
                assertNotNull(myQAP, "QA (Pokemon) not initialized");
        }


        /**
         * Test method for {@link model.QuestionAnswer#getAnswer()}.
         * @throws MissingPokemonException 
         */
        @Test
        void testGetAnswer() throws MissingPokemonException {
                final Pokemon exp = myPokedex.findPokemon(1);
                assertEquals(exp.getName(), myQAP.getAnswer(), "Get pokemon returned the wrong"
                                + "one"); // TODO
        }

        /**
         * Test method for {@link model.QuestionAnswer#getPokemon()}.
         * @throws MissingPokemonException 
         */
        @Test
        void testGetPokemon() throws MissingPokemonException {
                final Pokemon exp = myPokedex.findPokemon(1);
                assertEquals(exp.getName(), myQAP.getPokemon().getName(), "Get pokemon returned the wrong"
                                + "one");
        }

        /**
         * Test method for {@link model.QuestionAnswer#setNewPokemon(model.Pokemon)}.
         * @throws MissingPokemonException 
         */
        @Test
        void testSetNewPokemon() throws MissingPokemonException {
                final Pokemon first = myPokedex.findPokemon(1);
                final Pokemon exp = myPokedex.findPokemon(2);
                myQAP.setNewPokemon(exp);
                assertNotEquals(first.getName(), myQAP.getPokemon().getName(), "Get pokemon returned the wrong"
                                + "one");
                assertEquals(exp.getName(), myQAP.getPokemon().getName(), "Get pokemon returned the wrong"
                                + "one");
        }

        /**
         * Test method for {@link model.QuestionAnswer#getChoices()}.
         */
        @Test
        void testGetChoices() {
                final String list = myQA.getChoicesStr();
                final String[] s = list.split("[\\n\\s]");
                final ArrayList<String> exp = new ArrayList<String>();
                for (int i = 0; i < s.length; i++) {
                        if (i % 2 == 1)
                                exp.add(s[i]);
                }
                assertEquals(exp, myQA.getChoices(), "Choices are not equal"); 
        }

        /**
         * Test method for {@link model.QuestionAnswer#getAnswerIndex()}.
         */
        @Test
        void testGetAnswerIndex() {
                
                
                boolean done = false;
                int index = 0;
                for (int i = 0; i < myTest.size() && !done; i++) {
                        if (myPoke.getName().equals(myTest.get(i))) {
                                done = true;
                                index = i;
                        }
                }
                assertEquals(index, myQAPQ.getAnswerIndex());
        }

        /**
         * Test method for {@link model.QuestionAnswer#clearUsed()}.
         */
        @Test
        void testClearUsed() {
                for (int i = 0; i < TESTS; i++) {
                        myQA = new QuestionAnswer();
                }
                assertFalse(QuestionAnswer.getUSED().isEmpty(), "USED list was never filled up");
                myQA.clearUsed();
                assertTrue(QuestionAnswer.getUSED().isEmpty(),"USED list was never cleared"); 
        }

        /**
         * Test method for {@link model.QuestionAnswer#getChoicesStr()}.
         */
        @Test
        void testGetChoicesStr() {
                final String exp = "1) Bob\n"
                                + "2) Bulbasaur\n"
                                + "3) Smith\n"
                                + "4) John\n";
                assertEquals(exp, myQAPQ.getChoicesStr(), "Choices String not equal");
        }

}
