package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
public class InfoBulle implements IConfig{
    private int x;
    private int y;
    private Soldat aSoldat;
    private int offsetXCalculed;

    /**
     * Constructeur
     * @param pElement Le soldat concerné
     * @param pX Coordonnée en abscisse
     * @param pY Coordonnée en ordonné
     */
    public InfoBulle(Soldat pElement, int pX, int pY){
    	aSoldat = pElement;
        x = pX;
        if(pElement.getPosition().getY() == HAUTEUR_CARTE-1)
        	y = pY - HAUTEUR_INFO_BULLE;
        else
        	y = pY;
    }

    protected void paintComponent(Graphics g) {
        int outScreen; //-1 si oui
        int offsetXInfoCalculed;

        g.setColor(Color.darkGray);
        if(x + LARGEUR_INFO_BULLE > LARGEUR_FENETRE){ //Permet de ne pas faire "Sortir" l'info bulle de l'ecran
            outScreen = -1;
            offsetXInfoCalculed = OFFSET_X_INFO - LARGEUR_INFO_BULLE;
            offsetXCalculed = OFFSET_X - LARGEUR_INFO_BULLE;

        }
        else{
            outScreen = 1;
            offsetXInfoCalculed = OFFSET_X_INFO;
            offsetXCalculed = OFFSET_X;
        }
        int[] xPoly = { x, x + LARGEUR_INFO_BULLE*outScreen, x + LARGEUR_INFO_BULLE*outScreen, x };
        int[] yPoly = { y, y, y + HAUTEUR_INFO_BULLE, y + HAUTEUR_INFO_BULLE };
        Polygon vPoly = new Polygon(xPoly, yPoly, xPoly.length);
        g.fillPolygon(vPoly);

        Polygon[] vLifePolys = getPolyLife();
        if ((aSoldat.getPoints() * 100) / aSoldat.type.getPoints() < 30) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.green);
        }
        g.fillPolygon(vLifePolys[1]);
        g.setColor(Color.black);
        g.drawPolygon(vLifePolys[0]);

        g.setColor(Color.WHITE);
        g.drawString(aSoldat.toString() ,x + offsetXInfoCalculed, y + OFFSET_Y_INFO);
        g.drawString("PV ", x + offsetXInfoCalculed, y + OFFSET_Y_INFO + ECART_INFO);
        g.drawString(""+ aSoldat.getPoints() + "/" + aSoldat.getTypeSoldat().getPoints(), x + offsetXCalculed + ((LARGEUR_BARRE_PV)/2)-20 , y + OFFSET_Y_INFO + ECART_INFO);
        g.drawString("Vision :" + aSoldat.getTypeSoldat().getPortee(), x + offsetXInfoCalculed, y + OFFSET_Y_INFO + 2*ECART_INFO);
        g.drawString("Puissance Frappe : " + aSoldat.getTypeSoldat().getPuissance(), x + offsetXInfoCalculed, y +OFFSET_Y_INFO + 3*(ECART_INFO));
        g.drawString("Puissance Tir : " + aSoldat.getTypeSoldat().getTir(), x + offsetXInfoCalculed, y + OFFSET_Y_INFO + 4*(ECART_INFO));
        g.setColor(Color.black);
        g.drawPolygon(vPoly);

    }

    /**
     * @return La barre de vie
     */
    private Polygon[] getPolyLife() {
        int[] xPoly = { x + offsetXCalculed, x + offsetXCalculed + LARGEUR_BARRE_PV, x + offsetXCalculed + LARGEUR_BARRE_PV, x + offsetXCalculed };
        int[] yPoly = { y + OFFSET_Y_SUP+ ECART_INFO, y + OFFSET_Y_SUP+ ECART_INFO, y + OFFSET_Y_INF+ ECART_INFO, y + OFFSET_Y_INF+ ECART_INFO };
        Polygon vLifeFull = new Polygon(xPoly, yPoly, xPoly.length);

        int xoffset = (aSoldat.getPoints() * LARGEUR_BARRE_PV) / aSoldat.type.getPoints();

        xPoly = new int[] { x + offsetXCalculed, x + offsetXCalculed + xoffset, x + offsetXCalculed + xoffset, x + offsetXCalculed };
        yPoly = new int[] { y + OFFSET_Y_SUP+ ECART_INFO, y + OFFSET_Y_SUP+ ECART_INFO, y + OFFSET_Y_INF+ ECART_INFO, y + OFFSET_Y_INF+ ECART_INFO };
        Polygon vLife = new Polygon(xPoly, yPoly, xPoly.length);

        return new Polygon[] { vLifeFull, vLife };
    }
}