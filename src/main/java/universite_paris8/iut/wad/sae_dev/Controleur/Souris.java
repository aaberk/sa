package universite_paris8.iut.wad.sae_dev.Controleur;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import universite_paris8.iut.wad.sae_dev.Vue.InventaireVue;
import universite_paris8.iut.wad.sae_dev.Vue.TerrainVue;
import universite_paris8.iut.wad.sae_dev.Modele.Joueur;
import universite_paris8.iut.wad.sae_dev.Modele.Terrain;
import universite_paris8.iut.wad.sae_dev.Modele.Inventaire;
import universite_paris8.iut.wad.sae_dev.Modele.TypeMateriaux;

public class Souris implements EventHandler<MouseEvent> {
    private InventaireVue inventaireVue;
    private TerrainVue terrainVue;
    private Terrain terrain;
    private Joueur joueur;
    private Inventaire inventaire;

    public Souris(InventaireVue inventaireVue, TerrainVue terrainVue, Terrain terrain, Joueur joueur, Inventaire inventaire) {
        this.inventaireVue = inventaireVue;
        this.joueur = joueur;
        this.terrainVue = terrainVue;
        this.terrain = terrain;
        this.inventaire = inventaire;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double clicX = event.getX();
            double clicY = event.getY();

            if (inventaireVue.gererClicInventaire(clicX, clicY)) {
                return;
            }

            int tailleTuile = terrain.getTailleTuile();
            int x = (int)(clicX / tailleTuile);
            int y = (int)(clicY / tailleTuile);

            if (x >= 0 && x < terrain.largeur() && y >= 0 && y < terrain.hauteur()) {
                int typeActuel = terrain.typeTuile(x, y);

                // ==== CLIC DROIT : poser un bloc ====
                if (event.getButton() == MouseButton.SECONDARY) {
                    int blocSelectionne = joueur.getBlocSelectionne();
                    TypeMateriaux materiauxRequis = Inventaire.typeBlocVersMateriaux(blocSelectionne);

                    if (typeActuel == 1) {
                        // Case ciel : poser un bloc transparent SI on a les matériaux nécessaires
                        if (materiauxRequis != null && inventaire.contientMateriaux(materiauxRequis)) {
                            int blocTransparent = terrain.versionTransparente(blocSelectionne);
                            terrain.modifierBloc(x, y, blocTransparent);
                            System.out.println("Bloc transparent posé (" + blocTransparent + ") en " + x + "," + y);
                        } else {
                            System.out.println("Pas assez de matériaux pour poser un bloc transparent");
                        }
                    }
                    else if (terrain.estTransparent(typeActuel)) {
                        // Si déjà un bloc transparent → poser bloc normal
                        int blocNormal = terrain.versionNormal(typeActuel);
                        if (materiauxRequis != null && inventaire.contientMateriaux(materiauxRequis)) {
                            if (inventaire.retirerMateriaux(materiauxRequis, 1)) {
                                terrain.modifierBloc(x, y, blocNormal);
                                System.out.println("Bloc normal posé (" + blocNormal + ") en " + x + "," + y);
                                inventaireVue.rafraichirAffichage();
                            } else {
                                System.out.println("Pas assez de matériaux pour poser le bloc normal");
                            }
                        } else {
                            System.out.println("Pas assez de matériaux pour transformer le bloc");
                        }
                    } else {
                        System.out.println("Impossible de poser un bloc ici");
                    }

                    terrainVue.majAffichage();

                    // ==== CLIC GAUCHE : casser / récolter ====
                } else if (event.getButton() == MouseButton.PRIMARY) {
                    if (typeActuel == 1) return;

                    if (terrain.estBrise(typeActuel)) {
                        if (Inventaire.estBlocCollectable(typeActuel)) {
                            TypeMateriaux materiauxRecupere = Inventaire.typeBlocVersMateriaux(typeActuel);
                            if (materiauxRecupere != null) {
                                inventaire.ajouterMateriaux(materiauxRecupere, 1);
                                System.out.println("Matériau collecté en " + x + "," + y);
                            }
                        }
                        terrain.modifierBloc(x, y, 1);
                        System.out.println("Bloc supprimé en " + x + "," + y);
                    } else {
                        terrain.modifierBloc(x, y, terrain.versionBrisee(typeActuel));
                        System.out.println("Bloc cassé en " + x + "," + y);
                    }

                    inventaireVue.rafraichirAffichage();
                    terrainVue.majAffichage();
                }
            }
        }
    }
}