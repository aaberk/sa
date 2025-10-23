package universite_paris8.iut.wad.sae_dev.Controleur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    private Pane pane;

    @FXML
    private Button btncrafting;

    // Éléments FXML
    @FXML private Pane paneCamera;
    @FXML private TilePane tilePane;
    @FXML private Pane paneInventaire;
    @FXML private HBox barreVie;


    // Gestion du jeu
    private Timeline gameLoop;
    private final double FRAME_DURATION = 0.017; // ~60 FPS

    // Modèles
    private Terrain terrain;
    private Joueur joueur;
    private PnjJake pnjJake;
    private BrosseADent brosseADent;
    private DentifriceVolant dentifriceVolant;
    private PnjDonut pnjDonut;
    private Inventaire inventaire;
    private List<Projectile> projectiles;

    // Vues
    private ArrierePlan arrierePlan;
    private TerrainVue terrainVue;
    private JoueurVue joueurVue;
    private PnjJakeVue pnjJakeVue;
    private PnjDonutVue pnjDonutVue;
    private BrosseADentVue brosseADentVue;
    private DentifriceVolantVue DentifriceVolantVue;
private ImageView gameOverImageView;

    private InventaireVue inventaireVue;
    private VieVue barreDeVieVue;
    private List<ProjectileVue> projectilesVue;

    // Contrôleurs d'entrée
    private Clavier clavier;
    private Souris souris;
    private Terraformer terraformer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paneCamera.setLayoutX(100);
        this.initialiserBtnCrafting();
        this.initialiserModeles();
        this.initialiserVues();
        this.initialiserControleurs();
        this.demarrerJeu();
        Image gameOverImage = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/affichage/gameover.png").toExternalForm());
        gameOverImageView = new ImageView(gameOverImage);
        gameOverImageView.setX(200);
        gameOverImageView.setFitWidth(1000); // ajuste selon la taille de ta fenêtre
        gameOverImageView.setFitHeight(800);
        gameOverImageView.setVisible(false); // pas visible au début

        pane.getChildren().add(gameOverImageView); // ajoute-la tout en haut

    }

    public void initialiserBtnCrafting() {
        try {
            Image image = new Image(getClass().getResource("/universite_paris8/iut/wad/sae_dev/images/affichage/craft.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            imageView.setPreserveRatio(true);
            btncrafting.setGraphic(imageView);
            btncrafting.setStyle("-fx-background-color: transparent;");
        } catch (Exception e) {
            System.out.println("Erreur chargement image craft.png : " + e.getMessage());
        }
    }

    public void initialiserModeles() {
        terrain = new Terrain();
        inventaire = new Inventaire();
        joueur = new Joueur(0, 100, terrain);
        terrain.setJoueur(joueur);
        pnjJake = new PnjJake(100, 650, terrain, joueur);
        brosseADent = new BrosseADent(300, 100, terrain, joueur);
        terrain.ajouterEnnemi(brosseADent);
        dentifriceVolant = new DentifriceVolant(300, 200, terrain, joueur);
        terrain.ajouterEnnemi(dentifriceVolant);
        pnjDonut = new PnjDonut(720, 500, joueur, terrain);
        projectiles = new ArrayList<>();
    }

    /**
     * Initialise toutes les vues du jeu
     */
    public void initialiserVues() {
        arrierePlan = new ArrierePlan(terrain, paneCamera);
        terrainVue = new TerrainVue(terrain, tilePane);
        inventaireVue = new InventaireVue(pane, inventaire, paneInventaire);
        joueurVue = new JoueurVue(joueur, paneCamera, inventaire);
        pnjJakeVue = new PnjJakeVue(pnjJake, paneCamera);
        brosseADentVue = new BrosseADentVue(brosseADent, paneCamera);
        DentifriceVolantVue = new DentifriceVolantVue(dentifriceVolant, paneCamera);
        barreDeVieVue = new VieVue(joueur, barreVie);
        pnjDonutVue = new PnjDonutVue(pnjDonut, paneCamera);
        projectilesVue = new ArrayList<>();
    }

    /**
     * Initialise les contrôleurs d'entrée (clavier et souris)
     */
    public void initialiserControleurs() {
        // Configuration du clavier
        clavier = new Clavier(joueur, joueurVue, inventaire, terraformer, this);
        paneCamera.setFocusTraversable(true);
        paneCamera.addEventHandler(KeyEvent.KEY_PRESSED, clavier);
        paneCamera.addEventHandler(KeyEvent.KEY_RELEASED, clavier);

        // Configuration de la souris
        Terraformer terraformer = new Terraformer(terrain, terrainVue, joueur, inventaire, inventaireVue);
        souris = new Souris(inventaireVue, terrainVue, terrain, joueurVue, inventaire, terraformer, paneCamera);
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
    /**
     * Met à jour les déplacements de tous les personnages
     */
    public void deplacerPersonnages() {
        joueur.seDeplacer();
        pnjJake.seDeplacer();
        pnjDonut.seDeplacer();

        if (!brosseADent.estMort()) {
            brosseADent.seDeplacer();
        }

        if (!dentifriceVolant.estMort()) {
            dentifriceVolant.seDeplacer();
        }

        pnjJakeVue.mettreAJourAffichage();
    }
// Dans la méthode gererProjectiles() du Controleur.java, ajoutez cette logique :

    public void gererProjectiles() {
        // Création de nouveaux projectiles du dentifrice volant
        if (dentifriceVolant.peutTirer()) {
            creerProjectileDentifrice();
            dentifriceVolant.reinitialiserCompteurTir();
        }

        // Mise à jour de tous les projectiles du terrain
        terrain.miseAJourProjectiles();

        // NOUVELLE LOGIQUE : Gestion des collisions projectiles du joueur avec ennemis
        gererCollisionsProjectilesEnnemis();

        // Mise à jour de l'affichage des projectiles
        terrainVue.majProjectiles();
    }

    /**
     * Gère les collisions entre les projectiles du joueur et les ennemis
     */
    private void gererCollisionsProjectilesEnnemis() {
        List<Projectile> projectilesASupprimer = new ArrayList<>();

        for (Projectile projectile : terrain.getProjectiles()) {
            if (projectile.estProjectileDuJoueur() && projectile.estActif()) {

                // Collision avec BrosseADent
                if (!brosseADent.estMort() && projectileToucheEnnemi(projectile, brosseADent)) {
                    brosseADent.subirDegats(1);
                    projectile.desactiver();
                    projectilesASupprimer.add(projectile);

                    if (brosseADent.estMort()) {
                        System.out.println("BrosseADent éliminée !");
                        // Retirer complètement l'ennemi de l'affichage
                        paneCamera.getChildren().remove(brosseADentVue.getImageView());
                        terrain.retirerEnnemi(brosseADent);
                    }
                }

                // Collision avec DentifriceVolant
                if (!dentifriceVolant.estMort() && projectileToucheEnnemi(projectile, dentifriceVolant)) {
                    dentifriceVolant.subirDegats(1);
                    projectile.desactiver();
                    projectilesASupprimer.add(projectile);

                    if (dentifriceVolant.estMort()) {
                        System.out.println("DentifriceVolant éliminé !");
                        // Retirer complètement l'ennemi de l'affichage
                        paneCamera.getChildren().remove(DentifriceVolantVue.getImageView());
                        terrain.retirerEnnemi(dentifriceVolant);
                    }
                }
            }
        }

        // Supprimer les projectiles désactivés
        for (Projectile proj : projectilesASupprimer) {
            terrain.retirerProjectile(proj);
        }
    }

    /**
     * Vérifie si un projectile touche un ennemi
     */
    private boolean projectileToucheEnnemi(Projectile projectile, Ennemi ennemi) {
        int distanceX = Math.abs(projectile.getX() - ennemi.getX());
        int distanceY = Math.abs(projectile.getY() - ennemi.getY());

        // Zone de collision (ajustable selon la taille des sprites)
        int seuilCollision = 40;

        return distanceX < seuilCollision && distanceY < seuilCollision;
    }

    public void creerProjectileDentifrice() {
        int projectileX = this.dentifriceVolant.getPositionTirX();
        int projectileY = this.dentifriceVolant.getPositionTirY();
        ProjectileDentifrice projectileDentifrice = ProjectileDentifrice.creerProjectileVertical(projectileX, projectileY, this.terrain);

        // Ajout au terrain (modèle central)
        this.terrain.ajouterProjectile(projectileDentifrice);

        // Création de la vue
        ProjectileDentifriceVue projectileVue = new ProjectileDentifriceVue(projectileDentifrice, this.paneCamera);
        this.terrainVue.ajouterProjectileVue(projectileVue);
    }

    public void gererVie() {
        // Collision avec la brosse à dent (seulement si elle n'est pas morte)
        if (!brosseADent.estMort() && this.brosseADent.toucheJoueur()) {
            this.joueur.retirerVie();
        }

        for(Projectile projectile : this.terrain.getProjectiles()) {
            if (projectile.estProjectileEnnemi() && projectile.toucheJoueur(this.joueur)) {
                this.joueur.retirerVie();
                System.out.println("Projectile ennemi touche le joueur!");
                break;
            }
        }
    }

    public void tirerFlecheDuJoueur(int direction) {
        int flecheX = this.joueur.getX() + 35;
        int flecheY = this.joueur.getY() + 10;

        FlecheArc fleche = new FlecheArc(flecheX, flecheY, direction, this.terrain, 8, 300);

        // Ajout au terrain (modèle central)
        this.terrain.ajouterProjectile(fleche);

        // Création de la vue
        FlecheArcVue vue = new FlecheArcVue(fleche, this.paneCamera);
        this.terrainVue.ajouterProjectileVue(vue);
    }

    /**
     * Vérifie l'état général du jeu (game over, victoire, etc.)
     */
    public void verifierEtatJeu() {
        if (joueur.estMort()) {
            gererGameOver();
            gameOverImageView.setVisible(true);
        }
    }

    /**
     * Gère la fin de partie
     */
    public void gererGameOver() {
        gameLoop.stop();
        System.out.println("Game Over!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/wad/sae_dev/fxml/gameover.fxml"));
            Parent gameOverRoot = loader.load();

            Scene scene = new Scene(gameOverRoot);
            Stage stage = (Stage) pane.getScene().getWindow(); // récupère la fenêtre actuelle
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }    }

    // Getters pour l'accès aux modèles (si nécessaire pour d'autres classes)
    public Terrain getTerrain() { return terrain; }
    public Inventaire getInventaire() { return inventaire; }
    public InventaireVue getInventaireVue() { return inventaireVue; }

    /**
     * Gère le retour au menu principal
     */
    @FXML
    public void retournerAccueil(ActionEvent event) throws IOException {
        this.arreterJeu();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/universite_paris8/iut/wad/sae_dev/fxml/menu.fxml"));
        Scene scene = new Scene((Parent)loader.load(), (double)900.0F, (double)600.0F);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sugar Rush");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void ouvrirCrafting(ActionEvent event) throws IOException {
        // Crée une nouvelle fenêtre
        Stage craftingStage = new Stage();

        // Crée la vue de crafting avec ton inventaire actuel ET la référence au pane
        CraftingVue craftingVue = new CraftingVue(inventaire, inventaireVue, paneCamera); // ← Passer le pane en paramètre

        // Crée la scène
        craftingVue.setInventaireVue(inventaireVue);
        Scene craftingScene = new Scene(craftingVue, 350, 400);

        // Applique la scène à la fenêtre
        craftingStage.setScene(craftingScene);
        craftingStage.setTitle("Menu de Crafting");

        // Ajouter un listener pour remettre le focus quand la fenêtre se ferme
        craftingStage.setOnHidden(e -> {
            // Remettre le focus sur le pane principal
            paneCamera.requestFocus();
        });

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