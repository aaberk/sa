package universite_paris8.iut.wad.sae_dev.Modele.Utilisables;

import universite_paris8.iut.wad.sae_dev.Modele.*;
import universite_paris8.iut.wad.sae_dev.Modele.Entites.Joueur;
import universite_paris8.iut.wad.sae_dev.Modele.Projectiles.FlecheArc;

public class Arc extends ObjetUtilisable {
    public Arc() {
        super("Arc", Role.ARME, TypeMateriaux.ARC);
    }


    @Override
    public void utiliser(int x, int y, Terrain terrain, Terraformer terraformer) {
        System.out.println(getNom() + " utilisé pour tirer une flèche");
        System.out.println("Arc utilisé en (" + x + ", " + y + ")");
        System.out.println("Liste ennemis : " + terrain.getListeEnnemis().size());

        Joueur joueur = terrain.getJoueur();


        FlecheArc fleche = new FlecheArc(joueur.getX(), joueur.getY(), joueur.getDirection(), terrain, 10,300);
        terrain.ajouterProjectile(fleche);

    }
}
