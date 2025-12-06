package cstjean.mobile.tp2.logic.joueurs;

/**
 * Classe pour un joueur qui a un tour.
 */
public class Joueur {
    /**
     * Tour du joueur.
     */
    private boolean estTour;

    /**
     * Nom du Joueur.
     */
    private String nom;

    /**
     * Constructeur pour le joueur.
     */
    public Joueur() {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nouveauNom) {
        nom = nouveauNom;
    }

    public void setStatusTour(boolean status) {
        estTour = status;
    }

    public boolean getStatusTour() {
        return estTour;
    }
}
