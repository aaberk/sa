package universite_paris8.iut.wad.sae_dev.Modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Inventaire {
    // SI l'inventaire est fermé quand on joue ObservableLIst ne sert à rien, ça pourrait une list simple
    // SI l'inventaire reste affiché, il faut poser unlistener sur l'observablelist
    private final List<TypeMateriaux> materiaux;
    private final List<Integer> quantites;
    private int caseSelectionnee;

    public Inventaire() {
        materiaux = new ArrayList<>();
        quantites = new ArrayList<>();

        materiaux.add(TypeMateriaux.PIOCHE);
        quantites.add(1);

        materiaux.add(TypeMateriaux.EPEE);
        quantites.add(0);

        materiaux.add(TypeMateriaux.ARC);
        quantites.add(0);

        materiaux.add(TypeMateriaux.COOKIE);
        quantites.add(0);

        materiaux.add(TypeMateriaux.BROWNIE);
        quantites.add(0);

        materiaux.add(TypeMateriaux.PELOUSE);
        quantites.add(0);

        this.caseSelectionnee = -1;
    }

    public int getTaille () {
        return materiaux.size();
    }

    public TypeMateriaux getMateriau(int index) {
        return materiaux.get(index);
    }

    public int getQuantite(int index) {
        return quantites.get(index);
    }



    //    public void setQuantite(int index, int quantite) {
//        quantites.set(index, quantite);
//    }
//
//    public void ajouterQuantite(int index, int q) {
//        int actuelle = quantites.get(index);
//        quantites.set(index, (actuelle + q));
//    }
//
//    public void retirerQuantite(int index, int q) {
//        int actuelle = quantites.get(index);
//        quantites.set(index, Math.max(0, actuelle - q));
//    }
//
    // Pour obtenir la quantité par matériau directement
    public int getQuantite(TypeMateriaux type) {
        for (int i = 0; i < materiaux.size(); i++) {
            if (materiaux.get(i) == type) {
                return quantites.get(i);
            }
        }
        return 0;
    }
//
//    public List<TypeMateriaux> getListeMateriaux() {
//        return materiaux;
//    }

    public boolean contientMateriaux(TypeMateriaux type) {
        for (int i = 0; i < materiaux.size(); i++) {
            if (materiaux.get(i) == type) {
                return quantites.get(i) > 0;
            }
        }
        return false;
    }

    public void utiliserObjetActuel(Joueur joueur, Terraformer terraformer) {
        ObjetUtilisable objet = getObjetSelectionne();

        if (objet != null && getQuantite(objet.getType()) > 0) {
            int x = joueur.getX() / joueur.getTerrain().getTailleTuile();
            int y = joueur.getY() / joueur.getTerrain().getTailleTuile();

            objet.utiliser(x, y, joueur.getTerrain(), terraformer);
        } else {
            System.out.println("Aucun objet utilisable sélectionné ou quantité nulle");
        }
    }


    public boolean retirerMateriaux(TypeMateriaux type, int q) {
        for (int i = 0; i < materiaux.size(); i++) {
            if (materiaux.get(i) == type) {
                int actuelle = quantites.get(i);
                if (actuelle >= q) {
                    quantites.set(i, actuelle - q);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public void ajouterMateriaux(TypeMateriaux type, int q) {
        for (int i = 0; i < materiaux.size(); i++) {
            if (materiaux.get(i) == type) {
                int actuelle = quantites.get(i);
                quantites.set(i, actuelle + q);
                return;
            }
        }
        materiaux.add(type);
        quantites.add(q);
    }

//    public TypeMateriaux getTypeSelectionne() {
//        if (caseSelectionnee >= 0 && caseSelectionnee < materiaux.size()) {
//            return materiaux.get(caseSelectionnee);
//        }
//        return null;
//    }


    public static TypeMateriaux typeBlocVersMateriaux(int typeBloc) {
        switch (typeBloc) {
            case 2: case 12: case 15: case 16: return TypeMateriaux.PELOUSE;
            case 3: case 13: return TypeMateriaux.COOKIE;
            case 4: case 14: return TypeMateriaux.BROWNIE;
            default: return null;
        }
    }

    public int materiauxVersTypeBloc(TypeMateriaux mat) {
        return switch (mat) {
            case PELOUSE -> 5;
            case COOKIE -> 3;
            case BROWNIE -> 4;
            case PIOCHE, ARC, EPEE -> 1; // on retourne ciel pour les objets non posables
            default -> 1;
        };
    }

    public void selectionnerCase(int index) {
        this.caseSelectionnee = index;
    }

    public int retournerCaseSelectionnee() {
        return caseSelectionnee;
    }

    public TypeMateriaux getMateriauCaseSelectionne() {
        if (caseSelectionnee >= 0 && caseSelectionnee < materiaux.size()) {
            return materiaux.get(caseSelectionnee);
        }
        return null;
    }

    public ObjetUtilisable getObjetSelectionne() {
        if (caseSelectionnee >= 0 && caseSelectionnee < materiaux.size()) {
            TypeMateriaux mat = materiaux.get(caseSelectionnee);
            if (mat != null) {
                return mat.getObjet();
            }
        }
        return null;
    }




//    public static ObjetUtilisable typeVersObjet (TypeMateriaux type){
//        switch (type) {
//            case EPEE -> new Epee();
//            case HACHE -> new Arc();
//            case PIOCHE -> new Pioche();
//            default: return null;
//        }
//    }

    public static boolean estBlocCollectable(int typeBloc) {
        return typeBloc == 12 || typeBloc == 13 || typeBloc == 14
                || typeBloc == 15 || typeBloc == 16;
    }

    public boolean fabriquer(TypeMateriaux typeAFabriquer) {
        Recette recette = GestionnaireDeRecettes.getRecettePour(typeAFabriquer);
        if (recette == null) {
            System.out.println("Aucune recette pour ce matériau.");
            return false;
        }

        // Vérifie si l'inventaire contient tous les ingrédients en quantité suffisante
        for (Map.Entry<TypeMateriaux, Integer> entry : recette.getIngredients().entrySet()) {
            TypeMateriaux ingredient = entry.getKey();
            int quantiteRequise = entry.getValue();

            if (getQuantite(ingredient) < quantiteRequise) {
                System.out.println("Pas assez de " + ingredient + " pour fabriquer " + typeAFabriquer);
                return false;
            }
        }

        // Retire les ingrédients
        for (Map.Entry<TypeMateriaux, Integer> entry : recette.getIngredients().entrySet()) {
            retirerMateriaux(entry.getKey(), entry.getValue());
        }

        // Ajoute l'objet fabriqué
        ajouterMateriaux(typeAFabriquer, 1);
        System.out.println("Fabrication réussie de : " + typeAFabriquer);
        return true;
    }

}