package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import universite_paris8.iut.wad.sae_dev.Modele.Inventaire;
import universite_paris8.iut.wad.sae_dev.Modele.TypeMateriaux;

public class InventaireVue {
    private Pane pane;
    private Inventaire inventaire;
    private Pane paneInventaire;
    private final int TAILLECASE = 56;
    private final int TAILLEITEM = 42;
    private final int APPARITIONITEM = (TAILLECASE - TAILLEITEM)/2;
    private boolean fermer;

    public InventaireVue(Pane pane, Inventaire inventaire, Pane paneInventaire) {
        fermer = true;
        this.pane = pane;
        this.inventaire = inventaire;
        this.paneInventaire = paneInventaire;
        afficherInventaire();
    }

    public boolean estFerme() {
        return fermer;
    }

    private void afficherInventaire() {
        Image sac = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/ciel1.png").toExternalForm());
        ImageView sacView = new ImageView(sac);
        sacView.setFitHeight(46);
        sacView.setFitWidth(46);
        sacView.setTranslateX(10);
        sacView.setTranslateY(10);
        this.pane.getChildren().add(sacView);
    }

    public void afficherContenu() {
        fermer = false;
        paneInventaire.getChildren().clear();

        Image cookie = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/cookie.png").toExternalForm());
        Image pelouse = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/pelouse.png").toExternalForm());
        Image brownie = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/terre.png").toExternalForm());
        Image inventaireVide = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/ciel1.png").toExternalForm());

        int x = 200;
        int y = 40;

        for (TypeMateriaux type : inventaire.getMateriauxQuantites().keySet()) {
            int quantite = inventaire.getQuantite(type);

            if (x > 500) {
                x = 200;
                y = y + TAILLECASE;
            }

            ImageView inventaireVideVue = new ImageView(inventaireVide);
            inventaireVideVue.setFitHeight(TAILLECASE);
            inventaireVideVue.setFitWidth(TAILLECASE);
            inventaireVideVue.setTranslateY(y);
            inventaireVideVue.setTranslateX(x);
            paneInventaire.getChildren().add(inventaireVideVue);

            ImageView img = new ImageView();
            switch (type) {
                case COOKIE:
                    img = new ImageView(cookie);
                    break;
                case PELOUSE:
                    img = new ImageView(pelouse);
                    break;
                case BROWNIE:
                    img = new ImageView(brownie);
                    break;
            }

            img.setTranslateX(x + APPARITIONITEM);
            img.setTranslateY(y + APPARITIONITEM);
            img.setFitHeight(TAILLEITEM);
            img.setFitWidth(TAILLEITEM);
            paneInventaire.getChildren().add(img);

            Label quantiteLabel = new Label(String.valueOf(quantite));
            quantiteLabel.setTranslateX(x + TAILLECASE - 15);
            quantiteLabel.setTranslateY(y + TAILLECASE - 15);
            quantiteLabel.setTextFill(Color.WHITE);
            quantiteLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            quantiteLabel.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 2px; -fx-background-radius: 3px;");
            paneInventaire.getChildren().add(quantiteLabel);

            x = x + TAILLECASE;
        }

        while (x < 500) {
            ImageView inventaireVideVue = new ImageView(inventaireVide);
            inventaireVideVue.setFitHeight(TAILLECASE);
            inventaireVideVue.setFitWidth(TAILLECASE);
            inventaireVideVue.setTranslateY(y);
            inventaireVideVue.setTranslateX(x);
            paneInventaire.getChildren().add(inventaireVideVue);
            x = x + TAILLECASE;
        }
    }

    public void ouvrirContenu() {
        paneInventaire.setVisible(true);
        afficherContenu();
    }

    public void fermerContenue() {
        fermer = true;
        paneInventaire.setVisible(false);
        paneInventaire.getChildren().clear();
    }

    public void rafraichirAffichage() {
        if (!fermer) {
            afficherContenu();
        }
    }

}