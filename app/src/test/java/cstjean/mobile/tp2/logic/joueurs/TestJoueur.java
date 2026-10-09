package cstjean.mobile.tp2.logic.joueurs;

import junit.framework.TestCase;

/**
 * Classe de test pour les méthodes d'un pion.
 */
public class TestJoueur extends TestCase {
    /**
     * Test de la création d'un joueur.
     */
    public void testCreer() {
        Joueur joueur = new Joueur();
        assertFalse(joueur.getStatusTour());
        joueur.setStatusTour(true);
        assertTrue(joueur.getStatusTour());
    }

    /**
     * Test pour setter un nom.
     */
    public void testSetNom() {
        Joueur joueur = new Joueur();
        joueur.setNom("Gwen");
        assertEquals("Gwen", joueur.getNom());
    }
}
