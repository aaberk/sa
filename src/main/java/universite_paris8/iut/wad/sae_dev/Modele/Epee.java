package universite_paris8.iut.wad.sae_dev.Modele;

public class Epee extends ObjetUtilisable {

    public Epee (){
        super ("Epee", Role.ARME);
    }

    @Override
    public void utiliser() {
        System.out.println(getNom()+"est utilisé contre ...");
    }
}
