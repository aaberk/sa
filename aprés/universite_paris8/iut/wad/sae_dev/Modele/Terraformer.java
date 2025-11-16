package universite_paris8.iut.wad.sae_dev.Modele;

import universite_paris8.iut.wad.sae_dev.Modele.Entites.Joueur;
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

    private boolean estDansTerrain(int x, int y) {
        return x >= 0 && x < terrain.largeur() && y >= 0 && y < terrain.hauteur();
    }

    private boolean estSurJoueur(int x, int y) {
        int tailleTuile = terrain.getTailleTuile();
        int joueurXGauche = joueur.getX() / tailleTuile;
        int joueurXDroite = (joueur.getX() + joueur.getLargeur()) / tailleTuile;
        int joueurY = joueur.getY() / tailleTuile;
        int joueurYBas = (joueur.getY() + joueur.getHauteur()) / tailleTuile;

        return (x >= joueurXGauche && x <= joueurXDroite)
                && (y >= joueurY && y <= joueurYBas);
    }

    private boolean estTropLoin(int x, int y) {
        int tailleTuile = terrain.getTailleTuile();
        int joueurX = joueur.getX() / tailleTuile;
        int joueurY = joueur.getY() / tailleTuile;
        int distanceMax = 3;
        return Math.abs(x - joueurX) > distanceMax || Math.abs(y - joueurY) > distanceMax;
    }

    public int gererTerraforming(int x, int y) {
        if (!estDansTerrain(x, y)) return -1;
        if (estTropLoin(x, y)) return -1;
        if (estSurJoueur(x, y)) return -2;

        return terrain.typeTuile(x, y);
    }

    private String verifierConditionsPose(int x, int y, int type) {
        if (type == -1) return "Trop loin pour poser le bloc !";
        if (type == -2) return "Impossible de poser un bloc sur le joueur !";
        if (inventaire.getMateriauCaseSelectionne() == null) return "Aucun matériau sélectionné.";
        return null;
    }

    private String placerBlocTransparent(int x, int y, TypeMateriaux materiaux, int blocSelectionne) {
        if (materiaux.getRole() == Role.CONSTRUCTION && inventaire.contientMateriaux(materiaux)) {
            int blocTransparent = terrain.versionTransparente(blocSelectionne);
            terrain.modifierBloc(x, y, blocTransparent);
            return "Bloc transparent posé en " + x + "," + y;
        }
        return "Pas assez de matériaux pour poser un bloc transparent";
    }

    private String transformerBlocTransparent(int x, int y, TypeMateriaux materiaux, int type) {
        int blocNormal = terrain.versionNormal(type);
        if (materiaux.getRole() == Role.CONSTRUCTION && inventaire.contientMateriaux(materiaux)) {
            if (inventaire.retirerMateriaux(materiaux, 1)) {
                terrain.modifierBloc(x, y, blocNormal);
                return "Bloc normal posé en " + x + "," + y;
            }
        }
        return "Pas assez de matériaux pour poser le bloc normal";
    }

    public String poserUnBloc(int x, int y) {
        int type = gererTerraforming(x, y);
        String erreur = verifierConditionsPose(x, y, type);
        if (erreur != null) return erreur;

        TypeMateriaux materiaux = inventaire.getMateriauCaseSelectionne();
        int blocSelectionne = inventaire.materiauxVersTypeBloc(materiaux);

        if (type == 1) return placerBlocTransparent(x, y, materiaux, blocSelectionne);
        if (terrain.estTransparent(type)) return transformerBlocTransparent(x, y, materiaux, type);

        return "Impossible de poser un bloc ici";
    }

    private void collecterMateriau(int x, int y, int type) {
        TypeMateriaux materiaux = Inventaire.typeBlocVersMateriaux(type);
        if (materiaux != null) inventaire.ajouterMateriaux(materiaux, 1);
    }

    public String casserUnBloc(int x, int y) {
        int type = gererTerraforming(x, y);
        if (type == 1) return "Il n'y a rien à casser.";
        if (type == -1 || type == -2) return "Trop loin pour casser le bloc !";

        if (terrain.estBrise(type)) {
            if (Inventaire.estBlocCollectable(type)) collecterMateriau(x, y, type);
            terrain.modifierBloc(x, y, 1);
            return "Bloc supprimé en " + x + "," + y;
        } else {
            terrain.modifierBloc(x, y, terrain.versionBrisee(type));
            return "Bloc cassé en " + x + "," + y;
        }
    }
}
