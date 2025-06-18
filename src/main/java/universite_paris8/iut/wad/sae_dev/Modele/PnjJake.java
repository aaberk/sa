package universite_paris8.iut.wad.sae_dev.Modele;

public class PnjJake extends Pnj {

    private Joueur joueur;

    public PnjJake(int x, int y, Terrain terrain, Joueur joueur) {
        super(x, y, terrain);
        this.joueur = joueur;
    }

    @Override
    public void seDeplacer() {
        appliquerGravite();
    }

    // Méthode simple pour savoir si le joueur est proche
    public boolean joueurEstProche() {
        if (joueur == null) return false;

        int distance = Math.abs(this.getX() - joueur.getX());
        return distance < 100; // Si distance moins de 100 pixels
    }
}