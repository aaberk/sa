package universite_paris8.iut.wad.sae_dev.Controleur;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import universite_paris8.iut.wad.sae_dev.Modele.Terrain;


import java.io.IOException;

public class Menu {

    @FXML
    private void lancerJeu(ActionEvent event) throws IOException {
        System.out.println("Lancer Jeu");
        Terrain terrain = new Terrain();
        int largeurFenetre = terrain.largeurEnPixels();
        int hauteurFenetre = terrain.getHauteurPixels();

        changerScene(event, "/universite_paris8/iut/wad/sae_dev/fxml/jeu.fxml", largeurFenetre, hauteurFenetre);
    }

    @FXML
    private void afficherRegles(ActionEvent event) throws IOException {
        System.out.println("Afficher les règles");
        changerScene(event, "/universite_paris8/iut/wad/sae_dev/fxml/regles.fxml", 900, 600);
    }

    @FXML
    private void quitterJeu(ActionEvent event) {
        System.out.println("Quitter Jeu");
        System.exit(0);
    }

    @FXML
    private void retournerMenu(ActionEvent event) throws IOException {
        System.out.println("Retourner au menu");
        changerScene(event, "/universite_paris8/iut/wad/sae_dev/fxml/menu.fxml", 900, 600);
    }

    private void changerScene(ActionEvent event, String cheminFxml, int largeur, int hauteur) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(cheminFxml));
        Scene scene = new Scene(loader.load(), largeur, hauteur);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sugar Rush");
        stage.setResizable(false);
        stage.show();
    }
}