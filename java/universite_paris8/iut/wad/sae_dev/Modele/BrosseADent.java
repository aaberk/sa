package universite_paris8.iut.wad.sae_dev.Modele;

public class BrosseADent extends Ennemi {

    private int compteur = 0;
    private int distanceDetection = 200;
    private int compteurSaut = 0; // Pour éviter les sauts répétés

    public BrosseADent(int x, int y, Terrain terrain, Joueur joueur) {
        super(x, y, terrain, joueur, 46, 60);
        setDansLesAirs(true);
    }

    @Override
    public void seDeplacer() {
        compteur++;

        // Décrémenter le compteur de saut
        if (compteurSaut > 0) {
            compteurSaut--;
        }

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
        int directionVoulue = 0;

        // Déterminer la direction souhaitée
        if (distanceX > 0) {
            nouveauX += getVitesse();
            directionVoulue = 1;
        } else if (distanceX < 0) {
            nouveauX -= getVitesse();
            directionVoulue = -1;
        }

        // Vérifier si le déplacement horizontal est possible
        if (peutSeDeplacerHorizontalement(nouveauX)) {
            setX(nouveauX);
            setDirection(directionVoulue);
        } else {
            // Si bloqué et pas en l'air, essayer de sauter
            if (!isDansLesAirs() && compteurSaut == 0) {
                sauterObstacle();
                compteurSaut = 30; // Éviter les sauts répétés pendant 30 frames
            }
        }
    }

    /**
     * Vérifie si le déplacement horizontal est possible sur toute la hauteur du personnage
     */
    @Override
    public boolean peutSeDeplacerHorizontalement(int nouveauX) {
        // Vérifier les limites du terrain
        if (nouveauX < 0 || nouveauX + getLargeur() > getTerrain().getLargeurPixels()) {
            return false;
        }

        // Vérifier les collisions sur toute la hauteur du personnage
        int y = getY();
        int hauteur = getHauteur();

        // Vérifier plusieurs points sur la hauteur (tête, milieu, pieds)
        for (int offsetY = 0; offsetY <= hauteur; offsetY += 10) {
            int yTest = y + offsetY;
            if (yTest > y + hauteur) {
                yTest = y + hauteur;
            }

            // Vérifier collision côté gauche et droit
            if (getTerrain().collision(nouveauX, yTest) ||
                    getTerrain().collision(nouveauX + getLargeur(), yTest)) {
                return false;
            }
        }

        return true;
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
        // Vérifier qu'il y a de l'espace au-dessus pour sauter
        int espaceSaut = 20; // Hauteur nécessaire pour le saut

        // Vérifier l'espace au-dessus
        boolean peutSauter = true;
        for (int i = 1; i <= espaceSaut; i++) {
            if (getTerrain().collision(getX(), getY() - i) ||
                    getTerrain().collision(getX() + getLargeur(), getY() - i)) {
                peutSauter = false;
                break;
            }
        }

        if (peutSauter) {
            setVelociteY(-12.0);
            setDansLesAirs(true);
            System.out.println("BrosseADent saute pour éviter l'obstacle");
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