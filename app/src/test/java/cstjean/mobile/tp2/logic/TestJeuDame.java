package cstjean.mobile.tp2.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cstjean.mobile.tp2.logic.pions.Dame;
import cstjean.mobile.tp2.logic.pions.Pion;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests pour les actions lors du jeu de dames.
 */
public class TestJeuDame {

    /**
     * Jeu de Dame qui gère les actions du jeu.
     */
    private JeuDame jeuDame;
    /**
     * Pion de couleur blanche.
     */
    private Pion pionBlanc;

    /**
     * Pion de couleur noire.
     */
    private Pion pionNoir;

    /**
     * historique des actions lors du jeu.
     */
    private final Historique historique = Historique.getInstance();

    /**
     * Actions à faire avant chaque tests.
     */
    @Before
    public void setUp() {
        pionBlanc = new Pion(Pion.Couleur.BLANC);
        pionNoir = new Pion(Pion.Couleur.NOIR);
        jeuDame = JeuDame.getInstance();
        jeuDame.getDamier().initialiser();
    }

    @Test
    public void testVerifierChangementDame() {
        jeuDame.reset();
        historique.reset();
        jeuDame.getDamier().initialiserVide();

        jeuDame.getDamier().ajouterPion(50, pionNoir);
        jeuDame.getDamier().ajouterPion(1, pionBlanc);

        jeuDame.verifierChangementDame(50);
        jeuDame.verifierChangementDame(1);
        assertEquals('D', jeuDame.getDamier().getPion(50).getRepresentation());
        assertEquals('d', jeuDame.getDamier().getPion(1).getRepresentation());
    }


    /**
     * Test pour la vérification des limites de déplacements possibles selon la position du pion.
     *
     */
    @Test
    public void testDeplacementsPossiblesPion() {
        jeuDame.reset();
        historique.reset();
        jeuDame.getDamier().initialiserVide();
        jeuDame.getDamier().ajouterPion(45, pionBlanc);
        LinkedList<Integer> mouvementDroiteBlanc =
                jeuDame.deplacementPossiblesPion(45, jeuDame.getDamier().getPion(45));
        assertEquals(1, mouvementDroiteBlanc.size());
        jeuDame.changerTour();

        jeuDame.getDamier().ajouterPion(5, pionNoir);
        LinkedList<Integer> mouvementDroiteNoir =
                jeuDame.deplacementPossiblesPion(5, jeuDame.getDamier().getPion(5));
        assertEquals((Integer) 10, mouvementDroiteNoir.getFirst());
        jeuDame.changerTour();

        jeuDame.getDamier().ajouterPion(34, pionBlanc);
        LinkedList<Integer> mouvementCentreBlanc =
                jeuDame.deplacementPossiblesPion(34, jeuDame.getDamier().getPion(34));
        assertEquals((Integer) 29, mouvementCentreBlanc.get(0));
        assertEquals((Integer) 30, mouvementCentreBlanc.get(1));
        jeuDame.changerTour();

        jeuDame.getDamier().ajouterPion(20, pionNoir);
        LinkedList<Integer> mouvementCentreNoir =
                jeuDame.deplacementPossiblesPion(20, jeuDame.getDamier().getPion(20));
        assertEquals((Integer) 24, mouvementCentreNoir.get(0));
        assertEquals((Integer) 25, mouvementCentreNoir.get(1));
        jeuDame.changerTour();

        jeuDame.getDamier().ajouterPion(36, pionBlanc);
        LinkedList<Integer> mouvementGaucheBlanc =
                jeuDame.deplacementPossiblesPion(36, jeuDame.getDamier().getPion(36));
        assertEquals((Integer) 31, mouvementGaucheBlanc.getFirst());
        jeuDame.changerTour();

        jeuDame.getDamier().ajouterPion(16, pionNoir);
        LinkedList<Integer> mouvementGaucheNoir =
                jeuDame.deplacementPossiblesPion(16, jeuDame.getDamier().getPion(16));
        assertEquals((Integer) 21, mouvementGaucheNoir.getFirst());
        LinkedList<Integer> deplacementsNull = jeuDame.deplacementPossiblesPion(25, null);
        assertTrue(deplacementsNull.isEmpty());
        LinkedList<Integer> deplacementsHorsLimites =
                jeuDame.deplacementPossiblesPion(51, pionBlanc);
        assertTrue(deplacementsHorsLimites.isEmpty());
        LinkedList<Integer> deplacementsNegatifs =
                jeuDame.deplacementPossiblesPion(-1, pionBlanc);
        assertTrue(deplacementsNegatifs.isEmpty());
        LinkedList<Integer> deplacementsOccupes =
                jeuDame.deplacementPossiblesPion(40, pionBlanc);
        assertFalse(deplacementsOccupes.contains(34));
    }

