package cstjean.mobile.tp2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import cstjean.mobile.tp2.logic.Historique;
import cstjean.mobile.tp2.logic.JeuDame;
import cstjean.mobile.tp2.logic.affichage.Affichage;
import cstjean.mobile.tp2.logic.joueurs.Joueur;
import cstjean.mobile.tp2.logic.pions.Dame;
import cstjean.mobile.tp2.logic.pions.Pion;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * the main Page for the board game.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Gestionnaire des actions pour le jeu de dames.
     */
    private JeuDame jeuDame;

    /**
     * Historique des actions.
     */
    private Historique historique = Historique.getInstance();

    /**
     * Texte pour les déplacements.
     */
    private TextView deplacements;

    /**
     * Bouton pour un retour en arrière.
     */
    private Button boutonHistorique;

    /**
     * Affichage des éléments sur le damier.
     */
    private Affichage affichageDamier;

    /**
     * grille qui contient toute les cases du damier.
     */
    private GridLayout grilleDamier;

    /**
     * texte qui affiche a qui appertient le tour du jeu.
     */
    private TextView textChangementTour;

    /**
     * texte qui contient le nom de joueur1 entré.
     */
    private String nomjoueur1;

    /**
     * texte qui contient le nom de joueur2 entré.
     */
    private String nomjoueur2;

    private final Map<Integer, Button> posToButton = new HashMap<>();
    private int selectedPosition = -1;
    private LinkedList<Integer> currentTargets = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nomjoueur1 = getIntent().getStringExtra("joueur1");
        nomjoueur2 = getIntent().getStringExtra("joueur2");
        textChangementTour = findViewById(R.id.changement_tour);
        jeuDame = JeuDame.getInstance();
        grilleDamier = findViewById(R.id.damier_grid);
        deplacements = findViewById(R.id.deplacements_possibles);
        boutonHistorique = findViewById(R.id.bouton_historique);
        affichageDamier = new Affichage(jeuDame.getDamier());
        jeuDame.getDamier().ajouterPion(50, new Dame());

        boutonHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoriqueActivity.class);
                startActivity(intent);
            }
        });

        String stringdamier = affichageDamier.getAffichageFinal().replaceAll("\\s+", "");
        char[][] affichage = new char[10][10];
        for (int i = 0; i < 100; i++) {
            int ligne = i / 10;   // division entière → ligne
            int colonne = i % 10; // reste → colonne
            if (stringdamier.charAt(i) == '-') {
                affichage[ligne][colonne] = ' ';
            } else {
                affichage[ligne][colonne] = stringdamier.charAt(i);
            }
        }
        Log.d("AffichageDamier", "Contenu du damier :\n" + stringdamier);
        Log.d("AffichageDamier", "taille de la string :\n" + stringdamier.length());
        for (int i = 0; i < 10; i++) {
            StringBuilder ligne = new StringBuilder();
            for (int j = 0; j < 10; j++) {
                ligne.append(affichage[i][j]).append(" ");
            }
            Log.d("DamierLigne", "Ligne " + i + ": " + ligne);
        }

        int ids = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (((i + j) % 2) != 0) {
                    ids++;
                }
                grilleDamier.addView(creerBouton(i, j, ids, affichage[i][j]));
            }
            Log.d("DamierLigne", "affichage Ligne " + i);
        }
        Log.d("GridCheck", "Nombre de boutons ajoutés : " + grilleDamier.getChildCount());
        refreshUi();
    }

    private Button creerBouton(int i, int j, int id, char contenu) {
        Button button = new Button(this);
        button.setTag(id);
        String displayText;
        if (contenu == 'P') {
            displayText = "⚫";
        } else if (contenu == 'p') {
            displayText = "⚪";
        } else if (contenu == 'D') {
            button.setTextColor(Color.BLACK);
            displayText = "♔";
        } else if (contenu == 'd') {
            button.setTextColor(Color.WHITE);
            displayText = "♕";
        } else {
            displayText = String.valueOf(contenu);
        }

        button.setText(displayText);
        button.setTextSize(20f);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                GridLayout.spec(i, 1f),
                GridLayout.spec(j, 1f)
        );
        params.width = 0;
        params.height = 0;
        params.setMargins(2, 2, 2, 2);
        button.setLayoutParams(params);
        button.setPadding(8, 8, 8, 8);
        if (((i + j) % 2) == 0) {
            button.setBackgroundColor(Color.parseColor("#F5DEB3"));
            button.setEnabled(false);
            button.setId(View.generateViewId());
        } else {
            button.setBackgroundColor(Color.parseColor("#8B4513"));
            button.setEnabled(true);
            button.setId(id);
            posToButton.put(id, button);
        }
        button.setOnClickListener(v -> {
            Pion pionActuel;
            if (button.isEnabled()) {
                int positionPion = button.getId();
                pionActuel = jeuDame.getDamier().getPion(positionPion);
                Log.d("", "l'id du bouton est " + button.getId());
                jeuDame.deplacementPossiblesPion(positionPion, pionActuel);
                if (jeuDame.getDamier().getPion(positionPion) instanceof Dame) {
                    LinkedList<Integer> liste = jeuDame.deplacementPossiblesDame(positionPion);
                    String texte = TextUtils.join(", ", liste);

                    deplacements.setText(String.format(Locale.getDefault(),
                            "deplacement possible pour la dame %d: %s", positionPion, texte));
                } else {
                    LinkedList<Integer> liste = jeuDame.deplacementPossiblesPion(positionPion, pionActuel);
                    if (!jeuDame.prisesPossiblesPion(positionPion, pionActuel).isEmpty()) {
                        liste.addAll(jeuDame.prisesPossiblesPion(positionPion, pionActuel));
                    }
                    String texte = TextUtils.join(", ", liste);

                    deplacements.setText(String.format(Locale.getDefault(),
                            "deplacement possible pour le pion %d: %s", positionPion, texte));
                }
                onCaseClicked(positionPion);
            }

        });

        return button;
    }

    /**
     * methode pour raffraichir l'affichage du damier a chaque interaction.
     */
    private void refreshUi() {
        if (Objects.requireNonNull(jeuDame.getListeJoueurs().get("noir")).getStatusTour()) {
            textChangementTour.setText(String.format(Locale.getDefault(), nomjoueur2));
        } else if (Objects.requireNonNull(jeuDame.getListeJoueurs().get("blanc")).getStatusTour()) {
            textChangementTour.setText(String.format(Locale.getDefault(), nomjoueur1));
        }
    }

    private void onCaseClicked(Integer pos) {
        if (pos == null || pos < 1 || pos > 50) {
            return;
        }

        Pion pion = jeuDame.getDamier().getPion(pos);
        Joueur joueurBlanc = jeuDame.getListeJoueurs().get("blanc");
        Joueur joueurNoir = jeuDame.getListeJoueurs().get("noir");
        assert joueurBlanc != null;
        boolean isBlancTour = joueurBlanc.getStatusTour();
        assert joueurNoir != null;
        boolean isNoirTour = joueurNoir.getStatusTour();

        // Si on touche une piece du joueur actif -> sélectionner et afficher surbrillance
        if (pion != null && ((pion.getCouleur() == Pion.Couleur.BLANC && isBlancTour) ||
                (pion.getCouleur() == Pion.Couleur.NOIR && isNoirTour))) {
            selectedPosition = pos;
            if (jeuDame.verifierDame(pos)) {
                currentTargets = jeuDame.deplacementPossiblesDame(pos);
            } else {
                // on combine déplacements simples et prises (selon règles)
                LinkedList<Integer> prises = jeuDame.prisesPossiblesPion(pos, pion);
                if (!prises.isEmpty()) {
                    currentTargets = prises;
                } else {
                    currentTargets = jeuDame.deplacementPossiblesPion(pos, pion);
                }
            }
            highlightTargets(currentTargets);
            return;
        }

        //  Si on a une sélection et on clique une cible -> exécuter le mouvement
        if (selectedPosition != -1 && currentTargets.contains(pos)) {
            executeMove(selectedPosition, pos);
            selectedPosition = -1;
            currentTargets.clear();
            clearHighlights();
            return;
        }

        selectedPosition = -1;
        currentTargets.clear();
        clearHighlights();
    }

    private void highlightTargets(LinkedList<Integer> targets) {
        clearHighlights();
        for (Integer t : targets) {
            Button b = posToButton.get(t);
            if (b != null) {
                b.setBackgroundColor(Color.parseColor("#FFD54F"));
            }
        }
    }

    private void clearHighlights() {
        for (Map.Entry<Integer, Button> e : posToButton.entrySet()) {
            Button b = e.getValue();
            // Remet la couleur marron par défaut
            b.setBackgroundColor(Color.parseColor("#8B4513"));
        }
    }

    private void executeMove(int from, int to) {
        Pion p = jeuDame.getDamier().getPion(from);
        if (p == null) {
            return;
        }

        if (jeuDame.verifierDame(from)) {
            jeuDame.deplacementDame(from, to);
        } else {
            // prioriser prise si disponible
            LinkedList<Integer> prises = jeuDame.prisesPossiblesPion(from, p);
            if (prises.contains(to)) {
                jeuDame.prisePion(from, to);
            } else {
                jeuDame.deplacementPion(from, to);
            }
        }
        updateButtonsDamier();
        jeuDame.verifierFinPartie();
        if (jeuDame.getGagnant() != null) {
            afficherFinDePartie();
        }
    }

    private void updateButtonsDamier() {
        for (int pos = 1; pos <= 50; pos++) {
            Button b = posToButton.get(pos);
            if (b == null) {
                continue;
            }
            Pion p = jeuDame.getDamier().getPion(pos);
            if (p == null) {
                b.setText((String.valueOf("")));
            } else {
                char representation = p.getRepresentation();
                String displayText;

                if (representation == 'P') {
                    displayText = "⚫";
                } else if (representation == 'p') {
                    displayText = "⚪";
                } else if (representation == 'D') {
                    b.setTextColor(Color.BLACK);
                    displayText = "♔";
                } else if (representation == 'd') {
                    b.setTextColor(Color.WHITE);
                    displayText = "♕";
                } else {
                    displayText = String.valueOf(representation);
                }

                b.setText(displayText);
            }
        }
    }

    private void afficherFinDePartie() {
        String gagnant = jeuDame.getGagnant();
        String message = (gagnant == null) ?
                "Égalité!" :
                "Victoire du joueur " + gagnant + " 🎉";

        textChangementTour.setText(message);
        boutonHistorique.setText(R.string.recommencer);
        boutonHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommencerLaPartie();
            }
        });
    }

    private void recommencerLaPartie() {
        jeuDame.reset();
        historique.reset();
        refreshUi();
        updateButtonsDamier();
        boutonHistorique.setText(R.string.retour);
        boutonHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoriqueActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = new Intent(MainActivity.this, AccueilActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateButtonsDamier();
        refreshUi();
    }


}