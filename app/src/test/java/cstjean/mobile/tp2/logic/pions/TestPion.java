package cstjean.mobile.tp2.logic.pions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Classe de test unitaire pour la classe Pion.
 * Vérifie la création et la couleur par défaut d’un pion.
 * Utilise JUnit 3 (extends TestCase).
 *
 * @author Eva Beaulieu
 * @author Chloé Nguedia
 */

public class TestPion {

    /**
     * Teste la création de pions avec et sans paramètre.
     * Vérifie que la couleur est bien définie.
     * - Pion blanc avec "blanc".
     * - Pion noir avec "noir".
     * - Pion par défaut est blanc.
     */
    @Test
    public void testCreer() {
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        Pion pionVide = new Pion();
        assertEquals('p', pionBlanc.getRepresentation());
        assertEquals('P', pionNoir.getRepresentation());
        assertEquals('p', pionVide.getRepresentation());

        assertEquals(Pion.Couleur.BLANC, pionBlanc.getCouleur());
        assertEquals(Pion.Couleur.NOIR, pionNoir.getCouleur());
        assertEquals(Pion.Couleur.BLANC, pionVide.getCouleur());
    }
}
