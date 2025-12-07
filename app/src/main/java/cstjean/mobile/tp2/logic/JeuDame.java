package cstjean.mobile.tp2.logic;

import cstjean.mobile.tp2.logic.joueurs.Joueur;
import cstjean.mobile.tp2.logic.pions.Dame;
import cstjean.mobile.tp2.logic.pions.Pion;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * Classe qui gère toute la logique du jeu de dames :
 * - gestion des joueurs et des tours
 * - déplacements simples
 * - prises
 * - déplacements et prises des dames
 * - promotion des pions
 * - historique des actions
 * - fin de partie
 * Le damier est délégué à la classe Damier.
 */
public class JeuDame {

    /**
     * Instance unique de JeuDame.
     */
    private static JeuDame instance = null;

    /**
     * Damier pour le jeu.
     */
    private Damier damier;

    /**
     * File qui garde les actions dans un historique qui peut être remonté en ordre.
     */
    private final Historique historique = Historique.getInstance();

    /**
     * Tour des joueurs.
     */
    private final Map<String, Joueur> listeJoueurs = new HashMap<>();

    /**
     * booleen indiquant la fin de la pratie.
     */
    private boolean finPartie;

    /**
     * string qui représente le nom du gagnant.
     */
    private String gagnant;

    /**
     * Constructeur pour le jeu.
     *
     * @param damier damier pour le jeu.
     */
    private JeuDame(Damier damier) {
        this.damier = damier;
        damier.initialiser();
        listeJoueurs.put("blanc", new Joueur());
        listeJoueurs.put("noir", new Joueur());
        finPartie = false;
        Objects.requireNonNull(listeJoueurs.get("blanc")).setStatusTour(true);
    }

    /**
     * retourne l'instance si elle existe, sinon, la crée.
     *
     * @return l'instance de jeuDame.
     */
    public static JeuDame getInstance() {
        if (instance == null) {
            instance = new JeuDame(new Damier());
        }
        return instance;
    }

    /**
     * Réinitialise la liste des cours et ajoute des exemples par défaut.
     */
    public void reset() {
        listeJoueurs.clear();
        listeJoueurs.put("blanc", new Joueur());
        listeJoueurs.put("noir", new Joueur());
        finPartie = false;
        Objects.requireNonNull(listeJoueurs.get("blanc")).setStatusTour(true);
        damier = new Damier();
        damier.initialiser();
        gagnant = null;
    }

    public Map<String, Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    public boolean isFinPartie() {
        return finPartie;
    }

    public Damier getDamier() {
        return damier;
    }

    /**
     * Retourne le gagnant de la partie.
     *
     * @return "blanc", "noir" ou null si la partie n'est pas terminée
     */
    public String getGagnant() {
        return gagnant;
    }

