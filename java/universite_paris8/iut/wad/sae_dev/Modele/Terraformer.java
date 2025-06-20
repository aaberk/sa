package universite_paris8.iut.wad.sae_dev.Modele;

import universite_paris8.iut.wad.sae_dev.Vue.InventaireVue;
import universite_paris8.iut.wad.sae_dev.Vue.TerrainVue;

public class Terraformer {

    private Terrain terrain;
    private TerrainVue terrainVue;
    private Joueur joueur;
    private Inventaire inventaire;
    private InventaireVue inventaireVue;

    public Terraformer(Terrain terrain, TerrainVue terrainVue, Joueur joueur, Inventaire inventaire, InventaireVue inventaireVue) {
        this.terrain = terrain;
        this.terrainVue = terrainVue;
        this.joueur = joueur;
        this.inventaire = inventaire;
        this.inventaireVue = inventaireVue;
    }

    public int gererTerraforming(int x, int y) {
        int tailleTuile = terrain.getTailleTuile();

        // ✅ Vérifie que les coordonnées sont dans le terrain
        if (x < 0 || x >= terrain.largeur() || y < 0 || y >= terrain.hauteur()) {
            return -1;
        }

        // 📍 Coordonnées du joueur en cases
        int joueurXGauche = joueur.getX() / tailleTuile;
        int joueurXDroite = (joueur.getX() + joueur.getLargeur()) / tailleTuile;
        int joueurY = joueur.getY() / tailleTuile;
        int joueurYBas = (joueur.getY() + joueur.getHauteur()) / tailleTuile;

        // 🔒 Empêche de casser/poser sur le joueur
        boolean surJoueur = (x >= joueurXGauche && x <= joueurXDroite)
                && (y >= joueurY && y <= joueurYBas);

        int distanceMax = 3;
        boolean tropLoin = Math.abs(x - joueurXGauche) > distanceMax
                || Math.abs(x - joueurXDroite) > distanceMax
                || Math.abs(y - joueurY) > distanceMax
                || Math.abs(y - joueurYBas) > distanceMax;

        if (tropLoin) return -1;
        if (surJoueur) return -2;

        // ✅ Si tout va bien, on retourne le type de tuile
        return terrain.typeTuile(x, y);
    }


    public String poserUnBloc(int x, int y) {
        int type = gererTerraforming(x, y);
        if (type == -1) {
            System.out.println("trop loin pour construire");
            return "Trop loin pour poser le bloc !";
        }

        if (type == -2) {
            return "Impossible de poser un bloc sur le joueur !";
        }

        // 🌱 Utilisation du matériau sélectionné dans l’inventaire
        TypeMateriaux materiauxRequis = inventaire.getMateriauCaseSelectionne();
        if (materiauxRequis == null) return "Aucun matériau sélectionné.";

        int blocSelectionne = inventaire.materiauxVersTypeBloc(materiauxRequis);

        if (type == 1) {
            // 🟦 Case ciel : poser un bloc transparent si assez de matériaux
            if (materiauxRequis.getRole() == Role.CONSTRUCTION && inventaire.contientMateriaux(materiauxRequis)) {
                int blocTransparent = terrain.versionTransparente(blocSelectionne);
                terrain.modifierBloc(x, y, blocTransparent);
                return "Bloc transparent posé (" + blocTransparent + ") en " + x + "," + y;
            } else {
                return "Pas assez de matériaux pour poser un bloc transparent";
            }

        } else if (terrain.estTransparent(type)) {
            // 🔁 Transformation du bloc transparent en bloc normal
            int blocNormal = terrain.versionNormal(type);
            if (materiauxRequis.getRole() == Role.CONSTRUCTION && inventaire.contientMateriaux(materiauxRequis)) {
                if (inventaire.retirerMateriaux(materiauxRequis, 1)) {
                    terrain.modifierBloc(x, y, blocNormal);
                    return "Bloc normal posé (" + blocNormal + ") en " + x + "," + y;
                } else {
                    return "Pas assez de matériaux pour poser le bloc normal";
                }
            } else {
                return "Pas assez de matériaux pour transformer le bloc";
            }

        } else {
            return "Impossible de poser un bloc ici";
        }
    }


    public String casserUnBloc(int x, int y) {
        int type = gererTerraforming(x,y);
        if (type == 1) return "Il n'y a rien à casser.";

        if (type == -1 || type == -2) {
            System.out.println("trop loin pr casser");
            return "Trop loin pour casser le bloc !";
        }

        if (terrain.estBrise(type)) {
            if (Inventaire.estBlocCollectable(type)) {
                TypeMateriaux materiauxRecupere = Inventaire.typeBlocVersMateriaux(type);
                if (materiauxRecupere != null) {
                    inventaire.ajouterMateriaux(materiauxRecupere, 1);
                    System.out.println("Matériau collecté en " + x + "," + y);
                }
            }
            terrain.modifierBloc(x, y, 1);
            return "Bloc supprimé en " + x + "," + y; // ne s'affiche pas
        } else {
            terrain.modifierBloc(x, y, terrain.versionBrisee(type));
            return "Bloc cassé en " + x + "," + y;
        }
    }
}