    /**
     * Test du déplacement d'un pion sur le damier.
     */
    @Test
    public void testDeplacementPion() {
        jeuDame.reset();
        historique.reset();
        assertNull(jeuDame.getDamier().getPion(30));

        Pion pionDeBaseBlanc = jeuDame.getDamier().getPion(35);
        jeuDame.deplacementPion(35, 30);
        assertNull(jeuDame.getDamier().getPion(35));
        assertEquals(pionDeBaseBlanc, jeuDame.getDamier().getPion(30));
        assertNull(jeuDame.getDamier().getPion(25));

        Pion pionDeBaseNoir = jeuDame.getDamier().getPion(20);
        jeuDame.deplacementPion(20, 25);
        assertNull(jeuDame.getDamier().getPion(20));
        assertEquals(pionDeBaseNoir, jeuDame.getDamier().getPion(25));

        Pion pionFauxDeplacement = jeuDame.getDamier().getPion(37);
        jeuDame.deplacementPion(37, 32);
        assertEquals(pionFauxDeplacement, jeuDame.getDamier().getPion(37));
        jeuDame.deplacementPion(37, -1);
        assertEquals(pionFauxDeplacement, jeuDame.getDamier().getPion(37));
        jeuDame.deplacementPion(37, 51);
        assertEquals(pionFauxDeplacement, jeuDame.getDamier().getPion(37));
        LinkedList<Integer> deplacements = jeuDame.deplacementPossiblesPion(25, null);
        assertTrue(deplacements.isEmpty());

        jeuDame.reset();
        jeuDame.getDamier().changementDame(31);
        assertEquals('d', jeuDame.getDamier().getPion(31).getRepresentation());
        jeuDame.deplacementPion(31, 26);
        assertNull(jeuDame.getDamier().getPion(26));
    }

    /**
     * Test de l'ajout d'un déplacement dans l'historique.
     */
    @Test
    public void testAjoutDeplacementHistorique() {
        jeuDame.reset();
        historique.reset();
        jeuDame.deplacementPion(35, 30);
        jeuDame.deplacementPion(20, 25);
        assertEquals(2, historique.getListeHistorique().size());
        assertTrue(historique.getListeHistorique().contains("35-30"));
        assertTrue(historique.getListeHistorique().contains("(20-25)"));

    }

    /**
     * Test du retour sur les dernières actions faites.
     */
    @Test
    public void testRetourSurHistorique() {
        jeuDame.reset();
        jeuDame.deplacementPion(35, 30);
        jeuDame.deplacementPion(20, 25);
        jeuDame.retourSurHistorique(2);
        assertNotNull(jeuDame.getDamier().getPion(35));
        assertNotNull(jeuDame.getDamier().getPion(20));
        assertNull(jeuDame.getDamier().getPion(30));
        assertNull(jeuDame.getDamier().getPion(25));
        jeuDame.deplacementPion(34, 30);
        jeuDame.deplacementPion(20, 25);
        jeuDame.changerTour();
        jeuDame.prisePion(25, 34);
        assertNull(jeuDame.getDamier().getPion(25));
        assertNull(jeuDame.getDamier().getPion(30));
        assertNotNull(jeuDame.getDamier().getPion(34));
        jeuDame.retourSurHistorique(1);
        assertNotNull(jeuDame.getDamier().getPion(25));
        assertNotNull(jeuDame.getDamier().getPion(30));
        assertNull(jeuDame.getDamier().getPion(34));
        jeuDame.retourSurHistorique(-1);
        assertNotNull(jeuDame.getDamier().getPion(30));

        jeuDame.reset();
        jeuDame.retourSurHistorique(1);
        historique.reset();
        assertEquals(0, historique.getListeHistorique().size());
    }