    /**
     * Méthode qui sert à vérifier le type de déplacement qu'un pion ou une dame peut effectuer.
     *
     * @param positionPion position du pion que l'on veut déplacer.
     * @param pion         pion que l'on veut déplacer.
     * @return le type de déplacement que peut faire le pion selon sa position.
     */
    public LinkedList<Integer> deplacementPossiblesPion(int positionPion, Pion pion) {
        int[] bordureGauche = {6, 16, 26, 36, 46};
        int[] bordureDroite = {5, 15, 25, 35, 45};
        int[] coloneChangerSix = {1, 2, 3, 4, 11, 12, 13, 14, 21, 22, 23, 24, 31, 32, 33, 34, 41, 42, 43, 44};
        LinkedList<Integer> deplacementsPossibles = new LinkedList<>();
        if (pion == null) {
            return deplacementsPossibles;
        }
        if (Arrays.stream(bordureGauche).anyMatch(x -> x == positionPion)) {
            if (pion.getCouleur() == Pion.Couleur.BLANC &&
                    Objects.requireNonNull(listeJoueurs.get("blanc")).getStatusTour() &&
                    damier.getPion(positionPion - 5) == null) {
                deplacementsPossibles.add(positionPion - 5);
            } else if (pion.getCouleur() == Pion.Couleur.NOIR &&
                    Objects.requireNonNull(listeJoueurs.get("noir")).getStatusTour() &&
                    damier.getPion(positionPion + 5) == null) {
                deplacementsPossibles.add(positionPion + 5);
            }
        } else if (Arrays.stream(bordureDroite).anyMatch(x -> x == positionPion)) {
            if (pion.getCouleur() == Pion.Couleur.BLANC &&
                    Objects.requireNonNull(listeJoueurs.get("blanc")).getStatusTour() &&
                    damier.getPion(positionPion - 5) == null) {
                deplacementsPossibles.add(positionPion - 5);
            } else if (pion.getCouleur() == Pion.Couleur.NOIR &&
                    Objects.requireNonNull(listeJoueurs.get("noir")).getStatusTour() &&
                    damier.getPion(positionPion + 5) == null) {
                deplacementsPossibles.add(positionPion + 5);
            }
        } else {
            if (pion.getCouleur() == Pion.Couleur.BLANC &&
                    Objects.requireNonNull(listeJoueurs.get("blanc")).getStatusTour()) {
                if (Arrays.stream(coloneChangerSix).anyMatch(x -> x == positionPion)) {
                    if (damier.getPion(positionPion - 5) == null) {
                        deplacementsPossibles.add(positionPion - 5);
                    }
                    if (damier.getPion(positionPion - 4) == null) {
                        deplacementsPossibles.add(positionPion - 4);
                    }
                } else {
                    if (damier.getPion(positionPion - 6) == null) {
                        deplacementsPossibles.add(positionPion - 6);
                    }
                    if (damier.getPion(positionPion - 5) == null) {
                        deplacementsPossibles.add(positionPion - 5);
                    }
                }
            } else if (pion.getCouleur() == Pion.Couleur.NOIR &&
                    Objects.requireNonNull(listeJoueurs.get("noir")).getStatusTour()) {
                if (Arrays.stream(coloneChangerSix).anyMatch(x -> x == positionPion)
                ) {
                    if (damier.getPion(positionPion + 5) == null) {
                        deplacementsPossibles.add(positionPion + 5);
                    }
                    if (damier.getPion(positionPion + 6) == null) {
                        deplacementsPossibles.add(positionPion + 6);
                    }
                } else {
                    if (damier.getPion(positionPion + 4) == null) {
                        deplacementsPossibles.add(positionPion + 4);
                    }
                    if (damier.getPion(positionPion + 5) == null) {
                        deplacementsPossibles.add(positionPion + 5);
                    }
                }
            }
        }
        return deplacementsPossibles;
    }

    /**
     * Méthode pour déplacer un pion de sa position vers la position finale.
     *
     * @param positionPion position de base du pion.
     * @param deplacement  position vers laquelle on veut déplacer le pion.
     */
    public void deplacementPion(Integer positionPion, int deplacement) {
        Pion pion = damier.getPion(positionPion);
        if (pion == null ||
                pion.getClass() == Dame.class ||
                pion.getClass() != Pion.class ||
                damier.getPion(deplacement) != null ||
                deplacement > 50 ||
                deplacement < 1) {
            return;
        }

        Pion pionVoulu = damier.getPion(positionPion);
        LinkedList<Integer> deplacementsPossibles = deplacementPossiblesPion(positionPion, pionVoulu);

        if (deplacementsPossibles.stream().anyMatch(x -> x == deplacement)) {
            damier.ajouterPion(positionPion, null);
            damier.ajouterPion(deplacement, pionVoulu);
            ajoutDeplacementHistorique(positionPion, deplacement, "deplacement");
            verifierChangementDame(deplacement);
            changerTour();
            verifierFinPartie();
        }
    }

    /**
     * Vérifie si un pion doit être changé en dame.
     *
     * @param positionPion la position du pion à vérifier.
     */
    public void verifierChangementDame(int positionPion) {
        Pion pion = damier.getPion(positionPion);
        int[] whiteLimit = {46, 47, 48, 49, 50};
        int[] blackLimit = {1, 2, 3, 4, 5};

        if (pion.getCouleur() == Pion.Couleur.BLANC &&
                Arrays.stream(blackLimit).anyMatch(x -> x == positionPion)) {
            damier.changementDame(positionPion);
        }
        if (pion.getCouleur() == Pion.Couleur.NOIR &&
                Arrays.stream(whiteLimit).anyMatch(x -> x == positionPion)) {
            damier.changementDame(positionPion);
        }
    }

