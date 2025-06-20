package universite_paris8.iut.wad.sae_dev.Vue;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import universite_paris8.iut.wad.sae_dev.Modele.*;

public class CraftingVue extends VBox {
    private final ComboBox<TypeMateriaux> comboBox;
    private final Label message;

    private final Inventaire inventaire;
    private InventaireVue inventaireVue;
    private Pane paneJeu; // Référence au pane principal du jeu

    private final Label ingredientsLabel;

    public CraftingVue(Inventaire inventaire, InventaireVue inventaireVue, Pane paneJeu) {
        this.inventaire = inventaire;
        this.inventaireVue = inventaireVue;
        this.paneJeu = paneJeu; // Stocker la référence

        ingredientsLabel = new Label("Ingrédients : ");

        setSpacing(10);
        setPadding(new Insets(10));
        setStyle("-fx-background-color: #dddddd; -fx-border-color: black;");

        comboBox = new ComboBox<>();
        comboBox.getItems().addAll(
                TypeMateriaux.EPEE,
                TypeMateriaux.ARC
                // Tu peux en ajouter d'autres ici plus tard
        );
        comboBox.setPromptText("Choisir un objet à fabriquer");

        comboBox.setOnAction(e -> afficherIngredients());

        Button boutonFabriquer = new Button("Fabriquer");

        message = new Label("");

        boutonFabriquer.setOnAction(e -> fabriquerObjet());

        Button boutonRetour = new Button("Retour au jeu");
        boutonRetour.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.close();

            if (paneJeu != null) {
                paneJeu.requestFocus();
            }
        });

        getChildren().addAll(
                new Label(" Crafting"),
                comboBox,
                ingredientsLabel,
                boutonFabriquer,
                message,
                boutonRetour
        );
    }

    private void fabriquerObjet() {
        TypeMateriaux selection = comboBox.getValue();
        if (selection == null) {
            message.setText("Aucun objet sélectionné !");
            return;
        }

        Recette recette = GestionnaireDeRecettes.getRecettePour(selection);
        if (recette == null) {
            message.setText("Aucune recette trouvée !");
            return;
        }

        boolean possible = true;
        for (var entry : recette.getIngredients().entrySet()) {
            TypeMateriaux mat = entry.getKey();
            int quantite = entry.getValue();
            if (inventaire.getQuantite(mat) < quantite) {
                possible = false;
                break;
            }
        }

        if (possible) {
            // Retirer les ingrédients
            for (var entry : recette.getIngredients().entrySet()) {
                inventaire.retirerMateriaux(entry.getKey(), entry.getValue());
            }

            // Ajouter l'objet fabriqué
            inventaire.ajouterMateriaux(selection, 1);
            if (inventaireVue != null) {
                inventaireVue.rafraichirAffichage();
            }
            message.setText("Fabrication réussie : " + selection.name());
        } else {
            message.setText("Pas assez d'ingrédients !");
        }
    }

    private void afficherIngredients() {
        TypeMateriaux selection = comboBox.getValue();
        if (selection == null) {
            ingredientsLabel.setText("Ingrédients : (aucun objet sélectionné)");
            return;
        }

        Recette recette = GestionnaireDeRecettes.getRecettePour(selection);
        if (recette == null) {
            ingredientsLabel.setText("Ingrédients : (aucune recette)");
            return;
        }

        StringBuilder sb = new StringBuilder("Ingrédients :\n");
        for (var entry : recette.getIngredients().entrySet()) {
            sb.append("- ").append(entry.getValue())
                    .append(" x ").append(entry.getKey().name()).append("\n");
        }
        ingredientsLabel.setText(sb.toString());
    }

    public void setInventaireVue(InventaireVue inventaireVue) {this.inventaireVue = inventaireVue;}
}