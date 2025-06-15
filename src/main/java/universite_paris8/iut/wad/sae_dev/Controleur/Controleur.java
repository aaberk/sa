package universite_paris8.iut.wad.sae_dev.Controleur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;
import universite_paris8.iut.wad.sae_dev.Modele.*;
import universite_paris8.iut.wad.sae_dev.Modele.Joueur;
import universite_paris8.iut.wad.sae_dev.Vue.InventaireVue;
import universite_paris8.iut.wad.sae_dev.Vue.RichardVue;
import universite_paris8.iut.wad.sae_dev.Vue.JakeVue;
import universite_paris8.iut.wad.sae_dev.Vue.TerrainVue;
import universite_paris8.iut.wad.sae_dev.Vue.VieVue;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private TilePane tilePane;
    @FXML
    private Pane paneInventaire;
    @FXML
    private HBox barreVie;

    private Timeline gameLoop;
    private int temps;

    private Joueur joueur;
    private Jake jake;
    private RichardVue richardVue;
    private JakeVue jakeVue;

    private VieVue barreDeVieVue;

    private Inventaire inventaire;
    private InventaireVue inventaireVue;

    private Clavier clavier;
    private Souris souris;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Terrain terrain = new Terrain();
        TerrainVue terrainVue = new TerrainVue(terrain, tilePane);

        inventaire = new Inventaire();
        inventaireVue = new InventaireVue(pane, inventaire, paneInventaire);

        jake = new Jake(200, 300, terrain);
        jakeVue = new JakeVue(jake, pane);

        joueur = new Joueur(0, 100, terrain);
        richardVue = new RichardVue(joueur, pane);

        barreDeVieVue = new VieVue(joueur, barreVie);


        clavier = new Clavier(joueur, richardVue);
        pane.setFocusTraversable(true);
        pane.addEventHandler(KeyEvent.KEY_PRESSED, clavier);
        pane.addEventHandler(KeyEvent.KEY_RELEASED, clavier);

        Terraformer terraformer = new Terraformer(terrain,terrainVue, joueur,inventaire,inventaireVue);
        souris = new Souris(inventaireVue, terrainVue, terrain, joueur, inventaire, terraformer);
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, souris);

        initAnimation();
        gameLoop.play();
    }

    private void initAnimation() {
        gameLoop = new Timeline();
        temps = 0;
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(Duration.seconds(0.017), ev -> {
            joueur.seDeplacer();
            jake.seDeplacer();
            temps++;
        });

        gameLoop.getKeyFrames().add(kf);
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public InventaireVue getInventaireVue() {
        return inventaireVue;
    }

    @FXML
    private void retournerAccueil(ActionEvent event) throws IOException {
        System.out.println("Retourner au menu");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/wad/sae_dev/fxml/menu.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sugar Rush");
        stage.setResizable(false);
        stage.show();
    }
}