package universite_paris8.iut.wad.sae_dev.Modele;

public class BrosseADent extends Ennemi {

    private int compteur = 0;
    private int distanceDetection = 200;

    public BrosseADent(int x, int y, Terrain terrain, Joueur joueur) {
        super(x, y, terrain, joueur,32,32);
        setDansLesAirs(true);
    }

    @Override
    public void seDeplacer() {
        compteur++;

        if (compteur >= 8) {
            compteur = 0;

            int distanceX = calculerDistanceX();
            int distanceY = calculerDistanceY();

            if (estJoueurProche(distanceX, distanceY)) {
                deplacerVersJoueur(distanceX, distanceY);
            }
        }

        appliquerGravite();
    }

    @Override
    public void deplacerVersJoueur(int distanceX, int distanceY) {
        int nouveauX = getX();

        if (distanceX > 0) {
            nouveauX += getVitesse();
            setDirection(1);
        } else if (distanceX < 0) {
            nouveauX -= getVitesse();
            setDirection(-1);
        }

        if (peutSeDeplacerHorizontalement(nouveauX)) {
            setX(nouveauX);
        } else {
            if (!isDansLesAirs()) {
                sauterObstacle();
            }
        }
    }

    public int calculerDistanceX() {
        return getJoueur().getX() - getX();
    }

    public int calculerDistanceY() {
        return getJoueur().getY() - getY();
    }

    public boolean estJoueurProche(int distanceX, int distanceY) {
        int distanceAbsX = Math.abs(distanceX);
        int distanceAbsY = Math.abs(distanceY);

        return distanceAbsX <= distanceDetection && distanceAbsY <= distanceDetection;
    }

    public void sauterObstacle() {
        if (!getTerrain().collision(getX(), getY() - 10) &&
                !getTerrain().collision(getX() + getLargeur(), getY() - 10)) {
            setVelociteY(-10.0);
            setDansLesAirs(true);
        }
    }

    @Override
    public boolean toucheJoueur() {
        int distance = 35;
        int distanceX = Math.abs(getX() - getJoueur().getX());
        int distanceY = Math.abs(getY() - getJoueur().getY());

        return distanceX < distance && distanceY < distance;
    }
}