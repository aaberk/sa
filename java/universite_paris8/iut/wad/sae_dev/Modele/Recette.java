package universite_paris8.iut.wad.sae_dev.Modele;

import java.util.Map;

public class Recette {
    private final TypeMateriaux resultat;
    private final Map<TypeMateriaux, Integer> ingredients;

    public Recette(TypeMateriaux resultat, Map<TypeMateriaux, Integer> ingredients) {
        this.resultat = resultat;
        this.ingredients = ingredients;
    }

    public TypeMateriaux getResultat() {
        return resultat;
    }

    public Map<TypeMateriaux, Integer> getIngredients() {
        return ingredients;
    }
}
