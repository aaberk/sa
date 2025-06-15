// Cette classe crée et affiche les PNJ à l'écran en héritant de PersonneVue.
package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.Jake;

public class JakeVue extends PersonnageVue {

    public JakeVue(Jake jake, Pane pane) {
        super(jake, pane);
    }

    @Override
    public void chargerImages() {
        String chemin = "/universite_paris8/iut/wad/sae_dev/images/joueurs/";

        this.imageD = new Image(getClass().getResource(chemin + "jakeI.png").toExternalForm());
        this.imageG = new Image(getClass().getResource(chemin + "jakeIG.png").toExternalForm());
        this.imageImmobileG = new Image(getClass().getResource(chemin + "jakeIG.png").toExternalForm());
        this.imageImmobileD = new Image(getClass().getResource(chemin + "jakeI.png").toExternalForm());
    }

    public void pnjMort() {
        personneMort();
    }
}