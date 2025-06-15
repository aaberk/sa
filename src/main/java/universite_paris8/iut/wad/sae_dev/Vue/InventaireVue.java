package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import universite_paris8.iut.wad.sae_dev.Modele.Inventaire;
import universite_paris8.iut.wad.sae_dev.Modele.Role;
import universite_paris8.iut.wad.sae_dev.Modele.TypeMateriaux;

public class InventaireVue {
    private Pane pane;
    private Inventaire inventaire;
    private Pane paneInventaire;
    private final int TAILLECASE = 56;
    private final int TAILLEITEM = 42;
    private final int APPARITIONITEM = (TAILLECASE - TAILLEITEM) / 2;
    private boolean fermer;

    private final double HITBOX_X_MIN = 10;
    private final double HITBOX_X_MAX = 56;
    private final double HITBOX_Y_MIN = 10;
    private final double HITBOX_Y_MAX = 56;

    private final Image imageSac;
    private final Image imageInventaireVide;
    private final Image imageEpee;
    private final Image imagePioche;
    private final Image imageHache;
    private final Image imageCookie;
    private final Image imagePelouse;
    private final Image imageBrownie;

    public InventaireVue(Pane pane, Inventaire inventaire, Pane paneInventaire) {
        fermer = true;
        this.pane = pane;
        this.inventaire = inventaire;
        this.paneInventaire = paneInventaire;

        String chemin = "/universite_paris8/iut/wad/sae_dev/images/";

        imageSac = new Image(getClass().getResource(chemin + "affichage/inventaire.png").toExternalForm());
        imageInventaireVide = new Image(getClass().getResource(chemin + "affichage/imageBlanc.png").toExternalForm());

        imageEpee = new Image(getClass().getResource(chemin + "outils/epee.png").toExternalForm());
        imagePioche = new Image(getClass().getResource(chemin + "outils/pioche.png").toExternalForm());
        imageHache = new Image(getClass().getResource(chemin + "outils/hache.png").toExternalForm());

        imageCookie = new Image(getClass().getResource(chemin + "affichage/cookie.png").toExternalForm());
        imagePelouse = new Image(getClass().getResource(chemin + "affichage/pelouse.png").toExternalForm());
        imageBrownie = new Image(getClass().getResource(chemin + "affichage/terre.png").toExternalForm());

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
        sacView.setFitHeight(70);
        sacView.setFitWidth(70);
        sacView.setTranslateX(10);
        sacView.setTranslateY(10);
        this.pane.getChildren().add(sacView);
    }

    public void afficherContenu() {
        fermer = false;
        paneInventaire.getChildren().clear();

        int x = 200;
        int y = 40;

        // Afficher d'abord les armes et les outils (sans quantité)
        for (TypeMateriaux type : inventaire.getMateriauxQuantites().keySet()) {
            if (type.getRole() == Role.ARME || type.getRole() == Role.OUTIL) {
                if (x > 500) {
                    x = 200;
                    y += TAILLECASE;
                }
                afficherCase(type, x, y, false);
                x += TAILLECASE;
            }
        }

        // Puis les ressources (avec quantité)
        for (TypeMateriaux type : inventaire.getMateriauxQuantites().keySet()) {
            Role r = type.getRole();
            if (r != Role.ARME && r != Role.OUTIL) {
                if (x > 500) {
                    x = 200;
                    y += TAILLECASE;
                }
                afficherCase(type, x, y, true);
                x += TAILLECASE;
            }
        }

    }

    private void afficherCase(TypeMateriaux type, int x, int y, boolean afficherQuantite) {
        ImageView inventaireVideVue = new ImageView(imageInventaireVide);
        inventaireVideVue.setFitHeight(TAILLECASE);
        inventaireVideVue.setFitWidth(TAILLECASE);
        inventaireVideVue.setTranslateY(y);
        inventaireVideVue.setTranslateX(x);
        paneInventaire.getChildren().add(inventaireVideVue);

        ImageView img = new ImageView();
        switch (type) {
            case EPEE: img = new ImageView(imageEpee); break;
            case PIOCHE: img = new ImageView(imagePioche); break;
            case HACHE: img = new ImageView(imageHache); break;
            case COOKIE: img = new ImageView(imageCookie); break;
            case PELOUSE: img = new ImageView(imagePelouse); break;
            case BROWNIE: img = new ImageView(imageBrownie); break;
            default: break;
        }

        img.setTranslateX(x + APPARITIONITEM);
        img.setTranslateY(y + APPARITIONITEM);
        img.setFitHeight(TAILLEITEM);
        img.setFitWidth(TAILLEITEM);
        paneInventaire.getChildren().add(img);

        if (afficherQuantite) {
            int quantite = inventaire.getQuantite(type);
            Label quantiteLabel = new Label(String.valueOf(quantite));
            quantiteLabel.setTranslateX(x + TAILLECASE - 15);
            quantiteLabel.setTranslateY(y + TAILLECASE - 15);
            quantiteLabel.setTextFill(Color.WHITE);
            quantiteLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            quantiteLabel.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 2px; -fx-background-radius: 3px;");
            paneInventaire.getChildren().add(quantiteLabel);
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