    /**
     * Méthode pour garder dans l'historique les déplacements.
     *
     * @param positionPion    position du pion sur le damier en manoury.
     * @param deplacement     position finale du pion apès le déplacement.
     * @param typeDeplacement type de déplacement (prise ou simple déplacement)
     */
    public void ajoutDeplacementHistorique(int positionPion, int deplacement, String typeDeplacement) {
        StringBuilder builder = new StringBuilder();
        if (damier.getPion(deplacement).getCouleur() == Pion.Couleur.BLANC) {
            builder.append(positionPion);
            if (typeDeplacement.equals("deplacement")) {
                builder.append("-");
            } else {
                builder.append("x");
            }
            builder.append(deplacement);
        } else {
            builder.append("(");
            builder.append(positionPion);
            if (typeDeplacement.equals("deplacement")) {
                builder.append("-");
            } else {
                builder.append("x");
            }
            builder.append(deplacement);
            builder.append(")");
        }
        historique.getListeHistorique().add(builder.toString());
    }

    /**
     * Méthode pour revenir en arrière sur l'historique.
     *
     * @param nombreBondsArriere nombre de retours en arrière à effectuer.
     */
    public void retourSurHistorique(int nombreBondsArriere) {
        if (historique.getListeHistorique().isEmpty() || nombreBondsArriere == 0) {
            return;
        }

        for (int i = 0; i < nombreBondsArriere; i++) {
            if (historique.getListeHistorique().isEmpty()) {
                break;
            }
            String derniereAction = historique.getListeHistorique().remove(historique.getListeHistorique().size() - 1);

            String cleanAction = derniereAction.replace("(", "").replace(")", "");

            if (cleanAction.contains("x")) {
                String[] parts = cleanAction.split("x");
                int posDepart = Integer.parseInt(parts[0]);
                int posArrivee = Integer.parseInt(parts[1]);

                Pion pionMangeur = damier.getPion(posArrivee);
                damier.ajouterPion(posArrivee, null);
                damier.ajouterPion(posDepart, pionMangeur);

                int posCapturee;

                posCapturee = (posDepart + posArrivee + 1) / 2;

                Pion.Couleur couleurCapturee = (pionMangeur.getCouleur() == Pion.Couleur.BLANC) ?
                        Pion.Couleur.NOIR : Pion.Couleur.BLANC;

                if (posCapturee > 0 && posCapturee <= 50) {
                    damier.ajouterPion(posCapturee, new Pion(couleurCapturee));
                }

            } else {
                String[] parts = cleanAction.split("-");
                int posDepart = Integer.parseInt(parts[0]);
                int posArrivee = Integer.parseInt(parts[1]);

                Pion pionVoulu = damier.getPion(posArrivee);
                damier.ajouterPion(posArrivee, null);
                damier.ajouterPion(posDepart, pionVoulu);
            }

            changerTour();
        }
    }

