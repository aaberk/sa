package universite_paris8.iut.wad.sae_dev.Vue.Projectiles;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.Projectiles.Projectile;

public class FlecheArcVue extends ProjectileVue {

    public FlecheArcVue (Projectile projectile, Pane pane) {
        super(projectile, pane);
    }

    @Override
    public void chargerImage() {
        this.projectileImage = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/outils/flecheD.png").toExternalForm());
    }

}
