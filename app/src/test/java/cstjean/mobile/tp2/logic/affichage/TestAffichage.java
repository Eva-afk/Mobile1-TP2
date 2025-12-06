package cstjean.mobile.tp2.logic.affichage;

import cstjean.mobile.tp2.logic.Damier;

import junit.framework.TestCase;

/**
 * Classe qui teste l'affichage du damier.
 */
public class TestAffichage extends TestCase {

    /**
     * Affiche le damier avec les pions au bon endroit.
     * "-" pour null,
     * "P" pour un pion noir,
     * "p" pour un pion blanc.
     */
    public void testAffichage() {
        String builder = "-P-P-P-P-P\n" +
                "P-P-P-P-P-\n" +
                "-P-P-P-P-P\n" +
                "P-P-P-P-P-\n" +
                "----------\n" +
                "----------\n" +
                "-p-p-p-p-p\n" +
                "p-p-p-p-p-\n" +
                "-p-p-p-p-p\n" +
                "p-p-p-p-p-\n";
        Damier damier = new Damier();
        damier.initialiser();
        Affichage affichage = new Affichage(damier);
        assertEquals(builder, affichage.getAffichageFinal()
        );
    }
}
