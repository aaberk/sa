package universite_paris8.iut.wad.sae_dev.Modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
/*
 * Cette classe représente un joueur et gère ses coordonnées X et Y à l'aide de propriétés JavaFX.
 */
public class Joueur {
        private IntegerProperty x = new SimpleIntegerProperty();
        private IntegerProperty y = new SimpleIntegerProperty();

        // Direction avec IntegerProperty : 0 = immobile, -1 = gauche, 1 = droite
        private IntegerProperty direction = new SimpleIntegerProperty(0);

        private int largeurJoueur = 46;
        private int hauteurJoueur = 56;

        private Terrain terrain;

        // Saut / Gravité
        private boolean dansLesAirs = false;
        private final double GRAVITE = 0.6;
        private final double FORCE_SAUT = -10.0;
        private double velociteY = 0;


        private int vitesse = 5;

        private int blocSelectionne = 2;

        private IntegerProperty vie = new SimpleIntegerProperty();
        private final int vieMax = 3;

        public Joueur(int x, int y, Terrain terrain) {
                this.x.set(x);
                this.y.set(y);
                this.terrain = terrain;
                this.vie.set(vieMax);
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

        public int getX() {
                return x.get();
        }

        public int getY() {
                return y.get();
        }

        public int getDirection() {
                return direction.get();
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

        public int getBlocSelectionne() {
                return blocSelectionne;
        }

        public void setBlocSelectionne(int bloc) {
                this.blocSelectionne = bloc;
                System.out.println("Bloc sélectionné : " + blocSelectionne); // utile pour debug
        }

        public void seDeplacer() {
                switch (getDirection()) {
                        case -1 -> deplacerGauche();
                        case 1 -> deplacerDroite();
                        // 0 = immobile
                }

                appliquerGravite();
        }

        public void deplacerGauche() {
                int nouveauX = getX() - vitesse;
                if (nouveauX >= 0 && !terrain.collision(nouveauX, getY() + hauteurJoueur)) {
                        setX(nouveauX);
                }
        }

        public void deplacerDroite() {
                int nouveauX = getX() + vitesse;
                if (nouveauX + largeurJoueur <= terrain.getLargeurPixels() &&
                        !terrain.collision(nouveauX + largeurJoueur, getY() + hauteurJoueur)) {
                        setX(nouveauX);
                }
        }

        public void saut() {
                if (!dansLesAirs) {
                        if (!terrain.collision(getX(), getY() - 10) &&
                                !terrain.collision(getX() + largeurJoueur, getY() - 10)) {
                                velociteY = FORCE_SAUT;
                                dansLesAirs = true;
                        }
                }
        }

        public void appliquerGravite() {
                if (dansLesAirs) {
                        velociteY += GRAVITE;

                        int nouvelleY = (int) (getY() + velociteY);

                        if (!terrain.collision(getX(), nouvelleY + hauteurJoueur) &&
                                !terrain.collision(getX() + largeurJoueur, nouvelleY + hauteurJoueur)) {
                                setY(nouvelleY);
                        }
                        else {
                                dansLesAirs = false;
                                velociteY = 0;

                                while (!terrain.collision(getX(), getY() + hauteurJoueur + 1) &&
                                        !terrain.collision(getX() + largeurJoueur, getY() + hauteurJoueur + 1)) {
                                        setY(getY() + 1);
                                }
                        }
                }
                else {
                        if (!terrain.collision(getX(), getY() + hauteurJoueur + 1) &&
                                !terrain.collision(getX() + largeurJoueur, getY() + hauteurJoueur + 1)) {
                                dansLesAirs = true;
                        }
                }
        }

        public int getVie () {
                return this.vie.get();
        }

        public void retirerVie () {
                if (this.vie.get() > 0){
                        this.vie.set(this.vie.get() - 1);
                }
        }

        public void ajouterVie () {
                if (this.vie.get() < vieMax){
                        this.vie.set(this.vie.get() + 1);
                }
        }

        public IntegerProperty vieProperty() {  return this.vie; }

        public boolean estMort () {
                return this.vie.get() <= 0;
        }
}