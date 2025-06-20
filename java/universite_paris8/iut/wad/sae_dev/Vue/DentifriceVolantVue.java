package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.DentifriceVolant;

public class DentifriceVolantVue extends PersonnageVue {

    public DentifriceVolantVue(DentifriceVolant ennemi, Pane pane) {
        super(ennemi, pane);
    }

    @Override
    public void chargerImages() {
        String chemin = "/universite_paris8/iut/wad/sae_dev/images/ennemis/";

        this.imageD = new Image(getClass().getResource(chemin + "Dentifrice.png").toExternalForm());
        this.imageG = new Image(getClass().getResource(chemin + "Dentifrice.png").toExternalForm());
        this.imageImmobileG = new Image(getClass().getResource(chemin + "Dentifrice.png").toExternalForm());
        this.imageImmobileD = new Image(getClass().getResource(chemin + "Dentifrice.png").toExternalForm());
    }

// Ajoutez cette méthode dans votre classe DentifriceVolantVue.java :

    public void ennemiMort() {
        personneMort();
    }
}