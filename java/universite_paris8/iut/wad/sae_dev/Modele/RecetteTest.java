package universite_paris8.iut.wad.sae_dev.Modele;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class RecetteTest {

    @Test
    public void testGetResultat() {
        Recette recette = new Recette(TypeMateriaux.ARC, Map.of(
                TypeMateriaux.BROWNIE, 2
        ));

        assertEquals(TypeMateriaux.ARC, recette.getResultat(), "Le résultat de la recette devrait être ARC");
    }

    @Test
    public void testGetIngredients() {
        Map<TypeMateriaux, Integer> ingredients = Map.of(
                TypeMateriaux.COOKIE, 2,
                TypeMateriaux.BROWNIE, 1
        );

        Recette recette = new Recette(TypeMateriaux.EPEE, ingredients);

        assertEquals(ingredients, recette.getIngredients(), "Les ingrédients devraient correspondre à ceux passés au constructeur");
    }

    @Test
    public void testIngredientManquant() {
        Recette recette = new Recette(TypeMateriaux.EPEE, Map.of(
                TypeMateriaux.COOKIE, 1
        ));

        assertFalse(recette.getIngredients().containsKey(TypeMateriaux.BROWNIE), "La recette ne devrait pas contenir de BROWNIE");
    }
}