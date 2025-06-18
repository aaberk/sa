package universite_paris8.iut.wad.sae_dev.Modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Projectile {

    // Propriétés observables pour la position
    private final IntegerProperty x = new SimpleIntegerProperty();
    private final IntegerProperty y = new SimpleIntegerProperty();

    // Caractéristiques du projectile
    private final int direction;
    private final Terrain terrain;
    private final int DISTANCE_COLLISION = 30;
    private final double GRAVITE_PROJECTILE = 0.3; // Gravité pour les projectiles qui tombent

    private final int vitesse;
    private final int largeur;
    private final int hauteur;
    private final int porteeMax;
    private final boolean projectileVertical;

    // État du projectile
    private boolean actif = true;
    private int distanceParcourue = 0;
    private double velociteY = 0;

    /**
     * Constructeur principal avec paramètres personnalisables
     */
    public Projectile(int x, int y, int direction, Terrain terrain, int vitesse, int largeur, int hauteur, int porteeMax) {
        this.x.set(x);
        this.y.set(y);
        this.direction = direction;
        this.terrain = terrain;
        this.vitesse = vitesse;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.porteeMax = porteeMax;
        this.projectileVertical = (direction == 0); // Direction 0 = projectile vertical
    }


    /**
     * Constructeur spécial pour projectiles verticaux (du drone)
     */
    public static Projectile creerProjectileVertical(int x, int y, Terrain terrain) {
        return new Projectile(x, y, 0, terrain, 4, 16, 16, 400);
    }

    /**
     * Met à jour la position du projectile
     */
    public void seDeplacer() {
        if (!actif) {
            return;
        }

        if (projectileVertical) {
            deplacerVerticalement();
        }
        else {
            deplacerHorizontalement();
        }
    }

    /**
     * Déplacement horizontal classique
     */
    public void deplacerHorizontalement() {
        int deplacement = vitesse * direction;
        int nouveauX = getX() + deplacement;

        // Vérifier les limites du terrain
        if (estHorsLimites(nouveauX)) {
            desactiver();
        }

        // Vérifier la portée maximale
        distanceParcourue += Math.abs(deplacement);
        if (distanceParcourue >= porteeMax) {
            desactiver();
        }

        // Vérifier collision avec le terrain
        if (terrain.collision(nouveauX, getY()) || terrain.collision(nouveauX + largeur, getY() + hauteur)) {
            desactiver();
        }

        setX(nouveauX);
    }

    /**
     * Déplacement vertical avec gravité (pour les projectiles du drone)
     */
    public void deplacerVerticalement() {
        // Appliquer la gravité
        velociteY += GRAVITE_PROJECTILE;
        int nouvelleY = (int) (getY() + velociteY);

        // Vérifier si le projectile touche le sol
        if (terrain.collision(getX(), nouvelleY + hauteur) ||
                terrain.collision(getX() + largeur, nouvelleY + hauteur)) {
            desactiver();
        }

        // Vérifier les limites verticales
        if (nouvelleY > terrain.getHauteurPixels()) {
            desactiver();
        }

        // Vérifier la portée maximale (distance verticale parcourue)
        distanceParcourue += (int) Math.abs(velociteY);
        if (distanceParcourue >= porteeMax) {
            desactiver();
        }

        setY(nouvelleY);
    }

    /**
     * Vérifie si le projectile est hors des limites du terrain
     */
    public boolean estHorsLimites(int nouveauX) {
        return nouveauX < 0 || nouveauX + largeur > terrain.getLargeurPixels();
    }

    /**
     * Vérifie si le projectile touche le joueur
     */
    public boolean toucheJoueur(Joueur joueur) {
        if (!actif) {
            return false;
        }

        boolean collision = estEnCollisionAvec(joueur);
        if (collision) {
            desactiver();
        }

        return collision;
    }

    /**
     * Vérifie la collision avec un personnage donné
     */
    public boolean estEnCollisionAvec(Personnage personnage) {
        int distanceX = Math.abs(getX() - personnage.getX());
        int distanceY = Math.abs(getY() - personnage.getY());

        return distanceX < DISTANCE_COLLISION && distanceY < DISTANCE_COLLISION;
    }

    /**
     * Désactive le projectile
     */
    public void desactiver() {
        this.actif = false;
    }

    /**
     * Vérifie si le projectile peut encore toucher des cibles
     */
    public boolean peutToucher() {
        return actif && distanceParcourue < porteeMax;
    }

    // === PROPRIÉTÉS OBSERVABLES ===
    public IntegerProperty xProperty() { return x; }
    public IntegerProperty yProperty() { return y; }

    // === GETTERS ===
    public int getX() { return x.get(); }
    public int getY() { return y.get(); }
    public boolean isActif() { return actif; }
    public Terrain getTerrain() { return terrain; }

    // === SETTERS ===
    public void setX(int x) { this.x.set(x); }
    public void setY(int y) { this.y.set(y); }

}