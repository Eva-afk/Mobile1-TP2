package cstjean.mobile.tp2.logic;

import cstjean.mobile.tp2.logic.affichage.TestAffichage;
import cstjean.mobile.tp2.logic.joueurs.TestJoueur;
import cstjean.mobile.tp2.logic.pions.TestDame;
import cstjean.mobile.tp2.logic.pions.TestPion;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        TestDamier.class,
        TestPion.class,
        TestDame.class,
        TestAffichage.class,
        TestJoueur.class,
        TestJeuDame.class,
        TestFinPartie.class
})

/**
 * Test comprenant tous les tests du projet.
 *
 * @author Eva Beaulieu
 * @author Chloé Nguedia
 */
public class TestComplet {
    // inutile
}
