package cstjean.mobile.tp2.logic.affichage;

import cstjean.mobile.tp2.logic.Damier;

/**
 * Classe pour l'affichage du damier avec toutes ses valeurs.
 */
public class Affichage {

    /**
     * Damier à afficher.
     */
    private final Damier damier;

    /**
     * Constructeur de l'affichage.
     *
     * @param damier damier qu'il faut afficher.
     */
    public Affichage(Damier damier) {
        this.damier = damier;
    }

    /**
     * Crée un stringBuilder pour l'affichage et y ajoute à chaque valeur du damier un élément.
     * "-" pour null,
     * "P" pour un pion noir,
     * "p" pour un pion blanc.
     *
     * @return retourne le string comprenant tous les ajouts au StringBuilder.
     */
    public String getAffichageFinal() {
        StringBuilder affichageFinal = new StringBuilder();
        int emptySpace = 1;
        for (int i = 1; i <= 50; i++) {
            if (damier.getPion(i) == null) {
                affichageFinal.append("--");
            } else if (emptySpace % 2 != 0) {
                affichageFinal.append("-")
                        .append(damier.getPion(i).getRepresentation());
            } else {
                affichageFinal.append(damier.getPion(i).getRepresentation())
                        .append("-");
            }
            if (i % 5 == 0) {
                affichageFinal.append("\n");
                emptySpace++;
            }
        }
        return affichageFinal.toString();
    }
}
