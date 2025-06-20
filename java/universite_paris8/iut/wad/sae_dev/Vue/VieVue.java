package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import universite_paris8.iut.wad.sae_dev.Modele.Joueur;

import java.util.ArrayList;
import java.util.List;

public class VieVue {
    private HBox vies;
    private List<ImageView> coeurs;
    private Image coeurPlein, coeurMoitie, coeurVide;

    public VieVue(Joueur joueur, HBox vies) {
        this.vies = vies;
        this.coeurs = new ArrayList<>();
        this.coeurPlein = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/affichage/coeurPlein.png").toExternalForm());
        this.coeurVide = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/affichage/coeurVide.png").toExternalForm());
        this.coeurMoitie = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/affichage/coeurMoitie.png").toExternalForm());

        for (int i = 0; i < 3; i++) {
            ImageView coeur = new ImageView(coeurPlein);
            coeur.setFitWidth(58);
            coeur.setFitHeight(50);
            coeurs.add(coeur);
            vies.getChildren().add(coeur);
        }

        ChangeListener<Number> vieListener = (obs, oldVal, newVal) -> mettreAJour(joueur.getVie());
        joueur.vieProperty().addListener(vieListener);

        mettreAJour(joueur.getVie());
    }

    public void mettreAJour(int vieActuelle) {
        int pointsDeVieRestants = vieActuelle;
        for (int i = 0; i < coeurs.size(); i++) {
            if (pointsDeVieRestants >= 2) {
                coeurs.get(i).setImage(coeurPlein);
                pointsDeVieRestants -= 2;
            } else if (pointsDeVieRestants == 1) {
                coeurs.get(i).setImage(coeurMoitie);
                pointsDeVieRestants -= 1;
            } else {
                coeurs.get(i).setImage(coeurVide);
            }
        }
    }

}
