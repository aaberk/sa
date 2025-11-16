package universite_paris8.iut.wad.sae_dev.Modele.Entites;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import universite_paris8.iut.wad.sae_dev.Modele.Terrain;

/**
 * Classe abstraite utilisant le pattern Template Method
 * La méthode seDeplacer() est la méthode template
 */
public abstract class Personnage {

        private final Terrain terrain;

        // Propriétés observables
        private final IntegerProperty x = new SimpleIntegerProperty();
        private final IntegerProperty y = new SimpleIntegerProperty();
        private final int largeur;
        private final int hauteur;
        private final IntegerProperty direction = new SimpleIntegerProperty(0);
        private final int vitesse;

        // Physique
        private boolean dansLesAirs = false;
        private double velociteY = 0;
        private static final double GRAVITE = 0.6;

        // Vie
        private final int vieMax;
        private final IntegerProperty vie = new SimpleIntegerProperty();

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

        // === MÉTHODE TEMPLATE (concrète) ===
        /**
         * Méthode template qui définit le squelette du déplacement
         * Cette méthode ne doit PAS être surchargée par les sous-classes
         */
        public final void seDeplacer() {
                effectuerDeplacementSpecifique();

                appliquerGravite();
        }

        // === MÉTHODES ABSTRAITES (à implémenter) ===
        /**
         * Méthode abstraite : chaque type de personnage définit son déplacement
         */
        protected abstract void effectuerDeplacementSpecifique();

        // === MÉTHODES DE DÉPLACEMENT COMMUNES ===

        public void deplacerGauche() {
                int nouveauX = getX() - vitesse;
                if (peutSeDeplacerEn(nouveauX, getY())) {
                        setX(nouveauX);
                        setDirection(-1);
                }
        }

        public void deplacerDroite() {
                int nouveauX = getX() + vitesse;
                if (peutSeDeplacerEn(nouveauX, getY())) {
                        setX(nouveauX);
                        setDirection(1);
                }
        }

        private boolean peutSeDeplacerEn(int nouveauX, int nouveauY) {
                return nouveauX >= 0
                        && nouveauX + largeur <= terrain.largeurEnPixels()
                        && !terrain.collision(nouveauX, nouveauY + hauteur)
                        && !terrain.collision(nouveauX + largeur, nouveauY + hauteur);
        }

        // === GRAVITÉ (comportement commun) ===

        public void appliquerGravite() {
                if (dansLesAirs) {
                        gererChute();
                } else {
                        verifierSiTombe();
                }
        }

        private void gererChute() {
                velociteY += GRAVITE;
                int nouvelleY = (int) (getY() + velociteY);

                if (peutTomberEn(nouvelleY)) {
                        setY(nouvelleY);
                } else {
                        atterrir();
                }
        }

        private boolean peutTomberEn(int nouvelleY) {
                return !terrain.collision(getX(), nouvelleY + hauteur)
                        && !terrain.collision(getX() + largeur, nouvelleY + hauteur);
        }

        private void atterrir() {
                dansLesAirs = false;
                velociteY = 0;

                while (!terrain.collision(getX(), getY() + hauteur + 1)
                        && !terrain.collision(getX() + largeur, getY() + hauteur + 1)) {
                        setY(getY() + 1);
                }
        }

        private void verifierSiTombe() {
                if (!terrain.collision(getX(), getY() + hauteur + 1)
                        && !terrain.collision(getX() + largeur, getY() + hauteur + 1)) {
                        dansLesAirs = true;
                }
        }

        // === GESTION DE LA VIE ===

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

        // === GETTERS / SETTERS / PROPERTIES ===

        public IntegerProperty xProperty() { return x; }
        public IntegerProperty yProperty() { return y; }
        public IntegerProperty directionProperty() { return direction; }
        public IntegerProperty vieProperty() { return vie; }

        public int getX() { return x.get(); }
        public int getY() { return y.get(); }
        public int getDirection() { return direction.get(); }
        public int getVie() { return vie.get(); }
        public int getLargeur() { return largeur; }
        public int getHauteur() { return hauteur; }
        public int getVitesse() { return vitesse; }
        public int getVieMax() { return vieMax; }
        public Terrain getTerrain() { return terrain; }
        public boolean isDansLesAirs() { return dansLesAirs; }
        public double getVelociteY() { return velociteY; }

        public void setX(int x) { this.x.set(x); }
        public void setY(int y) { this.y.set(y); }
        public void setDirection(int direction) { this.direction.set(direction); }
        public void setDansLesAirs(boolean dansLesAirs) { this.dansLesAirs = dansLesAirs; }
        public void setVelociteY(double velociteY) { this.velociteY = velociteY; }
}