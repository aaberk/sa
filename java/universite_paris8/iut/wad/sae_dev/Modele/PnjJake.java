package universite_paris8.iut.wad.sae_dev.Modele;

public class PnjJake extends Pnj {

    private Joueur joueur;
    public PnjJake(int x, int y, Terrain terrain, Joueur joueur) {
        super(x, y, terrain, joueur,46,56);
        this.joueur = joueur;
    }
}
