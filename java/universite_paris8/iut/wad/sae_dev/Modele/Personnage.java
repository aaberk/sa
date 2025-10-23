package universite_paris8.iut.wad.sae_dev.Modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Personnage {

        // Propriétés observables pour la position et l'état
        private final IntegerProperty x = new SimpleIntegerProperty();
        private final IntegerProperty y = new SimpleIntegerProperty();
        private final IntegerProperty direction = new SimpleIntegerProperty(0);
        private final IntegerProperty vie = new SimpleIntegerProperty();

        // Caractéristiques physiques
        private final int largeur;
        private final int hauteur;
        private final int vitesse;
        private final int vieMax;

        // Référence au terrain
        private final Terrain terrain;

        // Gestion de la physique
        private boolean dansLesAirs = false;
        private double velociteY = 0;
        private static final double GRAVITE = 0.6;

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

        // === PROPRIÉTÉS OBSERVABLES ===
        public IntegerProperty xProperty() { return x; }
        public IntegerProperty yProperty() { return y; }
        public IntegerProperty directionProperty() { return direction; }
        public IntegerProperty vieProperty() { return vie; }

        // === GETTERS ===
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
        public double getGravite() { return GRAVITE; }

        // === SETTERS ===
        public void setX(int x) { this.x.set(x); }
        public void setY(int y) { this.y.set(y); }
        public void setDirection(int direction) { this.direction.set(direction); }
        public void setDansLesAirs(boolean dansLesAirs) { this.dansLesAirs = dansLesAirs; }
        public void setVelociteY(double velociteY) { this.velociteY = velociteY; }

        // === MÉTHODES DE DÉPLACEMENT ===

        /**
         * Déplace le personnage vers la gauche en vérifiant les collisions
         */
        public void deplacerGauche() {
                int nouveauX = getX() - vitesse;
                if (peutSeDeplacerEn(nouveauX, getY())) {
                        setX(nouveauX);
                        setDirection(-1);
                }
        }

        /**
         * Déplace le personnage vers la droite en vérifiant les collisions
         */
        public void deplacerDroite() {
                int nouveauX = getX() + vitesse;
                if (peutSeDeplacerEn(nouveauX, getY())) {
                        setX(nouveauX);
                        setDirection(1);
                }
        }

        /**
         * Vérifie si le personnage peut se déplacer à une position donnée
         */
        private boolean peutSeDeplacerEn(int nouveauX, int nouveauY) {
                return nouveauX >= 0
                        && nouveauX + largeur <= terrain.getLargeurPixels()
                        && !terrain.collision(nouveauX, nouveauY + hauteur)
                        && !terrain.collision(nouveauX + largeur, nouveauY + hauteur);
        }

        /**
         * Applique la gravité au personnage
         */
        public void appliquerGravite() {
                if (dansLesAirs) {
                        gererChute();
                } else {
                        verifierSiTombe();
                }
        }

        /**
         * Gère la chute du personnage quand il est dans les airs
         */
        private void gererChute() {
                velociteY += GRAVITE;
                int nouvelleY = (int) (getY() + velociteY);

                if (peutTomberEn(nouvelleY)) {
                        setY(nouvelleY);
                } else {
                        atterrir();
                }
        }

        /**
         * Vérifie si le personnage peut tomber à une position Y donnée
         */
        private boolean peutTomberEn(int nouvelleY) {
                return !terrain.collision(getX(), nouvelleY + hauteur)
                        && !terrain.collision(getX() + largeur, nouvelleY + hauteur);
        }

        /**
         * Fait atterrir le personnage sur le sol
         */
        private void atterrir() {
                dansLesAirs = false;
                velociteY = 0;

                // Ajuste la position pour être exactement sur le sol
                while (!terrain.collision(getX(), getY() + hauteur + 1)
                        && !terrain.collision(getX() + largeur, getY() + hauteur + 1)) {
                        setY(getY() + 1);
                }
        }

        /**
         * Vérifie si le personnage doit commencer à tomber
         */
        private void verifierSiTombe() {
                if (!terrain.collision(getX(), getY() + hauteur + 1)
                        && !terrain.collision(getX() + largeur, getY() + hauteur + 1)) {
                        dansLesAirs = true;
                }
        }

        // === GESTION DE LA VIE ===

        /**
         * Retire une unité de vie au personnage
         */
        public void retirerVie() {
                if (this.vie.get() > 0) {
                        this.vie.set(this.vie.get() - 1);
                }
        }

        /**
         * Ajoute une unité de vie au personnage (sans dépasser le maximum)
         */
        public void ajouterVie() {
                if (this.vie.get() < vieMax) {
                        this.vie.set(this.vie.get() + 1);
                }
        }

        /**
         * Vérifie si le personnage est mort
         */
        public boolean estMort() {
                return this.vie.get() <= 0;
        }

        /**
         * Remet le personnage à sa vie maximale
         */
        public void guerirCompletement() {
                this.vie.set(vieMax);
        }

        // === MÉTHODES ABSTRAITES ===

        /**
         * Méthode abstraite pour définir le comportement de déplacement spécifique à chaque personnage
         */
        public abstract void seDeplacer();

}