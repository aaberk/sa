package universite_paris8.iut.wad.sae_dev.Modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Personnage {
        private IntegerProperty x = new SimpleIntegerProperty();
        private IntegerProperty y = new SimpleIntegerProperty();
        private IntegerProperty direction = new SimpleIntegerProperty(0);
        private int largeur;
        private int hauteur;
        private int vitesse;
        private Terrain terrain;
        private IntegerProperty vie = new SimpleIntegerProperty();
        private int vieMax;
        private boolean dansLesAirs = false;
        private final double GRAVITE = 0.6;
        private double velociteY = 0;

        public Personnage(int x, int y, int largeur, int hauteur, int vitesse, int vieMax, Terrain terrain) {
                this.x.set(x);
                this.y.set(y);
                this.largeur = largeur;
                this.hauteur = hauteur;
                this.vitesse = vitesse;
                this.vieMax = vieMax;
                this.vie.set(vieMax);
                this.terrain = terrain;
        }

        public IntegerProperty xProperty() {
                return x;
        }

        public IntegerProperty yProperty() {
                return y;
        }

        public IntegerProperty directionProperty() {
                return direction;
        }

        public IntegerProperty vieProperty() {
                return vie;
        }

        public int getX() {
                return x.get();
        }

        public int getY() {
                return y.get();
        }

        public int getDirection() {
                return direction.get();
        }

        public int getVie() {
                return vie.get();
        }

        public void setX(int x) {
                this.x.set(x);
        }

        public void setY(int y) {
                this.y.set(y);
        }

        public void setDirection(int direction) {
                this.direction.set(direction);
        }

        public int getLargeur() {
                return largeur;
        }

        public int getHauteur() {
                return hauteur;
        }

        public int getVitesse() {
                return vitesse;
        }

        public Terrain getTerrain() {
                return terrain;
        }

        public boolean isDansLesAirs() {
                return dansLesAirs;
        }

        public void setDansLesAirs(boolean dansLesAirs) {
                this.dansLesAirs = dansLesAirs;
        }

        public double getVelociteY() {
                return velociteY;
        }

        public void setVelociteY(double velociteY) {
                this.velociteY = velociteY;
        }

        public double getGravite() {
                return GRAVITE;
        }

        public void deplacerGauche() {
                int nouveauX = getX() - vitesse;
                if (nouveauX >= 0 && !terrain.collision(nouveauX, getY() + hauteur)) {
                        setX(nouveauX);
                }
        }

        public void deplacerDroite() {
                int nouveauX = getX() + vitesse;
                if (nouveauX + largeur <= terrain.getLargeurPixels() &&
                        !terrain.collision(nouveauX + largeur, getY() + hauteur)) {
                        setX(nouveauX);
                }
        }

        public void appliquerGravite() {
                if (dansLesAirs) {
                        velociteY += GRAVITE;

                        int nouvelleY = (int) (getY() + velociteY);

                        if (!terrain.collision(getX(), nouvelleY + hauteur) &&
                                !terrain.collision(getX() + largeur, nouvelleY + hauteur)) {
                                setY(nouvelleY);
                        }
                        else {
                                dansLesAirs = false;
                                velociteY = 0;

                                while (!terrain.collision(getX(), getY() + hauteur + 1) &&
                                        !terrain.collision(getX() + largeur, getY() + hauteur + 1)) {
                                        setY(getY() + 1);
                                }
                        }
                }
                else {
                        if (!terrain.collision(getX(), getY() + hauteur + 1) &&
                                !terrain.collision(getX() + largeur, getY() + hauteur + 1)) {
                                dansLesAirs = true;
                        }
                }
        }

        public void retirerVie() {
                if (this.vie.get() > 0) {
                        this.vie.set(this.vie.get() - 1);
                }
        }

        public void ajouterVie() {
                if (this.vie.get() < vieMax) {
                        this.vie.set(this.vie.get() + 1);
                }
        }

        public boolean estMort() {
                return this.vie.get() <= 0;
        }

        public abstract void seDeplacer();
}
