package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.BrosseADent;

public class BrosseADentVue extends PersonnageVue {

    public BrosseADentVue(BrosseADent ennemi, Pane pane) {
        super(ennemi, pane);
    }

    @Override
    public void chargerImages() {
        String chemin = "/universite_paris8/iut/wad/sae_dev/images/ennemis/";

        this.imageD = new Image(getClass().getResource(chemin + "Brosse.png").toExternalForm());
        this.imageG = new Image(getClass().getResource(chemin + "BrosseG.png").toExternalForm());
    }

    public void ennemiMort() {
        personneMort();
    }
}