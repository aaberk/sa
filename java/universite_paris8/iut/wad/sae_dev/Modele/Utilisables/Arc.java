package universite_paris8.iut.wad.sae_dev.Modele.Utilisables;

import universite_paris8.iut.wad.sae_dev.Modele.*;
import universite_paris8.iut.wad.sae_dev.Modele.Entites.Joueur;
import universite_paris8.iut.wad.sae_dev.Modele.Projectiles.FlecheArc;

public class Arc extends ObjetUtilisable {
    public Arc() {
        super("Arc", Role.ARME, TypeMateriaux.ARC);
    }

    @Override
    public void utiliser(int x, int y, Jeu jeu, Terraformer terraformer) {
        Joueur joueur = jeu.getJoueur();
        Terrain terrain = jeu.getTerrain();

        int flecheX = joueur.getX() + 35;
        int flecheY = joueur.getY() + 10;
        FlecheArc fleche = new FlecheArc(
                flecheX,
                flecheY,
                joueur.getDirection(),
                terrain,
                10,
                300
        );

        jeu.ajouterProjectile(fleche);
    }
}
