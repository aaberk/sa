package universite_paris8.iut.wad.sae_dev.Modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Projectile {
    private final IntegerProperty x = new SimpleIntegerProperty();
    private final IntegerProperty y = new SimpleIntegerProperty();

    private int direction;
    private Terrain terrain;
    private boolean actif = true;
    private int portee;
    private int vitesse;
    private int distance = 0;

    public enum TypeProprietaire {
        JOUEUR,
        ENNEMI
    }

    private TypeProprietaire proprietaire;

    public Projectile(int x, int y, int direction, Terrain terrain, int vitesse, int portee, TypeProprietaire proprietaire) {
        this.x.set(x);
        this.y.set(y);
        this.direction = direction;
        this.terrain = terrain;
        this.vitesse = vitesse;
        this.portee = portee;
        this.proprietaire = proprietaire;
    }

    // Constructeur de compatibilité (par défaut = ennemi)
    public Projectile(int x, int y, int direction, Terrain terrain, int vitesse, int portee) {
        this(x, y, direction, terrain, vitesse, portee, TypeProprietaire.ENNEMI);
    }

    public abstract void seDeplacer();

    public boolean estEnCollisionAvec(Personnage p) {
        int dx = Math.abs(getX() - p.getX());
        int dy = Math.abs(getY() - p.getY());
        return dx < 30 && dy < 30;
    }

    public boolean estActif() {
        return actif;
    }

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

    public int getDirection() {
        return direction;
    }

    public int getDistanceParcourue() {
        return distance;
    }

    public void ajouterDistance(int ajout) {
        distance += ajout;
    }

    public boolean peutToucher() {
        return estActif() && getDistanceParcourue() < getPortee();
    }

    public void desactiver() {
        actif = false;
    }

    public TypeProprietaire getProprietaire() {
        return proprietaire;
    }

    public boolean estProjectileDuJoueur() {
        return proprietaire == TypeProprietaire.JOUEUR;
    }

    public boolean estProjectileEnnemi() {
        return proprietaire == TypeProprietaire.ENNEMI;
    }

    public IntegerProperty xProperty() { return x; }
    public IntegerProperty yProperty() { return y; }
    public int getX() { return x.get(); }
    public int getY() { return y.get(); }
    public int getPortee() { return portee; }
    public int getVitesse() { return vitesse; }

    public void setY(int y) { this.y.set(y); }
    public void setX(int x) { this.x.set(x); }

    public Terrain getTerrain() { return terrain; }
}