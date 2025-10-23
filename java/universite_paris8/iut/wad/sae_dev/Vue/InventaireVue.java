package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private final Image imageArc;
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
        imageArc = new Image(getClass().getResource(chemin + "outils/arc.png").toExternalForm());

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
                ouvrirContenu();
            } else {
                fermerContenue();
            }
            return true;
        }
        return false;
    }

    public void afficherInventaire() {
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

        for (int i = 0; i < inventaire.getTaille(); i++) {
            TypeMateriaux materiau = inventaire.getMateriau(i);
            boolean afficherQuantite = materiau.getRole() != Role.OUTIL && materiau.getRole() != Role.ARME;
            afficherCase(materiau, x, y, afficherQuantite, i);
            x += TAILLECASE;
        }
    }

    private void afficherCase(TypeMateriaux type, int x, int y, boolean afficherQuantite, int index) {
        ImageView fond = new ImageView(imageInventaireVide);
        fond.setFitHeight(TAILLECASE);
        fond.setFitWidth(TAILLECASE);
        fond.setTranslateY(y);
        fond.setTranslateX(x);
        paneInventaire.getChildren().add(fond);

        ImageView img = switch (type) {
            case EPEE -> new ImageView(imageEpee);
            case PIOCHE -> new ImageView(imagePioche);
            case ARC -> new ImageView(imageArc);
            case COOKIE -> new ImageView(imageCookie);
            case PELOUSE -> new ImageView(imagePelouse);
            case BROWNIE -> new ImageView(imageBrownie);
            default -> null;
        };

        if (img != null) {
            img.setTranslateX(x + APPARITIONITEM);
            img.setTranslateY(y + APPARITIONITEM);
            img.setFitHeight(TAILLEITEM);
            img.setFitWidth(TAILLEITEM);

            int quantite = inventaire.getQuantite(type);
            if (quantite == 0) {
                img.setOpacity(0.3); // rend l'image grise/transparente si t'en as pas
            } else {
                img.setOpacity(1.0); // image normale si t'en as
            }

            paneInventaire.getChildren().add(img);
        }
        //  Ajout du cadre de sélection si c’est la case sélectionnée
        if (inventaire.retournerCaseSelectionnee() == index && !(inventaire.getQuantite(index) <= 0)) {
            Rectangle cadre = new Rectangle(TAILLECASE, TAILLECASE);
            cadre.setTranslateX(x);
            cadre.setTranslateY(y);
            cadre.setFill(Color.TRANSPARENT);
            cadre.setStroke(Color.BURLYWOOD);
            cadre.setStrokeWidth(5);
            paneInventaire.getChildren().add(cadre);
        }
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

    public boolean gererClicCase(double clicX, double clicY) {
        if (fermer) return false;

        int x = 200;
        int y = 40;

        for (int i = 0; i < inventaire.getTaille(); i++) {
            double minX = x;
            double maxX = x + TAILLECASE;
            double minY = y;
            double maxY = y + TAILLECASE;

            if (clicX >= minX && clicX <= maxX && clicY >= minY && clicY <= maxY) {
                inventaire.selectionnerCase(i);
                rafraichirAffichage();
                return true;
            }

            x += TAILLECASE;
        }

        return false;
    }


    public void mettreCadreSelection() {
        if (!fermer) {
            afficherContenu();
        }
    }

    public void ouvrirContenu() {
        fermer = false;
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
