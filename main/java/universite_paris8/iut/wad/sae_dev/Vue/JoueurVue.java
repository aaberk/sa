// Cette classe crée et affiche le joueur à l'écran.
package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.Joueur;

public class JoueurVue {
    private Joueur joueur;
    private Pane pane;
    private ImageView joueurView;
    private Image joueurImageD, joueurImageG, joueurImageImmobileG, joueurImageImmobileD;
    private boolean derniereDirectionGauche = false;

    public JoueurVue(Joueur joueur, Pane pane) {
        this.joueur = joueur;
        this.pane = pane;

        chargerImages();

        this.joueurView = new ImageView(joueurImageImmobileD);

        lierPosition();
        detecterDirection();
        pane.getChildren().add(joueurView);
    }

    private void chargerImages() {
        String chemin = "/universite_paris8/iut/wad/sae_dev/images/";
        this.joueurImageD = new Image(getClass().getResource(chemin + "joueurD.gif").toExternalForm());
        this.joueurImageG = new Image(getClass().getResource(chemin + "joueurG.gif").toExternalForm());
        this.joueurImageImmobileG = new Image(getClass().getResource(chemin + "joueurIG.png").toExternalForm());
        this.joueurImageImmobileD = new Image(getClass().getResource(chemin + "joueurI.png").toExternalForm());
    }

    private void lierPosition() {
        joueurView.translateXProperty().bind(joueur.xProperty());
        joueurView.translateYProperty().bind(joueur.yProperty());
    }

    private void detecterDirection() {
        joueur.directionProperty().addListener((obs, oldVal, newVal) -> {
            changerImage(newVal.intValue());
        });
    }

    private void changerImage(int direction) {
        if (direction == 0) {
            // Immobile - utilise la dernière direction mémorisée
            if (derniereDirectionGauche) {
                joueurView.setImage(joueurImageImmobileG);
            } else {
                joueurView.setImage(joueurImageImmobileD);
            }
        } else if (direction == 1) {
            // Droite
            joueurView.setImage(joueurImageD);
            derniereDirectionGauche = false;
        } else if (direction == -1) {
            // Gauche
            joueurView.setImage(joueurImageG);
            derniereDirectionGauche = true;
        }
    }

    public void setDirectionImmobile(boolean versGauche) {
        if (versGauche) {
            joueurView.setImage(joueurImageImmobileG);
            derniereDirectionGauche = true;
        } else {
            joueurView.setImage(joueurImageImmobileD);
            derniereDirectionGauche = false;
        }
    }

    public void joueurMort (){
        if (this.joueur.estMort()){
            pane.getChildren().remove(joueurView);
        }
    }

    public void mettreÀJour (){

    }
}