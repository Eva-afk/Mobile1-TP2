package cstjean.mobile.tp2.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cstjean.mobile.tp2.logic.pions.Pion;
import org.junit.Before;
import org.junit.Test;

/**
 * Test de la fin de partie.
 *
 * @author Eva Beaulieu
 * @author Chloé Nguedia
 */

public class TestFinPartie {

    private JeuDame jeu;

    @Before
    public void setUp() {
        jeu = JeuDame.getInstance();
        jeu.reset();
    }

    /**
     * Si aucun pion blanc, noir gagne.
     */
    @Test
    public void testFinPartieBlancPlusDePions() {
        jeu.getDamier().initialiser();
        supprimerPions(Pion.Couleur.BLANC);

        jeu.verifierFinPartie();

        assertTrue(jeu.isFinPartie());
        assertEquals("noir", jeu.getGagnant());
    }

    /**
     * Si aucun pion noir,  blanc gagne.
     */
    @Test
    public void testFinPartieNoirPlusDePions() {
        jeu.getDamier().initialiser();
        supprimerPions(Pion.Couleur.NOIR);

        jeu.verifierFinPartie();

        assertTrue(jeu.isFinPartie());
        assertEquals("blanc", jeu.getGagnant());
    }

    /**
     * joueur blanc ne peut plus jouer, noir gagne.
     */
    @Test
    public void testFinPartieBlancBloque() {
        jeu.getDamier().initialiser();
        supprimerTousLesPionsExcept(Pion.Couleur.NOIR);

        jeu.verifierFinPartie();

        assertTrue(jeu.isFinPartie());
        assertEquals("noir", jeu.getGagnant());
    }

    /**
     * joueur noir ne peut plus jouer → blanc gagne.
     */
    @Test
    public void testFinPartieNoirBloque() {
        jeu.getDamier().initialiser();
        supprimerTousLesPionsExcept(Pion.Couleur.BLANC);

        jeu.verifierFinPartie();

        assertTrue(jeu.isFinPartie());
        assertEquals("blanc", jeu.getGagnant());
    }

    /**
     * Pas de fin de partie si les deux joueurs peuvent jouer.
     */
    @Test
    public void testPartieContinue() {
        jeu.reset();
        jeu.getDamier().initialiser();

        jeu.verifierFinPartie();

        assertFalse(jeu.isFinPartie());
        assertNull(jeu.getGagnant());
    }

    private void supprimerPions(Pion.Couleur couleur) {
        for (int pos = 1; pos <= 50; pos++) {
            Pion p = jeu.getDamier().getPion(pos);
            if (p != null && p.getCouleur() == couleur) {
                jeu.getDamier().ajouterPion(pos, null);
            }
        }
    }

    private void supprimerTousLesPionsExcept(Pion.Couleur couleur) {
        for (int pos = 1; pos <= 50; pos++) {
            Pion p = jeu.getDamier().getPion(pos);
            if (p != null && p.getCouleur() != couleur) {
                jeu.getDamier().ajouterPion(pos, null);
            }
        }
    }

}
