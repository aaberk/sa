package universite_paris8.iut.wad.sae_dev.Modele;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Inventaire {
    // SI l'inventaire est fermé quand on joue ObservableLIst ne sert à rien, ça pourrait une list simple
    // SI l'inventaire reste affiché, il faut poser unlistener sur l'observablelist
    private ObservableMap<TypeMateriaux, Integer> materiauxQuantites;
    public Inventaire() {
        this.materiauxQuantites = FXCollections.observableHashMap();
        this.materiauxQuantites.put(TypeMateriaux.COOKIE, 0);
        this.materiauxQuantites.put(TypeMateriaux.BROWNIE, 0);
        this.materiauxQuantites.put(TypeMateriaux.PELOUSE, 0);    }

    public ObservableMap<TypeMateriaux, Integer> getMateriauxQuantites() {
        return materiauxQuantites;
    }

    public void ajouterMateriaux(TypeMateriaux type, int quantite) {
        int quantiteActuelle = materiauxQuantites.getOrDefault(type, 0);
        materiauxQuantites.put(type, quantiteActuelle + quantite);
        System.out.println("Ajouté " + quantite + " " + type + " (total: " + materiauxQuantites.get(type) + ")");
    }

    public boolean retirerMateriaux(TypeMateriaux type, int quantite) {
        int quantiteActuelle = materiauxQuantites.getOrDefault(type, 0);
        if (quantiteActuelle >= quantite) {
            int nouvelleQuantite = quantiteActuelle - quantite;
            if (nouvelleQuantite == 0) {
                materiauxQuantites.remove(type);
            } else {
                materiauxQuantites.put(type, nouvelleQuantite);
            }
            System.out.println("Retiré " + quantite + " " + type + " (reste: " + nouvelleQuantite + ")");
            return true;
        }
        return false;
    }

    public int getQuantite(TypeMateriaux type) {
        return materiauxQuantites.getOrDefault(type, 0);
    }

    public boolean contientMateriaux(TypeMateriaux type) {
        return materiauxQuantites.containsKey(type) && materiauxQuantites.get(type) > 0;
    }


    public static TypeMateriaux typeBlocVersMateriaux(int typeBloc) {
        switch (typeBloc) {
            case 2: case 12: return TypeMateriaux.PELOUSE;
            case 3: case 13: return TypeMateriaux.BROWNIE;
            case 4: case 14: return TypeMateriaux.COOKIE;
            default: return null;
        }
    }

    public static boolean estBlocCollectable(int typeBloc) {
        return typeBloc == 2 || typeBloc == 3 || typeBloc == 4 ||
                typeBloc == 12 || typeBloc == 13 || typeBloc == 14;
    }
}