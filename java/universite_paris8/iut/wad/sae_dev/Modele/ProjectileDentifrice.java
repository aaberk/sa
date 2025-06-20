package universite_paris8.iut.wad.sae_dev.Modele;

public class ProjectileDentifrice extends Projectile {

    private final int largeur = 16;
    private final int hauteur = 16;
    private final double GRAVITE_PROJECTILE = 0.3;
    private double velociteY = 0;

    public ProjectileDentifrice(int x, int y, Terrain terrain) {
        super(x, y, 0, terrain, 4, 400, TypeProprietaire.ENNEMI);
    }

    public static ProjectileDentifrice creerProjectileVertical(int x, int y, Terrain terrain) {
        return new ProjectileDentifrice(x, y, terrain);
    }

    @Override
    public void seDeplacer() {
        if (!estActif()) return;

        velociteY += GRAVITE_PROJECTILE;
        int nouvelleY = (int) (getY() + velociteY);

        if (getTerrain().collision(getX(), nouvelleY + hauteur)
                || getTerrain().collision(getX() + largeur, nouvelleY + hauteur)
                || nouvelleY > getTerrain().getHauteurPixels()) {
            desactiver();
            return;
        }

        ajouterDistance((int) Math.abs(velociteY));
        if (!peutToucher()) {
            desactiver();
            return;
        }

        setY(nouvelleY);
    }
}
