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

    private final double HITBOX_X_MIN = 10;
    private final double HITBOX_X_MAX = 56;
    private final double HITBOX_Y_MIN = 10;
    private final double HITBOX_Y_MAX = 56;

    private final Image imageSac;
    private final Image imageCookie;
    private final Image imagePelouse;
    private final Image imageBrownie;
    private final Image imageInventaireVide;

    public InventaireVue(Pane pane, Inventaire inventaire, Pane paneInventaire) {
        fermer = true;
        this.pane = pane;
        this.inventaire = inventaire;
        this.paneInventaire = paneInventaire;

        // Chargement des images une seule fois
        this.imageSac = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/ciel1.png").toExternalForm());
        this.imageCookie = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/cookie.png").toExternalForm());
        this.imagePelouse = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/pelouse.png").toExternalForm());
        this.imageBrownie = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/terre.png").toExternalForm());
        this.imageInventaireVide = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/ciel1.png").toExternalForm());

        afficherInventaire();
    }

    public boolean estFerme() {
        return fermer;
    }


    public boolean estDansHitbox(double clicX, double clicY) {
        return clicX >= HITBOX_X_MIN && clicX <= HITBOX_X_MAX &&
                clicY >= HITBOX_Y_MIN && clicY <= HITBOX_Y_MAX;
    }


    public boolean gererClicInventaire(double clicX, double clicY) {
        if (estDansHitbox(clicX, clicY)) {
            if (estFerme()) {
                System.out.println("clic inventaire");
                ouvrirContenu();
            } else {
                fermerContenue();
            }
            return true;
        }
        return false;
    }

    private void afficherInventaire() {
        ImageView sacView = new ImageView(imageSac);
        sacView.setFitHeight(46);
        sacView.setFitWidth(46);
        sacView.setTranslateX(10);
        sacView.setTranslateY(10);
        this.pane.getChildren().add(sacView);
    }

    public void afficherContenu() {
        fermer = false;
        paneInventaire.getChildren().clear();

        int x = 200;
        int y = 40;

        for (TypeMateriaux type : inventaire.getMateriauxQuantites().keySet()) {
            int quantite = inventaire.getQuantite(type);

            if (x > 500) {
                x = 200;
                y = y + TAILLECASE;
            }

            ImageView inventaireVideVue = new ImageView(imageInventaireVide);
            inventaireVideVue.setFitHeight(TAILLECASE);
            inventaireVideVue.setFitWidth(TAILLECASE);
            inventaireVideVue.setTranslateY(y);
            inventaireVideVue.setTranslateX(x);
            paneInventaire.getChildren().add(inventaireVideVue);

            ImageView img = new ImageView();
            switch (type) {
                case COOKIE:
                    img = new ImageView(imageCookie);
                    break;
                case PELOUSE:
                    img = new ImageView(imagePelouse);
                    break;
                case BROWNIE:
                    img = new ImageView(imageBrownie);
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
            ImageView inventaireVideVue = new ImageView(imageInventaireVide);
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