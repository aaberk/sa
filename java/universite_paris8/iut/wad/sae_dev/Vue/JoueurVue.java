// Cette classe crée et affiche le joueur à l'écran en héritant de PersonneVue.
package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.*;

public class JoueurVue extends PersonnageVue {

    private Inventaire inventaire;
    private Pane paneCamera;

    public JoueurVue(Joueur joueur, Pane pane, Inventaire inventaire) {
        super(joueur, pane);
        this.inventaire = inventaire;
        this.paneCamera = pane; // Assignation du pane principal
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
                case "Arc" -> {
                    this.imageD = new Image(getClass().getResource(chemin + "richardArcD.gif").toExternalForm());
                    this.imageG = new Image(getClass().getResource(chemin + "richardArcG.gif").toExternalForm());
                    this.imageImmobileD = new Image(getClass().getResource(chemin + "richardArcI.png").toExternalForm());
                    this.imageImmobileG = new Image(getClass().getResource(chemin + "richardArcIG.png").toExternalForm());
                }
            }
        }
    }

    public void cameraPersonnage() {
        if (paneCamera.getScene() != null) {
            double largeurScene = paneCamera.getScene().getWidth();
            double hauteurScene = paneCamera.getScene().getHeight();

            double centreX = largeurScene / 5 - super.personnage.getX();
            double centreY = hauteurScene / 5- super.personnage.getY();

            paneCamera.setTranslateX(centreX);
            paneCamera.setTranslateY(centreY);
        }
    }



    public void joueurMort() {
        personneMort();
    }
}