package wargame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Image {
    BufferedImage aBackground;
    Polygon aPoly;

    /**
     * Constructeur
     * @param pBackground L'image 
     * @param pPoly Le polygone concerné
     */
    public Image(BufferedImage pBackground, Polygon pPoly){
        this.aBackground = pBackground;
        this.aPoly = pPoly;
    }

    /**
     * Constructeur
     * @param pBackground Image à insérer
     */
    public Image(BufferedImage pBackground){
        this.aBackground = pBackground;
    }

    /**
     * Obtenir la texture de l'image
     * @param src Source de l'image
     * @param shp Polygone où insérer
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return Une bufferedImage
     */
    public static BufferedImage getTexturedImage(BufferedImage src, Polygon shp, int x, int y) {
        Rectangle r = shp.getBounds();
        // create a transparent image with 1 px padding.
        BufferedImage tmp = new BufferedImage( r.width+2,r.height+2,BufferedImage.TYPE_INT_ARGB);
        // get the graphics object
        Graphics2D g = tmp.createGraphics();
        // create a transform to center the shape in the image
        AffineTransform centerTransform = AffineTransform.getTranslateInstance((double)-r.x+1, (double)-r.y+1);
        // set the transform to the graphics object
        g.setTransform(centerTransform);
        // set the shape as the clip
        g.setClip(shp);
        // draw the image
        g.drawImage(src, x, y, null);
        // clear the clip
        g.setClip(null);
        // dispose of any graphics object we explicitly create
        g.dispose();

        return tmp;
    }

    /**
     * Dessine les hexagones avec image
     * @param g Endroit où le dessiner
     */
    public void drawHexa(Graphics g){
        BufferedImage vTmpImage = getTexturedImage(this.aBackground, this.aPoly, this.aPoly.getBounds().x,this.aPoly.getBounds().y);
        g.drawImage(vTmpImage, this.aPoly.getBounds().x,this.aPoly.getBounds().y, null);
    }
}
