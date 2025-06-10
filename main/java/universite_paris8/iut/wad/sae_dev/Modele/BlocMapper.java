package universite_paris8.iut.wad.sae_dev.Modele;

public class BlocMapper {

    public static TypeMateriaux typeBlocVersMateriaux(int typeBloc) {
        switch (typeBloc) {
            case 2: case 12: return TypeMateriaux.PELOUSE;
            case 3: case 13: return TypeMateriaux.BROWNIE;
            case 4: case 14: return TypeMateriaux.COOKIE;
            default: return null;
        }
    }



    public static int materiauxVersTypeBloc(TypeMateriaux materiaux) {
        switch (materiaux) {
            case PELOUSE: return 2;
            case COOKIE: return 4;
            case BROWNIE: return 3;
            default: return 1;
        }
    }

    public static boolean estBlocCollectable(int typeBloc) {
        return typeBloc == 2 || typeBloc == 3 || typeBloc == 4 ||
                typeBloc == 12 || typeBloc == 13 || typeBloc == 14;
    }}