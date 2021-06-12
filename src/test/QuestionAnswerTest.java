/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

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

        private Pokedex myPokedex;
        private QuestionAnswer myQA;
        private QuestionAnswer myQAP;

        /**
         * @throws java.lang.Exception
         */
        @BeforeEach
        void setUp() throws Exception {
                myPokedex = Pokedex.getInstance();
                myQA = new QuestionAnswer();
                myQAP = new QuestionAnswer(myPokedex.findPokemon(1));
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
         */
        @Test
        void testSetNewPokemon() {
                fail("Not yet implemented"); // TODO
        }

        /**
         * Test method for {@link model.QuestionAnswer#getChoices()}.
         */
        @Test
        void testGetChoices() {
                fail("Not yet implemented"); // TODO
        }

        /**
         * Test method for {@link model.QuestionAnswer#getAnswerIndex()}.
         */
        @Test
        void testGetAnswerIndex() {
                fail("Not yet implemented"); // TODO
        }

        /**
         * Test method for {@link model.QuestionAnswer#clearUsed()}.
         */
        @Test
        void testClearUsed() {
                fail("Not yet implemented"); // TODO
        }

        /**
         * Test method for {@link model.QuestionAnswer#getChoicesStr()}.
         */
        @Test
        void testGetChoicesStr() {
                fail("Not yet implemented"); // TODO
        }

}
