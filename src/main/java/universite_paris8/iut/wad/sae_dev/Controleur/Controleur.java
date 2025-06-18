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
import universite_paris8.iut.wad.sae_dev.Vue.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    // Éléments FXML
    @FXML private Pane pane;
    @FXML private TilePane tilePane;
    @FXML private Pane paneInventaire;
    @FXML private HBox barreVie;
    @FXML private Pane cameraPane;


    // Gestion du jeu
    private Timeline gameLoop;
    private final double FRAME_DURATION = 0.017; // ~60 FPS

    // Modèles
    private Terrain terrain;
    private Joueur joueur;
    private PnjJake pnjJake;
    private BrosseADent brosseADent;
    private DentifriceVolant dentifriceVolant;
    private Inventaire inventaire;
    private List<Projectile> projectiles;

    // Vues
    private TerrainVue terrainVue;
    private JoueurVue joueurVue;
    private PnjJakeVue pnjJakeVue;
    private BrosseADentVue brosseADentVue;
    private DentifriceVolantVue DentifriceVolantVue;
    private InventaireVue inventaireVue;
    private VieVue barreDeVieVue;
    private List<ProjectileVue> projectilesVue;

    // Contrôleurs d'entrée
    private Clavier clavier;
    private Souris souris;
    private Terraformer terraformer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserModeles();
        initialiserVues();
        initialiserControleurs();
        demarrerJeu();
    }

    /**
     * Initialise tous les modèles du jeu
     */
    public void initialiserModeles() {
        terrain = new Terrain();
        inventaire = new Inventaire();
        joueur = new Joueur(0, 100, terrain);
        pnjJake = new PnjJake(200, 300, terrain, joueur);
        brosseADent = new BrosseADent(300, 100, terrain, joueur);
        terrain.ajouterEnnemi(brosseADent);
        dentifriceVolant = new DentifriceVolant(300, 100, terrain, joueur);
        terrain.ajouterEnnemi(dentifriceVolant);
        projectiles = new ArrayList<>();
    }

    /**
     * Initialise toutes les vues du jeu
     */
    public void initialiserVues() {
        terrainVue = new TerrainVue(terrain, tilePane);
        inventaireVue = new InventaireVue(cameraPane, inventaire, paneInventaire);
        joueurVue = new JoueurVue(joueur, pane, inventaire);
        pnjJakeVue = new PnjJakeVue(pnjJake, pane);
        brosseADentVue = new BrosseADentVue(brosseADent, pane);
        DentifriceVolantVue = new DentifriceVolantVue(dentifriceVolant, pane);
        barreDeVieVue = new VieVue(joueur, barreVie);
        projectilesVue = new ArrayList<>();
    }

    /**
     * Initialise les contrôleurs d'entrée (clavier et souris)
     */
    public void initialiserControleurs() {
        // Configuration du clavier
        clavier = new Clavier(joueur, joueurVue, inventaire, terraformer);
        pane.setFocusTraversable(true);
        pane.addEventHandler(KeyEvent.KEY_PRESSED, clavier);
        pane.addEventHandler(KeyEvent.KEY_RELEASED, clavier);

        // Configuration de la souris
        Terraformer terraformer = new Terraformer(terrain, terrainVue, joueur, inventaire, inventaireVue);
        souris = new Souris(inventaireVue, terrainVue, terrain, joueurVue, inventaire, terraformer);
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, souris);
    }

    /**
     * Démarre la boucle de jeu
     */
    public void demarrerJeu() {
        initAnimation();
        gameLoop.play();
    }

    /**
     * Initialise la boucle d'animation du jeu
     */
    public void initAnimation() {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(Duration.seconds(FRAME_DURATION), ev -> mettreAJourJeu());
        gameLoop.getKeyFrames().add(kf);
    }

    /**
     * Méthode principale de mise à jour du jeu appelée à chaque frame
     */
    public void mettreAJourJeu() {
        deplacerPersonnages();
        gererProjectiles();
        gererVie();
        verifierEtatJeu();
        joueurVue.cameraPersonnage();
    }

    /**
     * Met à jour les déplacements de tous les personnages
     */
    public void deplacerPersonnages() {
        joueur.seDeplacer();
        pnjJake.seDeplacer();
        brosseADent.seDeplacer();
        dentifriceVolant.seDeplacer();
        pnjJakeVue.mettreAJourImage();

    }

    /**
     * Gère la logique des projectiles (création, déplacement, suppression)
     */
    public void gererProjectiles() {
        // Création de nouveaux projectiles
        if (dentifriceVolant.peutTirer()) {
            creerProjectile();
            dentifriceVolant.reinitialiserCompteurTir();
        }

        // Mise à jour des projectiles existants
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            projectile.seDeplacer();

            // Suppression des projectiles inactifs
            if (!projectile.isActif()) {
                supprimerProjectile(i);
            }
        }
    }




    /**
     * Crée un nouveau projectile tiré par la brosse à dent (drone)
     * À remplacer dans votre Controleur.java
     */
    public void creerProjectile() {
        // Utilise les nouvelles méthodes de positionnement du drone
        int projectileX = dentifriceVolant.getPositionTirX();
        int projectileY = dentifriceVolant.getPositionTirY();

        // Crée un projectile vertical qui tombe
        Projectile projectile = Projectile.creerProjectileVertical(projectileX, projectileY, terrain);
        ProjectileVue projectileVue = new ProjectileVue(projectile, pane);

        projectiles.add(projectile);
        projectilesVue.add(projectileVue);

        System.out.println("Drone tire un projectile vertical à la position: " + projectileX + ", " + projectileY);
    }
    /**
     * Supprime un projectile du jeu (modèle et vue)
     */
    public void supprimerProjectile(int index) {
        projectilesVue.get(index).retirerDuPane();
        projectiles.remove(index);
        projectilesVue.remove(index);
    }

    /**
     * Gère toutes les collisions
     */
    public void gererVie() {
        // Collision brosse à dent - joueur
        if (brosseADent.toucheJoueur()) {
            joueur.retirerVie();
            System.out.println("Brosse à dent touche le joueur!");
        }
        // Collisions projectiles - joueur
        for (Projectile projectile : projectiles) {
            if (projectile.toucheJoueur(joueur)) {
                joueur.retirerVie();
                System.out.println("Projectile touche le joueur!");
            }
        }
    }

    /**
     * Vérifie l'état général du jeu (game over, victoire, etc.)
     */
    public void verifierEtatJeu() {
        if (joueur.estMort()) {
            gererGameOver();
        }
    }

    /**
     * Gère la fin de partie
     */
    public void gererGameOver() {
        gameLoop.stop();
        System.out.println("Game Over!");
        // TODO: Afficher écran de game over
    }

    // Getters pour l'accès aux modèles (si nécessaire pour d'autres classes)
    public Terrain getTerrain() { return terrain; }
    public Inventaire getInventaire() { return inventaire; }
    public InventaireVue getInventaireVue() { return inventaireVue; }

    /**
     * Gère le retour au menu principal
     */
    @FXML
    public void retournerAccueil(ActionEvent event) throws IOException {
        arreterJeu();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/wad/sae_dev/fxml/menu.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sugar Rush");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void ouvrirCrafting(ActionEvent event) throws IOException {
// Crée une nouvelle fenêtre
        Stage craftingStage = new Stage();

// Crée la vue de crafting avec ton inventaire actuel
        CraftingVue craftingVue = new CraftingVue(inventaire,inventaireVue); // ← ici tu dois passer l'inventaire réel du joueur !

// Crée la scène
        craftingVue.setInventaireVue(inventaireVue); // ← ceci est crucial
        Scene craftingScene = new Scene(craftingVue, 350, 400);

// Applique la scène à la fenêtre
        craftingStage.setScene(craftingScene);
        craftingStage.setTitle("Menu de Crafting");
        craftingStage.show();

    }

    /**
     * Arrête proprement le jeu
     */
    public void arreterJeu() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
}