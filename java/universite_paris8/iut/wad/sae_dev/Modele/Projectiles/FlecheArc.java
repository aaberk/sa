package universite_paris8.iut.wad.sae_dev.Modele.Projectiles;

import universite_paris8.iut.wad.sae_dev.Modele.Terrain;

public class FlecheArc extends Projectile {

    public FlecheArc(int x, int y, int direction, Terrain terrain, int vitesse, int portee) {
        super(x, y, direction, terrain, vitesse, portee, TypeProprietaire.JOUEUR);
    }

    @Override
    public void seDeplacer() {
        if (!estActif()) return;

        int deplacement = getVitesse() * getDirection();
        setX(getX() + deplacement);
        ajouterDistance(Math.abs(deplacement));

        if (getTerrain().collision(getX(), getY()) || !peutToucher()) {
            desactiver();
        }

    }
}
