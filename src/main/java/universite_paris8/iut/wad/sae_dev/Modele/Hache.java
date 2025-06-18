package universite_paris8.iut.wad.sae_dev.Modele;

public class Hache extends ObjetUtilisable {
    public Hache() {
        super("Hache", Role.OUTIL, TypeMateriaux.HACHE);
    }

    @Override
    public void utiliser(int x, int y, Terrain terrain, Terraformer terraformer) {
        System.out.println(getNom() + " utilisée (effet spécifique à implémenter)");
        // terraformer.casserUnBloc(x, y); ou autre logique plus tard
    }
}
