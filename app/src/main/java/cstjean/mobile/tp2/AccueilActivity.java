package cstjean.mobile.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activité d'accueil de l'application.
 * Permet à deux joueurs de saisir leurs noms avant de commencer une partie.
 */
public class AccueilActivity extends AppCompatActivity {

    /**
     * Champ de saisie pour le nom du joueur 1.
     */
    private EditText txtNomJoueur1;

    /**
     * Champ de saisie pour le nom du joueur 2.
     */
    private EditText txtNomJoueur2;

    /**
     * Bouton pour démarrer la partie.
     */
    private Button btnJouer;

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise les composants de l'interface utilisateur et configure les écouteurs.
     *
     * @param savedInstanceState État sauvegardé de l'activité, s'il existe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Active le mode Edge-to-Edge pour l'activité
        setContentView(R.layout.activity_accueil);

        // Liaison des composants de l'interface utilisateur
        txtNomJoueur1 = findViewById(R.id.txtNomJoueur1);
        txtNomJoueur2 = findViewById(R.id.txtNomJoueur2);
        btnJouer = findViewById(R.id.btnJouer);

        // Création d'un TextWatcher pour surveiller les changements dans les champs de texte
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verifierChamps(); // Vérifie si les champs sont remplis
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        // Ajout du TextWatcher aux champs de texte
        txtNomJoueur1.addTextChangedListener(watcher);
        txtNomJoueur2.addTextChangedListener(watcher);

        // Configuration de l'action du bouton "Jouer"
        btnJouer.setOnClickListener(v -> {
            // Création d'une intention pour démarrer l'activité principale
            Intent intent = new Intent(AccueilActivity.this, MainActivity.class);
            intent.putExtra("joueur1", txtNomJoueur1.getText().toString().trim()); // Ajout du nom du joueur 1
            intent.putExtra("joueur2", txtNomJoueur2.getText().toString().trim()); // Ajout du nom du joueur 2
            startActivity(intent); // Démarrage de l'activité principale
        });
    }

    /**
     * Vérifie si les champs de texte des noms des joueurs sont remplis.
     * Active ou désactive le bouton "Jouer" en conséquence.
     */
    private void verifierChamps() {
        String joueur1 = txtNomJoueur1.getText().toString().trim(); // Récupère le texte du champ joueur 1
        String joueur2 = txtNomJoueur2.getText().toString().trim(); // Récupère le texte du champ joueur 2

        // Active le bouton "Jouer" si les deux champs sont remplis
        btnJouer.setEnabled(!joueur1.isEmpty() && !joueur2.isEmpty());
    }
}