    /**
     * Méthode pour obtenir toutes les prises possibles pour un pion.
     *
     * @param positionPion position du pion à vérifier.
     * @param pion         pion à la position.
     * @return la liste des positions de prises possibles.
     */
    public LinkedList<Integer> prisesPossiblesPion(int positionPion, Pion pion) {
        if (pion == null || positionPion < 1 || positionPion > 50) {
            return new LinkedList<>();
        }

        LinkedList<Integer> prisesPossibles = new LinkedList<>();
        Pion.Couleur couleurJoueur = pion.getCouleur();
        String cleJoueur = couleurJoueur == Pion.Couleur.BLANC ? "blanc" : "noir";

        if (!Objects.requireNonNull(listeJoueurs.get(cleJoueur)).getStatusTour()) {
            return prisesPossibles;
        }

        Pion.Couleur couleurAdverse = couleurJoueur == Pion.Couleur.BLANC ? Pion.Couleur.NOIR : Pion.Couleur.BLANC;
        boolean goingForward = couleurJoueur == Pion.Couleur.NOIR;

        int[] offsetsDepart = goingForward ? getOffsetsPourPosition(positionPion) : getOffsetsArriere(positionPion);
        int offsetGauche = offsetsDepart[0];
        int offsetDroite = offsetsDepart[1];

        int direction = goingForward ? 1 : -1;

        if (goingForward) {

            int posAdverseGauche = positionPion + (direction * offsetGauche);
            if (estSurBordureGauche(positionPion) &&
                    estPionCouleur(damier, posAdverseGauche, couleurAdverse)) {

                int[] offsetsApres = getOffsetsPourPosition(posAdverseGauche);
                int captureGauche = posAdverseGauche + (direction * offsetsApres[0]);

                if (estPositionValide(captureGauche) && damier.getPion(captureGauche) == null) {
                    prisesPossibles.add(captureGauche);
                }
            }

            int posAdverseDroite = positionPion + (direction * offsetDroite);
            if (estSurBordureDroite(positionPion) &&
                    estPionCouleur(damier, posAdverseDroite, couleurAdverse)) {

                int[] offsetsApres = getOffsetsPourPosition(posAdverseDroite);
                int captureDroite = posAdverseDroite + (direction * offsetsApres[1]);

                if (estPositionValide(captureDroite) && damier.getPion(captureDroite) == null) {
                    prisesPossibles.add(captureDroite);
                }
            }
        } else {
            int posAdverseDroite = positionPion + (direction * offsetGauche);
            if (estSurBordureDroite(positionPion) &&
                    estPionCouleur(damier, posAdverseDroite, couleurAdverse)) {

                int[] offsetsApres = getOffsetsArriere(posAdverseDroite);
                int captureDroite = posAdverseDroite + (direction * offsetsApres[0]);

                if (estPositionValide(captureDroite) && damier.getPion(captureDroite) == null) {
                    prisesPossibles.add(captureDroite);
                }
            }

            int posAdverseGauche = positionPion + (direction * offsetDroite);
            if (estSurBordureGauche(positionPion) &&
                    estPionCouleur(damier, posAdverseGauche, couleurAdverse)) {

                int[] offsetsApres = getOffsetsArriere(posAdverseGauche);
                int captureGauche = posAdverseGauche + (direction * offsetsApres[1]);

                if (estPositionValide(captureGauche) && damier.getPion(captureGauche) == null) {
                    prisesPossibles.add(captureGauche);
                }
            }
        }

        return prisesPossibles;
    }

    private boolean estPositionValide(int position) {
        return position >= 1 && position <= 50;
    }

    /**
     * Détermine si la position est sur la bordure gauche.
     *
     * @param position position à vérifier.
     */
    private boolean estSurBordureGauche(int position) {
        int[] bordureGauche = {6, 16, 26, 36, 46};
        return Arrays.stream(bordureGauche).noneMatch(x -> x == position);
    }

    /**
     * Détermine si la position est sur la bordure droite.
     *
     * @param position position à vérifier.
     */
    private boolean estSurBordureDroite(int position) {
        int[] bordureDroite = {5, 15, 25, 35, 45};
        return Arrays.stream(bordureDroite).noneMatch(x -> x == position);
    }

    private boolean estPionCouleur(Damier damier, int position, Pion.Couleur couleur) {
        Pion p = damier.getPion(position);
        return p != null && p.getCouleur() == couleur;
    }

    /**
     * Méthode pour gérer la prise d'un pion.
     *
     * @param positionPion position du pion.
     * @param deplacement  position finale lors de la prise.
     */
    public void prisePion(int positionPion, int deplacement) {
        if (damier.getPion(positionPion) == null ||
                damier.getPion(positionPion).getClass() == Dame.class ||
                damier.getPion(positionPion).getClass() != Pion.class ||
                damier.getPion(deplacement) != null) {
            return;
        }

        Pion pionVoulu = damier.getPion(positionPion);
        LinkedList<Integer> prisesPossibles = prisesPossiblesPion(positionPion, pionVoulu);

        if (prisesPossibles.stream().anyMatch(x -> x == deplacement)) {
            int positionCapturee = calculerPositionCapturee(positionPion, deplacement);

            damier.ajouterPion(positionPion, null);
            damier.ajouterPion(positionCapturee, null);
            damier.ajouterPion(deplacement, pionVoulu);

            ajoutDeplacementHistorique(positionPion, deplacement, "prise");
            verifierChangementDame(deplacement);
            changerTour();
            verifierFinPartie();
        }
    }

