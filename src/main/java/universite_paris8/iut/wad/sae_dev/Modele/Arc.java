package universite_paris8.iut.wad.sae_dev.Modele;

public class Arc extends ObjetUtilisable {
    public Arc() {
        super("Arc", Role.OUTIL, TypeMateriaux.ARC);
    }

    @Override
    public void utiliser(int x, int y, Terrain terrain, Terraformer terraformer) {
        System.out.println("l'arc est utilisé");

        for (Ennemi ennemi : terrain.getListeEnnemis()) {



        }
    }
}
