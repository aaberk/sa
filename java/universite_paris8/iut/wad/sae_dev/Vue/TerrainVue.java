package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.layout.TilePane;
import universite_paris8.iut.wad.sae_dev.Modele.Projectile;
import universite_paris8.iut.wad.sae_dev.Modele.Terrain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TerrainVue {
    private final Terrain terrain;
    private final TilePane tilePane;
    private List<ProjectileVue> projectilesVue = new ArrayList<>();

    public TerrainVue(Terrain terrain, TilePane tilePane) {
        this.terrain = terrain;
        this.tilePane = tilePane;
        this.afficherTerrain();
    }

    public void afficherTerrain() {
        String chemin = "/universite_paris8/iut/wad/sae_dev/images/blocs/";

        // On garde l'image ciel pour les cas d'erreur, mais on ne l'affichera pas
        Image ciel = new Image(getClass().getResource(chemin + "ciel.png").toExternalForm());
        Image pelouse = new Image(getClass().getResource(chemin + "pelouse.png").toExternalForm());
        Image terre = new Image(getClass().getResource(chemin + "terre.png").toExternalForm());
        Image pelouseG = new Image(getClass().getResource(chemin + "pelouseG.png").toExternalForm());
        Image pelouseD = new Image(getClass().getResource(chemin + "pelouseD.png").toExternalForm());
        Image cookie = new Image(getClass().getResource(chemin + "cookie.png").toExternalForm());

        Image terreBrisee = new Image(getClass().getResource(chemin + "terreBrisee.png").toExternalForm());
        Image pelouseGBrisee = new Image(getClass().getResource(chemin + "pelouseGBrisee.png").toExternalForm());
        Image pelouseDBrisee = new Image(getClass().getResource(chemin + "pelouseDBrisee.png").toExternalForm());
        Image pelouseBrisee = new Image(getClass().getResource(chemin + "pelouseBrisee.png").toExternalForm());
        Image cookieBrisee = new Image(getClass().getResource(chemin + "cookieBrisee.png").toExternalForm());

        Image terreTransparent = new Image(getClass().getResource(chemin + "terreTransparent.png").toExternalForm());
        Image pelouseGTransparent = new Image(getClass().getResource(chemin + "pelouseGTransparent.png").toExternalForm());
        Image pelouseDTransparent = new Image(getClass().getResource(chemin + "pelouseDTransparent.png").toExternalForm());
        Image pelouseTransparent = new Image(getClass().getResource(chemin + "pelouseTransparent.png").toExternalForm());
        Image cookieTransparent = new Image(getClass().getResource(chemin + "cookieTransparent.png").toExternalForm());

        for (int y = 0; y < terrain.hauteur(); y++) {
            for (int x = 0; x < terrain.largeur(); x++) {
                int val = terrain.typeTuile(x, y);
                ImageView tuile = creerTuile(val, ciel, pelouse, terre, pelouseG, pelouseD, cookie,
                        pelouseBrisee, pelouseGBrisee, pelouseDBrisee, cookieBrisee, terreBrisee,
                        pelouseTransparent, pelouseGTransparent, pelouseDTransparent, cookieTransparent, terreTransparent);

                if (tuile != null) {
                    tilePane.getChildren().add(tuile);
                } else {
                    // Pour les cases de ciel (val == 1), on ajoute un espace vide transparent
                    // Cela maintient la structure de la grille du TilePane
                    ImageView espaceVide = new ImageView();
                    espaceVide.setFitWidth(terrain.getTailleTuile());
                    espaceVide.setFitHeight(terrain.getTailleTuile());
                    espaceVide.setOpacity(0); // Complètement transparent
                    tilePane.getChildren().add(espaceVide);
                }
            }
        }
    }

    private ImageView creerTuile(int val, Image ciel, Image pelouse, Image terre, Image pelouseG, Image pelouseD, Image cookie,
                                 Image pelouseBrisee, Image pelouseGBrisee, Image pelouseDBrisee, Image cookieBrisee, Image terreBrisee,
                                 Image pelouseTransparent, Image pelouseGTransparent, Image pelouseDTransparent, Image cookieTransparent, Image terreTransparent) {
        ImageView tuile = null;

        switch (val) {
            case 1:
                // On ne crée plus de tuile pour le ciel, on retourne null
                // L'arrière-plan sera visible à la place
                return null;
            case 2:
                tuile = new ImageView(pelouseD);
                break;
            case 3:
                tuile = new ImageView(cookie);
                break;
            case 4:
                tuile = new ImageView(terre);
                break;
            case 5:
                tuile = new ImageView(pelouse);
                break;
            case 6:
                tuile = new ImageView(pelouseG);
                break;
            case 12:
                tuile = new ImageView(pelouseDBrisee);
                break;
            case 13:
                tuile = new ImageView(cookieBrisee);
                break;
            case 14:
                tuile = new ImageView(terreBrisee);
                break;
            case 15:
                tuile = new ImageView(pelouseBrisee);
                break;
            case 16:
                tuile = new ImageView(pelouseGBrisee);
                break;
            case 22:
                tuile = new ImageView(pelouseDTransparent);
                break;
            case 23:
                tuile = new ImageView(cookieTransparent);
                break;
            case 24:
                tuile = new ImageView(terreTransparent);
                break;
            case 25:
                tuile = new ImageView(pelouseTransparent);
                break;
            case 26:
                tuile = new ImageView(pelouseGTransparent);
                break;
        }

        return tuile;
    }

    public void majAffichage() {
        tilePane.getChildren().clear();
        afficherTerrain();
    }


    public List<ProjectileVue> getListeProjectiles() {
        return projectilesVue;
    }


    public boolean contientVuePour(Projectile p) {
        return projectilesVue.stream().anyMatch(v -> v.getProjectile() == p);
    }

    public void majProjectiles() {
        Iterator<ProjectileVue> it = projectilesVue.iterator();
        while (it.hasNext()) {
            ProjectileVue vue = it.next();
            if (!vue.getProjectile().estActif()) {
                vue.retirerDuPane();
                it.remove();
            }
        }
    }

    public void ajouterProjectileVue(ProjectileVue vue) {
        projectilesVue.add(vue);
    }

}


