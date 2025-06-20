package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.Projectile;

public class ProjectileDentifriceVue extends ProjectileVue {

    public ProjectileDentifriceVue(Projectile projectile, Pane pane) {
        super(projectile, pane);

    }

    @Override
    public void chargerImage() {
        // Temporairement, on utilise une image simple
        // TODO: Remplacer par la vraie image de boule de dentifrice
        this.projectileImage = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/affichage/bombe.png").toExternalForm());

    }
}