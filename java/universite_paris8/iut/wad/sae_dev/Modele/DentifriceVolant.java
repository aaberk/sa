package universite_paris8.iut.wad.sae_dev.Modele;

public class DentifriceVolant extends Ennemi {

    private Joueur joueur;
    private int compteur = 0;
    private int vitesseTir = 0;
    private int distanceDetection = 200;
    private int distanceTir = 150;

    public DentifriceVolant(int x, int y, Terrain terrain, Joueur joueur) {
        super(x, y, terrain, joueur, 46,60);
        this.joueur = joueur;
        setDansLesAirs(true);
    }

    @Override
    public void seDeplacer() {
        compteur++;
        vitesseTir++;

        if (compteur >= 8) {
            compteur = 0;

            int distanceX = calculerDistanceX();
            int distanceY = calculerDistanceY();

            deplacerVersJoueur(distanceX, distanceY);

            if (estJoueurProche(distanceX, distanceY) && doitTirer(distanceX, distanceY)) {
                System.out.println("Drone brosse tire un projectile vers le bas!");
            }
        }
    }

    public int calculerDistanceX() {
        return joueur.getX() - getX();
    }

    public int calculerDistanceY() {
        return joueur.getY() - getY();
    }

    public boolean estJoueurProche(int distanceX, int distanceY) {
        int distanceAbsX = Math.abs(distanceX);
        int distanceAbsY = Math.abs(distanceY);

        return distanceAbsX <= distanceDetection && distanceAbsY <= distanceDetection;
    }

    public boolean doitTirer(int distanceX, int distanceY) {
        int distanceAbsX = Math.abs(distanceX);

        return distanceAbsX <= distanceTir && vitesseTir >= 60;
    }

    /**
     * Déplacement horizontal uniquement pour suivre le joueur comme un drone
     */
    public void deplacerVersJoueur(int distanceX, int distanceY) {
        int nouveauX = getX();

        if (distanceX > 10) { // Zone morte pour éviter les oscillations
            nouveauX += getVitesse();
            setDirection(1);
        } else if (distanceX < -10) {
            nouveauX -= getVitesse();
            setDirection(-1);
        } else {
            setDirection(0); // Reste stationnaire si proche du joueur
        }

        // Vérifie les limites horizontales
        if (peutSeDeplacerHorizontalement(nouveauX)) {
            setX(nouveauX);
        }
    }

    public boolean peutSeDeplacerHorizontalement(int nouveauX) {
        boolean dansLesLimites = nouveauX >= 0 && nouveauX + getLargeur() <= getTerrain().getLargeurPixels();

        // Pour un drone, on vérifie uniquement les collisions avec les murs latéraux
        // et non pas les blocs en dessous
        boolean pasDeCollisionLaterale = true;
        if (nouveauX < getX()) {
            // Se déplace vers la gauche
            pasDeCollisionLaterale = !getTerrain().collision(nouveauX, getY() + getHauteur() / 2);
        }
        else if (nouveauX > getX()) {
            pasDeCollisionLaterale = !getTerrain().collision(nouveauX + getLargeur(), getY() + getHauteur() / 2);
        }

        return dansLesLimites && pasDeCollisionLaterale;
    }

    public boolean peutTirer() {
        return vitesseTir >= 100;
    }

    public void reinitialiserCompteurTir() {
        vitesseTir = 0;
    }

    /**
     * Direction de tir vers le bas (projectiles tombent)
     */
    public int getDirectionTir() {
        return 0;
    }

    /**
     * Position de tir depuis le bas du drone
     */
    public int getPositionTirX() {
        return getX() + getLargeur() / 2;
    }

    public int getPositionTirY() {
        return getY() + getHauteur(); // Tire depuis le bas du drone
    }
}