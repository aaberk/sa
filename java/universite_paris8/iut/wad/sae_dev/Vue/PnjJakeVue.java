package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.PnjJake;

public class PnjJakeVue extends PersonnageVue {
    private final PnjJake jake;
    private final Image imageNeutre;
    private final Image imageMessage;
    private final ImageView imageView;

    public PnjJakeVue(PnjJake jake, Pane pane) {
        super(jake, pane); // Appel du constructeur de PersonnageVue
        this.jake = jake;

        this.imageNeutre = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/joueurs/jakeIG.png").toExternalForm());
        this.imageMessage = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/joueurs/jakeM.gif").toExternalForm());

        this.imageView = new ImageView(imageNeutre);
        imageView.setImage(imageNeutre);
        imageView.setTranslateX(jake.getX());
        imageView.setTranslateY(jake.getY() - imageNeutre.getHeight()); // Décalage vers le haut

        pane.getChildren().add(this.imageView);
    }

    @Override
    public void chargerImages() {
        this.imageG = imageMessage;
        this.imageImmobileG = imageNeutre;
    }

    public void mettreAJourAffichage() {
        if (jake.joueurEstProche()) {
            imageView.setImage(imageMessage);
        } else {
            imageView.setImage(imageNeutre);
        }
    }
}
