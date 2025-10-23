package universite_paris8.iut.wad.sae_dev.Modele;

/**
 * Cette classe définit la structure du terrain sous forme d'une grille de tuiles codées par des entiers.
 * Elle gère uniquement la carte, les collisions et les modifications de blocs.
 */
public class Terrain {

    private static final int TAILLE_TUILE = 59;

    // 1 = ciel
    // 5 = pelouse de base
    // 4 = terre
    // 3 = bloc cookie
    // 2 = pelouse droite
    // 6 = pelouse gauche

    private final int[][] typesTuiles = {
            // Lignes 0-4: Ciel/Air (type 1)
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            // Lignes 5-19: Couches souterraines avec motifs
            {5,5,4,4,4,4,5,1,4,4,4,5,5,1,4,4,4,4,5,4,4,4,1,5,5,5,4,4,4,4,5,5},
            {4,4,4,3,4,4,4,1,4,4,4,4,4,1,4,3,4,4,4,4,4,4,1,4,4,4,4,3,4,4,4,4},
            {4,4,4,4,4,4,3,4,4,4,4,4,4,4,4,4,4,4,3,4,4,4,4,4,4,4,4,4,4,4,3,4},
            {3,4,3,3,4,4,4,4,3,4,3,3,4,3,4,3,3,4,4,4,4,3,4,3,3,4,3,4,3,3,4,4},
            {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
            {4,4,3,4,4,4,4,3,4,4,4,4,4,4,3,4,4,4,4,3,4,4,4,4,4,4,3,4,4,4,4,3},
            {4,4,4,4,4,3,4,4,4,4,4,4,4,4,4,4,4,3,4,4,4,4,4,4,4,4,4,4,4,3,4,4},
            {3,4,4,4,3,4,4,3,4,4,3,4,3,3,4,4,4,3,4,4,3,4,3,3,4,4,4,3,4,4,3,4},
            {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
            {4,4,4,3,4,4,3,4,4,4,4,4,4,4,4,3,4,4,3,4,4,4,4,4,4,4,4,3,4,4,3,4},
            {3,4,4,4,4,4,4,3,4,4,3,3,4,3,4,4,4,4,4,4,4,3,4,4,3,3,4,3,4,4,4,4},
            {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
            {3,4,4,4,4,4,4,3,4,3,3,4,3,4,4,4,4,4,4,4,3,4,3,3,4,3,4,4,4,4,4,4},
            {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
            {4,3,4,4,4,4,3,4,4,4,4,4,4,3,4,4,4,4,3,4,4,4,4,4,4,3,4,4,4,4,3,4},
            {4,4,4,4,3,4,4,4,4,4,4,4,4,4,4,4,3,4,4,4,4,4,4,4,4,4,4,4,3,4,4,4},
            {3,4,4,4,4,4,4,4,3,4,3,3,4,3,4,4,4,4,4,4,4,4,3,4,3,3,4,3,4,4,4,4},
            {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
            // Lignes 28-31: Couches souterraines profondes (bedrock)
            {4,4,3,4,4,4,4,3,4,4,4,4,4,4,3,4,4,4,4,3,4,4,4,4,4,4,3,4,4,4,4,3},
            {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
            {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
            {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}
    };

    // === MÉTHODES DE GESTION DES BLOCS ===

    /**
     * Retourne la version brisée d'un bloc
     */
    public int versionBrisee(int type) {
        switch (type) {
            case 2: return 12; // extrémité droite → brisée
            case 3: return 13; // cookie → cookie brisé
            case 4: return 14; // terre → terre brisée
            case 5: return 15; // pelouse → pelouse brisée
            case 6: return 16; // extrémité gauche → brisée
            default: return 1;
        }
    }

    /**
     * Vérifie si un bloc est dans un état brisé
     */
    public boolean estBrise(int type) {
        return type >= 12 && type <= 16;
    }

    /**
     * Vérifie si un bloc est transparent
     */
    public boolean estTransparent(int type) {
        return type >= 22 && type <= 26;
    }

    /**
     * Vérifie si un bloc est dans son état normal
     */
    public boolean estNormal(int type) {
        return !estBrise(type) && !estTransparent(type);
    }

    /**
     * Convertit un bloc transparent en bloc normal
     */
    public int versionNormal(int typeTransparent) {
        switch (typeTransparent) {
            case 22: return 2;
            case 23: return 3;
            case 24: return 4;
            case 25: return 5;
            case 26: return 6;
            default: return 1;
        }
    }

    /**
     * Convertit un bloc normal en bloc transparent
     */
    public int versionTransparente(int typeNormal) {
        switch (typeNormal) {
            case 2: return 22;
            case 3: return 23;
            case 4: return 24;
            case 5: return 25;
            case 6: return 26;
            default: return 1;
        }
    }

    // === MÉTHODES D'ACCÈS AUX TUILES ===

    /**
     * Retourne le type de tuile à une position donnée
     */
    public int typeTuile(int x, int y) {
        return this.typesTuiles[y][x];
    }

    /**
     * Modifie le type de bloc à une position donnée
     */
    public void modifierBloc(int x, int y, int nouveauType) {
        if (x >= 0 && x < largeur() && y >= 0 && y < hauteur()) {
            typesTuiles[y][x] = nouveauType;
        }
    }

    // === DIMENSIONS DU TERRAIN ===

    public int hauteur() {
        return this.typesTuiles.length;
    }

    public int largeur() {
        return this.typesTuiles[0].length;
    }

    public int largeurEnPixels() {
        return largeur() * TAILLE_TUILE;
    }

    public int getHauteurPixels() {
        return hauteur() * TAILLE_TUILE;
    }

    public int getTailleTuile() {
        return TAILLE_TUILE;
    }

    // === CONVERSIONS COORDONNÉES ===

    public int getColonne(int pixelX) {
        return pixelX / TAILLE_TUILE;
    }

    public int getLigne(int pixelY) {
        return pixelY / TAILLE_TUILE;
    }

    public int ligne() {
        return hauteur();
    }

    public int colonne() {
        return largeur();
    }

    // === GESTION DES COLLISIONS ===

    public boolean collision(int tuileX, int tuileY) {
        int colonne = tuileX / TAILLE_TUILE;
        int ligne = tuileY / TAILLE_TUILE;

        if (colonne < 0 || colonne >= largeur() || ligne < 0 || ligne >= hauteur()) {
            return true;
        }

        int type = typeTuile(colonne, ligne);
        // Peut traverser si c'est une case ciel ou une case transparente
        return type != 1 && !(type >= 22 && type <= 26);
    }
}