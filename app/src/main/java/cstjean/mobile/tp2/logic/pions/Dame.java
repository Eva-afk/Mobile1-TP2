package cstjean.mobile.tp2.logic.pions;

/**
 * La classe Dame représente une dame dans le jeu de dames.
 * Elle hérite de la classe Pion et possède une couleur.
 */
public class Dame extends Pion {

    /**
     * Constructeur qui initialise une dame avec une couleur spécifique.
     *
     * @param couleur La couleur de la dame.
     */
    public Dame(Couleur couleur) {
        super(couleur);
    }

    /**
     * Constructeur par défaut qui initialise une dame avec la couleur blanche.
     */
    public Dame() {
        super();
    }

    /**
     * Retourne la représentation de la couleur de la dame.
     *
     * @return la représentation de la couleur.
     */
    @Override
    public char getRepresentation() {
        char representation = ' ';
        if (getCouleur() == Couleur.NOIR) {
            representation = 'D';
        }
        if (getCouleur() == Couleur.BLANC) {
            representation = 'd';
        }

        return representation;
    }
}