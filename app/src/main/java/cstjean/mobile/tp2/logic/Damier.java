package cstjean.mobile.tp2.logic;

import cstjean.mobile.tp2.logic.joueurs.Joueur;
import cstjean.mobile.tp2.logic.pions.Dame;
import cstjean.mobile.tp2.logic.pions.Pion;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Représente un damier de jeu de dames utilisant une notation Manoury (1 à 50).
 * Les cases du damier sont stockées dans une LinkedList.
 * Chaque case peut contenir un pion ou être vide (null).
 *
 * @author Eva Beaulieu
 * @author Chloé Nguedia
 */
public class Damier {
    /**
     * Liste chainée represantant les 50 cases du damier.
     */
    private final Map<Integer, Pion> pionsDamier = new HashMap<>();

    /**
     * Tour des joueurs.
     */
    private final Map<String, Joueur> listeJoueurs = new HashMap<>();

    /**
     * Constructeur : initialise le damier avec 50 cases vides (1 à 50 en notation Manoury).
     */
    public Damier() {
    }

    /**
     * Ajoute un pion à une position donnée (notation Manoury : 1 à 50).
     *
     * @param positionPion La position où placer le pion (1-50).
     * @param ajoutPion    Le pion à placer.
     */
    public void ajouterPion(int positionPion, Pion ajoutPion) {
        if (positionPion >= 1 && positionPion <= 50) {
            pionsDamier.put(positionPion, ajoutPion);
        }
    }

    /**
     * Retourne le pion à une position donnée.
     *
     * @param positionPion La position du pion (1-50).
     * @return Le pion à cette position, ou null si la case est vide.
     */
    public Pion getPion(int positionPion) {
        if (positionPion >= 1 && positionPion <= 50) {
            return pionsDamier.get(positionPion);
        }
        return null;
    }

    /**
     * Méthode qui permet d'obtenir le nombre de pions présent sur le damier.
     *
     * @return retourne le nombre de pions sur le damier.
     */
    public int getNombrePion() {
        int nbPions = 0;
        for (Map.Entry<Integer, Pion> entry : pionsDamier.entrySet()) {
            if (entry.getValue() != null) {
                nbPions++;
            }
        }
        return nbPions;
    }

    /**
     * Initialise un damier avec tous ses pions placés.
     *
     */
    public void initialiser() {
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        for (int i = 1; i <= 50; i++) {
            if (i <= 20) {
                ajouterPion(i, pionNoir);
            } else if (i >= 31) {
                ajouterPion(i, pionBlanc);
            } else {
                pionsDamier.put(i, null);
            }
        }
        Joueur joueurBlanc = new Joueur();
        Joueur joueurNoir = new Joueur();
        listeJoueurs.put("blanc", joueurBlanc);
        listeJoueurs.put("noir", joueurNoir);
        Objects.requireNonNull(listeJoueurs.get("blanc")).setStatusTour(true);
    }

    /**
     * Initialise un damier vide pour faciliter les tests.
     */
    public void initialiserVide() {
        for (int i = 1; i <= 50; i++) {
            pionsDamier.put(i, null);
        }
        Joueur joueurBlanc = new Joueur();
        Joueur joueurNoir = new Joueur();
        listeJoueurs.put("blanc", joueurBlanc);
        listeJoueurs.put("noir", joueurNoir);
        Objects.requireNonNull(listeJoueurs.get("blanc")).setStatusTour(true);
    }

    /**
     * Change un pion à la fin du damier en dame.
     *
     * @param positionPion position du pion à changer.
     */
    public void changementDame(int positionPion) {
        Pion pionVoulu = pionsDamier.get(positionPion);
        assert pionVoulu != null;
        if (pionVoulu.getCouleur() == Pion.Couleur.BLANC) {
            pionsDamier.replace(positionPion, new Dame(Pion.Couleur.BLANC));
        } else {
            pionsDamier.replace(positionPion, new Dame(Pion.Couleur.NOIR));
        }
    }

}
