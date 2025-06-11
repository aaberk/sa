// Cette classe gère l'affichage graphique du terrain en utilisant un TilePane pour une meilleure organisation

package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.layout.TilePane;
import universite_paris8.iut.wad.sae_dev.Modele.Terrain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TerrainVue {
    private Terrain terrain;
    private TilePane tilePane;

    public TerrainVue(Terrain terrain, TilePane tilePane) {
        this.terrain = terrain;
        this.tilePane = tilePane;
        this.afficherTerrain();
    }

    public void afficherTerrain() {
        Image ciel = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/ciel.png").toExternalForm());
        Image pelouse = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/pelouse.png").toExternalForm());
        Image terre = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/terre.png").toExternalForm());
        Image pelouseG = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/pelouseG.png").toExternalForm());
        Image pelouseD = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/pelouseD.png").toExternalForm());
        Image cookie = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/cookie.png").toExternalForm());

        Image terreBrisee = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/terreBrisee.png").toExternalForm());
        Image pelouseGBrisee = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/pelouseGBrisee.png").toExternalForm());
        Image pelouseDBrisee = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/pelouseDBrisee.png").toExternalForm());
        Image pelouseBrisee = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/pelouseBrisee.png").toExternalForm());
        Image cookieBrisee = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/cookieBrisee.png").toExternalForm());

        Image terreTransparent = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/terreTransparent.png").toExternalForm());
        Image pelouseGTransparent = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/pelouseGTransparent.png").toExternalForm());
        Image pelouseDTransparent = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/pelouseDTransparent.png").toExternalForm());
        Image pelouseTransparent = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/pelouseTransparent.png").toExternalForm());
        Image cookieTransparent = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/cookieTransparent.png").toExternalForm());


        for (int y = 0; y < terrain.hauteur(); y++) {
            for (int x = 0; x < terrain.largeur(); x++) {
                int val = terrain.typeTuile(x, y);
                ImageView tuile = creerTuile(val, ciel, pelouse, terre, pelouseG, pelouseD, cookie,
                        pelouseBrisee, pelouseGBrisee, pelouseDBrisee, cookieBrisee, terreBrisee,
                        pelouseTransparent, pelouseGTransparent, pelouseDTransparent, cookieTransparent, terreTransparent);

                if (tuile != null) {
                    tilePane.getChildren().add(tuile);
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
                tuile = new ImageView(ciel);
                break;
            case 2:
                tuile = new ImageView(pelouse);
                break;
            case 3:
                tuile = new ImageView(terre);
                break;
            case 4:
                tuile = new ImageView(cookie);
                break;
            case 5:
                tuile = new ImageView(pelouseD);
                break;
            case 6:
                tuile = new ImageView(pelouseG);
                break;
            case 12:
                tuile = new ImageView(pelouseBrisee);
                break;
            case 13:
                tuile = new ImageView(terreBrisee);
                break;
            case 14:
                tuile = new ImageView(cookieBrisee);
                break;
            case 15:
                tuile = new ImageView(pelouseDBrisee);
                break;
            case 16:
                tuile = new ImageView(pelouseGBrisee);
                break;
            case 22:
                tuile = new ImageView(pelouseTransparent);
                break;
            case 23:
                tuile = new ImageView(terreTransparent);
                break;
            case 24:
                tuile = new ImageView(cookieTransparent);
                break;
            case 25:
                tuile = new ImageView(pelouseDTransparent);
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
}