    /**
     * Fait le calcul  pour la position qui est capturée.
     *
     * @param depart  position de départ.
     * @param arrivee position finale.
     */
    private int calculerPositionCapturee(int depart, int arrivee) {
        boolean goingForward = arrivee > depart;
        int[] offsetsDepart = goingForward ? getOffsetsPourPosition(depart) : getOffsetsArriere(depart);
        int direction = goingForward ? 1 : -1;

        int pos1 = depart + (direction * offsetsDepart[0]);
        int pos2 = depart + (direction * offsetsDepart[1]);

        int[] offsets1 = goingForward ? getOffsetsPourPosition(pos1) : getOffsetsArriere(pos1);

        if (pos1 + (direction * offsets1[0]) == arrivee ||
                pos1 + (direction * offsets1[1]) == arrivee) {
            return pos1;
        } else {
            return pos2;
        }
    }

    private int[] getOffsetsPourPosition(int position) {
        int rangeeVisuelle = (position - 1) / 5 + 1;

        if (rangeeVisuelle % 2 == 1) {
            return new int[]{5, 6};
        } else {
            return new int[]{4, 5};
        }
    }

    private int[] getOffsetsArriere(int position) {
        int rangeeVisuelle = (position - 1) / 5 + 1;

        if (rangeeVisuelle % 2 == 1) {
            return new int[]{4, 5};
        } else {
            return new int[]{5, 6};
        }
    }

    /**
     * Méthode pour gérer le changement de tour.
     */
    public void changerTour() {
        if (Objects.requireNonNull(listeJoueurs.get("blanc")).getStatusTour()) {
            Objects.requireNonNull(listeJoueurs.get("noir")).setStatusTour(true);
            Objects.requireNonNull(listeJoueurs.get("blanc")).setStatusTour(false);
        } else {
            Objects.requireNonNull(listeJoueurs.get("noir")).setStatusTour(false);
            Objects.requireNonNull(listeJoueurs.get("blanc")).setStatusTour(true);
        }
    }

    /**
     * Vérifie si le pion à la position donnée est une dame.
     *
     * @param position La position à vérifier (1 à 50).
     * @return true si le pion est une instance de Dame, sinon false.
     */
    public boolean verifierDame(int position) {
        return damier.getPion(position) instanceof Dame;
    }

    /**
     * Calcule tous les déplacements possibles (simples ou avec prise) pour une dame à une position donnée.
     *
     * @param positionDame La position actuelle de la dame (1 à 50).
     * @return Une liste des positions accessibles selon les règles de déplacement et de capture.
     */
    public LinkedList<Integer> deplacementPossiblesDame(int positionDame) {
        LinkedList<Integer> deplacementPossible = new LinkedList<>();

        if (verifierDame(positionDame)) {
            int[] coord = determinerCoordonneePion(positionDame);
            int ligne = coord[0];
            int col = coord[1];
            Dame dame = (Dame) damier.getPion(positionDame);

            deplacementPossible.addAll(deplacementDiagonal(dame, ligne, col, -1, -1));
            deplacementPossible.addAll(deplacementDiagonal(dame, ligne, col, -1, 1));
            deplacementPossible.addAll(deplacementDiagonal(dame, ligne, col, 1, -1));
            deplacementPossible.addAll(deplacementDiagonal(dame, ligne, col, 1, 1));
        }

        return deplacementPossible;
    }

