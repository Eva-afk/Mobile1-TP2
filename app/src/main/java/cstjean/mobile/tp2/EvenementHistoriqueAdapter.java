package cstjean.mobile.tp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cstjean.mobile.tp2.logic.Historique;

/**
 * Adapter pour afficher l'historique des événements dans une RecyclerView.
 * Utilise un singleton pour récupérer les données.
 */
public class EvenementHistoriqueAdapter extends RecyclerView.Adapter<EvenementHistoriqueViewHolder> {

    /**
     * Singleton contenant les données de l'historique.
     */
    private final Historique historique = Historique.getInstance();

    /**
     * Constructeur de l'adapter.
     * Initialise l'adapter sans paramètres supplémentaires.
     */
    public EvenementHistoriqueAdapter() {
    }

    /**
     * Crée et retourne un ViewHolder pour un item de la liste.
     *
     * @param parent   la vue parente dans laquelle l'élément sera affiché
     * @param viewType le type de vue (non utilisé ici)
     * @return un nouveau EvenementHistoriqueViewHolder
     */
    @NonNull
    @Override
    public EvenementHistoriqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_historique, parent, false);
        return new EvenementHistoriqueViewHolder(view);
    }

    /**
     * Lie les données de l'historique au ViewHolder à la position donnée.
     *
     * @param holder   le ViewHolder à lier
     * @param position la position de l'élément dans la liste
     */
    @Override
    public void onBindViewHolder(@NonNull EvenementHistoriqueViewHolder holder, int position) {
        holder.bindEvenementHistorique(historique.getListeHistorique().get(position));
    }

    /**
     * Retourne le nombre total d'éléments dans l'historique.
     *
     * @return le nombre d'éléments dans la liste de l'historique
     */
    @Override
    public int getItemCount() {
        return historique.getListeHistorique().size();
    }
}

/**
 * ViewHolder pour un élément de l'historique des événements.
 * Permet de gérer l'affichage et les interactions pour un élément spécifique.
 */
class EvenementHistoriqueViewHolder extends RecyclerView.ViewHolder {
    /**
     * Vue TextView pour afficher l'action associée à l'événement.
     */
    private final TextView action;

    /**
     * Constructeur du ViewHolder.
     * Initialise les composants de l'interface utilisateur pour un élément.
     *
     * @param itemView la vue représentant un élément de la liste
     */
    public EvenementHistoriqueViewHolder(@NonNull View itemView) {
        super(itemView);
        action = itemView.findViewById(R.id.item_action_historique);
    }

    /**
     * Remplit les vues avec les informations de l'événement historique et mémorise l'index.
     *
     * @param evenementHistorique l'événement historique à afficher
     */
    void bindEvenementHistorique(String evenementHistorique) {
        action.setText(evenementHistorique);
    }
}