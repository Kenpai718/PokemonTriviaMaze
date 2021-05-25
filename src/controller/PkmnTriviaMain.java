/**
 * 
 */
package controller;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import view.PokemonGUI;


/**
 * Runs the trivia maze game and starts up GUI
 * 
 * @author AJ Downey
 * @author Ken Ahrens
 * @author Katlyn Malone
 *
 */
public class PkmnTriviaMain {

    /**
     * Runs the program and sets look and feel
     * 
     * @param theArgs Command line arguments, ignored.
     */
    public static void main(final String... theArgs) {
        /* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (final IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (final InstantiationException ex) {
            ex.printStackTrace();
        } catch (final ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                new PokemonGUI();
            }
        });
    }
}

