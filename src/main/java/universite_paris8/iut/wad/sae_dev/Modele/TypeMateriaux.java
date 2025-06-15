package universite_paris8.iut.wad.sae_dev.Modele;

public enum TypeMateriaux {
    COOKIE(Role.CONSOMMABLE),
    PIOCHE(Role.OUTIL),
    HACHE(Role.OUTIL),
    EPEE(Role.ARME),
    CUILLERE(Role.OUTIL),
    BROWNIE(Role.CONSOMMABLE),
    //    SUCRE_ORGE(Role.CONSOMMABLE),
//    BONBON_COCA(Role.CONSOMMABLE),
//    CARAMEL(Role.CONSOMMABLE),
//    BONBON_CLE(Role.MONNAIE),
//    CLE(Role.OUTIL),
//    PELLE(Role.OUTIL),
    PELOUSE(Role.CONSTRUCTION);

    private final Role role;

    TypeMateriaux(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
}
