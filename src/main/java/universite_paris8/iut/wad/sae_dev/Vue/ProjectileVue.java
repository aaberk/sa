package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.Projectile;

public class ProjectileVue {
    private Projectile projectile;
    private Pane pane;
    private ImageView projectileView;
    private Image imageProjectile;

    public ProjectileVue(Projectile projectile, Pane pane) {
        this.projectile = projectile;
        this.pane = pane;

        chargerImage();
        creerAffichage();
        lierPosition();
    }

    private void chargerImage() {
        // Temporairement, on utilise une image simple
        // TODO: Remplacer par la vraie image de boule de dentifrice
            this.imageProjectile = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/blocs/cookie.png").toExternalForm());

    }

    private void creerAffichage() {
        this.projectileView = new ImageView(imageProjectile);
        this.projectileView.setFitWidth(16);
        this.projectileView.setFitHeight(16);
        pane.getChildren().add(projectileView);
    }

    private void lierPosition() {
        projectileView.translateXProperty().bind(projectile.xProperty());
        projectileView.translateYProperty().bind(projectile.yProperty());
    }

    public void retirerDuPane() {
        pane.getChildren().remove(projectileView);
    }

    public boolean estVisible() {
        return pane.getChildren().contains(projectileView);
    }

    public Projectile getProjectile() {
        return projectile;
    }
}