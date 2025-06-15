// Cette classe crée et affiche le joueur à l'écran en héritant de PersonneVue.
package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.Joueur;

public class RichardVue extends PersonnageVue {

    private Image imageEpee, imagePioche, imageHache;

    public RichardVue(Joueur joueur, Pane pane) {
        super(joueur, pane);
    }

    @Override
    public void chargerImages() {
        String chemin = "/universite_paris8/iut/wad/sae_dev/images/joueurs/";
        this.imageD = new Image(getClass().getResource(chemin + "richardD.gif").toExternalForm());
        this.imageG = new Image(getClass().getResource(chemin + "richardG.gif").toExternalForm());
        this.imageImmobileG = new Image(getClass().getResource(chemin + "richardIG.png").toExternalForm());
        this.imageImmobileD = new Image(getClass().getResource(chemin + "richardI.png").toExternalForm());
        this.imageEpee = new Image(getClass().getResource(chemin + "richardI.png").toExternalForm());
        this.imagePioche = new Image(getClass().getResource(chemin + "richardI.png").toExternalForm());
        this.imageHache = new Image(getClass().getResource(chemin + "richardI.png").toExternalForm());
    }

    public void joueurMort() {
        personneMort();
    }
}