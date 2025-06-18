// Cette classe crée et affiche le joueur à l'écran en héritant de PersonneVue.
package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.*;

public class JoueurVue extends PersonnageVue {

    private Inventaire inventaire;
    private Image imageEpee, imagePioche, imageHache;

    public JoueurVue(Joueur joueur, Pane pane, Inventaire inventaire) {
        super(joueur, pane);
        this.inventaire = new Inventaire();
    }

    @Override
    public void chargerImages() {
        String chemin = "/universite_paris8/iut/wad/sae_dev/images/joueurs/";
        this.imageD = new Image(getClass().getResource(chemin + "richardD.gif").toExternalForm());
        this.imageG = new Image(getClass().getResource(chemin + "richardG.gif").toExternalForm());
        this.imageImmobileG = new Image(getClass().getResource(chemin + "richardIG.png").toExternalForm());
        this.imageImmobileD = new Image(getClass().getResource(chemin + "richardI.png").toExternalForm());
    }

    public void changerImageSelonOutil(ObjetUtilisable outil) {
        String chemin = "/universite_paris8/iut/wad/sae_dev/images/joueurs/";

        if (outil == null || inventaire.getQuantite(outil.getType ()) <= 0) {
            chargerImages();
        } else{
            switch (outil.getNom()) {
                case "Pioche" -> {
                    this.imageD = new Image(getClass().getResource(chemin + "richardPiocheD.gif").toExternalForm());
                    this.imageG = new Image(getClass().getResource(chemin + "richardPiocheG.gif").toExternalForm());
                    this.imageImmobileD = new Image(getClass().getResource(chemin + "richardPiocheI.png").toExternalForm());
                    this.imageImmobileG = new Image(getClass().getResource(chemin + "richardPiocheIG.png").toExternalForm());
                }
                case "Épée" -> {
                    this.imageD = new Image(getClass().getResource(chemin + "richardEpeeD.gif").toExternalForm());
                    this.imageG = new Image(getClass().getResource(chemin + "richardEpeeG.gif").toExternalForm());
                    this.imageImmobileD = new Image(getClass().getResource(chemin + "richardEpeeI.png").toExternalForm());
                    this.imageImmobileG = new Image(getClass().getResource(chemin + "richardEpeeIG.png").toExternalForm());
                }
                case "Hache" -> {
                    this.imageD = new Image(getClass().getResource(chemin + "richardHacheD.gif").toExternalForm());
                    this.imageG = new Image(getClass().getResource(chemin + "richardHacheG.gif").toExternalForm());
                    this.imageImmobileD = new Image(getClass().getResource(chemin + "richardHacheI.png").toExternalForm());
                    this.imageImmobileG = new Image(getClass().getResource(chemin + "richardHacheIG.png").toExternalForm());
                }
            }
        }
    }



    public void joueurMort() {
        personneMort();
    }
}