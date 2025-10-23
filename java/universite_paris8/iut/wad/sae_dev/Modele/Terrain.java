// Cette classe définit la structure du terrain sous forme d'une grille de tuiles codées par des entiers.

package universite_paris8.iut.wad.sae_dev.Modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Terrain {

    private static final int TAILLE_TUILE = 59;
// correspondance des blocs
// 1 = ciel
// 5 = pelouse de base
// 4 = terre
// 3 = bloc cookie
// 2 = pelouse extremite droite
// 6 = pelouse extremite gauche

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
            // Lignes 20-27: Couches souterraines moyennes,
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

    private Joueur joueur;
    private List<Ennemi> ennemis = new ArrayList<>();
    private List<Projectile> projectiles = new ArrayList<>();

    // === GETTERS / SETTERS ===

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public List<Ennemi> getListeEnnemis() {
        return ennemis;
    }

    public void ajouterEnnemi(Ennemi e) {
        ennemis.add(e);
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void ajouterProjectile(Projectile p) {
        projectiles.add(p);
    }

    // === PROJECTILES ===

    public void miseAJourProjectiles() {
        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
            Projectile p = it.next();
            p.seDeplacer();
            if (!p.estActif()) {
                it.remove();
            }
        }
    }

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

    public boolean estBrise(int type) {
        return type >= 12 && type <= 16;
    }

    public boolean estTransparent(int type) {
        return type >= 22 && type <= 26;
    }

    public boolean estNormal(int type) {
        return !estBrise(type) && !estTransparent(type);
    }

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


    public int typeTuile(int x, int y) {
        return this.typesTuiles[y][x];
    }

    public int hauteur() {
        return this.typesTuiles.length;
    }

    public int largeur() {
        return this.typesTuiles[0].length;
    }

    public int getLargeurPixels() {
        return largeur() * TAILLE_TUILE;
    }

    public int getHauteurPixels() {
        return hauteur() * TAILLE_TUILE;
    }

    public int getTailleTuile() {
        return TAILLE_TUILE;
    }

    public boolean collision(int tuileX, int tuileY) {
        int colonne = tuileX / TAILLE_TUILE;
        int ligne = tuileY / TAILLE_TUILE;

        if (colonne < 0 || colonne >= largeur() || ligne < 0 || ligne >= hauteur()) {
            return true;
        }

        int type = typeTuile(colonne, ligne);
        return type != 1 && !(type >= 22 && type <= 26); // Peut traverser si c'est une case ciel ou une case transparente
    }

    public void modifierBloc(int x, int y, int nouveauType) {
        if (x >= 0 && x < largeur() && y >= 0 && y < hauteur()) {
            typesTuiles[y][x] = nouveauType;
        }
    }

    /**
     * Retire un ennemi de la liste des ennemis
     */
    public void retirerEnnemi(Ennemi ennemi) {
        if (ennemis != null) {
            ennemis.remove(ennemi);
            System.out.println("Ennemi retiré du terrain");
        }
    }

    /**
     * Retire un projectile de la liste des projectiles
     */
    public void retirerProjectile(Projectile projectile) {
        if (projectiles != null) {
            projectiles.remove(projectile);
        }
    }

    // Ajoutez ces méthodes à votre classe Terrain.java

    /**
     * Convertit une coordonnée pixel X en coordonnée de colonne de grille
     */
    public int getColonne(int pixelX) {
        return pixelX / TAILLE_TUILE;
    }

    /**
     * Convertit une coordonnée pixel Y en coordonnée de ligne de grille
     */
    public int getLigne(int pixelY) {
        return pixelY / TAILLE_TUILE;
    }

    /**
     * Retourne le nombre de lignes du terrain
     */
    public int ligne() {
        return hauteur();
    }

    /**
     * Retourne le nombre de colonnes du terrain
     */
    public int colonne() {
        return largeur();
    }
}
