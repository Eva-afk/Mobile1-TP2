package cstjean.mobile.tp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cstjean.mobile.tp2.logic.Historique;

public class EvenementHistoriqueAdapter extends RecyclerView.Adapter<EvenementHistoriqueViewHolder> {

    private final Historique historique = Historique.getInstance();

    /**
     * Constructeur de l'adapter.
     */
    public EvenementHistoriqueAdapter() {
    }

    /**
     * Crée et retourne un ViewHolder pour un item de la liste.
     *
     * @param parent la vue parente
     * @param viewType le type de vue
     * @return un nouveau CoursSessionViewHolder
     */
    @NonNull
    @Override
    public EvenementHistoriqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_historique, parent, false);
        return new EvenementHistoriqueViewHolder(view);
    }

    /**
     * Lie les données du singleton au ViewHolder à la position donnée.
     *
     * @param holder le ViewHolder à lier
     * @param position la position de l'élément
     */
    @Override
    public void onBindViewHolder(@NonNull EvenementHistoriqueViewHolder holder, int position) {
        holder.bindEvenementHistorique(historique.getListeHistorique().get(position), position);
    }

    /**
     * Retourne le nombre d'éléments à afficher à l'aide du singleton.
     *
     * @return le nombre d'éléments
     */
    @Override
    public int getItemCount() {
         return historique.getListeHistorique().size();
    }
}

/**
 * ViewHolder pour un élément représentant un cours.
 * Gère le clic pour démarrer l'activity de détails à l'aide de l'index courant.
 */
class EvenementHistoriqueViewHolder extends RecyclerView.ViewHolder {
    /** Vue TextView pour le département du cours. */
    private final TextView action;

    /** Index courant de l'élément dans la liste. */
    private int indexCourant;

    /**
     * Constructeur du ViewHolder.
     *
     * @param itemView l'interface de l'élément de la liste
     */
    public EvenementHistoriqueViewHolder(@NonNull View itemView) {
        super(itemView);
        action = itemView.findViewById(R.id.item_action_historique);
    }

    /**
     * Remplit les vues avec les informations de la session et mémorise l'index.
     *
     * @param evenementHistorique la session à afficher
     * @param position la position dans la liste
     */
    void bindEvenementHistorique(String evenementHistorique, int position) {
        action.setText(evenementHistorique);
        indexCourant = position;
    }
}
