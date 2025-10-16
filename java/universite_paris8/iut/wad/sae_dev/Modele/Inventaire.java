package universite_paris8.iut.wad.sae_dev.Modele;

import universite_paris8.iut.wad.sae_dev.Modele.Entites.Joueur;
import universite_paris8.iut.wad.sae_dev.Modele.Utilisables.ObjetUtilisable;

import java.util.ArrayList;
import java.util.List;

public class Inventaire {
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

    public int getTaille() {
        return materiaux.size();
    }

    public TypeMateriaux getMateriau(int index) {
        return materiaux.get(index);
    }

    public int getQuantite(int index) {
        return quantites.get(index);
    }

    public int getQuantite(TypeMateriaux type) {
        for (int i = 0; i < materiaux.size(); i++) {
            if (materiaux.get(i) == type) {
                return quantites.get(i);
            }
        }
        return 0;
    }

    public boolean contientMateriaux(TypeMateriaux type) {
        for (int i = 0; i < materiaux.size(); i++) {
            if (materiaux.get(i) == type) {
                return quantites.get(i) > 0;
            }
        }
        return false;
    }

    public void utiliserObjetActuel(Joueur joueur, Jeu jeu, Terraformer terraformer) {
        ObjetUtilisable objet = getObjetSelectionne();

        if (objet != null && getQuantite(objet.getType()) > 0) {
            int x = joueur.getX() / joueur.getTerrain().getTailleTuile();
            int y = joueur.getY() / joueur.getTerrain().getTailleTuile();

            objet.utiliser(x, y, jeu, terraformer);
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

    public static boolean estBlocCollectable(int typeBloc) {
        return typeBloc == 12 || typeBloc == 13 || typeBloc == 14
                || typeBloc == 15 || typeBloc == 16;
    }
}