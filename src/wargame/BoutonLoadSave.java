
package wargame;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JComponent;

public class BoutonLoadSave extends JComponent implements IConfig {

	private static final long serialVersionUID = 1L;
	public int widthBouton = LARGEUR_BOUTON_LOAD_SAVE;
    public int heightBouton = HAUTEUR_BOUTON_LOAD_SAVE;
    public int posX;
    public int posY;
    public int numeroBouton;
    private int widthTexte;
    private int heightTexte;
    private int midTexte;
    private String texte;
    private boolean isTarget = false;
    private boolean isFocus = false;
    private Font boutonFont;


    /**
     * Constructeur BoutonLoadSave
     * @param pX
     * @param pY
     * @param txt
     */
    public BoutonLoadSave( int pX, int pY, int i, String txt) {
        posX = pX;
        posY = pY;
        texte = txt;
        
        this.setNumero(i);
        

        try {
            InputStream isFont = this.getClass().getResourceAsStream("/resources/Font/Breathe-Fire.otf");
            boutonFont = Font.createFont(Font.TRUETYPE_FONT, isFont);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(boutonFont);
            boutonFont = boutonFont.deriveFont(TAILLE_TEXTE_LOAD_SAVE);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Donne le numero du bouton (et donc de la partie)
     * @return numeroBouton
     */
    public int getNumero() {
        return this.numeroBouton;
    }

    /**
     * D�finir le numero du bouton
     * @param i
     */
    public void setNumero(int i) {
        this.numeroBouton = i;
    }

    /**
     * Indique si le bouton est survole
     * @return isFocus 
     */
    public boolean getFocus() {
        return this.isFocus;
    }

    /**
     * Le bouton est survole
     * @param isFocus
     */
    public void setFocus(boolean isFocus) {
        this.isFocus = isFocus;
    }

    /**
     * Indique si les bouton est selectionne
     * @return isTarget
     */
    public boolean getTarget() {
        return this.isTarget;
    }

    /**
     * Le bouton est selectionne
     * @param isTarget
     */
    public void setTarget(boolean isTarget) {
        this.isTarget = isTarget;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Recuperation taille texte
		FontMetrics metrics = g.getFontMetrics(boutonFont);
		widthTexte = metrics.stringWidth(texte);
		heightTexte = metrics.getHeight()-metrics.getLeading();
		midTexte = heightTexte/6;

        this.setBounds((posX-(widthBouton/2)-3), (posY-(heightBouton/2)-3), widthBouton, heightBouton);
        g.setFont(boutonFont);

        if (getFocus()) { //Bouton survole

            //Affichage bouton
            g.setColor(BOUTON_LOAD_CONTOUR_SURVOLE);
            g.fillRoundRect( (posX-(widthBouton/2)-3) , (posY-(heightBouton/2)-3) , (widthBouton+6) , (heightBouton+6), 12, 12);
            g.setColor(BOUTON_LOAD_SURVOLE);
            g.fillRoundRect( (posX-(widthBouton/2)) , (posY-(heightBouton/2)), widthBouton, heightBouton,10,10);

            //Afficage texte
            g.setColor(TEXTE_LOAD);
            g.drawString(texte,(posX-(widthTexte/2)),(posY+midTexte));
        
        } else if (getTarget()) { //Bouton selectionne

			//Affichage des boutons
			g.setColor(BOUTON_LOAD_CONTOUR_SELECTIONNE); //Couleur Contour Bouton
			g.fillRoundRect( (posX-(widthBouton/2)-3) , (posY-(heightBouton/2)-3), (widthBouton+6), (heightBouton+6),12,12);
			g.setColor(BOUTON_LOAD_SELECTIONNE); //Couleur Bouton
			g.fillRoundRect( (posX-(widthBouton/2)) , (posY-(heightBouton/2)), widthBouton, heightBouton,10,10);
			
			//Affichage texte
			g.setColor(TEXTE_LOAD_SELECTIONNE);
			g.drawString(texte,(posX-(widthTexte/2)),(posY+midTexte));
		
		} else { //Bouton normal
			
			//Affichage des boutons
			g.setColor(BOUTON_LOAD_CONTOUR); //Couleur Contour Bouton
			g.fillRoundRect( (posX-(widthBouton/2)-3) , (posY-(heightBouton/2)-3), (widthBouton+6), (heightBouton+6),12,12);
			g.setColor(BOUTON_LOAD); //Couleur Bouton
			g.fillRoundRect( (posX-(widthBouton/2)) , (posY-(heightBouton/2)), widthBouton, heightBouton,10,10);
			
			//Affichage texte
			g.setColor(TEXTE_LOAD);
			g.drawString(texte,(posX-(widthTexte/2)),(posY+midTexte));
		}
    }
}