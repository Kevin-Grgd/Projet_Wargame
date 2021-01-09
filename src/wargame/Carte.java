package wargame;

import java.awt.Polygon;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Carte extends JPanel implements IConfig, ICarte {
    private static final long serialVersionUID = 1L;
    private Hexagone[][] hexagone;
    private Position[][] map;
    private transient Heros[] armeeHeros;
    private transient Monstre[] armeeMonstre;
    private transient int heros_restant;
    private transient int monstre_restant;
    private int nbTours;


    /**
     * Constructeur de la carte
    */
    public Carte() {
        hexagone = new Hexagone[LARGEUR_CARTE][HAUTEUR_CARTE];
        map = new Position[LARGEUR_CARTE][HAUTEUR_CARTE];
        armeeHeros = new Heros[NB_HEROS];
        armeeMonstre = new Monstre[NB_MONSTRES];
        heros_restant = NB_HEROS;
        monstre_restant = NB_MONSTRES;
        nbTours = 1;
        
        for (int x = 0; x < LARGEUR_CARTE ; x++) {
            for (int y = 0; y < HAUTEUR_CARTE ; y++) {
            	map[x][y] = new Position(x, y);
            	hexagone[x][y] = new Hexagone(map[x][y]);
            }
        }
        ajoutObstacle();
        ajoutSoldat();
    }
    
    public int getTours() {
    	return this.nbTours;
    }
    
    public void setTours(int nbTours) {
    	this.nbTours = nbTours;
    }

    /**
     * Ajout des obstacles sur la carte
     * @param pCarte Carte où ajouter les obstacles
     */
    public void ajoutObstacle() {
        for (int i = 0; i < NB_OBSTACLES; i++)
        	trouvePositionVide().setElement(new Obstacle());
    }
    
    /**
     * Ajout des soldats sur la carte
     * @param pCarte Carte où ajouter les soldats
     */
    public void ajoutSoldat() {
        int x; 
        int y;
        int i;
        // Ajout des héros sur le côté gauche de la map
        for (i = 0; i < NB_HEROS; i++) {
            do {
                x = (int) (Math.random() * ((double)LARGEUR_CARTE / 2)); // Côté gauche de la carte
                y = (int) (Math.random() * HAUTEUR_CARTE); // De bas en haut
            } while (map[x][y].getElement() != null); // Tant que il y a un obstacle on prend une autre position
            
            armeeHeros[i] = new Heros(); // Création du héros
            map[x][y].setElement(armeeHeros[i]); // Ajout du héros dans la map
            herosVision(true);
        }

        // Ajout des monstres sur le côté droit de la map
        for (i = 0; i < NB_MONSTRES; i++) {
            do {
                x = LARGEUR_CARTE / 2 + (int) (Math.random() * (LARGEUR_CARTE - (double)LARGEUR_CARTE / 2)); // Côté droit de la carte
                y = (int) (Math.random() * HAUTEUR_CARTE); // De bas en haut
            } while (map[x][y].getElement() != null); // Tant que il y a un obstacle on prend une autre position
           
            armeeMonstre[i] = new Monstre();// Création du monstre
            map[x][y].setElement(armeeMonstre[i]); // Ajout du monstre dans la map
        }
    }
    
    /**
     * @return Tous les polygones de la carte
     */
    public Hexagone[][] getHexagones() {
        return hexagone;
    }
    /**
     * @return la carte
     */
    public Position[][] getCarte() {
        return map;
    }

    /**
     * @return L'armée de héros
     */
    public Heros[] getArmeeHeros() {
        return armeeHeros;
    }

    /**
     * @return L'armée de monstre
     */
    public Monstre[] getArmeeMonstre() {
        return armeeMonstre;
    }

    /**
     * @param pos La position de l'élément à obtenir
     * @return L'élément à obtenir
     */
    public Element getElement(Position pos) {
        return pos.getElement();
    }

    /**
     * @return Le nombre de héros restant
     */
    public int getHerosRestant(){
        return heros_restant;
    }

    /**
     * @param i Défini le nombre de héros restant
     */
    public void setHerosRestant(int i){
        heros_restant = i;
    }

    /**
     * @return Le nombre de monstre restant
     */
    public int getMonstreRestant(){
        return monstre_restant;
    }

    /**
     * @param i Défini le nombre de monstre restant
     */
    public void setMonstreRestant(int i){
        monstre_restant = i;
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
        } while (map[x][y].getElement() != null);
        return map[x][y];
    }

    // Trouve une position vide choisie aléatoirement parmi les 6 positions adjacentes de pos
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
        return armeeHeros[(int) Math.random()];
    }
 
    /**
     * Trouve un héros choisi aléatoirement parmi les 6 positons adjacentes
     * @param pos La position du soldat
     * @return Le héros s'il existe, null sinon
     */
    public Heros trouveHeros(Position pos) {
        int x;
        int y;
        ArrayList<Heros> herosAdjacents = new ArrayList<>();
        Position[] posAdjacentes = pos.getAdjacents();
		for(int i = 0 ; i <  posAdjacentes.length ; i++) {
            x = posAdjacentes[i].getX();
            y = posAdjacentes[i].getY();
            if (map[x][y].getElement() instanceof Heros){
				herosAdjacents.add((Heros) map[x][y].getElement());
           }
        }
		
		if(herosAdjacents.isEmpty())
            return null; //on n'a pas trouve de heros
		else
            return herosAdjacents.get((int) (Math.random()*herosAdjacents.size()));
    }

    public Heros trouveHerosTir(Monstre pMonstre){
        int vPortee = pMonstre.getPortee();
        Position vPos = pMonstre.getPosition();
        Heros vHerosTarget = null;
        Polygon vPoly = new Polygon();

        int hexaLargeur = vPortee * NB_PIX_CASE; 
        int hexaHauteur = hexaLargeur - NB_PIX_CASE;

        int xCenter = (int)(hexagone[vPos.getX()][vPos.getY()].getHexagone().getBounds().getCenterX() - (double)((vPortee*NB_PIX_CASE)*2)/2); 
        int yCenter = (int)(hexagone[vPos.getX()][vPos.getY()].getHexagone().getBounds().getCenterY() -(double)((vPortee*NB_PIX_CASE)*2)/2); 

        for(int i = 0 ; i < 6 ; i++) {
    		int x = (int) (xCenter + hexaLargeur * Math.sin(i*2*Math.PI/6));
			int y = (int) (yCenter + hexaHauteur * Math.cos(i*2*Math.PI/6));
			vPoly.addPoint(x, y);
    	}

        for(int x = 0; x < LARGEUR_CARTE; x++){ 
            for (int y = 0; y < HAUTEUR_CARTE; y++) {
                if(vPoly.contains(hexagone[x][y].getHexagone().getBounds2D().getCenterX(), hexagone[x][y].getHexagone().getBounds2D().getCenterY()) && (map[x][y].getElement() instanceof Heros)){
                    vHerosTarget = (Heros) map[x][y].getElement(); 
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
        if (soldat.getPosition().estVoisine(pos) && map[pos.getX()][pos.getY()].getElement() == null) {
            herosVision(false);
            try {
            	map[soldat.getPosition().getX()][soldat.getPosition().getY()].setElement(null);// On retire le soldat
                soldat.seDeplace(pos);// On change sa pos
                map[soldat.getPosition().getX()][soldat.getPosition().getY()].setElement(soldat);// On le replace
                herosVision(true);
                return true;
            } catch (Exception e) {
            	e.printStackTrace();
            	return false;
            }
           
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
        map[perso.getPosition().getX()][perso.getPosition().getY()].setElement(null);
        herosVision(false);
        if(perso instanceof Heros){
            for (int i = 0; i < armeeHeros.length;i++) {
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
        herosVision(true);
    }

    /**
     * Action du héros 
     * @param pHeros Le héros qui fait l'action
     * @param pos2 La position où le placer
     * @return Booléen si il peut jouer ou non
     */
    public boolean actionHeros(Heros pHeros, Position pos2) {
    	if(pHeros != null) {
    		if(pHeros.getJoue())
        		return false;
        	else { // S'il n'a pas déjà joué
                if (map[pos2.getX()][pos2.getY()].getElement() == null) { // Pas d'éléments on se déplace
                    deplaceSoldat(pos2, pHeros);
                    pHeros.setJoue(true);
                    return true;
                }
                else { // Sinon on combat
                    if (map[pos2.getX()][pos2.getY()].getElement() instanceof Monstre) { // On vérifie tout de même qu'il s'agisse bien d'un monstre 
                        Monstre vMonstre = (Monstre) map[pos2.getX()][pos2.getY()].getElement();
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
    	else {
    		return false;
    	}
    }

    public void toutDessiner(Graphics g) {
    	for (int x = 0; x < LARGEUR_CARTE ; x++) {
            for (int y = 0; y < HAUTEUR_CARTE ; y++) {
            	hexagone[x][y].seDessiner(g,map[x][y]);
            }
        }
    }

    /**
     * Définie la vision des héros
     * @param pSet Mets à vrai ou faux leur vision
     */
    public void herosVision(boolean pSet) {
    	for (int i = 0; i < NB_HEROS; i++) {
            if (armeeHeros[i] != null) {
                int vision = armeeHeros[i].getPortee();
                Position vPos = armeeHeros[i].getPosition();
                Polygon vPoly = new Polygon();
  
                map[vPos.getX()][vPos.getY()].setVisible(pSet);// Positon unite

                int hexaLargeur = ((2*vision+1)*NB_PIX_CASE); 
                int hexaHauteur = hexaLargeur - NB_PIX_CASE;
                  
                 int xCenter = (int) hexagone[vPos.getX()][vPos.getY()].getHexagone().getBounds().getCenterX(); 
                 int yCenter = (int) hexagone[vPos.getX()][vPos.getY()].getHexagone().getBounds().getCenterY();

                 for(int j = 0 ; j < 6 ; j++) {
             		int x = (int) (xCenter + ((double)hexaLargeur/2) * Math.cos(j*2*Math.PI/6));
         			int y = (int) (yCenter + (double)hexaHauteur/2 * Math.sin(j*2*Math.PI/6));
         			vPoly.addPoint(x, y);
             	}
                 
                 for(int x = 0; x < LARGEUR_CARTE; x++){ 
                    for (int y = 0; y < HAUTEUR_CARTE; y++) {
                    	if(vPoly.contains(hexagone[x][y].getHexagone().getBounds2D().getCenterX(), hexagone[x][y].getHexagone().getBounds2D().getCenterY())){
                            map[x][y].setVisible(pSet); 
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
            map[vPos.getX() - 1][vPos.getY()].setVisible(pSet);
            visionOne(new Position(vPos.getX() - 1, vPos.getY()), pSet, pVision - 1);
        }
        if (vPos.getX() + 1 < LARGEUR_CARTE) {
            map[vPos.getX() + 1][vPos.getY()].setVisible(pSet);
            visionOne(new Position(vPos.getX() + 1, vPos.getY()), pSet, pVision - 1);

        }
        if (vPos.getY() - 1 >= 0) {
            map[vPos.getX()][vPos.getY() - 1].setVisible(pSet);
            visionOne(new Position(vPos.getX(), vPos.getY() - 1), pSet, pVision - 1);

        }
        if (vPos.getY() + 1 < HAUTEUR_CARTE) {
            map[vPos.getX()][vPos.getY() + 1].setVisible(pSet);
            visionOne(new Position(vPos.getX(), vPos.getY() + 1), pSet, pVision - 1);

        }

        if (vPos.getY() % 2 != 0) { // Ligne impaire

            if (vPos.getX() + 1 < LARGEUR_CARTE && vPos.getY() + 1 < HAUTEUR_CARTE) {
                map[vPos.getX() + 1][vPos.getY() + 1].setVisible(pSet);
                visionOne(new Position(vPos.getX() + 1, vPos.getY() + 1), pSet, pVision - 1);

            }
            if (vPos.getX() + 1 < LARGEUR_CARTE && vPos.getY() - 1 >= 0) {
                map[vPos.getX() + 1][vPos.getY() - 1].setVisible(pSet);
                visionOne(new Position(vPos.getX() + 1, vPos.getY() - 1), pSet, pVision - 1);

            }
        } else { // Ligne paire
            if (vPos.getX() - 1 >= 0 && vPos.getY() + 1 < HAUTEUR_CARTE) {
                map[vPos.getX() - 1][vPos.getY() + 1].setVisible(pSet);
                visionOne(new Position(vPos.getX() - 1, vPos.getY() + 1), pSet, pVision - 1);

            }
            if (vPos.getX() - 1 >= 0 && vPos.getY() - 1 >= 0) {
                map[vPos.getX() - 1][vPos.getY() - 1].setVisible(pSet);
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
    
    public void Sauvegarde(int numSave){
        //Save
        SaveFile vFile = new SaveFile(numSave, this);
        try(FileOutputStream fileOut = new FileOutputStream(vFile.getFileName());
        ObjectOutputStream out = new ObjectOutputStream(fileOut);){
           
           out.writeObject(vFile);
           System.out.println("Carte : saveGame : "+vFile.getFileName());
           System.out.println("Serialized data is saved in carte.ser");
           System.out.println("nbTours sauvegarde :"+vFile.getTours());
           System.out.println("nbTours en VRAI :"+this.getTours());
        } catch (IOException i) {
           i.printStackTrace();
        }
    }
    
    public void Recharger(int numSave){
        //Load
        SaveFile vFile;
        String usrDirectory = System.getProperty("user.dir");
        String loadGame = usrDirectory+"/src/saves/save" + numSave + ".warsave";
        System.out.println("Carte : loadGame : "+loadGame);
        
        try(FileInputStream fileIn = new FileInputStream(loadGame);
        ObjectInputStream in = new ObjectInputStream(fileIn);) {
            
            vFile = (SaveFile)in.readObject();
            map = vFile.getCarte();
            armeeHeros =vFile.getArmeeHeros();
            armeeMonstre = vFile.getArmeeMonstre();
            monstre_restant = vFile.getMonstre_restant();
            heros_restant = vFile.getHeros_restant();
            nbTours = vFile.getTours();
            System.out.println("Serialized data is load");
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
            
        }
    }
}

