package cstjean.mobile.tp2.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import cstjean.mobile.tp2.logic.pions.Pion;
import org.junit.Before;
import org.junit.Test;

/**
 * Test de la classe Damier.
 *
 * @author Eva Beaulieu
 * @author Chloé Nguedia
 */
public class TestDamier {
    /**
     * Damier à tester.
     */
    private Damier damier;

    /**
     * Pion de couleur blanc à ajouter.
     */
    private Pion pionBlanc;
    /**
     * Pion de couleur Noire à ajouter.
     */
    private Pion pionNoir;
    /**
     * Pion vide à ajouter.
     */
    private Pion pionVide;

    /**
     * Initialise les objets nécessaires avant chaque test.
     * Appelée automatiquement par JUnit.
     */

    @Before
    public void setUp() {
        pionBlanc = new Pion(Pion.Couleur.BLANC);
        pionNoir = new Pion(Pion.Couleur.NOIR);
        pionVide = new Pion();
        damier = new Damier();
    }

    /**
     * Test du constructeur de la classe Damier.
     * Vérifie que la liste de pions contient bien 50 pions vides ou null après l'initialisation.
     */

    @Test
    public void testCreerDamier() {
        damier = new Damier();
        assertEquals(0, damier.getNombrePion());
    }

    /**
     * Test de la méthode permettant d'ajouter des pions au damier.
     * Vérifie que l'on peut modifier les pions à des positions spécifiques
     * et que la taille du damier reste inchangée.
     */

    @Test
    public void testAjouterPionsSurDamier() {
        damier = new Damier();
        damier.ajouterPion(10, pionBlanc);
        assertEquals(1, damier.getNombrePion());
        damier.ajouterPion(38, pionNoir);
        assertEquals(2, damier.getNombrePion());
        damier.ajouterPion(30, pionVide);
        assertEquals(3, damier.getNombrePion());
        damier.ajouterPion(51, pionNoir);
    }

    /**
     * Vérifie si le pion est à la position donnée.
     */
    @Test
    public void testPositionPion() {
        damier = new Damier();
        damier.ajouterPion(38, pionNoir);
        assertEquals(pionNoir, damier.getPion(38));
        assertNull(damier.getPion(51));
    }

    /**
     * Vérifie le nombre de pions sur le damier.
     */
    @Test
    public void testNombreDePion() {
        damier = new Damier();
        damier.ajouterPion(38, pionNoir);
        damier.ajouterPion(10, pionBlanc);
        damier.ajouterPion(30, pionVide);
        assertEquals(3, damier.getNombrePion());

    }

    /**
     * Vérifie si le damier s'initialise avec le bon nombre de pions
     * blanc et noir pour chaque joueur et s'il y a des espaces vides.
     */
    @Test
    public void testInitialisationDamier() {
        damier = new Damier();

        damier.initialiser();

        assertEquals(40, damier.getNombrePion());
    }

    /**
     * Test pour le changement d'un pion en dame.
     */
    @Test
    public void testChangementDame() {
        damier = new Damier();
        damier.initialiser();
        assertEquals('P', damier.getPion(3).getRepresentation());
        damier.changementDame(3);
        assertEquals('D', damier.getPion(3).getRepresentation());

        assertEquals('p', damier.getPion(48).getRepresentation());
        damier.changementDame(48);
        assertEquals('d', damier.getPion(48).getRepresentation());
    }

}
