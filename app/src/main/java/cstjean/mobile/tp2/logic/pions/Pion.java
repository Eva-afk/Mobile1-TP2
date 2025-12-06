package cstjean.mobile.tp2.logic.pions;

/**
 * Représente un pion dans un jeu de dames.
 * Un pion a une couleur qui peut être "blanc" ou "noir".
 * Par défaut, un pion est blanc s’il n’y a pas de couleur spécifiée.
 *
 * @author Eva Beaulieu
 * @author Chloé Nguedia
 */
public class Pion {
    /**
     * Couleur du pion.
     */
    private final Couleur couleur;

    /**
     * Constructeur avec paramètre : crée un pion avec la couleur donnée.
     * Appelle le constructeur principal avec la couleur "blanc".
     *
     * @param couleur couleur du pion.
     */
    public Pion(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * Constructeur sans paramètre : crée un pion blanc par défaut.
     * Appelle le constructeur principal avec la couleur "blanc".
     */
    public Pion() {
        this(Couleur.BLANC);
    }

    /**
     * Retourne la couleur du pion/dame.
     *
     * @return retourne une couleur.
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Fonction pour obtenir la représentation d'un pion.
     * 'p' si c'est blanc.
     * 'P' si c'est noir.
     * 'p' dans les autres cas.
     *
     * @return retourne la représentation d'un pion.
     */
    public char getRepresentation() {
        char representation = ' ';
        if (couleur == Couleur.NOIR) {
            representation = 'P';
        }
        if (couleur == Couleur.BLANC) {
            representation = 'p';
        }

        return representation;
    }

    /**
     * Enumération représentant les couleurs possibles d'un pion.
     * Chaque couleur a une représentation sous forme de chaîne de caractères.
     */

    public enum Couleur {
        /**
         * Couleur Noire.
         */
        NOIR,

        /**
         * Couleur Blanche.
         */
        BLANC

    }
}