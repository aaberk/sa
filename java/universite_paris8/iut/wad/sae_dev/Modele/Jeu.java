package universite_paris8.iut.wad.sae_dev.Modele;

import universite_paris8.iut.wad.sae_dev.Modele.Entites.*;
import universite_paris8.iut.wad.sae_dev.Modele.Projectiles.*;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
    private Terrain terrain;
    private Joueur joueur;
    private List<Ennemi> ennemis;
    private static final int SEUIL_COLLISION = 40;

    public Jeu(Terrain terrain, Joueur joueur) {
        this.terrain = terrain;
        this.joueur = joueur;
        this.ennemis = new ArrayList<>();
    }

    public void ajouterEnnemi(Ennemi ennemi) {
        ennemis.add(ennemi);
        terrain.ajouterEnnemi(ennemi);
    }

    public List<Projectile> gererProjectiles() {
        List<Projectile> nouveaux = new ArrayList<>();

        for (Ennemi ennemi : ennemis) {
            if (ennemi instanceof DentifriceVolant) {
                DentifriceVolant dentifrice = (DentifriceVolant) ennemi;
                if (dentifrice.peutTirer()) {
                    ProjectileDentifrice p = ProjectileDentifrice.creerProjectileVertical(
                            dentifrice.getPositionTirX(),
                            dentifrice.getPositionTirY(),
                            terrain
                    );
                    terrain.ajouterProjectile(p);
                    nouveaux.add(p);
                    dentifrice.reinitialiserCompteurTir();
                }
            }
        }

        terrain.miseAJourProjectiles();
        gererCollisionsProjectilesEnnemis();
        return nouveaux;
    }


    private void creerNouveauxProjectiles() {
        for (Ennemi ennemi : ennemis) {
            if (ennemi instanceof DentifriceVolant) {
                DentifriceVolant dentifrice = (DentifriceVolant) ennemi;
                if (dentifrice.peutTirer()) {
                    creerProjectileDentifrice(dentifrice);
                    dentifrice.reinitialiserCompteurTir();
                }
            }
        }
    }

    private void creerProjectileDentifrice(DentifriceVolant dentifriceVolant) {
        int projectileX = dentifriceVolant.getPositionTirX();
        int projectileY = dentifriceVolant.getPositionTirY();
        ProjectileDentifrice projectile = ProjectileDentifrice.creerProjectileVertical(
                projectileX, projectileY, terrain
        );
        terrain.ajouterProjectile(projectile);
    }

    private void gererCollisionsProjectilesEnnemis() {
        List<Projectile> projectilesASupprimer = new ArrayList<>();
        List<Ennemi> ennemisARetirer = new ArrayList<>();

        for (Projectile projectile : terrain.getProjectiles()) {
            if (projectile.estProjectileDuJoueur() && projectile.estActif()) {
                for (Ennemi ennemi : ennemis) {
                    if (!ennemi.estMort() && projectileToucheEnnemi(projectile, ennemi)) {
                        ennemi.subirDegats(1);
                        projectile.desactiver();
                        projectilesASupprimer.add(projectile);

                        if (ennemi.estMort()) {
                            System.out.println(ennemi.getClass().getSimpleName() + " éliminé !");
                            ennemisARetirer.add(ennemi);
                        }
                        break;
                    }
                }
            }
        }

        for (Projectile proj : projectilesASupprimer) {
            terrain.retirerProjectile(proj);
        }

        for (Ennemi ennemi : ennemisARetirer) {
            ennemis.remove(ennemi);
            terrain.retirerEnnemi(ennemi);
        }
    }

    private boolean projectileToucheEnnemi(Projectile projectile, Ennemi ennemi) {
        int distanceX = Math.abs(projectile.getX() - ennemi.getX());
        int distanceY = Math.abs(projectile.getY() - ennemi.getY());
        return distanceX < SEUIL_COLLISION && distanceY < SEUIL_COLLISION;
    }

    public void gererCollisionsEnnemisJoueur() {
        for (Ennemi ennemi : ennemis) {
            if (!ennemi.estMort() && ennemi.toucheJoueur()) {
                joueur.retirerVie();
                return;
            }
        }

        for (Projectile projectile : terrain.getProjectiles()) {
            if (projectile.estProjectileEnnemi() && projectile.toucheJoueur(joueur)) {
                joueur.retirerVie();
                System.out.println("Projectile ennemi touche le joueur!");
                break;
            }
        }
    }

    public FlecheArc tirerFlecheDuJoueur(int direction) {
        int flecheX = joueur.getX() + 35;
        int flecheY = joueur.getY() + 10;
        FlecheArc fleche = new FlecheArc(flecheX, flecheY, direction, terrain, 8, 300);
        terrain.ajouterProjectile(fleche);
        return fleche;
    }

    public void deplacerEnnemis() {
        for (Ennemi ennemi : ennemis) {
            if (!ennemi.estMort()) {
                ennemi.seDeplacer();
            }
        }
    }
}