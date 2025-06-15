package universite_paris8.iut.wad.sae_dev.Modele;

public class Pioche extends ObjetUtilisable {

    public Pioche (){
        super("Pioche",Role.OUTIL);
    }

    @Override
    public void utiliser() {
        System.out.println(getNom()+"est utilisé");
    }
}
