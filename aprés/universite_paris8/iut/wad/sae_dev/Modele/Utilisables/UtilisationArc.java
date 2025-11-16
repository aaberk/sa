package universite_paris8.iut.wad.sae_dev.Modele.Utilisables;

import universite_paris8.iut.wad.sae_dev.Modele.Entites.Joueur;
import universite_paris8.iut.wad.sae_dev.Modele.Jeu;
import universite_paris8.iut.wad.sae_dev.Modele.Projectiles.FlecheArc;
import universite_paris8.iut.wad.sae_dev.Modele.Terraformer;
import universite_paris8.iut.wad.sae_dev.Modele.Terrain;

public class UtilisationArc implements UtilisationObjet{
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
