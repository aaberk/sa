package universite_paris8.iut.wad.sae_dev.Modele;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class InventaireTest {

    private Inventaire inventaire;

    @BeforeEach
    public void setUp() {
        inventaire = new Inventaire();
    }

    @Test
    public void testAjouterMateriaux() {
        inventaire.ajouterMateriaux(TypeMateriaux.COOKIE, 3);
        assertEquals(3, inventaire.getQuantite(TypeMateriaux.COOKIE));
    }

    @Test
    public void testAjouterMateriauxAdditionne() {
        inventaire.ajouterMateriaux(TypeMateriaux.COOKIE, 2);
        inventaire.ajouterMateriaux(TypeMateriaux.COOKIE, 5);
        assertEquals(7, inventaire.getQuantite(TypeMateriaux.COOKIE));
    }

    @Test
    public void testRetirerMateriaux() {
        inventaire.ajouterMateriaux(TypeMateriaux.BROWNIE, 4);
        inventaire.retirerMateriaux(TypeMateriaux.BROWNIE, 2);
        assertEquals(2, inventaire.getQuantite(TypeMateriaux.BROWNIE));
    }

    @Test
    public void testRetirerPlusQuePossede() {
        inventaire.ajouterMateriaux(TypeMateriaux.BROWNIE, 1);
        inventaire.retirerMateriaux(TypeMateriaux.BROWNIE, 5); // on suppose que ça retire ce qu’il peut
        assertEquals(0, inventaire.getQuantite(TypeMateriaux.BROWNIE));
    }

    @Test
    public void testQuantiteInexistante() {
        assertEquals(0, inventaire.getQuantite(TypeMateriaux.EPEE));
    }

    @Test
    public void testContientMateriau() {
        inventaire.ajouterMateriaux(TypeMateriaux.ARC, 1);
        assertTrue(inventaire.contientMateriaux(TypeMateriaux.ARC));
        assertFalse(inventaire.contientMateriaux(TypeMateriaux.COOKIE));
    }
}