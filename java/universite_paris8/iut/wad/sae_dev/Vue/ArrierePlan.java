package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.Terrain;

public class ArrierePlan {
    private final Terrain terrain;
    private final Pane pane;
    private ImageView backgroundImageView;

    public ArrierePlan(Terrain terrain, Pane pane) {
        this.terrain = terrain;
        this.pane = pane;
        initialiserArrierePlan();
    }


    public void initialiserArrierePlan() {
            Image backgroundImage = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/background.png").toExternalForm());
            backgroundImageView = new ImageView(backgroundImage);

            backgroundImageView.setFitWidth(terrain.getLargeurPixels());
            backgroundImageView.setFitHeight(terrain.getHauteurPixels());


            pane.getChildren().add(0, backgroundImageView);
    }
}