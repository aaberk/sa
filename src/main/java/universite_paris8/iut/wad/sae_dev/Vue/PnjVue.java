// Cette classe crée et affiche les PNJ à l'écran en héritant de PersonneVue.
package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.Pnj;

public class PnjVue extends PersonnageVue {

    public PnjVue(Pnj pnj, Pane pane) {
        super(pnj, pane);
    }

    @Override
    protected void chargerImages() {
        String chemin = "/universite_paris8/iut/wad/sae_dev/images/";
        this.imageD = new Image(getClass().getResource(chemin + "joueurD.gif").toExternalForm());
        this.imageG = new Image(getClass().getResource(chemin + "joueurG.gif").toExternalForm());
        this.imageImmobileG = new Image(getClass().getResource(chemin + "joueurIG.png").toExternalForm());
        this.imageImmobileD = new Image(getClass().getResource(chemin + "joueurI.png").toExternalForm());
    }

    public void pnjMort() {
        personneMort();
    }
}