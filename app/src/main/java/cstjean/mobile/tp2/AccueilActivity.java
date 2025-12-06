package cstjean.mobile.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AccueilActivity extends AppCompatActivity {

    private EditText txtNomJoueur1;
    private EditText txtNomJoueur2;
    private Button btnJouer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accueil);

        txtNomJoueur1 = findViewById(R.id.txtNomJoueur1);
        txtNomJoueur2 = findViewById(R.id.txtNomJoueur2);
        btnJouer = findViewById(R.id.btnJouer);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verifierChamps();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        txtNomJoueur1.addTextChangedListener(watcher);
        txtNomJoueur2.addTextChangedListener(watcher);

        btnJouer.setOnClickListener(v -> {
            Intent intent = new Intent(AccueilActivity.this, MainActivity.class);
            intent.putExtra("joueur1", txtNomJoueur1.getText().toString().trim());
            intent.putExtra("joueur2", txtNomJoueur2.getText().toString().trim());
            startActivity(intent);
        });
    }
    private void verifierChamps() {
        String joueur1 = txtNomJoueur1.getText().toString().trim();
        String joueur2 = txtNomJoueur2.getText().toString().trim();

        btnJouer.setEnabled(!joueur1.isEmpty() && !joueur2.isEmpty());
    }
}