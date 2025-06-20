package universite_paris8.iut.wad.sae_dev.Modele;

import java.util.*;

public class GestionnaireDeRecettes {
    private static final List<Recette> recettes = new ArrayList<>();

    static {
        recettes.add(new Recette(TypeMateriaux.EPEE, Map.of(
                TypeMateriaux.COOKIE, 2,
                TypeMateriaux.BROWNIE, 1
        )));

        recettes.add(new Recette(TypeMateriaux.ARC, Map.of(
                TypeMateriaux.BROWNIE, 2
        )));
    }

    public static List<Recette> getRecettes() {
        return recettes;
    }

    public static Recette getRecettePour(TypeMateriaux resultat) {
        for (Recette r : recettes) {
            if (r.getResultat() == resultat) return r;
        }
        return null;
    }
}