    @Test
    public void testRetourSurHistoriqueCoverage() {
        jeuDame.reset();
        historique.reset();
        jeuDame.getDamier().ajouterPion(24, new Pion(Pion.Couleur.NOIR));
        jeuDame.getDamier().ajouterPion(30, new Pion(Pion.Couleur.BLANC));

        Objects.requireNonNull(jeuDame.getListeJoueurs().get("blanc")).setStatusTour(false);
        Objects.requireNonNull(jeuDame.getListeJoueurs().get("noir")).setStatusTour(true);

        jeuDame.prisePion(24, 35);
        jeuDame.retourSurHistorique(1);

        assertNotNull(jeuDame.getDamier().getPion(24));
        assertNotNull(jeuDame.getDamier().getPion(30));

        jeuDame.reset();
        historique.reset();
        jeuDame.getDamier().initialiserVide();
        jeuDame.getDamier().ajouterPion(25, new Pion(Pion.Couleur.NOIR));
        jeuDame.getDamier().ajouterPion(30, new Pion(Pion.Couleur.BLANC));
        jeuDame.prisePion(25, 34);
        jeuDame.retourSurHistorique(1);
        assertNotNull(jeuDame.getDamier().getPion(25));
        assertNotNull(jeuDame.getDamier().getPion(30));

    }

    @Test
    public void testRetourSurHistoriqueDiffNegatifs() {
        jeuDame.reset();
        historique.reset();

        for (int i = 1; i <= 50; i++) {
            jeuDame.getDamier().ajouterPion(i, null);
        }

        Pion blanc = new Pion(Pion.Couleur.BLANC);
        Pion noir = new Pion(Pion.Couleur.NOIR);

        jeuDame.getDamier().ajouterPion(27, blanc);
        jeuDame.getDamier().ajouterPion(21, noir);

        Objects.requireNonNull(jeuDame.getListeJoueurs().get("blanc")).setStatusTour(true);
        Objects.requireNonNull(jeuDame.getListeJoueurs().get("noir")).setStatusTour(false);

        jeuDame.prisePion(27, 16);

        assertNull(jeuDame.getDamier().getPion(27));
        assertNotNull(jeuDame.getDamier().getPion(16));
        assertNull(jeuDame.getDamier().getPion(21));

        jeuDame.retourSurHistorique(1);

        assertNotNull(jeuDame.getDamier().getPion(27));
        assertEquals(Pion.Couleur.BLANC, jeuDame.getDamier().getPion(27).getCouleur());

        assertNull(jeuDame.getDamier().getPion(16));

        for (int i = 1; i <= 50; i++) {
            jeuDame.getDamier().ajouterPion(i, null);
        }

        jeuDame.getDamier().ajouterPion(26, blanc);
        jeuDame.getDamier().ajouterPion(21, noir);

        Objects.requireNonNull(jeuDame.getListeJoueurs().get("blanc")).setStatusTour(true);
        Objects.requireNonNull(jeuDame.getListeJoueurs().get("noir")).setStatusTour(false);

        jeuDame.prisePion(26, 17);

        assertNull(jeuDame.getDamier().getPion(26));
        assertNotNull(jeuDame.getDamier().getPion(17));

        jeuDame.retourSurHistorique(1);

        assertNotNull(jeuDame.getDamier().getPion(26));
        assertNull(jeuDame.getDamier().getPion(17));
    }

    @Test
    public void testRetourHistoriqueNoir() {
        jeuDame.reset();
        jeuDame.deplacementPion(35, 30);
        jeuDame.deplacementPion(20, 24);
        jeuDame.changerTour();
        jeuDame.prisePion(24, 35);
        jeuDame.retourSurHistorique(3);
        assertNotNull(jeuDame.getDamier().getPion(20));
    }

