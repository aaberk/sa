package universite_paris8.iut.wad.sae_dev.Modele;

public class Hache extends ObjetUtilisable {

    public Hache (){
        super("Hache",Role.OUTIL);
    }

    @Override
    public void utiliser() {
        System.out.println(getNom()+"est utilisé");
    }
}
