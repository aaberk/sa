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

    public int gererTerraforming(int x, int y) { // methode get coordonnees et verifier
        int tailleTuile = terrain.getTailleTuile();
        int typeActuel = terrain.typeTuile(x, y);

        if (x >= 0 && x < terrain.largeur() && y >= 0 && y < terrain.hauteur()) {

            // recuperer les coordonnees des 4 coins du joueur
            int joueurXGauche = joueur.getX() / tailleTuile;
            int joueurXDroite = (joueur.getX() + joueur.getLargeur()) / tailleTuile;
            int joueurY = joueur.getY() / tailleTuile;
            int joueurYBas = (joueur.getY() + joueur.getHauteur()) / tailleTuile;

            // verifie si le joueur est dans la tuile cliquee : vrai si le clic est dans sa hitbox
            boolean surJoueur = (x >= joueurXGauche && x <= joueurXDroite) && (y >= joueurY && y <= joueurYBas);

            // verifie si la tuile est trop loin du joueur
            int distanceMax = 3;
            boolean tropLoin = Math.abs(x - joueurXGauche) > distanceMax ||
                    Math.abs(x - joueurXDroite) > distanceMax ||
                    Math.abs(y - joueurY) > distanceMax ||
                    Math.abs(y - joueurYBas) > distanceMax;

            if (tropLoin) {
                return -1;
            }

            // eviter de poser un bloc sur la case que joueur occupe
            if (surJoueur) {
                return -2;
            }
        }
        return typeActuel;
    }

    public String poserUnBloc(int x, int y) {
        int type = gererTerraforming(x,y);
        if (type == -1) {
            return "Trop loin pour poser le bloc !";
        }

        if (type == -2) {
            return "Impossible de poser un bloc sur le joueur !";
        }

        // pour voir si le joueur a assez de matériaux pour poser le bloc
        int blocSelectionne = joueur.getBlocSelectionne();
        TypeMateriaux materiauxRequis = Inventaire.typeBlocVersMateriaux(blocSelectionne);

        if (type == 1) {
            // Case ciel : poser un bloc transparent SI on a les matériaux nécessaires
            if (materiauxRequis != null && inventaire.contientMateriaux(materiauxRequis)) {
                int blocTransparent = terrain.versionTransparente(blocSelectionne);
                terrain.modifierBloc(x, y, blocTransparent);
                return "Bloc transparent posé (" + blocTransparent + ") en " + x + "," + y;
            } else {
                return "Pas assez de matériaux pour poser un bloc transparent";
            } //TODO changer ce message plus tard car on n'est pas censés avoir de bloc transparent : retirer la fonctionnalité poser bloc vide
        } else if (terrain.estTransparent(type)) {
            int blocNormal = terrain.versionNormal(type);
            if (materiauxRequis != null && inventaire.contientMateriaux(materiauxRequis)) {
                if (inventaire.retirerMateriaux(materiauxRequis, 1)) {
                    terrain.modifierBloc(x, y, blocNormal);
                    return "Bloc normal posé (" + blocNormal + ") en " + x + "," + y;
                } else {
                    return "Pas assez de matériaux pour poser le bloc normal";
                }
            } else {
                return "Pas assez de matériaux pour transformer le bloc";
            } // TODO transformer le bloc
        } else {
            return "Impossible de poser un bloc ici";
        }
    }

    public String casserUnBloc(int x, int y) {
        int type = gererTerraforming(x,y);
        if (type == 1) return "Il n'y a rien à casser.";

        if (type == -1 || type == -2) {
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
