package cstjean.mobile.tp2.logic;

import java.util.Stack;

/**
 * historique des actions effectuées durant la partie.
 */
public class Historique {

    /**
     * Instance unique de Historique.
     */
    private static Historique instance = null;

    /**
     * File qui garde les actions dans un historique qui peut être remonté en ordre.
     *
     */
    private final Stack<String> listeHistorique = new Stack<>();

    /**
     * Constructeur privé qui initialise l'état en appelant reset().
     */
    private Historique() {
        reset();
    }

    /**
     * Crée ou recoit une instance de l'historique.
     *
     * @return l'instance de l'historique.
     */
    public static Historique getInstance() {
        if (instance == null) {
            instance = new Historique();
        }
        return instance;
    }

    public Stack<String> getListeHistorique() {
        return listeHistorique;
    }

    /**
     * Réinitialise la liste des cours et ajoute des exemples par défaut.
     */
    public void reset() {
        listeHistorique.clear();
    }
}
