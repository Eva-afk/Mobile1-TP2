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
import cstjean.mobile.tp2.logic.Historique;
import cstjean.mobile.tp2.logic.JeuDame;
import java.util.Objects;

/**
 * Fragment pour l'historique des actions.
 */
public class HistoriqueFragment extends Fragment {

    /**
     * Picker qui permet de choisir le nombre de retour en arrière voulu.
     */
    private NumberPicker nbBondsArriere;

    /**
     * instance de JeuDame pour gérer les actions en lien avec l'historique.
     */
    private final JeuDame jeuDame = JeuDame.getInstance();

    /**
     * Vue recyclée pour afficher les éléments de l'historique.
     */
    private RecyclerView recyclerViewHistorique;
    /**
     * Adapter pour le RecyclerView des cours.
     */
    private EvenementHistoriqueAdapter adapterEvenementHistorique;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Initialise la vue du fragment, configure le RecyclerView et lie le
     * listener du bouton d'ajout pour démarrer l'activité d'ajout.
     *
     * @param inflater           l'Inflater pour gonfler la vue
     * @param container          le conteneur parent
     * @param savedInstanceState état sauvegardé
     * @return la vue créée pour ce fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historique, container, false);

        nbBondsArriere = view.findViewById(R.id.numberPicker);

        recyclerViewHistorique = view.findViewById(R.id.recycler_view_historique);
        recyclerViewHistorique.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterEvenementHistorique = new EvenementHistoriqueAdapter();
        recyclerViewHistorique.setAdapter(adapterEvenementHistorique);

        int historiqueSize = Historique.getInstance().getListeHistorique().size();

        nbBondsArriere.setMinValue(0);

        if (historiqueSize > 0) {
            nbBondsArriere.setMaxValue(historiqueSize);
            nbBondsArriere.setValue(1);
        } else {
            nbBondsArriere.setMaxValue(0);
        }

        Button historiqueBtn = view.findViewById(R.id.retourHistoriqueBtn);

        historiqueBtn.setOnClickListener(v -> {
            jeuDame.retourSurHistorique(nbBondsArriere.getValue());
            Objects.requireNonNull(getActivity()).finish();
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