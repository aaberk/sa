package universite_paris8.iut.wad.sae_dev.Controleur;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import universite_paris8.iut.wad.sae_dev.Modele.*;
import universite_paris8.iut.wad.sae_dev.Vue.InventaireVue;
import universite_paris8.iut.wad.sae_dev.Vue.TerrainVue;
import universite_paris8.iut.wad.sae_dev.Modele.Joueur;
import universite_paris8.iut.wad.sae_dev.Modele.Terrain;
import universite_paris8.iut.wad.sae_dev.Modele.Inventaire;

public class Souris implements EventHandler<MouseEvent> {
    private InventaireVue inventaireVue;
    private TerrainVue terrainVue;
    private Terrain terrain;
    private Joueur joueur;
    private Inventaire inventaire;
    private Terraformer terraformer;

    public Souris(InventaireVue inventaireVue, TerrainVue terrainVue, Terrain terrain, Joueur joueur, Inventaire inventaire, Terraformer terraformer) {
        this.inventaireVue = inventaireVue;
        this.joueur = joueur;
        this.terrainVue = terrainVue;
        this.terrain = terrain;
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

            // recuperer les coordonnees du clic
            int tailleTuile = terrain.getTailleTuile();
            int x = (int) (clicX / tailleTuile);
            int y = (int) (clicY / tailleTuile);

            if (event.getButton() == MouseButton.SECONDARY) {
                terraformer.poserUnBloc(x, y);
            } else if (event.getButton() == MouseButton.PRIMARY) {
                terraformer.casserUnBloc(x, y);
            }
            inventaireVue.rafraichirAffichage();
            terrainVue.majAffichage();
        }
    }
}
