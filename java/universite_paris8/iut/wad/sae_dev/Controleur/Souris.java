package universite_paris8.iut.wad.sae_dev.Controleur;

/**
 * Gestionnaire des événements de souris pour le jeu.
 * Gère les clics pour l'inventaire, la sélection d'outils et les actions sur le terrain
 * en tenant compte du décalage de la caméra qui suit le joueur.
 */

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import universite_paris8.iut.wad.sae_dev.Modele.*;
import universite_paris8.iut.wad.sae_dev.Vue.InventaireVue;
import universite_paris8.iut.wad.sae_dev.Vue.JoueurVue;
import universite_paris8.iut.wad.sae_dev.Vue.TerrainVue;

public class Souris implements EventHandler<MouseEvent> {
    private InventaireVue inventaireVue;
    private TerrainVue terrainVue;
    private Terrain terrain;
    private JoueurVue joueurVue;
    private Inventaire inventaire;
    private Terraformer terraformer;
    private javafx.scene.layout.Pane paneCamera;

    public Souris(InventaireVue inventaireVue, TerrainVue terrainVue, Terrain terrain, JoueurVue joueurVue, Inventaire inventaire, Terraformer terraformer, javafx.scene.layout.Pane paneCamera) {
        this.inventaireVue = inventaireVue;
        this.terrainVue = terrainVue;
        this.terrain = terrain;
        this.joueurVue = joueurVue;
        this.inventaire = inventaire;
        this.terraformer = terraformer;
        this.paneCamera = paneCamera;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double clicX = event.getX();
            double clicY = event.getY();

            // Ajustement des coordonnées selon la position de la caméra
            double decalageX = paneCamera.getTranslateX();
            double decalageY = paneCamera.getTranslateY();
            double clicXAjuste = clicX - decalageX;
            double clicYAjuste = clicY - decalageY;

            // Gestion de l'inventaire (coordonnées originales)
            if (!inventaireVue.gererClicInventaire(clicX, clicY)) {
                // Sélection d'une case d'inventaire
                double zoneX = 350;
                double zoneY = 60;
                int tailleCase = 56;
                int nbCases = inventaire.getTaille();

            if (clicY >= zoneY && clicY <= zoneY + tailleCase) {
                for (int i = 0; i < nbCases; i++) {
                    double xMin = zoneX + i * tailleCase;
                    double xMax = xMin + tailleCase;

                    if (clicX >= xMin && clicX <= xMax) {
                        inventaire.selectionnerCase(i);
                        ObjetUtilisable outil = inventaire.getObjetSelectionne();
                        joueurVue.changerImageSelonOutil(outil);
                        System.out.println("Sélectionné : case " + i + " → " + inventaire.getMateriau(i));
                        inventaireVue.rafraichirAffichage(); // met à jour visuel
                        break;
                    }
                }
            }

                // Actions sur le terrain (coordonnées ajustées)
                int tailleTuile = terrain.getTailleTuile();
                int x = (int) (clicXAjuste / tailleTuile);
                int y = (int) (clicYAjuste / tailleTuile);

                TypeMateriaux selection = inventaire.getMateriauCaseSelectionne();

                if (event.getButton() == MouseButton.PRIMARY) {
                    ObjetUtilisable outil = inventaire.getObjetSelectionne();
                    if (outil != null && outil.getRole() == Role.OUTIL) {
                        outil.utiliser(x-2, y, terrain, terraformer);
                    }
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    if (selection != null && selection.getRole() == Role.CONSTRUCTION) {
                        terraformer.poserUnBloc(x-2, y);
                    }
                }

                inventaireVue.rafraichirAffichage();
                terrainVue.majAffichage();
            }
        }
    }
}