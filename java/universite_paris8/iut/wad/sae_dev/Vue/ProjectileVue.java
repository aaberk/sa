package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.wad.sae_dev.Modele.Projectile;

public abstract class ProjectileVue {
    private Projectile projectile;
    public Image projectileImage;
    private ImageView projectileView;
    private Pane pane;

    public ProjectileVue(Projectile projectile, Pane pane) {
        this.projectile = projectile;
        this.pane = pane;

        chargerImage();
        creerAffichage();
        lierPosition();
    }

    public abstract void chargerImage();

    private void creerAffichage() {
        this.projectileView = new ImageView(projectileImage);
        this.projectileView.setFitWidth(16);
        this.projectileView.setFitHeight(16);
        this.pane.getChildren().add(projectileView);
    }

    private void lierPosition() {
        projectileView.layoutXProperty().bind(projectile.xProperty());
        projectileView.layoutYProperty().bind(projectile.yProperty());
    }

    public void retirerDuPane() {
        if (pane.getChildren().contains(projectileView)) {
            pane.getChildren().remove(projectileView);
        }
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public boolean estVisible() {
        return pane.getChildren().contains(projectileView);
    }
}
