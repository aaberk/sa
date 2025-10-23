package universite_paris8.iut.wad.sae_dev.Modele;

public class Pioche extends ObjetUtilisable {
    public Pioche() {
        super("Pioche", Role.OUTIL, TypeMateriaux.PIOCHE);
    }

    @Override
    public void utiliser(int x, int y, Terrain terrain, Terraformer terraformer) {
        System.out.println(getNom() + " utilisée pour casser un bloc");
        terraformer.casserUnBloc(x, y);
    }
}
