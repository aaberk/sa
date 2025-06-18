// Cette classe gère les entrées clavier pour déplacer le joueur et sélectionner des blocs.

package universite_paris8.iut.wad.sae_dev.Controleur;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import universite_paris8.iut.wad.sae_dev.Modele.Inventaire;
import universite_paris8.iut.wad.sae_dev.Modele.Joueur;
import universite_paris8.iut.wad.sae_dev.Modele.Terraformer;
import universite_paris8.iut.wad.sae_dev.Vue.JoueurVue;

public class Clavier implements EventHandler<KeyEvent> {

    private final Joueur joueur;
    private final JoueurVue joueurVue;
    private int derniereDirection = 1;
    private Inventaire inventaire;
    private Terraformer terraformer;


    public Clavier(Joueur joueur, JoueurVue joueurVue,Inventaire inventaire, Terraformer terraformer) {
        this.joueur = joueur;
        this.joueurVue = joueurVue;
        this.inventaire = inventaire;
        this.terraformer = terraformer;
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (event.getCode()) {
                case Q,LEFT -> {
                    joueur.setDirection(-1); // -1 pour gauche
                    derniereDirection = -1;
                }
                case D,RIGHT -> {
                    joueur.setDirection(1); // 1 pour droite
                    derniereDirection = 1;
                }
                case Z,UP -> {
                    System.out.println("Touche saut pressée");
                    joueur.saut();
                }
//                case DIGIT1, NUMPAD1 -> joueur.setBlocSelectionne(1);
//                case DIGIT2, NUMPAD2 -> joueur.setBlocSelectionne(2);
//                case DIGIT3, NUMPAD3 -> joueur.setBlocSelectionne(3);
//                case DIGIT4, NUMPAD4 -> joueur.setBlocSelectionne(4);
//                case DIGIT5, NUMPAD5 -> joueur.setBlocSelectionne(5);
//                case DIGIT6, NUMPAD6 -> joueur.setBlocSelectionne(6);

                case N -> joueur.retirerVie();
                case B -> joueur.ajouterVie();

                case F -> inventaire.utiliserObjetActuel(joueur, terraformer);


            }
        }
        else if (event.getEventType() == KeyEvent.KEY_RELEASED &&
                (event.getCode() == KeyCode.Q || event.getCode() == KeyCode.D || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT)) {
            joueur.setDirection(0); // 0 pour immobile

            joueurVue.setDirectionImmobile(derniereDirection == -1);
        }
    }
}