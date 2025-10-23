package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.PnjDonut;

public class PnjDonutVue extends PersonnageVue {

    public PnjDonutVue(PnjDonut pnjDonut, Pane pane) {
        super(pnjDonut, pane);
    }

    @Override
    public void chargerImages() {
        String chemin = "/universite_paris8/iut/wad/sae_dev/images/joueurs/";
            // Images pour le donut qui se déplace
            this.imageD = new Image(getClass().getResource(chemin + "donutD.png").toExternalForm());
            this.imageG = new Image(getClass().getResource(chemin + "donutG.png").toExternalForm());
            this.imageImmobileD = new Image(getClass().getResource(chemin + "donutD.png").toExternalForm());
            this.imageImmobileG = new Image(getClass().getResource(chemin + "donutG.png").toExternalForm());
    }

}