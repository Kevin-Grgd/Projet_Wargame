package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Hexagone extends JComponent{
    private static final long serialVersionUID = 1905122041950251207L;

    private transient Element aElement;
    private boolean isVisible;
    private Polygon Hexa;
    private boolean aFocus = false;
    private boolean isTarget = false;
    private transient Image aImage;
    private transient BufferedImage aBackground;
    private transient Position aPosition;

    /**
     * Constructeur de l'hexagone
     * @param pElement L'élément sur l'hexagone
     * @param pVisible S'il est visible ou non
     * @param pHexa Le polygone concerné
     * @param pPos Sa position
     */
    public Hexagone(Element pElement, boolean pVisible, Polygon pHexa,Position pPos) {
        this.isVisible = pVisible;
        this.Hexa = pHexa;
        this.aElement = pElement;
        this.aImage = null;
        this.aPosition = pPos;
        this.isTarget = false;
        try {
            int vRand = new Random().nextInt(IConfig.NB_TEXTURE_OBSTACLE)+1;
            String vUrlString = "/resources/grass#.png".replace('#',  Integer.toString(vRand).charAt(0));
            URL url = this.getClass().getResource(vUrlString);
            this.aBackground = ImageIO.read(url);         
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retourne l'image de l'hexagone
     * @return Image de l'hexagone
     */
    public Image getaImage() {
        return aImage;
    }

    /**
     * Détermine l'image de l'hexagone
     * @param pImage L'image de l'hexagone
     */
    public void setaImage(Image pImage) {
        this.aImage = pImage;
    }

    /**
     * Constructeur de l'hexagone juste avec un polygone et une position
     * @param pHexa Le polygone a dessiné
     * @param pPos Sa position
     */
    public Hexagone(Polygon pHexa,Position pPos){
        this(null,false,pHexa,pPos);        
    }
    
    /**
     * @return L'élément de l'hexagone
     */
    public Element getElement(){
        return this.aElement;
    }

    /**
     * Définir l'élément de l'hexagone
     * @param pE L'élément à définir
     */
    public void setElement(Element pE){
        this.aElement = pE;
    }

    /**
     * 
     * @return Si l'hexagone a un élément
     */
    public Boolean hasElement(){
        return this.aElement != null;
    }

    @Override
    public void setVisible(boolean bool){
        this.isVisible = bool;
    }

    /**
     * 
     * @return Si l'hexagone est visible
     */
    public boolean getVisible(){
        return this.isVisible;
    }

    /**
     * Définir l'hexagone
     * @param pP Le polygone a définir
     */
    public void setHexagone(Polygon pP){
        this.Hexa = pP;
    }

    /**
     * 
     * @return L'hexagone choisi
     */
    public Polygon getHexagone(){
        return this.Hexa;
    }

    /**
     * Indique si l'hexagone est contenu dans ces coordonnées
     * @param x Coordonnée en abscisse
     * @param y Coordonnée en ordonné
     * @return S'il est dedans ou non
     */
    public boolean isContain(int x, int y){
        return this.Hexa.contains(new Point(x,y));
    }

    /**
     * 
     * @return Indique si l'hexagone a la souris sur lui
     */
    public boolean getFocus() {
        return this.aFocus;
    }

    /**
     * 
     * @return La position de l'hexagone
     */
    public Position getPos() {
        return this.aPosition;
    }

    /**
    * Choisi si l'hexagone est survolé    
    * @param aFocus S'il l'est ou non
    */
    public void setFocus(boolean aFocus) {
        this.aFocus = aFocus;
    }
    
    /**
     * Choisi si l'hexagone est concerné par un clique de souris
     * @param pTarget Vrai ou faux 
     */
    public void setTarget(boolean pTarget){
        this.isTarget = pTarget;
    }

    /**
     * 
     * @return S'il est ciblé
     */
    public boolean getTarget(){
        return this.isTarget;
    }

    /**
     * Dessine l'hexagone
     * @param g Endroit où le dessiner
     */
    public void seDessiner(Graphics g){
        super.paintComponent(g);

        if(!(isVisible)){
            g.setColor(Color.darkGray);
            g.fillPolygon(this.Hexa);
        }

        else{
            Image vImage = new Image(this.aBackground, this.Hexa);

            vImage.drawHexa(g);
            if(Boolean.TRUE.equals(this.hasElement())){
                this.getElement().renderElement(g, this.Hexa);
            }

            g.setColor(Color.BLACK);
            g.drawPolygon(this.Hexa);
        }

        if(getFocus()){
            Color vWhiteOpa = new Color(1,0,0,70);
            g.setColor(vWhiteOpa);
            g.fillPolygon(this.Hexa);
        }

        if(getTarget()){
            if (this.aElement instanceof Monstre){ // Si c'est un monstre
                Color vBlueOpa = new Color(1, 250, 0, 70);
                g.setColor(vBlueOpa);
                g.fillPolygon(this.Hexa);
            }
            else{
                Color vGrayOpa = new Color(1, 0, 0, 70);
                g.setColor(vGrayOpa);
                g.fillPolygon(this.Hexa);
            }
        }
    }


}
