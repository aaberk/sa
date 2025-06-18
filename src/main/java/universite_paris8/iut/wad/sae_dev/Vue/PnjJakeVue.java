package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.Pnj;
import universite_paris8.iut.wad.sae_dev.Modele.PnjJake;

public class PnjJakeVue extends PnjVue {

    private PnjJake jake;
    private Image imageNormale;
    private Image imageConsignes;

    public PnjJakeVue(Pnj pnj, Pane pane) {
        super(pnj, pane);
        this.jake = (PnjJake) pnj;
    }

    @Override
    public void chargerImages() {
        String chemin = "/universite_paris8/iut/wad/sae_dev/images/joueurs/";

        // Charger les deux images
        imageNormale = new Image(getClass().getResource(chemin + "jakeI.png").toExternalForm());
        imageConsignes = new Image(getClass().getResource(chemin + "jakeIG.png").toExternalForm());

        this.imageD = imageNormale;
        this.imageG = imageNormale;
        this.imageImmobileD = imageNormale;
        this.imageImmobileG = imageNormale;
    }

    public void mettreAJourImage() {
        if (jake.joueurEstProche()) {
            this.imageD = imageConsignes;
            this.imageG = imageConsignes;
            this.imageImmobileD = imageConsignes;
            this.imageImmobileG = imageConsignes;
        } else {
            this.imageD = imageNormale;
            this.imageG = imageNormale;
            this.imageImmobileD = imageNormale;
            this.imageImmobileG = imageNormale;
        }
    }

    public void pnjMort() {
        personneMort();
    }
}