    /**
     * Explore une diagonale donnée pour trouver les déplacements possibles d'une dame.
     * Inclut les déplacements simples et les prises simples (un seul pion adverse suivi d'une case libre).
     *
     * @param dame    La dame concernée.
     * @param ligne   Ligne de départ (1 à 10).
     * @param colonne Colonne de départ (1 à 10).
     * @param dl      Direction verticale (-1 ou 1).
     * @param dc      Direction horizontale (-1 ou 1).
     * @return Une liste des positions accessibles dans cette diagonale.
     */
    public LinkedList<Integer> deplacementDiagonal(Dame dame, int ligne, int colonne, int dl, int dc) {
        LinkedList<Integer> deplacements = new LinkedList<>();
        boolean priseSimplePossible = false;

        ligne += dl;
        colonne += dc;

        while (ligne >= 1 && ligne <= 10 && colonne >= 1 && colonne <= 10) {
            int numero = determinerCase(ligne, colonne);
            if (numero == -1) {
                break;
            }

            Pion pion = damier.getPion(numero);

            if (pion == null) {
                if (priseSimplePossible) {
                    deplacements.add(numero);
                    break;
                } else {

                    deplacements.add(numero);
                }
            } else {
                if (pion.getCouleur() == dame.getCouleur()) {
                    break;
                } else {
                    if (priseSimplePossible) {
                        break;
                    }
                    priseSimplePossible = true;
                }
            }

            ligne += dl;
            colonne += dc;
        }

        return deplacements;
    }

    /**
     * Convertit des coordonnées ligne/colonne en numéro de case jouable (de 1 à 50).
     * Seules les cases sombres sont jouables. Cette méthode retourne -1 si la case
     * demandée est hors du damier ou non jouable (case blanche).
     *
     * @param ligne   Ligne du damier (entre 1 et 10).
     * @param colonne Colonne du damier (entre 1 et 10).
     * @return Numéro de case jouable (1 à 50), ou -1 si la case n'est pas valide.
     */
    public int determinerCase(int ligne, int colonne) {
        if (ligne < 1 || ligne > 10 || colonne < 1 || colonne > 10) {
            return -1;
        }

        boolean caseJouable = (ligne % 2 == 1 && colonne % 2 == 0) || (ligne % 2 == 0 && colonne % 2 == 1);
        if (!caseJouable) {
            return -1;
        }

        int indexDansLigne = (ligne % 2 == 1) ? (colonne - 2) / 2 : (colonne - 1) / 2;
        return (ligne - 1) * 5 + indexDansLigne + 1;
    }

    /**
     * Convertit un numéro de case jouable (de 1 à 50) en coordonnées ligne/colonne sur le damier.
     * Le damier est un plateau 10×10, mais seules les cases sombres sont jouables.
     * La numérotation des cases jouables suit l'ordre de gauche à droite, de haut en bas.
     * Cette méthode permet de retrouver la position réelle (ligne, colonne) sur le damier.
     *
     * @param position Numéro de case jouable (entre 1 et 50).
     * @return Un tableau contenant {ligne, colonne}, avec des indices 1-based.
     */
    public int[] determinerCoordonneePion(int position) {
        int ligne = (position - 1) / 5 + 1;
        int indexDansLigne = (position - 1) % 5;
        int colonne = (ligne % 2 == 1) ? (indexDansLigne * 2 + 2) : (indexDansLigne * 2 + 1);
        return new int[]{ligne, colonne};
    }

