package wargame;

import java.awt.Polygon;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Carte extends JPanel implements IConfig, ICarte{
    private static final long serialVersionUID = 1L;
    public Hexagone[][] aMap;
    private transient Heros[] armeeHeros;
    private transient Monstre[] armeeMonstre;
    private transient int heros_restant;
    private transient int monstre_restant;
    private transient Polygon aPolybig;


    /**
    * @param nbCasesLargeur Nombre de case en largeur
    * @param nbCasesHauteur Nombre de case en hauteur
    * @param hexaLargeur Taille de l'hexagone en largeur
    * @param hexaHauteur Taille de l'hexagone en hauteur
    * @return La carte du jeu
    */
    public Carte(int nbCasesLargeur, int nbCasesHauteur, int hexaLargeur, int hexaHauteur) {
        this.aMap = new Hexagone[nbCasesLargeur][nbCasesHauteur];
        armeeHeros = new Heros[NB_HEROS];
        armeeMonstre = new Monstre[NB_MONSTRES];
        heros_restant = NB_HEROS;
        monstre_restant = NB_MONSTRES;

        int xCenter = 5;
        int yCenter = 50;
        int xOffSet = hexaLargeur / 2;
        Polygon vPoly;
        for (int x = 0; x < nbCasesLargeur; x++) {
            for (int y = 0; y < nbCasesHauteur; y++) {
                if (y % 2 == 0) {
                    int[] xPoly = { xCenter, xCenter, (int) (hexaLargeur * 0.5) + xCenter, hexaLargeur + xCenter,
                            hexaLargeur + xCenter, (int) (hexaLargeur * 0.5) + xCenter };
                    int[] yPoly = { (int) (hexaHauteur * 0.75) + yCenter, (int) (hexaHauteur * 0.25) + yCenter, yCenter,
                            (int) (hexaHauteur * 0.25) + yCenter, (int) (hexaHauteur * 0.75) + yCenter,
                            yCenter + hexaHauteur };
                    vPoly = new Polygon(xPoly, yPoly, xPoly.length);
                    aMap[x][y] = new Hexagone(vPoly, new Position(x, y));
                }

                else {
                    int[] xPoly = { xCenter + xOffSet, xCenter + xOffSet, (int) (hexaLargeur * 0.5) + xCenter + xOffSet,
                            hexaLargeur + xCenter + xOffSet, hexaLargeur + xCenter + xOffSet,
                            (int) (hexaLargeur * 0.5) + xCenter + xOffSet };
                    int[] yPoly = { (int) (hexaHauteur * 0.75) + yCenter, (int) (hexaHauteur * 0.25) + yCenter, yCenter,
                            (int) (hexaHauteur * 0.25) + yCenter, (int) (hexaHauteur * 0.75) + yCenter,
                            yCenter + hexaHauteur };
                    vPoly = new Polygon(xPoly, yPoly, xPoly.length);
                    aMap[x][y] = new Hexagone(vPoly, new Position(x, y));
                }
                yCenter += (int) (hexaHauteur * 0.75);
            }
            xCenter += hexaLargeur;
            yCenter = 50;
        }
    }

    
    /**
     * @return Tous les polygones de la carte
     */
    public Hexagone[][] getPolygons() {
        return this.aMap;
    }

    /**
     * @return L'armée de héros
     */
    public Heros[] getArmeeHeros() {
        return this.armeeHeros;
    }

    /**
     * @return L'armée de monstre
     */
    public Monstre[] getArmeeMonstre() {
        return this.armeeMonstre;
    }

    /**
     * @param pos La position de l'élément à obtenir
     * @return L'élément à obtenir
     */
    public Element getElement(Position pos) {
        int x;
        int y;
        x = pos.getX();
        y = pos.getY();
        return this.aMap[x][y].getElement();
    }


    /**
     * 
     * @return Le nombre de héros restant
     */
    public int getHerosRestant(){
        return this.heros_restant;
    }

    /**
     * 
     * @param i Défini le nombre de héros restant
     */
    public void setHerosRestant(int i){
        this.heros_restant = i;
    }

    /**
     * 
     * @return Le nombre de monstre restant
     */
    public int getMonstreRestant(){
        return this.monstre_restant;
    }

    /**
     * @param i Défini le nombre de monstre restant
     */
    public void setMonstreRestant(int i){
        this.monstre_restant = i;
    }

    /**
     * Trouve une position vide sur la carte
     * @return La position vide sur la carte
     */
    public Position trouvePositionVide() {
        int x;
        int y;
        do {
            x = (int) (Math.random() * LARGEUR_CARTE);
            y = (int) (Math.random() * HAUTEUR_CARTE);
        } while (aMap[x][y].getElement() != null);
        return aMap[x][y].getPos();
    }

    // Trouve une position vide choisie
    // aléatoirement parmi les 6 positions adjacentes de pos
    /**
     * Trouve une position vide adjacente
     * @param pos La position de base
     * @return La position trouvée
     */
    public Position trouvePositionVide(Position pos) {
       if (pos.getX() - 1 >= 0 && aMap[pos.getX() - 1][pos.getY()].getElement() == null) {
            return new Position(pos.getX() - 1, pos.getY());
        }

        if (pos.getY() - 1 >= 0 && aMap[pos.getX()][pos.getY() - 1].getElement() == null) {
            return new Position(pos.getX(), pos.getY() - 1);
        }

        if (pos.getX() + 1 < LARGEUR_CARTE && aMap[pos.getX() + 1][pos.getY()].getElement() == null) {
            return new Position(pos.getX() + 1, pos.getY());

        }

        if (pos.getY() + 1 < HAUTEUR_CARTE && aMap[pos.getX()][pos.getY() + 1].getElement() == null) {
            return new Position(pos.getX(), pos.getY() + 1);

        }

        if (pos.getY() % 2 != 0) { // Ligne impaire

            if (pos.getX() + 1 < LARGEUR_CARTE && pos.getY() + 1 < HAUTEUR_CARTE
                    && aMap[pos.getX() + 1][pos.getY() + 1].getElement() == null) {
                return new Position(pos.getX() + 1, pos.getY() + 1);

            }
            if (pos.getX() + 1 < LARGEUR_CARTE && pos.getY() - 1 >= 0
                    && aMap[pos.getX() + 1][pos.getY() - 1].getElement() == null) {
                return new Position(pos.getX() + 1, pos.getY() - 1);

            }
        } else { // Ligne paire
            if (pos.getX() - 1 >= 0 && pos.getY() + 1 < HAUTEUR_CARTE
                    && aMap[pos.getX() - 1][pos.getY() + 1].getElement() == null) {
                return new Position(pos.getX() - 1, pos.getY() + 1);
            }
            if (pos.getX() - 1 >= 0 && pos.getY() - 1 >= 0
                    && aMap[pos.getX() - 1][pos.getY() - 1].getElement() == null) {
                return new Position(pos.getX() - 1, pos.getY() - 1);
            }
        }

        return null;

    }

    // Trouve aléatoirement un héros sur la carte
    /**
     * Trouve un héros sur la carte
     * @return Le héros trouvé
     */
    public Heros trouveHeros() {
        return this.armeeHeros[(int) Math.random()];
    }
 
    /**
     * Trouve un héros choisi aléatoirement parmi les 6 positons adjacentes
     * @param pos La position du soldat
     * @return Le héros s'il existe
     */
    public Heros trouveHeros(Position pos) {
        
        if (pos.getX() - 1 >= 0 && aMap[pos.getX() - 1][pos.getY()].getElement() instanceof Heros) {
            return (Heros) aMap[pos.getX() - 1][pos.getY()].getElement();
        }
        if (pos.getX() + 1 < LARGEUR_CARTE && aMap[pos.getX() + 1][pos.getY()].getElement() instanceof Heros) {
            return (Heros) aMap[pos.getX() + 1][pos.getY()].getElement();

        }
        if (pos.getY() - 1 >= 0 && aMap[pos.getX()][pos.getY() - 1].getElement() instanceof Heros) {
            return (Heros) aMap[pos.getX()][pos.getY() - 1].getElement();
        }
        if (pos.getY() + 1 < HAUTEUR_CARTE && aMap[pos.getX()][pos.getY() + 1].getElement() instanceof Heros) {
            return (Heros) aMap[pos.getX()][pos.getY() + 1].getElement();

        }

        if (pos.getY() % 2 != 0) { // Ligne impaire

            if (pos.getX() + 1 < LARGEUR_CARTE && pos.getY() + 1 < HAUTEUR_CARTE
                    && aMap[pos.getX() + 1][pos.getY() + 1].getElement() instanceof Heros) {
                return (Heros) aMap[pos.getX() + 1][pos.getY() + 1].getElement();

            }
            if (pos.getX() + 1 < LARGEUR_CARTE && pos.getY() - 1 >= 0
                    && aMap[pos.getX() + 1][pos.getY() - 1].getElement() instanceof Heros) {
                return (Heros) aMap[pos.getX() + 1][pos.getY() - 1].getElement();

            }
        } else { // Ligne paire
            if (pos.getX() - 1 >= 0 && pos.getY() + 1 < HAUTEUR_CARTE
                    && aMap[pos.getX() - 1][pos.getY() + 1].getElement() instanceof Heros) {
                return (Heros) aMap[pos.getX() - 1][pos.getY() + 1].getElement();
            }
            if (pos.getX() - 1 >= 0 && pos.getY() - 1 >= 0
                    && aMap[pos.getX() - 1][pos.getY() - 1].getElement() instanceof Heros) {
                return (Heros) aMap[pos.getX() - 1][pos.getY() - 1].getElement();
            }
        }

        return null; // On ne l'a pas trouvé on renvoie null
    }

    public Heros trouveHerosTir(Monstre pMonstre){
        int vPortee = pMonstre.getPortee();
        Position vPos = pMonstre.getPosition();
        Heros vHerosTarget = null;

        int hexaLargeur = ((vPortee*50)*2)+1; 
        int hexaHauteur = hexaLargeur;

        int xCenter = (int)(aMap[vPos.getX()][vPos.getY()].getHexagone().getBounds().getCenterX() - (double)((vPortee*50)*2)/2); 
        int yCenter = (int)(aMap[vPos.getX()][vPos.getY()].getHexagone().getBounds().getCenterY() -(double)((vPortee*50)*2)/2);

        int[] xPoly = {xCenter, xCenter, (int)(hexaLargeur*0.50) + xCenter,hexaLargeur+xCenter, hexaLargeur+xCenter, (int)(hexaLargeur*0.50)+xCenter};
        int[] yPoly = {(int)(hexaHauteur*0.75) + yCenter, (int)(hexaHauteur*0.25) + yCenter, yCenter, (int)(hexaHauteur*0.25)+ yCenter, (int)(hexaHauteur*0.75)+yCenter, yCenter+hexaHauteur}; 

        Polygon vPoly = new Polygon(xPoly, yPoly,xPoly.length);

        for(int x = 0; x < this.aMap.length; x++){ 
            for (int y = 0; y <this.aMap[0].length; y++) {
                if(vPoly.contains(this.aMap[x][y].getHexagone().getBounds2D().getCenterX(), this.aMap[x][y].getHexagone().getBounds2D().getCenterY()) && (this.aMap[x][y].getElement() instanceof Heros)){
                    vHerosTarget = (Heros) this.aMap[x][y].getElement(); 
                }
            }
        } 
        return vHerosTarget;
    }

    /**
     * Déplace un soldat
     * @param pos Position où l'on souhaite déplacer
     * @param soldat Soldat à déplacer
     * @return Si c'est possible ou non de le déplacer
     */
    public boolean deplaceSoldat(Position pos, Soldat soldat) {
        // Si case voisine et qu'il n'y a pas d'éléments dessus
        if (soldat.getPosition().estVoisine(pos) && aMap[pos.getX()][pos.getY()].getElement() == null) {
            herosVision(false);

            aMap[soldat.getPosition().getX()][soldat.getPosition().getY()].setElement(null);// On retire le soldat
            soldat.seDeplace(pos);// On change sa pos
            aMap[soldat.getPosition().getX()][soldat.getPosition().getY()].setElement(soldat);// On le replace
            herosVision(true);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tue un personnage
     * @param perso Soldat à tuer
     */
    public void mort(Soldat perso) {
        // Il est mort on update la carte en mettant null
        this.aMap[perso.getPosition().getX()][perso.getPosition().getY()].setElement(null);
        this.herosVision(false);
        if(perso instanceof Heros){
            for (int i = 0; i< armeeHeros.length;i++) {
                if(armeeHeros[i] != null){
                    if(armeeHeros[i].equals((Heros)perso)){
                        armeeHeros[i] = null;
                    }
                }
            }
        }
        else{
            for (int i = 0; i< armeeMonstre.length;i++) {
                if(armeeMonstre[i] != null){
                    if(armeeMonstre[i].equals((Monstre)perso)){
                        armeeMonstre[i] = null;
                    }
                }
            }
        }
        this.herosVision(true);
    }

    /**
     * Action du héros 
     * @param pHeros Le héros qui fait l'action
     * @param pos2 La position où le placer
     * @return Booléen si il peut jouer ou non
     */
    public boolean actionHeros(Heros pHeros, Position pos2) {
        if (!(pHeros.getJoue())) { // S'il n'a pas déjà joué

            if (aMap[pos2.getX()][pos2.getY()].getElement() == null) { // Pas d'éléments on se déplace
                deplaceSoldat(pos2, pHeros);
                pHeros.setJoue(true);
                return true;
            }
            else { // Sinon on combat
                if (aMap[pos2.getX()][pos2.getY()].getElement() instanceof Monstre) { // On vérifie tout de même qu'il s'agisse bien d'un monstre 
                    Monstre vMonstre = (Monstre) aMap[pos2.getX()][pos2.getY()].getElement();
                    pHeros.combat(vMonstre); // On combat
                    pHeros.setJoue(true);
                    if (vMonstre.getPoints() <= 0) { // Plus de points de vie, il décède
                        mort(vMonstre);
                        setMonstreRestant(monstre_restant--);
                    }
                    return true;
                }
            }
        }
        // Sinon on retourne faux
        return false;
        
    }

    public void toutDessiner(Graphics g) {
        // Insérer code dessin
    }

    /**
     * Définie la vision des héros
     * @param pSet Mets à vrai ou faux leur vision
     */
    public void herosVision(boolean pSet) {
        for (int i = 0; i < NB_HEROS; i++) {
            if (this.armeeHeros[i] != null) {
                int vision = this.armeeHeros[i].getPortee();
                Position vPos = this.armeeHeros[i].getPosition();

                aMap[vPos.getX()][vPos.getY()].setVisible(pSet);// Positon unite
                //visionOne(vPos, pSet, vision);
                
                 int hexaLargeur = ((vision*50)*2)+1; 
                 int hexaHauteur = hexaLargeur;
                  
                 int xCenter = (int)(aMap[vPos.getX()][vPos.getY()].getHexagone().getBounds().getCenterX() - (double)((vision*50)*2)/2); 
                 int yCenter = (int)(aMap[vPos.getX()][vPos.getY()].getHexagone().getBounds().getCenterY() -(double)((vision*50)*2)/2);
                 
                 int[] xPoly = {xCenter, xCenter, (int)(hexaLargeur*0.50) + xCenter,hexaLargeur+xCenter, hexaLargeur+xCenter, (int)(hexaLargeur*0.50)+xCenter};
                 int[] yPoly = {(int)(hexaHauteur*0.75) + yCenter, (int)(hexaHauteur*0.25) + yCenter, yCenter, (int)(hexaHauteur*0.25)+ yCenter, (int)(hexaHauteur*0.75)+yCenter, yCenter+hexaHauteur}; 
                 Polygon vPoly = new Polygon(xPoly, yPoly,xPoly.length);
                 this.aPolybig = vPoly;

                 
                 for(int x = 0; x < this.aMap.length; x++){ 
                    for (int y = 0; y <this.aMap[0].length; y++) {
                        if(vPoly.contains( this.aMap[x][y].getHexagone().getBounds2D().getCenterX(), this.aMap[x][y].getHexagone().getBounds2D().getCenterY())){
                            this.aMap[x][y].setVisible(pSet); 
                        } 
                    } 
                }
            }
        }
    }

    public void seDessine(Graphics g){
        g.drawPolygon(this.aPolybig);
    }

    /**
     * Fonction subsidiaire récursive pour donner/retirer la vision aux héros
     * @param vPos Position de la case où mettre la vision
     * @param pSet Mettre / Retirer la vision
     * @param pVision Vision du héros de base
     */
    public void visionOne(Position vPos, boolean pSet, int pVision) {
        if (pVision == 0) // Fin de la récursivité
            return;

        if (vPos.getX() - 1 >= 0) {
            aMap[vPos.getX() - 1][vPos.getY()].setVisible(pSet);
            visionOne(new Position(vPos.getX() - 1, vPos.getY()), pSet, pVision - 1);
        }
        if (vPos.getX() + 1 < LARGEUR_CARTE) {
            aMap[vPos.getX() + 1][vPos.getY()].setVisible(pSet);
            visionOne(new Position(vPos.getX() + 1, vPos.getY()), pSet, pVision - 1);

        }
        if (vPos.getY() - 1 >= 0) {
            aMap[vPos.getX()][vPos.getY() - 1].setVisible(pSet);
            visionOne(new Position(vPos.getX(), vPos.getY() - 1), pSet, pVision - 1);

        }
        if (vPos.getY() + 1 < HAUTEUR_CARTE) {
            aMap[vPos.getX()][vPos.getY() + 1].setVisible(pSet);
            visionOne(new Position(vPos.getX(), vPos.getY() + 1), pSet, pVision - 1);

        }

        if (vPos.getY() % 2 != 0) { // Ligne impaire

            if (vPos.getX() + 1 < LARGEUR_CARTE && vPos.getY() + 1 < HAUTEUR_CARTE) {
                aMap[vPos.getX() + 1][vPos.getY() + 1].setVisible(pSet);
                visionOne(new Position(vPos.getX() + 1, vPos.getY() + 1), pSet, pVision - 1);

            }
            if (vPos.getX() + 1 < LARGEUR_CARTE && vPos.getY() - 1 >= 0) {
                aMap[vPos.getX() + 1][vPos.getY() - 1].setVisible(pSet);
                visionOne(new Position(vPos.getX() + 1, vPos.getY() - 1), pSet, pVision - 1);

            }
        } else { // Ligne paire
            if (vPos.getX() - 1 >= 0 && vPos.getY() + 1 < HAUTEUR_CARTE) {
                aMap[vPos.getX() - 1][vPos.getY() + 1].setVisible(pSet);
                visionOne(new Position(vPos.getX() - 1, vPos.getY() + 1), pSet, pVision - 1);

            }
            if (vPos.getX() - 1 >= 0 && vPos.getY() - 1 >= 0) {
                aMap[vPos.getX() - 1][vPos.getY() - 1].setVisible(pSet);
                visionOne(new Position(vPos.getX() - 1, vPos.getY() - 1), pSet, pVision - 1);

            }
        }
    }

    /**
     * Ajout un héros dans l'armée
     * @param pHeros Ajoute ce héros à l'armée
     */
    public void appendHeros(Heros pHeros) {
        for (int i = 0; i < NB_HEROS; i++) {
            if (this.armeeHeros[i] == null) {
                this.armeeHeros[i] = pHeros;
                return;
            }
        }
    }

    /**
     * Ajout d'un monstre dans l'armée
     * @param pMonstre Ajoute ce monstre à l'armée
     */
    public void appendMonstre(Monstre pMonstre) {
        for (int i = 0; i < NB_MONSTRES; i++) {
            if (this.armeeMonstre[i] == null) {
                this.armeeMonstre[i] = pMonstre;
                return;
            }
        }
    }

    // Les héros n'ayant pas jouer se reposent, les monstres jouent
    /**
     * Fonction de fin de tour - Fais jouer les monstres
     * @param pj Panneau de jeu utilisé
     */
    public void jouerSoldats(PanneauJeu pj) {
        // Les héros qui n'ont pas joué ce repose
        for (int i = 0; i < NB_HEROS; i++) {
            if (!(armeeHeros[i].getJoue())) {
                armeeHeros[i].seRepose();
            }
        }

        // Au tour des monstres de jouer
        for (int j = 0; j < NB_MONSTRES; j++) {
            Heros vHerosTarget = trouveHeros(armeeMonstre[j].getPosition());
            if (vHerosTarget == null) { // Si on ne trouve pas de héros dans les parages on se déplace
                Position vNextPos = trouvePositionVide(armeeMonstre[j].getPosition());
                deplaceSoldat(vNextPos, armeeMonstre[j]);
            } else { // Si on a trouvé un héros non loin on l'affronte
                armeeMonstre[j].combat(vHerosTarget);
            }
        }
    }
}
