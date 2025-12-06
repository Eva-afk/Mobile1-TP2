package cstjean.mobile.tp2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cstjean.mobile.tp2.logic.JeuDame;

public class HistoriqueFragment extends Fragment {
private Button HistoriqueBtn;
private NumberPicker nbBondsArriere;

JeuDame jeuDame;

    private RecyclerView recyclerViewHistorique;
    /** Adapter pour le RecyclerView des cours. */
    private EvenementHistoriqueAdapter adapterEvenementHistorique;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Initialise la vue du fragment, configure le RecyclerView et lie le
     * listener du bouton d'ajout pour démarrer l'activité d'ajout.
     *
     * @param inflater l'Inflater pour gonfler la vue
     * @param container le conteneur parent
     * @param savedInstanceState état sauvegardé
     * @return la vue créée pour ce fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historique, container, false);
        // Récupérer les éléments d'interface, ajouter les listener, etc.

        HistoriqueBtn = view.findViewById(R.id.retourHistoriqueBtn);
        nbBondsArriere = view.findViewById(R.id.nombreBondsArriere);

        recyclerViewHistorique = view.findViewById(R.id.recycler_view_historique);
        recyclerViewHistorique.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterEvenementHistorique = new EvenementHistoriqueAdapter();
        recyclerViewHistorique.setAdapter(adapterEvenementHistorique);

        HistoriqueBtn.setOnClickListener(v -> {
            jeuDame.retourSurHistorique(nbBondsArriere.getValue());
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapterEvenementHistorique = new EvenementHistoriqueAdapter();
        recyclerViewHistorique.setAdapter(adapterEvenementHistorique);
    }
}