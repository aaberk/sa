package universite_paris8.iut.wad.sae_dev.Modele;

public abstract class Pnj extends Personnage {

    private Joueur joueur;


    public Pnj(int x, int y, Terrain terrain,Joueur joueur, int largeur, int hauteur) {
        super(x, y, largeur, hauteur, 2, 1, terrain);
        this.joueur = joueur;
        setDirection(1);
    }


    @Override
    public void seDeplacer() {
        appliquerGravite();
    }


    public boolean joueurEstProche() {
        if (joueur == null) return false;

        int distance = Math.abs(this.getX() - joueur.getX());
        return distance < 100;
    }
}