    @Test
    public void testPrisePossibleBlancBordure() {
        jeuDame.reset();
        jeuDame.deplacementPion(31, 26);
        jeuDame.deplacementPion(17, 21);

        LinkedList<Integer> prises = jeuDame.prisesPossiblesPion(26, jeuDame.getDamier().getPion(26));

        assertEquals(1, prises.size());
        assertTrue(prises.contains(17));
    }

    @Test
    public void testPrisePossibleNoirBordure() {
        jeuDame.reset();
        jeuDame.changerTour();
        jeuDame.deplacementPion(20, 25);
        jeuDame.deplacementPion(34, 30);

        LinkedList<Integer> prises = jeuDame.prisesPossiblesPion(25, jeuDame.getDamier().getPion(25));

        assertEquals(1, prises.size());
        assertTrue(prises.contains(34));
    }

    @Test
    public void testPrisePossibleBlancCentreDoublePossibilite() {
        jeuDame.reset();
        jeuDame.changerTour();
        jeuDame.deplacementPion(19, 24);
        jeuDame.changerTour();
        jeuDame.deplacementPion(24, 29);

        jeuDame.changerTour();
        jeuDame.deplacementPion(20, 25);
        jeuDame.changerTour();
        jeuDame.deplacementPion(25, 30);

        LinkedList<Integer> prises = jeuDame.prisesPossiblesPion(34, jeuDame.getDamier().getPion(34));

        assertEquals(2, prises.size());
        assertTrue(prises.contains(23));
        assertTrue(prises.contains(25));
    }

    @Test
    public void testPrisePossibleNoirCentreDoublePossibilite() {
        jeuDame.deplacementPion(33, 28);
        jeuDame.changerTour();
        jeuDame.deplacementPion(28, 23);
        jeuDame.changerTour();
        jeuDame.deplacementPion(34, 30);
        jeuDame.changerTour();
        jeuDame.deplacementPion(30, 24);

        LinkedList<Integer> prises = jeuDame.prisesPossiblesPion(19, jeuDame.getDamier().getPion(19));

        assertEquals(2, prises.size());
        assertTrue(prises.contains(28));
        assertTrue(prises.contains(30));
    }

    @Test
    public void testPrisePossibleInvalidePionNull() {
        LinkedList<Integer> prises = jeuDame.prisesPossiblesPion(25, null);
        assertTrue(prises.isEmpty());
    }

    @Test
    public void testPrisePossibleInvalidePositionHors() {
        LinkedList<Integer> priseHorsLimite = jeuDame.prisesPossiblesPion(51, pionBlanc);
        assertTrue(priseHorsLimite.isEmpty());

        LinkedList<Integer> priseNegative = jeuDame.prisesPossiblesPion(-1, pionBlanc);
        assertEquals(0, priseNegative.size());
    }

    @Test
    public void testPrisePossibleInvalideCaseDepartVide() {
        assertNull(jeuDame.getDamier().getPion(25));

        LinkedList<Integer> prises = jeuDame.prisesPossiblesPion(25, jeuDame.getDamier().getPion(25));

        assertTrue(prises.isEmpty());
    }

    /**
     * Test les prises d'un pion spécifique.
     */
    @Test
    public void testPrisePion() {
        jeuDame.reset();
        jeuDame.deplacementPion(35, 30);
        jeuDame.changerTour();
        jeuDame.deplacementPion(30, 24);
        jeuDame.prisePion(20, 29);
        assertNull(jeuDame.getDamier().getPion(25));
        assertNotNull(jeuDame.getDamier().getPion(29));

        jeuDame.reset();
        jeuDame.prisePion(15, 25);
        assertNotNull(jeuDame.getDamier().getPion(15));

        jeuDame.reset();
        jeuDame.getDamier().initialiser();
        jeuDame.prisePion(20, 45);
        assertNotNull(jeuDame.getDamier().getPion(20));

        jeuDame.reset();
        jeuDame.prisePion(25, 34);
        assertNull(jeuDame.getDamier().getPion(25));
        assertNull(jeuDame.getDamier().getPion(30));
        assertNotNull(jeuDame.getDamier().getPion(34));

        jeuDame.reset();
        jeuDame.deplacementPion(31, 26);
        jeuDame.deplacementPion(17, 21);
        jeuDame.prisePion(26, 17);
        assertNotNull(jeuDame.getDamier().getPion(17));
    }

