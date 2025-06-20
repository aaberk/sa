// Classe abstraite pour gérer l'affichage des personnages (joueur et PNJ)
package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.Personnage;

public abstract class PersonnageVue {
    public Personnage personnage;
    private Pane pane;
    private ImageView personneView;
    public Image imageD, imageG, imageImmobileG, imageImmobileD;
    private boolean derniereDirectionGauche = false;

    public PersonnageVue(Personnage personnage, Pane pane) {
        this.personnage = personnage;
        this.pane = pane;

        chargerImages();

        this.personneView = new ImageView(imageImmobileD);

        lierPosition();
        detecterDirection();
        pane.getChildren().add(personneView);
    }

    public abstract void chargerImages();

    private void lierPosition() {
        personneView.translateXProperty().bind(personnage.xProperty());
        personneView.translateYProperty().bind(personnage.yProperty());
    }

    private void detecterDirection() {
        personnage.directionProperty().addListener((obs, oldVal, newVal) -> {
            changerImage(newVal.intValue());
        });
    }

    private void changerImage(int direction) {
        if (direction == 0) {
            // Immobile - utilise la dernière direction mémorisée
            if (derniereDirectionGauche) {
                personneView.setImage(imageImmobileG);
            }
            else {
                personneView.setImage(imageImmobileD);
            }
        }
        else if (direction == 1) {
            // Droite
            personneView.setImage(imageD);
            derniereDirectionGauche = false;
        }
        else if (direction == -1) {
            // Gauche
            personneView.setImage(imageG);
            derniereDirectionGauche = true;
        }
    }

    public void setDirectionImmobile(boolean versGauche) {
        if (versGauche) {
            personneView.setImage(imageImmobileG);
            derniereDirectionGauche = true;
        }
        else {
            personneView.setImage(imageImmobileD);
            derniereDirectionGauche = false;
        }
    }

    public void personneMort() {
        if (this.personnage.estMort()) {
            pane.getChildren().remove(personneView);
        }
    }


    public ImageView getImageView() {
        return personneView;
    }


}
