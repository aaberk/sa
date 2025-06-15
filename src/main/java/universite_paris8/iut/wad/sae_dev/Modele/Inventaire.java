package universite_paris8.iut.wad.sae_dev.Modele;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.MapChangeListener;

public class Inventaire {

    private final ObservableMap<TypeMateriaux, Integer> materiauxQuantites;

    public Inventaire() {
        this.materiauxQuantites = FXCollections.observableHashMap();

        this.materiauxQuantites.put(TypeMateriaux.EPEE, 0);
        this.materiauxQuantites.put(TypeMateriaux.HACHE, 0);
        this.materiauxQuantites.put(TypeMateriaux.PIOCHE, 0);
        this.materiauxQuantites.put(TypeMateriaux.COOKIE, 0);
        this.materiauxQuantites.put(TypeMateriaux.BROWNIE, 0);
        this.materiauxQuantites.put(TypeMateriaux.PELOUSE, 0);

    }

    public ObservableMap<TypeMateriaux, Integer> getMateriauxQuantites() {
        return materiauxQuantites;
    }

    public void ajouterMateriaux(TypeMateriaux type, int quantite) {
        if (materiauxQuantites.containsKey(type)) {
            int quantiteActuelle = materiauxQuantites.get(type);
            materiauxQuantites.put(type, quantiteActuelle + quantite);
        }
        else {
            materiauxQuantites.put(type, quantite);
        }
        System.out.println("Ajouté " + quantite + " " + type + " (total: " + materiauxQuantites.get(type) + ")");
    }

    public boolean retirerMateriaux(TypeMateriaux type, int quantite) {
        // Vérification si le matériau existe
        if (!materiauxQuantites.containsKey(type)) {
            return false;
        }

        int quantiteActuelle = materiauxQuantites.get(type);
        if (quantiteActuelle >= quantite) {
            int nouvelleQuantite = quantiteActuelle - quantite;
            if (nouvelleQuantite == 0) {
                materiauxQuantites.remove(type);
            }
            else {
                materiauxQuantites.put(type, nouvelleQuantite);
            }
            System.out.println("Retiré " + quantite + " " + type + " (reste: " + nouvelleQuantite + ")");
            return true;
        }
        return false;
    }

    public int getQuantite(TypeMateriaux type) {
        if (materiauxQuantites.containsKey(type)) {
            return materiauxQuantites.get(type);
        }
        return 0;
    }

    public boolean contientMateriaux(TypeMateriaux type) {
        if (materiauxQuantites.containsKey(type)) {
            return materiauxQuantites.get(type) > 0;
        }
        return false;
    }

    public static TypeMateriaux typeBlocVersMateriaux(int typeBloc) {
        switch (typeBloc) {
            case 2: case 12: case 15: case 16: return TypeMateriaux.PELOUSE;
            case 3: case 13: return TypeMateriaux.BROWNIE;
            case 4: case 14: return TypeMateriaux.COOKIE;
            default: return null;
        }
    }

    public static boolean estBlocCollectable(int typeBloc) {
        return typeBloc == 12 || typeBloc == 13 || typeBloc == 14
                || typeBloc == 15 || typeBloc == 16;
    }
}