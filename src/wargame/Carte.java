package wargame;

import java.awt.Polygon;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.ArrayList;

public class Carte extends JPanel implements IConfig, ICarte{
   
    private static final long serialVersionUID = 1972732166617015332L;
    public Hexagone[][] aMap;
    private Heros[] armeeHeros;
    private Monstre[] armeeMonstre; 
    private int heros_restant;
    private int monstre_restant;


    /**
     * Constructeur de la carte
    */
    public Carte() {
        aMap = new Hexagone[LARGEUR_CARTE][HAUTEUR_CARTE];
        //Map = new Position[LARGEUR_CARTE][HAUTEUR_CARTE];
        armeeHeros = new Heros[NB_HEROS];
        armeeMonstre = new Monstre[NB_MONSTRES];
        heros_restant = NB_HEROS;
        monstre_restant = NB_MONSTRES;
        for (int x = 0; x < LARGEUR_CARTE ; x++) {
            for (int y = 0; y < HAUTEUR_CARTE ; y++) {
            	aMap[x][y] = new Hexagone(new Position(x, y));
            	//Map[x][y] = new Position(x, y);
            }
        }
        ajoutObstacle();
        ajoutSoldat();
                
    }

    /**
     * Ajout des obstacles sur la carte
     * @param pCarte Carte où ajouter les obstacles
     */
    public void ajoutObstacle() {
        int x;
        int y;
        Obstacle vObstacle;
        Position vPos;
        for (int i = 0; i < NB_OBSTACLES; i++) {
        	vPos = trouvePositionVide();
            x = vPos.getX();
            y = vPos.getY();
            vObstacle = new Obstacle(vPos, aMap[x][y].getHexagone());
            aMap[x][y].setElement(vObstacle);
        }
    }
    
    /**
     * Ajout des soldats sur la carte
     * @param pCarte Carte où ajouter les soldats
     */
    public void ajoutSoldat() {
        int x, y, i;
        // Ajout des héros sur le côté gauche de la map
        for (i = 0; i < NB_HEROS; i++) {
            do {
                x = (int) (Math.random() * (LARGEUR_CARTE / 2)); // Côté gauche de la carte
                y = (int) (Math.random() * HAUTEUR_CARTE); // De bas en haut
            } while (aMap[x][y].getElement() != null); // Tant que il y a un obstacle on prend une autre position
            
            armeeHeros[i] = new Heros(new Position(x, y)); // Création du héros
            aMap[x][y].setElement(armeeHeros[i]); // Ajout du héros dans la map
            herosVision(true);
        }

        // Ajout des monstres sur le côté droit de la map
        for (i = 0; i < NB_MONSTRES; i++) {
            do {
                x = LARGEUR_CARTE / 2 + (int) (Math.random() * (LARGEUR_CARTE - LARGEUR_CARTE / 2)); // Côté droit de la carte
                y = (int) (Math.random() * HAUTEUR_CARTE); // De bas en haut
            } while (aMap[x][y].getElement() != null); // Tant que il y a un obstacle on prend une autre position
           
            armeeMonstre[i] = new Monstre(new Position(x, y));// Création du monstre
            aMap[x][y].setElement(armeeMonstre[i]); // Ajout du monstre dans la map
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
        Position vPos;
        do {
            x = new Random().nextInt(aMap.length);
            y = new Random().nextInt(aMap[0].length);
        } while (aMap[x][y].getElement() != null);
        vPos = new Position(x, y);

        return vPos;
    }

    // Trouve une position vide choisie
    // aléatoirement parmi les 6 positions adjacentes de pos
    /**
     * Trouve une position vide adjacente
     * @param pos La position de base
     * @return La position trouvée
     */
    public Position trouvePositionVide(Position pos) {
        Position[] posAdjacentes = pos.getAdjacents();
        int indiceAlea = 0;
        int vide = 0;
		for(int i = 0 ; i < posAdjacentes.length ; i++)
			if(posAdjacentes[i].getElement() == null)
				vide++;
				
		if (vide == 0)//toutes les positions adjacentes sont pleines
			return null;
		
		while(true) {
			indiceAlea = (int) (Math.random()*posAdjacentes.length);
			if(posAdjacentes[indiceAlea].getElement() == null)
				return posAdjacentes[indiceAlea];
		}
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
        int x;
        int y;
        ArrayList<Heros> herosAdjacents = new ArrayList<>();
        Position[] posAdjacentes = pos.getAdjacents();
		for(int i = 0 ; i <  posAdjacentes.length ; i++) {
                x = posAdjacentes[i].getX();
                y = posAdjacentes[i].getY();
            if (aMap[x][y].getElement() instanceof Heros){
				herosAdjacents.add((Heros) aMap[x][y].getElement());
			}
        }
		if(herosAdjacents.isEmpty()){
            return null; //on n'a pas trouve de heros
        }
		else{
            return herosAdjacents.get((int) (Math.random()*herosAdjacents.size()));
        }
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
    	if(pHeros.getJoue())
    		return false;
    	else { // S'il n'a pas déjà joué
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
                return false;
            }
        }
    }

    public void toutDessiner(Graphics g) {
    	for (int x = 0; x < LARGEUR_CARTE ; x++) {
            for (int y = 0; y < HAUTEUR_CARTE ; y++) {
            	aMap[x][y].seDessiner(g);
            }
        }
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

    public void Sauvegarde(){
        //Save

        SaveFile vFile = new SaveFile(1);
        vFile.setArmeeHeros(armeeHeros);
        vFile.setArmeeMonstre(armeeMonstre);
        vFile.setHeros_restant(heros_restant);
        vFile.setMonstre_restant(monstre_restant);
        vFile.setaMap(aMap);

        try {
           FileOutputStream fileOut = new FileOutputStream(vFile.getFileName());
           ObjectOutputStream out = new ObjectOutputStream(fileOut);
           out.writeObject(vFile);
           out.close();
           fileOut.close();
           System.out.printf("Serialized data is saved in carte.ser");
        } catch (IOException i) {
           i.printStackTrace();
        }
    }

    public void Recharger(){
    //Load
    SaveFile vFile = new SaveFile(1);
    try {
        FileInputStream fileIn = new FileInputStream(vFile.getFileName());
        ObjectInputStream in = new ObjectInputStream(fileIn);
        vFile = (SaveFile)in.readObject();

        in.close();

        this.aMap = vFile.getaMap();
        this.armeeHeros =vFile.getArmeeHeros();
        this.armeeMonstre = vFile.getArmeeMonstre();
        this.monstre_restant = vFile.getMonstre_restant();
        this.heros_restant = vFile.getHeros_restant();

        for (int x = 0; x < LARGEUR_CARTE ; x++) {
            for (int y = 0; y < HAUTEUR_CARTE ; y++) {
                this.aMap[x][y].reloadData();
            }
        }
        fileIn.close();

    } catch (IOException | ClassNotFoundException i) {
        i.printStackTrace();
        return;
    }
    }
}