    @Test
    public void testRetourSurPrisePion() {
        historique.reset();
        jeuDame.reset();
        jeuDame.deplacementPion(35, 30);
        jeuDame.deplacementPion(20, 24);
        jeuDame.changerTour();
        jeuDame.prisePion(24, 35);
    }

    /**
     * Test pour le changement de tour.
     */
    @Test
    public void testChangerTour() {
        jeuDame.reset();
        jeuDame = JeuDame.getInstance();
        jeuDame.getDamier().initialiser();
        assertTrue(Objects.requireNonNull(jeuDame.getListeJoueurs().get("blanc")).getStatusTour());
        assertFalse(Objects.requireNonNull(jeuDame.getListeJoueurs().get("noir")).getStatusTour());
        jeuDame.changerTour();
        assertFalse(Objects.requireNonNull(jeuDame.getListeJoueurs().get("blanc")).getStatusTour());
        assertTrue(Objects.requireNonNull(jeuDame.getListeJoueurs().get("noir")).getStatusTour());
        jeuDame.changerTour();
        assertTrue(Objects.requireNonNull(jeuDame.getListeJoueurs().get("blanc")).getStatusTour());
        assertFalse(Objects.requireNonNull(jeuDame.getListeJoueurs().get("noir")).getStatusTour());
    }

    /**
     * test la possibilité.
     */
    @Test
    public void testDeplacementSimpleDame() {
        jeuDame.getDamier().initialiserVide();
        jeuDame.getDamier().ajouterPion(30, new Dame(Pion.Couleur.NOIR));
        LinkedList<Integer> deplacements = jeuDame.deplacementPossiblesDame(30);
        assertTrue(deplacements.containsAll(List.of(24, 19, 13, 8, 2, 25, 34, 39, 43, 48, 35)));
    }

    @Test
    public void testExecutionCaptureDame() {
        jeuDame.getDamier().initialiserVide();
        Dame dame = new Dame(Pion.Couleur.NOIR);
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);

        jeuDame.getDamier().ajouterPion(30, dame);
        jeuDame.getDamier().ajouterPion(24, pionBlanc);

        jeuDame.deplacementDame(30, 19);

        assertNull(jeuDame.getDamier().getPion(30));
        assertNull(jeuDame.getDamier().getPion(24));
        assertEquals(dame, jeuDame.getDamier().getPion(19));
    }

    @Test
    public void testExecutionDeplacementSimpleDame() {
        jeuDame.reset();
        Dame dame = new Dame(Pion.Couleur.NOIR);

        jeuDame.getDamier().ajouterPion(30, dame);

        jeuDame.deplacementDame(30, 24);

        assertNull(jeuDame.getDamier().getPion(30));
        assertEquals(dame, jeuDame.getDamier().getPion(24));
    }

    @Test
    public void testGetFinPartie() {
        jeuDame.reset();
        assertFalse(jeuDame.isFinPartie());
        jeuDame.setFinPartie(true);
        assertTrue(jeuDame.isFinPartie());
    }

    @Test
    public void determinerCaseHorsChamps() {
        assertEquals(-1, jeuDame.determinerCase(0, 5));
        assertEquals(-1, jeuDame.determinerCase(11, 5));
        assertEquals(-1, jeuDame.determinerCase(5, 0));
        assertEquals(-1, jeuDame.determinerCase(5, 11));
    }

    @Test
    public void determinerCaseNonJouable() {
        assertEquals(-1, jeuDame.determinerCase(1, 1));
        assertEquals(-1, jeuDame.determinerCase(2, 2));
        assertEquals(-1, jeuDame.determinerCase(10, 10));
    }
}