    /**
     * Effectue un déplacement de dame sur le damier, en appliquant les règles de déplacement et de capture.
     * Si le déplacement est valide, la méthode met à jour le damier et l'historique.
     *
     * @param positionDepart  La position de départ de la dame (1 à 50).
     * @param positionArrivee La position d'arrivée souhaitée (1 à 50).
     */
    public void deplacementDame(int positionDepart, int positionArrivee) {

        if (positionDepart < 1 || positionDepart > 50 || positionArrivee < 1 || positionArrivee > 50) {
            return;
        }

        if (!(damier.getPion(positionDepart) instanceof Dame)) {
            return;
        }

        if (damier.getPion(positionArrivee) != null) {
            return;
        }

        LinkedList<Integer> deplacements = deplacementPossiblesDame(positionDepart);
        if (!deplacements.contains(positionArrivee)) {
            return;
        }

        int[] depart = determinerCoordonneePion(positionDepart);
        int[] arrivee = determinerCoordonneePion(positionArrivee);
        int deltaLigne = arrivee[0] - depart[0];
        int deltaCol = arrivee[1] - depart[1];
        if (Math.abs(deltaLigne) != Math.abs(deltaCol)) {
            return;
        }

        int deplacementLigne = Integer.signum(deltaLigne);
        int deplacementCol = Integer.signum(deltaCol);
        int ligne = depart[0] + deplacementLigne;
        int col = depart[1] + deplacementCol;
        int pionPrisPosition = -1;

        Dame dame = (Dame) damier.getPion(positionDepart);
        while (ligne != arrivee[0] || col != arrivee[1]) {
            int caseIntermediaire = determinerCase(ligne, col);
            if (caseIntermediaire == -1) {
                return;
            }

            Pion pionInter = damier.getPion(caseIntermediaire);
            if (pionInter != null) {
                if (pionInter.getCouleur() == dame.getCouleur()) {
                    return;
                }
                if (pionPrisPosition != -1) {
                    return;
                }
                pionPrisPosition = caseIntermediaire;
            }

            ligne += deplacementLigne;
            col += deplacementCol;
        }

        damier.ajouterPion(positionDepart, null);
        if (pionPrisPosition != -1) {
            damier.ajouterPion(pionPrisPosition, null);
            damier.ajouterPion(positionArrivee, dame);
            ajoutDeplacementHistorique(positionDepart, positionArrivee, "prise");
        } else {
            damier.ajouterPion(positionArrivee, dame);
            ajoutDeplacementHistorique(positionDepart, positionArrivee, "deplacement");
        }
        verifierFinPartie();
        changerTour();
    }

    public void setFinPartie(boolean status) {
        finPartie = status;
    }

    /**
     * Vérifie si la partie est terminée :
     * - Un joueur n'a plus de pion
     * - Un joueur ne peut plus faire aucun déplacement.
     */
    public void verifierFinPartie() {
        int nbPionsBlanc = 0;
        int nbPionsNoir = 0;

        // Compter les pions restants
        for (int pos = 1; pos <= 50; pos++) {
            Pion pion = damier.getPion(pos);
            if (pion != null) {
                if (pion.getCouleur() == Pion.Couleur.BLANC) {
                    nbPionsBlanc++;
                } else {
                    nbPionsNoir++;
                }
            }
        }

        // Condition 1 : plus aucun pion
        if (nbPionsBlanc == 0) {
            setFinPartie(true);
            gagnant = "noir";
            System.out.println("Victoire des noirs !");
            return;
        }
        if (nbPionsNoir == 0) {
            setFinPartie(true);
            gagnant = "blanc";
            System.out.println("Victoire des blancs !");
            return;
        }

        // Condition 2 : vérifier si un joueur peut encore jouer
        if (peutEncoreJouer("blanc")) {
            setFinPartie(true);
            gagnant = "noir";
            System.out.println("Victoire des noirs ! (les blancs ne peuvent plus jouer)");
            return;
        }
        if (peutEncoreJouer("noir")) {
            setFinPartie(true);
            gagnant = "blanc";
            System.out.println("Victoire des blancs ! (les noirs ne peuvent plus jouer)");
            return;
        }
    }

    /**
     * Vérifie si le joueur peut jouer : au moins un pion avec un déplacement ou une prise possible.
     *
     * @param joueur le joueur qui peut encore jouer.
     * @return booléen qui permet de rejouer ou non.
     */
    private boolean peutEncoreJouer(String joueur) {
        for (int pos = 1; pos <= 50; pos++) {
            Pion pion = damier.getPion(pos);
            if (pion != null && pion.getCouleur().name().equalsIgnoreCase(joueur)) {
                // Dame ou pion :
                if (pion instanceof Dame) {
                    if (!deplacementPossiblesDame(pos).isEmpty()) {
                        return false;
                    }
                } else {
                    if (!deplacementPossiblesPion(pos, pion).isEmpty() ||
                            !prisesPossiblesPion(pos, pion).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}