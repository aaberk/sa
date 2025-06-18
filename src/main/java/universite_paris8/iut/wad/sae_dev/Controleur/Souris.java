package universite_paris8.iut.wad.sae_dev.Controleur;

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

    public Souris(InventaireVue inventaireVue, TerrainVue terrainVue, Terrain terrain, JoueurVue joueurVue, Inventaire inventaire, Terraformer terraformer) {
        this.inventaireVue = inventaireVue;
        this.terrainVue = terrainVue;
        this.terrain = terrain;
        this.joueurVue = joueurVue;
        this.inventaire = inventaire;
        this.terraformer = terraformer;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double clicX = event.getX();
            double clicY = event.getY();

            if (inventaireVue.gererClicInventaire(clicX, clicY)) {
                return;
            }

            // --- Inventaire : Sélection d'une case
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

            int tailleTuile = terrain.getTailleTuile();
            int x = (int) (clicX / tailleTuile);
            int y = (int) (clicY / tailleTuile);

            TypeMateriaux selection = inventaire.getMateriauCaseSelectionne();

            if (event.getButton() == MouseButton.PRIMARY) {
                ObjetUtilisable outil = inventaire.getObjetSelectionne();
                if (outil != null && outil.getRole() == Role.OUTIL) {
                    outil.utiliser(x, y, terrain, terraformer);
                } else {
                    System.out.println("Cet objet ne permet pas de casser un bloc.");
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                if (selection != null && selection.getRole() == Role.CONSTRUCTION) {
                    terraformer.poserUnBloc(x, y);
                } else {
                    System.out.println("Impossible de poser cet objet.");
                }
            }
            inventaireVue.rafraichirAffichage();
            terrainVue.majAffichage();
        }
    }

}
