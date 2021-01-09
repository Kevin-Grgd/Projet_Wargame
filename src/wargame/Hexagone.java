package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Hexagone extends JComponent implements IConfig{
    private static final long serialVersionUID = 1905122041950251207L;

    private Polygon Hexa = new Polygon();
    private transient Image aImage;

    /**
     * Constructeur de l'hexagone
     * @param pHexa Le polygone concern�
     * @param posX Sa position en x
     * @param posY Sa position en y
     */
    public Hexagone(Polygon pHexa, Position pos) {
        int xCenter = LARGEUR_FENETRE - (LARGEUR_CARTE+1) * NB_PIX_CASE + NB_PIX_CASE/4;//Pour centrer la carte dans la fenetre
        int yCenter = HAUTEUR_BARRE_OUTIL + HAUTEUR_FENETRE/2 - (HAUTEUR_CARTE/2)*NB_PIX_CASE + NB_PIX_CASE/2;
        int xOffSet = NB_PIX_CASE / 2;//decalage pour les lignes impaires
        aImage = null;
        
        if(pHexa == null) {
        	int x, y;
        	int decalageX = NB_PIX_CASE * pos.getX(); //decalage des cases en fonction de (posX, posY)
        	int decalageY = ((int) (NB_PIX_CASE * 0.75)) * pos.getY();
        	if(pos.getY() % 2 == 0) {
        		for(int i = 0 ; i < 6 ; i++) {
        			x = (int) ((xCenter + decalageX) + (NB_PIX_CASE/2+3) * Math.sin(i*2*Math.PI/6));
        			y = (int) ((yCenter + decalageY) + (NB_PIX_CASE/2) * Math.cos(i*2*Math.PI/6));
        			Hexa.addPoint(x, y);
        		}
        	}
        	else {
        		for(int i = 0 ; i < 6 ; i++) {
        			x = (int) ((xCenter + decalageX + xOffSet) + (NB_PIX_CASE/2+3) * Math.sin(i*2*Math.PI/6));
        			y = (int) ((yCenter + decalageY) + (NB_PIX_CASE/2) * Math.cos(i*2*Math.PI/6));
        			Hexa.addPoint(x, y);
        		}	
        	}
        }
        else {
        	Hexa = pHexa;
        }
        
        try {
            String vUrlString = "/resources/grass#.png".replace('#',  Integer.toString(pos.getGrassNumber()).charAt(0));
            URL url = getClass().getResource(vUrlString);
            BufferedImage aBackground = ImageIO.read(url); 
            aImage = new Image(aBackground, Hexa);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Constructeur de l'hexagone juste avec un polygone et une position
     * @param pHexa Le polygone a dessin�
     * @param pPos Sa position
     */
    public Hexagone(Position pos){
        this(null, pos);        
    }

    /**
     * Retourne l'image de l'hexagone
     * @return Image de l'hexagone
     */
    public Image getaImage() {
        return aImage;
    }

    /**
     * D�termine l'image de l'hexagone
     * @param pImage L'image de l'hexagone
     */
    public void setaImage(Image pImage) {
        this.aImage = pImage;
    }

    /**
     * Indique si l'hexagone est contenu dans ces coordonn�es
     * @param x Coordonn�e en abscisse
     * @param y Coordonn�e en ordonn�
     * @return S'il est dedans ou non
     */
    public boolean isContain(int x, int y){
        return this.Hexa.contains(new Point(x,y));
    }

    /**
     * @return L'hexagone choisi
     */
    public Polygon getHexagone(){
        return this.Hexa;
    }
    
    /**
     * Dessine l'hexagone
     * @param g Endroit o� le dessiner
     * @param pos La case a dessiner
     */
    public void seDessiner(Graphics g, Position pos){
        super.paintComponent(g);
        if(!(pos.getVisible())){
            g.setColor(COULEUR_INCONNU);
            g.drawPolygon(Hexa);
            g.fillPolygon(Hexa);
        }

        else{
        	aImage.drawHexa(g);
        	
            if(pos.getElement() != null){
            	try {
                    String vUrlString = pos.getElement().getUrl().replace('#',  Integer.toString(pos.getElement().getSkinNumber()).charAt(0));
                    URL url = getClass().getResource(vUrlString);
                    BufferedImage aBackground = ImageIO.read(url); 
                    Image vImage = new Image(aBackground, Hexa);
                    vImage.drawHexa(g);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            if(pos.getElement() instanceof Heros) {
            	Heros heros = (Heros) pos.getElement();
            	if(heros.getJoue()) {
            		 Color vWhiteOpa = new Color(0,0,0,180);
            		 g.setColor(vWhiteOpa);
            		 g.fillPolygon(Hexa);
            	}
            }
      
            g.setColor(Color.BLACK);
            g.drawPolygon(Hexa);
        }

        if(pos.getFocus()){
            Color vWhiteOpa = new Color(1,0,0,70);
            g.setColor(vWhiteOpa);
            g.fillPolygon(Hexa);
        }

        if(pos.getTarget()){
            if (pos.getElement() instanceof Monstre){ // Si c'est un monstre
                Color vBlueOpa = new Color(1, 250, 0, 70);
                g.setColor(vBlueOpa);
                g.fillPolygon(Hexa);
            }
            else{
                Color vGrayOpa = new Color(0, 100, 250, 150);
                g.setColor(vGrayOpa);
                g.fillPolygon(Hexa);
            }
        }
    }
}