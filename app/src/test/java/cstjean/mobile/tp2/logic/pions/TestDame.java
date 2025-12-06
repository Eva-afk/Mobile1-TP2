package cstjean.mobile.tp2.logic.pions;

import static org.junit.Assert.assertEquals;

/**
 * Classe de teste pour la classe dame.
 */
public class TestDame extends TestPion {
    /**
     * Teste la création de dames avec et sans paramètre ainsi que la représentation des couleurs de dames.
     * Vérifie que la couleur est bien définie.
     * - Dame blanche avec "blanc".
     * - Dame noire avec "noir".
     * - Dame par défaut est blanche.
     */
    @Override
    public void testCreer() {
        Dame dameBlanc = new Dame(Dame.Couleur.BLANC);
        Dame dameNoir = new Dame(Dame.Couleur.NOIR);
        Dame dameVide = new Dame();
        assertEquals('d', dameBlanc.getRepresentation());
        assertEquals('D', dameNoir.getRepresentation());
        assertEquals('d', dameVide.getRepresentation());

        assertEquals(Dame.Couleur.BLANC, dameBlanc.getCouleur());
        assertEquals(Dame.Couleur.NOIR, dameNoir.getCouleur());
        assertEquals(Dame.Couleur.BLANC, dameVide.getCouleur());
    }
}