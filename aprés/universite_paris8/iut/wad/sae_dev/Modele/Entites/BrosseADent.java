package universite_paris8.iut.wad.sae_dev.Modele.Entites;

import universite_paris8.iut.wad.sae_dev.Modele.Terrain;
import java.util.*;

/**
 * BrosseADent implémente le comportement spécifique de cet ennemi
 */
public class BrosseADent extends Ennemi {

    private int distanceDetection = 300;
    private int distanceMinimale = 50;
    private int derniereAttaque = 0;

    public BrosseADent(int x, int y, Terrain terrain, Joueur joueur) {
        super(x, y, terrain, joueur, 46, 60);
        setDansLesAirs(true);
    }

    /**
     * Implémentation du comportement spécifique de la BrosseADent
     * Utilise BFS pour trouver le chemin vers le joueur
     */
    @Override
    protected void comportementEnnemi() {
        derniereAttaque++;

        // Calcul BFS moins fréquent pour ralentir l'ennemi
        if (getCompteur() >= 400) {
            int distanceX = calculerDistanceX();
            int distanceY = calculerDistanceY();

            // Ne se déplace que si le joueur est détecté mais pas trop proche
            if (estJoueurProche(distanceX, distanceY)) {
                int distanceReelle = (int) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

                // Ne se déplace que si plus loin que la distance minimale
                if (distanceReelle > distanceMinimale) {
                    deplacerVersJoueurBFS();
                }
            }
        }
    }

    // === MÉTHODES SPÉCIFIQUES À LA BROSSE ===

    private void deplacerVersJoueurBFS() {
        Terrain terrain = getTerrain();

        int debutX = terrain.getColonne(getX());
        int debutY = terrain.getLigne(getY());

        int xJoueur = terrain.getColonne(getJoueur().getX());
        int yJoueur = terrain.getLigne(getJoueur().getY());

        int ligne = terrain.ligne();
        int colonne = terrain.colonne();

        if (debutX < 0 || debutX >= colonne || debutY < 0 || debutY >= ligne ||
                xJoueur < 0 || xJoueur >= colonne || yJoueur < 0 || yJoueur >= ligne) {
            return;
        }

        boolean[][] visite = new boolean[ligne][colonne];
        Map<String, int[]> parentMap = new HashMap<>();
        Queue<int[]> queue = new LinkedList<>();

        queue.add(new int[]{debutY, debutX});
        visite[debutY][debutX] = true;

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        boolean trouver = false;

        while (!queue.isEmpty() && !trouver) {
            int[] current = queue.poll();
            int y = current[0];
            int x = current[1];

            if (x == xJoueur && y == yJoueur) {
                trouver = true;
                break;
            }

            for (int[] d : directions) {
                int ny = y + d[0];
                int nx = x + d[1];

                if (estPositionValide(nx, ny, ligne, colonne, visite, terrain)) {
                    visite[ny][nx] = true;
                    queue.add(new int[]{ny, nx});
                    parentMap.put(ny + "," + nx, new int[]{y, x});
                }
            }
        }

        if (!trouver) return;

        List<int[]> path = new ArrayList<>();
        int[] curr = new int[]{yJoueur, xJoueur};

        while (!(curr[0] == debutY && curr[1] == debutX)) {
            path.add(curr);
            String key = curr[0] + "," + curr[1];
            if (!parentMap.containsKey(key)) break;
            curr = parentMap.get(key);
        }

        Collections.reverse(path);

        if (!path.isEmpty()) {
            int nextY = path.get(0)[0];
            int nextX = path.get(0)[1];

            int nouveauX = nextX * terrain.getTailleTuile();
            int nouveauY = nextY * terrain.getTailleTuile();

            if (nouveauX > getX()) {
                setDirection(1);
            } else if (nouveauX < getX()) {
                setDirection(-1);
            }

            setX(nouveauX);
            setY(nouveauY);
        }
    }

    private boolean estPositionValide(int x, int y, int maxLigne, int maxColonne,
                                      boolean[][] visite, Terrain terrain) {
        if (y < 0 || y >= maxLigne || x < 0 || x >= maxColonne) return false;
        if (visite[y][x]) return false;

        int pixelX = x * terrain.getTailleTuile();
        int pixelY = y * terrain.getTailleTuile();

        return !terrain.collision(pixelX, pixelY);
    }

    private int calculerDistanceX() {
        return getJoueur().getX() - getX();
    }

    private int calculerDistanceY() {
        return getJoueur().getY() - getY();
    }

    private boolean estJoueurProche(int distanceX, int distanceY) {
        int distanceAbsX = Math.abs(distanceX);
        int distanceAbsY = Math.abs(distanceY);

        return distanceAbsX <= distanceDetection && distanceAbsY <= distanceDetection;
    }

    @Override
    public boolean toucheJoueur() {
        int distance = 35;
        int distanceX = Math.abs(getX() - getJoueur().getX());
        int distanceY = Math.abs(getY() - getJoueur().getY());

        boolean touche = distanceX < distance && distanceY < distance;

        if (touche && derniereAttaque >= 400) {
            derniereAttaque = 0;
            return true;
        }

        return false;
    }
}